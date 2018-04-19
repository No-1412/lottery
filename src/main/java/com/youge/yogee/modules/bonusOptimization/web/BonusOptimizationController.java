package com.youge.yogee.modules.bonusOptimization.web;

import com.alibaba.fastjson.JSON;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.bonusOptimization.entity.CaipiaoBean;
import com.youge.yogee.modules.bonusOptimization.entity.DetailBean;
import com.youge.yogee.modules.bonusOptimization.entity.PortfolioChilVO;
import com.youge.yogee.modules.bonusOptimization.entity.PortfolioVO;
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import com.youge.yogee.modules.cbasketballmixed.service.CdBasketballMixedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 奖金优化
 * Created by ab on 2018/4/13.
 */
@Controller
@RequestMapping(value = "${frontPath}/bonusOptimization/bonusOptimizationController")//adminPath
public class BonusOptimizationController extends BaseController {

    @Autowired
    private CdBasketballMixedService cdBasketballMixedService;
    /**
     * 奖金优化前台请求
     * @param
     * @return
     */

    @RequestMapping(value = "bonusOpt",method = RequestMethod.POST)
    @ResponseBody
    public String bonusOpt(HttpServletRequest request){
        /*paraDate = "{\"buyWays\":\"1\",\n" +
                "\"detail\":[{\"matchId\":\"周三322\",\"hostWin\":\"\",\"hostFail\":\"\",\"size\":\"\",\"beat\":\"0/3.40,\",\"let\":\"\",\"isMust\":\"0\"},\n" +
                "{\"matchId\":\"周四303\",\"hostWin\":\"1-5/4.30,16-20/7.70,\",\"hostFail\":\"1-5/5.90,16-20/32.00,\",\"size\":\"1/1.80,0/1.69,\",\"beat\":\"0/2.92,1/1.24,\",\"let\":\"0/1.75,1/1.75,\",\"isMust\":\"0\"}],\"uid\":\"111\",\"followNum\":\"2,\",\"times\":\"5\"}";
*/

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String paraDate = JSON.toJSONString(jsonData);
        System.out.println(paraDate);
        CaipiaoBean caipiaoBean = JSON.parseObject(paraDate,CaipiaoBean.class);
        Map<String,Object> map = new HashMap<>();
        map.put("data",madeBasketball(caipiaoBean));
        return HttpResultUtil.successJson(map);
    }

    /**
     * 篮球——串关
     * @param caipiaoBean
     */
    private List<PortfolioVO> madeBasketball(CaipiaoBean caipiaoBean){
        //保存处理的原始数据
        List<List<String>> detailArr = new ArrayList<List<String>>();
        //处理原始数据，添加头部信息
        createDetail(caipiaoBean,detailArr);
        //构建二维数组
        String[][] arr = madeBeishu01(detailArr,caipiaoBean.getFollowNum());
        double[] arrBeishu = new double[arr.length];
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length-1-i; j++) {
                double odds = Double.parseDouble(arr[j][1]);
                double odds02 = Double.parseDouble(arr[j+1][1]);
                double oddsJiezhi = 0.0;
                String[] arrJiezhi = new String[2];
                if(odds>odds02){
                    oddsJiezhi = odds;
                    arrBeishu[j] = odds02;
                    arrBeishu[j+1] = oddsJiezhi;
                    arrJiezhi =arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = arrJiezhi;
                }else{
                    arrBeishu[j] =odds;
                }
                if(j==arr.length-2){
                    arrBeishu[j+1] = Double.parseDouble(arrJiezhi[1]);
                }
            }
        }
        //double[] dbjj={4.45,7.81,9.04,9.17,10.13,11.73,20.56,20.86,24.15};
        //计算倍数
        int zhushu = arrBeishu.length;//注数
        int tzje =zhushu*Integer.parseInt(caipiaoBean.getTimes())*2;
        int[] beishu = new int[arrBeishu.length];
        createBeishu(arrBeishu,tzje,zhushu,beishu);
        //
        List<PortfolioVO> portVOList =new ArrayList<PortfolioVO>();
        for (int i = 0; i < arr.length; i++) {
            PortfolioVO portfolioVO = new PortfolioVO();
            portfolioVO.setBeishu(beishu[i]);
           // String dbjj = detailArr.get(i);
            portfolioVO.setDbjj(Double.parseDouble(arr[i][1])*2);
            portfolioVO.setJiangjin(portfolioVO.getDbjj() * portfolioVO.getBeishu());
            List<PortfolioChilVO> portChilVOList = new ArrayList<PortfolioChilVO>();
            String portStr = arr[i][0];
            String[] arrStr = portStr.split("\\+");
            for (int j = 0; j < arrStr.length; j++) {
                PortfolioChilVO portChilVO = new PortfolioChilVO();
                String[] arrStr02 = arrStr[j].split("\\/");
                portChilVO.setName(arrStr02[0]);
                portChilVO.setSf(arrStr02[1]);
                portChilVO.setOdds(arrStr02[2]);
                portChilVO.setMatchId(arrStr02[3]);
                portChilVO.setBuyWays(arrStr02[4]);
                portChilVOList.add(portChilVO);
            }
            portfolioVO.setPortfolioChilVOList(portChilVOList);
            portVOList.add(portfolioVO);
        }
        return  portVOList;
    }

    /**
     * 根据串关方式，构建二维数组
     * 第一维数组：有多少概率组合就有多长
     * 第二维数组：长度为2，[0]--AA/主1-5/4.30+BB/主5-1/12.30+...+n(n串1)
     * [1]---4.30*12.30*...*n
     * @param detailArr
     * @param followNum
     * @return
     */
    private String[][] madeBeishu01(List<List<String>> detailArr,String followNum){
        String[][] arr = new String[0][2];
        if("2".equals(followNum.substring(0,followNum.length()-1))){//2串1
            //最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size()-1; i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //拆分第一个小数组中的数据
                    String[] list01Str01 = list01Str.split(",");
                    //再循环最外层，从第二个开始
                    for (int k = i+1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            //拆分第二个小数组中的数据
                            String[] list02Str02 = list02Str.split(",");
                            String[] arr01 = new String[2];
                            for (int m = 0; m < list01Str01.length; m++) {
                                arr01[0] = list01Str01[m]+"+"+list02Str02[m];
                                //保留小数点后两位，单倍奖金
                                arr01[1] = String.format("%.2f",Double.parseDouble(list01Str01[m].split("\\/")[2])
                                        *Double.parseDouble(list02Str02[m].split("\\/")[2]));
                            }
                            arr = Arrays.copyOf(arr, arr.length + 1);
                            arr[arr.length-1] = arr01;
                        }
                    }
                }
            }

        }else if("3".equals(followNum.substring(0,followNum.length()-1))){
    //最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size()-1; i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //拆分第一个小数组中的数据
                    String[] list01Str01 = list01Str.split(",");
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            //拆分第二个小数组中的数据
                            String[] list02Str02 = list02Str.split(",");
                            for (int m3 = i+k+1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    String[] list03Str03 = list03Str.split(",");

                                    String[] arr01 = new String[2];
                                    for (int m = 0; m < list01Str01.length; m++) {
                                        arr01[0] = list01Str01[m] + "+" + list02Str02[m] + "+" + list03Str03[m];
                                        //保留小数点后两位，单倍奖金
                                        arr01[1] = String.format("%.2f", Double.parseDouble(list01Str01[m].split("\\/")[2])
                                                * Double.parseDouble(list02Str02[m].split("\\/")[2])
                                                *Double.parseDouble(list03Str03[m].split("\\/")[2]));
                                    }
                                    arr = Arrays.copyOf(arr, arr.length + 1);
                                    arr[arr.length - 1] = arr01;
                                }
                            }

                        }
                    }
                }
            }
        }else if("4".equals(followNum.substring(0,followNum.length()-1))){
//最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size()-1; i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //拆分第一个小数组中的数据
                    String[] list01Str01 = list01Str.split(",");
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            //拆分第二个小数组中的数据
                            String[] list02Str02 = list02Str.split(",");
                            for (int m3 = i+k+1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    String[] list03Str03 = list03Str.split(",");
                                    for (int m4 = i+m3+1; m4 < detailArr.size(); m4++) {
                                        List<String> list04 = detailArr.get(m4);
                                        for (int l4 = 0; l4 < list04.size(); l4++) {
                                            String list04Str = list04.get(l4);
                                            String[] list04Str04 = list04Str.split(",");

                                            String[] arr01 = new String[2];
                                            for (int m = 0; m < list01Str01.length; m++) {
                                                arr01[0] = list01Str01[m] + "+" + list02Str02[m] +
                                                        "+" + list03Str03[m] + "+" + list04Str04[m];
                                                //保留小数点后两位，单倍奖金
                                                arr01[1] = String.format("%.2f", Double.parseDouble(list01Str01[m].split("\\/")[2])
                                                        * Double.parseDouble(list02Str02[m].split("\\/")[2])
                                                        *Double.parseDouble(list03Str03[m].split("\\/")[2])
                                                        *Double.parseDouble(list04Str04[m].split("\\/")[2]));
                                            }
                                            arr = Arrays.copyOf(arr, arr.length + 1);
                                            arr[arr.length - 1] = arr01;
                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }else if("5".equals(followNum.substring(0,followNum.length()-1))){
//最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size()-1; i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //拆分第一个小数组中的数据
                    String[] list01Str01 = list01Str.split(",");
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            //拆分第二个小数组中的数据
                            String[] list02Str02 = list02Str.split(",");
                            for (int m3 = i+k+1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    String[] list03Str03 = list03Str.split(",");
                                    for (int m4 = i+m3+1; m4 < detailArr.size(); m4++) {
                                        List<String> list04 = detailArr.get(m4);
                                        for (int l4 = 0; l4 < list04.size(); l4++) {
                                            String list04Str = list04.get(l4);
                                            String[] list04Str04 = list04Str.split(",");
                                            for (int m5 = i+m4+1; m5 < detailArr.size(); m5++) {
                                                List<String> list05 = detailArr.get(m5);
                                                for (int l5 = 0; l5 < list05.size(); l5++) {
                                                    String list05Str = list05.get(l5);
                                                    String[] list05Str05 = list05Str.split(",");

                                                    String[] arr01 = new String[2];
                                                    for (int m = 0; m < list01Str01.length; m++) {
                                                        arr01[0] = list01Str01[m] + "+" + list02Str02[m] +
                                                                "+" + list03Str03[m] + "+" + list04Str04[m]+
                                                                "+" + list05Str05[m] ;
                                                        //保留小数点后两位，单倍奖金
                                                        arr01[1] = String.format("%.2f", Double.parseDouble(list01Str01[m].split("\\/")[2])
                                                                * Double.parseDouble(list02Str02[m].split("\\/")[2])
                                                                * Double.parseDouble(list03Str03[m].split("\\/")[2])
                                                                * Double.parseDouble(list04Str04[m].split("\\/")[2])
                                                                * Double.parseDouble(list05Str05[m].split("\\/")[2]));
                                                    }
                                                    arr = Arrays.copyOf(arr, arr.length + 1);
                                                    arr[arr.length - 1] = arr01;
                                                }
                                            }

                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }else if("6".equals(followNum.substring(0,followNum.length()-1))){
//最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size()-1; i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //拆分第一个小数组中的数据
                    String[] list01Str01 = list01Str.split(",");
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            //拆分第二个小数组中的数据
                            String[] list02Str02 = list02Str.split(",");
                            for (int m3 = i+k+1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    String[] list03Str03 = list03Str.split(",");
                                    for (int m4 = i+m3+1; m4 < detailArr.size(); m4++) {
                                        List<String> list04 = detailArr.get(m4);
                                        for (int l4 = 0; l4 < list04.size(); l4++) {
                                            String list04Str = list04.get(l4);
                                            String[] list04Str04 = list04Str.split(",");
                                            for (int m5 = i+m4+1; m5 < detailArr.size(); m5++) {
                                                List<String> list05 = detailArr.get(m5);
                                                for (int l5 = 0; l5 < list05.size(); l5++) {
                                                    String list05Str = list05.get(l5);
                                                    String[] list05Str05 = list05Str.split(",");
                                                    for (int m6 = i+m5+1; m6 < detailArr.size(); m6++) {
                                                        List<String> list06 = detailArr.get(m6);
                                                        for (int l6 = 0; l6 < list06.size(); l6++) {
                                                            String list06Str = list06.get(l6);
                                                            String[] list06Str06 = list06Str.split(",");

                                                            String[] arr01 = new String[2];
                                                            for (int m = 0; m < list01Str01.length; m++) {
                                                                arr01[0] = list01Str01[m] + "+" + list02Str02[m] +
                                                                        "+" + list03Str03[m] + "+" + list04Str04[m]+
                                                                        "+" + list05Str05[m] + "+" + list06Str06[m] ;
                                                                //保留小数点后两位，单倍奖金
                                                                arr01[1] = String.format("%.2f", Double.parseDouble(list01Str01[m].split("\\/")[2])
                                                                        * Double.parseDouble(list02Str02[m].split("\\/")[2])
                                                                        * Double.parseDouble(list03Str03[m].split("\\/")[2])
                                                                        * Double.parseDouble(list04Str04[m].split("\\/")[2])
                                                                        * Double.parseDouble(list05Str05[m].split("\\/")[2])
                                                                        * Double.parseDouble(list06Str06[m].split("\\/")[2]));
                                                            }
                                                            arr = Arrays.copyOf(arr, arr.length + 1);
                                                            arr[arr.length - 1] = arr01;
                                                        }
                                                        }
                                                    }

                                            }

                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }else if("7".equals(followNum.substring(0,followNum.length()-1))){
//最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size()-1; i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //拆分第一个小数组中的数据
                    String[] list01Str01 = list01Str.split(",");
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            //拆分第二个小数组中的数据
                            String[] list02Str02 = list02Str.split(",");
                            for (int m3 = i+k+1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    String[] list03Str03 = list03Str.split(",");
                                    for (int m4 = i+m3+1; m4 < detailArr.size(); m4++) {
                                        List<String> list04 = detailArr.get(m4);
                                        for (int l4 = 0; l4 < list04.size(); l4++) {
                                            String list04Str = list04.get(l4);
                                            String[] list04Str04 = list04Str.split(",");
                                            for (int m5 = i+m4+1; m5 < detailArr.size(); m5++) {
                                                List<String> list05 = detailArr.get(m5);
                                                for (int l5 = 0; l5 < list05.size(); l5++) {
                                                    String list05Str = list05.get(l5);
                                                    String[] list05Str05 = list05Str.split(",");
                                                    for (int m6 = i+m5+1; m6 < detailArr.size(); m6++) {
                                                        List<String> list06 = detailArr.get(m6);
                                                        for (int l6 = 0; l6 < list06.size(); l6++) {
                                                            String list06Str = list06.get(l6);
                                                            String[] list06Str06 = list06Str.split(",");
                                                            for (int m7 = i+m6+1; m7 < detailArr.size(); m7++) {
                                                                List<String> list07 = detailArr.get(m7);
                                                                for (int l7 = 0; l7 < list07.size(); l7++) {
                                                                    String list07Str = list07.get(l7);
                                                                    String[] list07Str07 = list07Str.split(",");

                                                                    String[] arr01 = new String[2];
                                                                    for (int m = 0; m < list01Str01.length; m++) {
                                                                        arr01[0] = list01Str01[m] + "+" + list02Str02[m] +
                                                                                "+" + list03Str03[m] + "+" + list04Str04[m]+
                                                                                "+" + list05Str05[m] + "+" + list06Str06[m]+
                                                                                "+" + list07Str07[m];
                                                                        //保留小数点后两位，单倍奖金
                                                                        arr01[1] = String.format("%.2f", Double.parseDouble(list01Str01[m].split("\\/")[2])
                                                                                * Double.parseDouble(list02Str02[m].split("\\/")[2])
                                                                                * Double.parseDouble(list03Str03[m].split("\\/")[2])
                                                                                * Double.parseDouble(list04Str04[m].split("\\/")[2])
                                                                                * Double.parseDouble(list05Str05[m].split("\\/")[2])
                                                                                * Double.parseDouble(list06Str06[m].split("\\/")[2])
                                                                                * Double.parseDouble(list07Str07[m].split("\\/")[2]));
                                                                    }
                                                                    arr = Arrays.copyOf(arr, arr.length + 1);
                                                                    arr[arr.length - 1] = arr01;
                                                                }
                                                            }

                                                        }
                                                    }
                                                }

                                            }

                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }else if("8".equals(followNum.substring(0,followNum.length()-1))){
//最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size()-1; i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //拆分第一个小数组中的数据
                    String[] list01Str01 = list01Str.split(",");
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            //拆分第二个小数组中的数据
                            String[] list02Str02 = list02Str.split(",");
                            for (int m3 = i+k+1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    String[] list03Str03 = list03Str.split(",");
                                    for (int m4 = i+m3+1; m4 < detailArr.size(); m4++) {
                                        List<String> list04 = detailArr.get(m4);
                                        for (int l4 = 0; l4 < list04.size(); l4++) {
                                            String list04Str = list04.get(l4);
                                            String[] list04Str04 = list04Str.split(",");
                                            for (int m5 = i+m4+1; m5 < detailArr.size(); m5++) {
                                                List<String> list05 = detailArr.get(m5);
                                                for (int l5 = 0; l5 < list05.size(); l5++) {
                                                    String list05Str = list05.get(l5);
                                                    String[] list05Str05 = list05Str.split(",");
                                                    for (int m6 = i+m5+1; m6 < detailArr.size(); m6++) {
                                                        List<String> list06 = detailArr.get(m6);
                                                        for (int l6 = 0; l6 < list06.size(); l6++) {
                                                            String list06Str = list06.get(l6);
                                                            String[] list06Str06 = list06Str.split(",");
                                                            for (int m7 = i+m6+1; m7 < detailArr.size(); m7++) {
                                                                List<String> list07 = detailArr.get(m7);
                                                                for (int l7 = 0; l7 < list07.size(); l7++) {
                                                                    String list07Str = list07.get(l7);
                                                                    String[] list07Str07 = list07Str.split(",");
                                                                    for (int m8 = i+m7+1; m8 < detailArr.size(); m8++) {
                                                                        List<String> list08 = detailArr.get(m8);
                                                                        for (int l8 = 0; l8 < list08.size(); l8++) {
                                                                            String list08Str = list08.get(l8);
                                                                            String[] list08Str08 = list08Str.split(",");

                                                                            String[] arr01 = new String[2];
                                                                            for (int m = 0; m < list01Str01.length; m++) {
                                                                                arr01[0] = list01Str01[m] + "+" + list02Str02[m] +
                                                                                        "+" + list03Str03[m] + "+" + list04Str04[m]+
                                                                                        "+" + list05Str05[m] + "+" + list06Str06[m]+
                                                                                        "+" + list07Str07[m]+ "+" + list08Str08[m];
                                                                                //保留小数点后两位，单倍奖金
                                                                                arr01[1] = String.format("%.2f", Double.parseDouble(list01Str01[m].split("\\/")[2])
                                                                                        * Double.parseDouble(list02Str02[m].split("\\/")[2])
                                                                                        * Double.parseDouble(list03Str03[m].split("\\/")[2])
                                                                                        * Double.parseDouble(list04Str04[m].split("\\/")[2])
                                                                                        * Double.parseDouble(list05Str05[m].split("\\/")[2])
                                                                                        * Double.parseDouble(list06Str06[m].split("\\/")[2])
                                                                                        * Double.parseDouble(list07Str07[m].split("\\/")[2])
                                                                                        * Double.parseDouble(list08Str08[m].split("\\/")[2]));
                                                                            }
                                                                            arr = Arrays.copyOf(arr, arr.length + 1);
                                                                            arr[arr.length - 1] = arr01;
                                                                        }
                                                                    }

                                                                }
                                                            }

                                                        }
                                                    }
                                                }

                                            }

                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }

        }

        return arr;
    }
    /**
     * 处理原始数据，添加头部信息
     * @param caipiaoBean
     * @param detailArr
     */
    private void createDetail(CaipiaoBean caipiaoBean,List<List<String>> detailArr){
        for (int j=0;j<caipiaoBean.getDetail().size();j++) {
            DetailBean detailBean = caipiaoBean.getDetail().get(j);
            String matchId = detailBean.getMatchId();
            List<String> detailStr = new ArrayList<String>();
            CdBasketballMixed cdBasketballMixed = cdBasketballMixedService.findByMatchId(matchId);
            //主队名字
            String heart = cdBasketballMixed.getWinningName()+"/";
            //主胜处理
            if(StringUtils.isNotEmpty(detailBean.getHostWin())){
                String[] hostWin = detailBean.getHostWin().substring(0,detailBean.getHostWin().length()-1).split(",");
                for (int i = 0; i < hostWin.length; i++) {
                    //TODO
                    detailStr.add(heart+"主"+hostWin[i]+"/"+matchId+"/hostWin");
                }
            }
            //主负处理
            if(StringUtils.isNotEmpty(detailBean.getHostFail())){
                String[] hostFail = detailBean.getHostFail().substring(0,detailBean.getHostFail().length()-1).split(",");
                for (int i = 0; i < hostFail.length; i++) {
                    //TODO
                    detailStr.add(heart+"客"+hostFail[i]+"/"+matchId+"/hostFail");
                }
            }
            //大小分处理
            if(StringUtils.isNotEmpty(detailBean.getSize())){
                String[] size = detailBean.getSize().substring(0,detailBean.getSize().length()-1).split(",");
                for (int i = 0; i < size.length; i++) {
                    //TODO
                    if(size[i].startsWith("1")){//大
                        detailStr.add(size[i].replaceFirst("1", heart +"大分")+"/"+matchId+"/size");
                    }else{
                        detailStr.add(size[i].replaceFirst("0", heart +"小分")+"/"+matchId+"/size");
                    }
                }
            }
            //胜负处理
            if(StringUtils.isNotEmpty(detailBean.getSf())){
                String[] beat = detailBean.getSf().substring(0,detailBean.getSf().length()-1).split(",");
                for (int i = 0; i < beat.length; i++) {
                    //TODO
                    if(beat[i].startsWith("1")){//胜
                        detailStr.add(beat[i].replaceFirst("1", heart +"主胜")+"/"+matchId+"/beat");
                    }else {
                        detailStr.add(beat[i].replaceFirst("0", heart +"主负")+"/"+matchId+"/beat");
                    }
                }
            }
            //让分胜负处理
            if(StringUtils.isNotEmpty(detailBean.getLet())){
                String[] let = detailBean.getLet().substring(0, detailBean.getLet().length() - 1).split(",");
                for (int i = 0; i < let.length; i++) {
                    //TODO
                    if(let[i].startsWith("1")){//胜
                        detailStr.add(let[i].replaceFirst("1", heart + "让胜")+"/"+matchId+"/let");
                    }else{
                        detailStr.add(let[i].replaceFirst("0", heart +"让负")+"/"+matchId+"/let");
                    }
                }
            }
            detailArr.add(detailStr);
        }
    }

    /**
     * 计算各赔率的倍数，递归计算
     * @param oddsArr 各赔率数组
     * @param amountBets 投注金额
     * @param noteNumber  投注数
     * @param retBeishu  结果倍数数组
     */
    private void createBeishu(double[] oddsArr,int amountBets,int noteNumber,int[] retBeishu){
        int multiple = amountBets/2-noteNumber+1;//最大倍数
        double average = 0.0;//平均奖金
        double sumOdds=0.0;
        for (int i = 0; i < oddsArr.length; i++) {
            sumOdds+=oddsArr[i];
        }
        //平均奖金
        average = sumOdds/noteNumber*(amountBets/2)/noteNumber;
        //计算倍数
        int calMultiple = calMultiple(oddsArr,multiple,average);
        System.out.println(average);
        retBeishu[oddsArr.length-1] = calMultiple;
        if(oddsArr.length>2){
            double[] oddsArr2 = new double[oddsArr.length-1];
            System.arraycopy(oddsArr,0,oddsArr2,0,oddsArr2.length);
            amountBets = amountBets-calMultiple*2;
            noteNumber = noteNumber-1;
            createBeishu(oddsArr2,amountBets,noteNumber,retBeishu);
        }else {
            retBeishu[0] = amountBets/2-calMultiple;
        }
    }

    /**
     *计算倍数
     * @param oddsArr  //单倍奖金数组
     * @param multiple  //最大倍数
     * @param average  //平均奖金
     * @return
     */
    private int calMultiple(double[] oddsArr,int multiple,double average){
        double[] oddsMul=new double[multiple];//单注最大倍数数组
        for (int i = 0; i < multiple; i++) {
            oddsMul[i] = oddsArr[oddsArr.length-1]*(i+1);
        }
        int ii=1;//记录最大赔率倍数值
        /**
         * 计算最接近平均奖金的奖金，
         * 并获取下标+1，即为倍数
         */
        double  deviation=oddsMul[0]-average>=0?oddsMul[0]-average:average-oddsMul[0];
        for (int i = 0; i < multiple; i++) {
            //
            double nextDeviation = oddsMul[i]-average>=0?oddsMul[i]-average:average-oddsMul[i];
            if(nextDeviation< deviation){
                deviation = nextDeviation;
                System.out.println(i+1);
                ii =  i+1;

            }
        }

        return ii;
    }
}
