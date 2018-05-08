package com.youge.yogee.modules.cdoptionpass.web;

import com.alibaba.fastjson.JSON;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;
import com.youge.yogee.modules.cdoptionpass.entity.Caipiao;
import com.youge.yogee.modules.cdoptionpass.entity.CdOptionPassVo;
import com.youge.yogee.modules.cdoptionpass.entity.SaiShi;
import com.youge.yogee.modules.cdoptionpass.service.CdOptionPassService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 彩票打印通用controller
 * @author yhw
 * Created by ab on 2018/4/4.
 * adminPath
 */
@Controller
@RequestMapping(value = "${adminPath}/cdoptionpass/cdOptionPassController")
public class CdOptionPassController extends BaseController{

    @Autowired
    private CdOptionPassService cdOptionPassService;

    @Autowired
    private CdFootballSingleOrderService cdFootballSingleOrderService;
    @Autowired
    private CdFootballFollowOrderService cdFootballFollowOrderService;

    @Autowired
    private CdBasketballSingleOrderService cdBasketballSingleOrderService;
    @Autowired
    private CdBasketballFollowOrderService cdBasketballFollowOrderService;

    /**
     * 跳转打印模板页面
     * @param orderNumber
     * @return
     */
    @RequestMapping(value = "gotoTemp")
    public String gotoTemp(@RequestParam(required = false) String orderNumber){
        String idThree = orderNumber.substring(0, 3);
        String retStr = null;
        String optionNumber = null;
        if ("ZDG".equals(idThree)) {
            CdFootballSingleOrder cdFootballSingleOrder = cdFootballSingleOrderService.findOrderByOrderNum(orderNumber);

            String buy_ways = cdFootballSingleOrder.getBuyWays();
            String match_ids = cdFootballSingleOrder.getMatchIds().substring(0,cdFootballSingleOrder.getMatchIds().length()-1);
            //System.out.println(buy_ways);
            if("1".equals(buy_ways)){//混投

            }else if("2".equals(buy_ways)){
                if(match_ids.split(",").length==3){//足彩_胜负平3关
                    optionNumber = "100011001110";
                }else if(match_ids.split(",").length==6){//足彩_胜负平6关
                    optionNumber = "100011001110";
                }else if(match_ids.split(",").length==8){//足彩_胜负平8关
                    optionNumber = "100011001110";
                }
            }else if("3".equals(buy_ways)){
                if(match_ids.split(",").length==3){//足彩_比分3关
                    optionNumber = "010000110101";
                }else if(match_ids.split(",").length==6){//足彩_比分6关
                    optionNumber = "010000110101";
                }else if(match_ids.split(",").length==8){//足彩_比分8关
                    optionNumber = "010000110101";
                }
            }else if("4".equals(buy_ways)){
                if(match_ids.split(",").length==3){//足球_总进球3关
                    optionNumber = "110010011110";
                }else if(match_ids.split(",").length==6){//足球_总进球6关
                    optionNumber = "000111000011";
                }else if(match_ids.split(",").length==8){//足球_总进球8关
                    optionNumber = "000111000011";
                }
            }else if("5".equals(buy_ways)){
                if(match_ids.split(",").length==3){//足彩_半全场3关
                    optionNumber = "010011000001";
                }else if(match_ids.split(",").length==6){//足彩_半全场6关
                    optionNumber = "010011000001";
                }else if(match_ids.split(",").length==8){//足彩_半全场8关
                    optionNumber = "010011000001";
                }
            }else if("6".equals(buy_ways)){
                if(match_ids.split(",").length==3){//足彩_让球胜负平3关
                    optionNumber = "100011001110";
                }else if(match_ids.split(",").length==6){//足彩_让球胜负平6关
                    optionNumber = "100011001110";
                }else if(match_ids.split(",").length==8){//足彩_让球胜负平8关
                    optionNumber = "100011001110";
                }
            }
        }else if ("ZCG".equals(idThree)) {
            CdFootballFollowOrder cdFootballFollowOrder = cdFootballFollowOrderService.findOrderByOrderNum(orderNumber);
            String buy_ways = cdFootballFollowOrder.getBuyWays();
            String match_ids = cdFootballFollowOrder.getDanMatchIds().substring(0, cdFootballFollowOrder.getDanMatchIds().length()-1);
            //System.out.println(buy_ways);
            if("1".equals(buy_ways)){//混投

            }else if("2".equals(buy_ways)){
                if(match_ids.split(",").length==3){//足彩_胜负平3关
                    optionNumber = "100011001110";
                }else if(match_ids.split(",").length==6){//足彩_胜负平6关
                    optionNumber = "100011001110";
                }else if(match_ids.split(",").length==8){//足彩_胜负平8关
                    optionNumber = "100011001110";
                }
            }else if("3".equals(buy_ways)){
                if(match_ids.split(",").length==3){//足彩_让球胜负平3关
                    optionNumber = "100011001110";
                }else if(match_ids.split(",").length==6){//足彩_让球胜负平6关
                    optionNumber = "100011001110";
                }else if(match_ids.split(",").length==8){//足彩_让球胜负平8关
                    optionNumber = "100011001110";
                }
            }else if("4".equals(buy_ways)){
                if(match_ids.split(",").length==3){//足彩_比分3关
                    optionNumber = "010000110101";
                }else if(match_ids.split(",").length==6){//足彩_比分6关
                    optionNumber = "010000110101";
                }else if(match_ids.split(",").length==8){//足彩_比分8关
                    optionNumber = "010000110101";
                }
            }else if("5".equals(buy_ways)){
                if(match_ids.split(",").length==3){//足球_总进球3关
                    optionNumber = "110010011110";
                }else if(match_ids.split(",").length==6){//足球_总进球6关
                    optionNumber = "000111000011";
                }else if(match_ids.split(",").length==8){//足球_总进球8关
                    optionNumber = "000111000011";
                }
            }else if("6".equals(buy_ways)){
                if(match_ids.split(",").length==3){//足彩_半全场3关
                    optionNumber = "010011000001";
                }else if(match_ids.split(",").length==6){//足彩_半全场6关
                    optionNumber = "010011000001";
                }else if(match_ids.split(",").length==8){//足彩_半全场8关
                    optionNumber = "010011000001";
                }
            }
        }else if ("RXJ".equals(idThree)) {

        }else if ("SFC".equals(idThree)) {

        }else if ("LDG".equals(idThree)) {

        }else if ("LCG".equals(idThree)) {

        }else if ("PLS".equals(idThree)) {

        }else if ("PLW".equals(idThree)) {

        }else if ("DLT".equals(idThree)) {

        }
        return retStr;
    }

    @ResponseBody
    @RequestMapping(value = "printAll")
    public String printAll(@RequestParam(required = false) String orderNumber,@RequestParam(required = false) String optionNumber) {
        String idThree = orderNumber.substring(0, 3);
        String retStr = null;
        if ("ZDG".equals(idThree)) {
            retStr = ZDGPrint(orderNumber,optionNumber);
        }else if ("ZCG".equals(idThree)) {
            retStr = ZCGPrint(orderNumber,optionNumber);
        }else if ("RXJ".equals(idThree)) {

        }else if ("SFC".equals(idThree)) {

        }else if ("LDG".equals(idThree)) {
            retStr = LDGPrint(orderNumber,optionNumber);
        }else if ("LCG".equals(idThree)) {
            retStr = LCGPrint(orderNumber,optionNumber);
        }else if ("PLS".equals(idThree)) {

        }else if ("PLW".equals(idThree)) {

        }else if ("DLT".equals(idThree)) {

        }
        return retStr;
    }

    /**
     * 组装数组数据_串关
     * @param orderNumber
     * @return
     */
    private String ZCGPrint(String orderNumber,String optionNumber){
        CdFootballFollowOrder cdFootballFollowOrder = cdFootballFollowOrderService.findOrderByOrderNum(orderNumber);
        //String optionNumber = null;
        String buy_ways = cdFootballFollowOrder.getBuyWays();
        //String match_ids = cdFootballFollowOrder.getDanMatchIds().substring(0, cdFootballFollowOrder.getDanMatchIds().length()-1);
       // System.out.println(buy_ways);

        if(StringUtils.isNotEmpty(optionNumber)){
            CdOptionPassVo cdOptionPassVo = cdOptionPassService.findByNumber(optionNumber);
            String paraStr = "";
            String score = cdFootballFollowOrder.getScore();
            String goal = cdFootballFollowOrder.getGoal();
            String half = cdFootballFollowOrder.getHalf();
            String beat = cdFootballFollowOrder.getBeat();
            String let = cdFootballFollowOrder.getLet();
            //处理五个数据
            paraStr = judge(buy_ways,score,goal,half,beat,let);
            Caipiao caipiao=null;
            if (StringUtils.isNotEmpty(paraStr)) {
                String[] testP = cdFootballFollowOrder.getFollowNum().substring(0,cdFootballFollowOrder.getFollowNum().length()-1).split(",");
                String[] followOrder = new String[testP.length];
                for (int i = 0; i < testP.length; i++) {//数据库暂时只有n串1模式
                    followOrder[i] = testP[i]+"*1";
                }
                String[] pass =cdOptionPassVo.getPass().split(",");
                String[] dict =cdOptionPassVo.getOption().split(",");//胜，平，负，-1为补位
                //组装数据
                caipiao = print(paraStr,followOrder,pass,dict,Integer.parseInt(cdFootballFollowOrder.getTimes()));
                //过关类型
                if("1".equals(cdFootballFollowOrder.getBuyWays())){
                    if(cdFootballFollowOrder.getDanMatchIds().substring(0,cdFootballFollowOrder.getDanMatchIds().length()-1).split(",").length==1){
                        caipiao.getLeixing()[3]=1;//单场
                    }else {
                        caipiao.getLeixing()[8] = 1;//混合过关
                    }
                }
            }
           // System.out.println(JSON.toJSONString(caipiao));
            return JSON.toJSONString(caipiao);
        }else{
            return null;
        }
    }
    /**
     * 组装数组数据_单关
     * @param orderNumber
     * @return
     */
    private String ZDGPrint(String orderNumber,String optionNumber){
        CdFootballSingleOrder cdFootballSingleOrder = cdFootballSingleOrderService.findOrderByOrderNum(orderNumber);
        String buy_ways = cdFootballSingleOrder.getBuyWays();
        //System.out.println(buy_ways);

        if(StringUtils.isNotEmpty(optionNumber)){
            CdOptionPassVo cdOptionPassVo = cdOptionPassService.findByNumber(optionNumber);
            String paraStr = "";
            String score = cdFootballSingleOrder.getScore();
            String goal = cdFootballSingleOrder.getGoal();
            String half = cdFootballSingleOrder.getHalf();
            String beat = cdFootballSingleOrder.getBeat();
            String let = cdFootballSingleOrder.getLet();
            //处理五个数据
            paraStr = judge(buy_ways,score,goal,half,beat,let);
            Caipiao caipiao=null;
            if (StringUtils.isNotEmpty(paraStr)) {
                String[] testP = {"单场"};
                String[] pass =cdOptionPassVo.getPass().split(",");
                String[] dict =cdOptionPassVo.getOption().split(",");//胜，平，负，-1为补位
                caipiao = print(paraStr,testP,pass,dict,0);
                //过关类型
                if("1".equals(cdFootballSingleOrder.getBuyWays())){
                    int ll = cdFootballSingleOrder.getMatchIds().substring(0,cdFootballSingleOrder.getMatchIds().length()-1).split(",").length;
                    if(ll==1){
                        caipiao.getLeixing()[3]=1;//单场
                    }else {
                        caipiao.getLeixing()[5] = 1;//一般过关
                    }
                }
            }
            //System.out.println(JSON.toJSONString(caipiao));
            return JSON.toJSONString(caipiao);
        }else{
            return null;
        }
    }

    /**
     * 篮彩单关
     * @param orderNumber
     * @param optionNumber
     * @return
     */
    private String LDGPrint(String orderNumber,String optionNumber){
        CdBasketballSingleOrder cdBasketballSingleOrder = cdBasketballSingleOrderService.findOrderByOrderNum(orderNumber);
       // String optionNumber = "1010101011010010";//默认篮球_胜负3关
        String buy_ways = cdBasketballSingleOrder.getBuyWays();
        String match_ids = cdBasketballSingleOrder.getMatchIds().substring(0,cdBasketballSingleOrder.getMatchIds().length()-1);
       // System.out.println(buy_ways);
        if(StringUtils.isNotEmpty(optionNumber)){
            CdOptionPassVo cdOptionPassVo = cdOptionPassService.findByNumber(optionNumber);
            String paraStr = "";
            String hostFail = cdBasketballSingleOrder.getHostFail();
            String hostFailStr = "";
            if(StringUtils.isNotEmpty(hostFail)){
                String[] hf01 = hostFail.split("\\|");
                for (int i = 0; i < hf01.length; i++) {
                    String[] hf02 = hf01[i].split("\\+");
                    String[] hf03 = hf02[2].split(",");
                    for (int k = 0; k < hf03.length; k++) {
                        if(StringUtils.isNotEmpty(hf03[k])){
                           // String[] hf04 = hf03[k].split("\\/");
                            //hf03[k] = "K"+hf03[k];
                            hf01[i] = hf01[i].replace(hf03[k],"K"+hf03[k]);
                            //System.out.println(hf01[i].toString());
                        }
                    }
                    hostFailStr+=hf01[i].toString()+"|";
                }
                //System.out.println("hostFail:"+hostFailStr);
            }
            String hostWin = cdBasketballSingleOrder.getHostWin();
            //主负     周四307+骑士vs奇才+6-10/6.45/1,21-25/50.00/1,|
            //主胜     周四307+骑士vs奇才+16-20/8.50/1,|
            //处理五个数据
            paraStr = judgeBasket(buy_ways,hostFailStr,hostWin,"","","");
            Caipiao caipiao=null;
            if (StringUtils.isNotEmpty(paraStr)) {
                String[] testP = {"单场"};
                String[] pass =cdOptionPassVo.getPass().split(",");
                String[] dict =cdOptionPassVo.getOption().split(",");//胜，平，负，-1为补位
                caipiao = print(paraStr,testP,pass,dict,0);
                //过关类型
                if("1".equals(cdBasketballSingleOrder.getBuyWays())){
                    if(cdBasketballSingleOrder.getMatchIds().substring(0,cdBasketballSingleOrder.getMatchIds().length()-1).split(",").length==1){
                        caipiao.getLeixing()[3]=1;//单场
                    }else {
                        caipiao.getLeixing()[5] = 1;//一般过关
                    }
                }
            }
            //System.out.println(JSON.toJSONString(caipiao));
            return JSON.toJSONString(caipiao);
        }else{
            return null;
        }
    }

    /**
     * 篮彩串关
     * @param orderNumber
     * @param optionNumber
     * @return
     */
    private String LCGPrint(String orderNumber,String optionNumber){
        CdBasketballFollowOrder cdBasketballFollowOrder = cdBasketballFollowOrderService.findOrderByOrderNum(orderNumber);
        //String optionNumber = "1010101011010010";//默认篮球_胜负3关
        String buy_ways = cdBasketballFollowOrder.getBuyWays();
        String match_ids = cdBasketballFollowOrder.getDanMatchIds().substring(0,cdBasketballFollowOrder.getDanMatchIds().length()-1);
       // System.out.println(buy_ways);
        if(StringUtils.isNotEmpty(optionNumber)){
            CdOptionPassVo cdOptionPassVo = cdOptionPassService.findByNumber(optionNumber);
            String paraStr = "";
            String hostFail = cdBasketballFollowOrder.getHostFail();
            String hostFailStr = "";
            if(StringUtils.isNotEmpty(hostFail)){
                String[] hf01 = hostFail.split("\\|");
                for (int i = 0; i < hf01.length; i++) {
                    String[] hf02 = hf01[i].split("\\+");
                    String[] hf03 = hf02[2].split(",");
                    for (int k = 0; k < hf03.length; k++) {
                        if(StringUtils.isNotEmpty(hf03[k])){
                            // String[] hf04 = hf03[k].split("\\/");
                            //hf03[k] = "K"+hf03[k];
                            hf01[i] = hf01[i].replace(hf03[k],"K"+hf03[k]);
                           // System.out.println(hf01[i].toString());
                        }
                    }
                    hostFailStr+=hf01[i].toString()+"|";
                }
                //System.out.println("hostFail:"+hostFailStr);
            }
            String hostWin = cdBasketballFollowOrder.getHostWin();
            //主负     周四307+骑士vs奇才+6-10/6.45/1,21-25/50.00/1,|
            //主胜     周四307+骑士vs奇才+16-20/8.50/1,|
            //处理五个数据
            String beat = cdBasketballFollowOrder.getBeat();
            String size = cdBasketballFollowOrder.getSize();
            String let = cdBasketballFollowOrder.getLet();
            paraStr = judgeBasket(buy_ways, hostFailStr, hostWin, size, beat,let);
            Caipiao caipiao=null;
            if (StringUtils.isNotEmpty(paraStr)) {
                String[] testP = {"单场"};
                String[] pass =cdOptionPassVo.getPass().split(",");
                String[] dict =cdOptionPassVo.getOption().split(",");//胜，平，负，-1为补位
                caipiao = print(paraStr,testP,pass,dict,0);
                //过关类型
                if("1".equals(cdBasketballFollowOrder.getBuyWays())){
                    if (cdBasketballFollowOrder.getDanMatchIds().substring(0,cdBasketballFollowOrder.getDanMatchIds().length()-1).split(",").length==1){
                        caipiao.getLeixing()[3]=1;//单场
                    }else {
                        caipiao.getLeixing()[8] = 1;//混合过关
                    }
                }
            }
            //System.out.println(JSON.toJSONString(caipiao));
            return JSON.toJSONString(caipiao);
        }else{
            return null;
        }
    }

    /**
     * 足球去掉胆，判断投注类型
     * @param buy_ways
     * @param score
     * @param goal
     * @param half
     * @param beat
     * @param let
     * @return
     */
    private String judge(String buy_ways,String score,String goal,String half,String beat,String let){
        String paraStr = "";
        if(score.startsWith("0")||score.startsWith("1")){//是否包含胆数据
            String[] scoreArr = score.split("\\|");
            String retScore = "";
            for (String ss : scoreArr){
                retScore += ss.substring(2, ss.length())+"|";
            }
            score = retScore;
        }
        if(goal.startsWith("0")||goal.startsWith("1")){//是否包含胆数据
            String[] goalArr = goal.split("\\|");
            String retGoal = "";
            for (String ss : goalArr){
                retGoal += ss.substring(2, ss.length())+"|";
            }
            goal = retGoal;
        }
        if(half.startsWith("0")||half.startsWith("1")){//是否包含胆数据
            String[] halfArr = half.split("\\|");
            String retHalf = "";
            for (String ss : halfArr){
                retHalf += ss.substring(2, ss.length())+"|";
            }
            half = retHalf;
        }
        if(beat.startsWith("0")||beat.startsWith("1")){//是否包含胆数据
            String[] beatArr = beat.split("\\|");
            String retBeat = "";
            for (String ss : beatArr){
                retBeat += ss.substring(2, ss.length())+"|";
            }
            beat = retBeat;
        }
        if(let.startsWith("0")||let.startsWith("1")){//是否包含胆数据
            String[] letArr = let.split("\\|");
            String retLet = "";
            for (String ss : letArr){
                retLet += ss.substring(2, ss.length())+"|";
            }
            let = retLet;
        }
        //1混投 2胜负平 3猜比分 4总进球 5半全场 6让球
        if("1".equals(buy_ways)){
            //把同一赛事编号下的赛事选项整合到一起
            //主负     周四307+骑士vs奇才+6-10/6.45/1,21-25/50.00/1,|
            //主胜     周四307+骑士vs奇才+16-20/8.50/1,|
            Map<String,String> mapBianhao = new HashMap<String,String>();
            String madeStr = score+goal+half+beat+let;
            if(StringUtils.isNotEmpty(madeStr)){
                String[] saishi = madeStr.split("\\|");
                for (int i = 0; i < saishi.length; i++) {
                    String[] sss = saishi[i].split("\\+");
                    if(StringUtils.isEmpty(mapBianhao.get(sss[0]))){
                        mapBianhao.put(sss[0],sss[1]+"+"+sss[2]);//以编号为key,后俩段为value
                    }else{
                        String newValue = mapBianhao.get(sss[0])+sss[2];
                        mapBianhao.put(sss[0],newValue);
                    }
                }
            }
            for (Map.Entry<String,String> entry : mapBianhao.entrySet()){
                paraStr += entry.getKey().toString()+"+"+entry.getValue().toString()+"|";
            }
        }else if("2".equals(buy_ways)){ paraStr = beat;
        }else if("3".equals(buy_ways)){ paraStr = score;
        }else if("4".equals(buy_ways)){ paraStr = goal;
        }else if("5".equals(buy_ways)){ paraStr = half;
        }else if("6".equals(buy_ways)){ paraStr = let;
        }
        return paraStr;
    }

    /**
     * 篮球去掉胆，判断投注类型
     * @param buy_ways
     * @param score
     * @param goal
     * @param half
     * @param beat
     * @param let
     * @return
     */
    private String judgeBasket(String buy_ways,String score,String goal,String half,String beat,String let){
        String paraStr = "";
        if(score.startsWith("0")||score.startsWith("1")){//是否包含胆数据
            String[] scoreArr = score.split("\\|");
            String retScore = "";
            for (String ss : scoreArr){
                retScore += ss.substring(2, ss.length())+"|";
            }
            score = retScore;
        }
        if(goal.startsWith("0")||goal.startsWith("1")){//是否包含胆数据
            String[] goalArr = goal.split("\\|");
            String retGoal = "";
            for (String ss : goalArr){
                retGoal += ss.substring(2, ss.length())+"|";
            }
            goal = retGoal;
        }
        if(half.startsWith("0")||half.startsWith("1")){//是否包含胆数据
            String[] halfArr = half.split("\\|");
            String retHalf = "";
            for (String ss : halfArr){
                retHalf += ss.substring(2, ss.length())+"|";
            }
            half = retHalf;
        }
        if(beat.startsWith("0")||beat.startsWith("1")){//是否包含胆数据
            String[] beatArr = beat.split("\\|");
            String retBeat = "";
            for (String ss : beatArr){
                retBeat += ss.substring(2, ss.length())+"|";
            }
            beat = retBeat;
        }
        if(let.startsWith("0")||let.startsWith("1")){//是否包含胆数据
            String[] letArr = let.split("\\|");
            String retLet = "";
            for (String ss : letArr){
                retLet += ss.substring(2, ss.length())+"|";
            }
            let = retLet;
        }
        //1混投 2胜负 3让分胜负 4大小分 5胜分差
            //把同一赛事编号下的赛事选项整合到一起
            //主负     周四307+骑士vs奇才+6-10/6.45/1,21-25/50.00/1,|
            //主胜     周四307+骑士vs奇才+16-20/8.50/1,|
            Map<String,String> mapBianhao = new HashMap<String,String>();
            String madeStr = score+goal+half+beat+let;
            if(StringUtils.isNotEmpty(madeStr)){
                String[] saishi = madeStr.split("\\|");
                for (int i = 0; i < saishi.length; i++) {
                    String[] sss = saishi[i].split("\\+");
                    if(StringUtils.isEmpty(mapBianhao.get(sss[0]))){
                        mapBianhao.put(sss[0],sss[1]+"+"+sss[2]);//以编号为key,后俩段为value
                    }else{
                        String newValue = mapBianhao.get(sss[0])+sss[2];
                        mapBianhao.put(sss[0],newValue);
                    }
                }
            }
            for (Map.Entry<String,String> entry : mapBianhao.entrySet()){
                paraStr += entry.getKey().toString()+"+"+entry.getValue().toString()+"|";
            }

        return paraStr;
    }
    /**
     * 彩票数据组装
     * @param paraStr 订单详情字符串
     * @param sPass  彩票过关方式客户信息
     * @param pass   彩票过关方式字典数据
     * @param dict   彩票竞猜选项字典数据
     * @param beishu
     * @return
     */
    private Caipiao print(String paraStr,String[] sPass,String[] pass,String[] dict, int beishu){
        Caipiao caipiao = new Caipiao();
        //    周一048+谢菲联vs加的夫城+1/3.00/1,|
        String[] beatArr = splitByStr(paraStr,"\\|");
        List<List<Integer>> saiShiList = new ArrayList<List<Integer>>();

        for (String beatStr : beatArr) {
            if(StringUtils.isNotEmpty(beatStr)){
                List<Integer> saiS = new ArrayList<Integer>();
                SaiShi saishi = new SaiShi();
                beatStr =beatStr.substring(0,beatStr.length()-1);
                String[] badd = splitByStr(beatStr,"\\+");
                String week =badd[0].substring(0,2);
                String newBadd01 = badd[0].replace(badd[0].substring(0, 2),"");
                int bianhao =Integer.parseInt(newBadd01);
                changeWeek(saishi,week);//
                changeBianhao(saishi, bianhao);//
                //押注结果/赔率/注数
                String[] badd2 = badd[2].split("\\,");
                String[] optionArr = new String[badd2.length];
                for (int i = 0; i <badd2.length ; i++) {
                    String[] badd2_0 = badd2[i].split("\\/");//客户竞猜选项信息
                    if("3".equals(badd2_0[0])){
                        optionArr[i] = "胜";
                    }else if("1".equals(badd2_0[0])){
                        optionArr[i] = "平";
                    }else if("0".equals(badd2_0[0])){
                        optionArr[i] = "负";
                    }else{
                        optionArr[i] = badd2_0[0];
                    }
                }
                List<Integer> xuanxiangList = changeOption(optionArr,dict);

               // beishu = Integer.parseInt(ss[2]);//注数
                //把赛事编号和竞猜选项合并成一个大集合
                saiS.addAll(Arrays.asList(saishi.getWeek()));
                saiS.addAll(Arrays.asList(saishi.getBianhao()));
                saiS.addAll(xuanxiangList);
                saiShiList.add(saiS);
            }
        }

        caipiao.setSaishiList(saiShiList);

        List<Integer> passList = changeOption(sPass,pass);
        caipiao.setFangshi(passList);
        if(beishu<10){
            changeBeishuGewei(caipiao,beishu);
        }else if(beishu>=10&&beishu<160){
            changeBeishuShiwei(caipiao,beishu/10);
            changeBeishuGewei(caipiao,beishu%10);
        }else if(beishu>=160&&beishu<=195){
            caipiao.getBeishu()[9] = 1;
            caipiao.getBeishu()[10] = 1;
            caipiao.getBeishu()[11] = 1;
            caipiao.getBeishu()[12] = 1;
            caipiao.getBeishu()[13] = 1;
            changeBeishuGewei(caipiao,beishu-150);
        }
        return caipiao;
    }

    /**
     * 竞猜选项处理，客户数据与字典数据比较，生成标准竞猜选项集合
     * @param source
     * @param dict
     * @return
     */
    private List<Integer> changeOption(String[] source,String[] dict){
        Integer[] retInt = new Integer[dict.length];
        for (int i = 0; i < retInt.length; i++) {//处理默认值null问题
            retInt[i] = 0;
        }
        for (int i = 0; i < source.length; i++) {
            String sourStr = source[i];
            for (int j = 0; j < dict.length; j++) {
                String dictStr = dict[j];
                if(dictStr.equals(sourStr)){
                    retInt[j] = 1;
                }
            }
        }
        return Arrays.asList(retInt);
    }

    /**
     * 彩票倍数个位处理
     * @param caipiao
     * @param beishu
     */
    private void changeBeishuGewei(Caipiao caipiao,int beishu){
        if(beishu<10){
            if(beishu>0){//单关没有倍数，去除
                caipiao.getBeishu()[beishu-1]=1;
            }
        }else if(beishu>=10&&beishu<46){//大于160的数据
            caipiao.getBeishu()[8] = 1;
            if(beishu <18){
                caipiao.getBeishu()[beishu%10] = 1;
            }else{
                caipiao.getBeishu()[7] = 1;
                if(beishu ==18){
                    caipiao.getBeishu()[0] = 1;
                }else if(beishu ==19){
                    caipiao.getBeishu()[1] = 1;
                }else if(beishu ==20){
                    caipiao.getBeishu()[2] = 1;
                }else if(beishu ==21){
                    caipiao.getBeishu()[3] = 1;
                }else if(beishu ==22){
                    caipiao.getBeishu()[4] = 1;
                }else if(beishu ==23){
                    caipiao.getBeishu()[5] = 1;
                }else if(beishu>=24){
                    caipiao.getBeishu()[6] = 1;
                    if(beishu ==25){
                        caipiao.getBeishu()[0] = 1;
                    }else if(beishu ==26){
                        caipiao.getBeishu()[1] = 1;
                    }else if(beishu ==27){
                        caipiao.getBeishu()[2] = 1;
                    }else if(beishu ==28){
                        caipiao.getBeishu()[3] = 1;
                    }else if(beishu ==29){
                        caipiao.getBeishu()[4] = 1;
                    }else if(beishu >=30){
                        caipiao.getBeishu()[5] = 1;
                        if(beishu ==31){
                            caipiao.getBeishu()[0] = 1;
                        }else if(beishu ==32){
                            caipiao.getBeishu()[1] = 1;
                        }else if(beishu ==33){
                            caipiao.getBeishu()[2] = 1;
                        }else if(beishu ==34){
                            caipiao.getBeishu()[3] = 1;
                        }else if(beishu >=35){
                            caipiao.getBeishu()[4] = 1;
                            if(beishu ==36){
                                caipiao.getBeishu()[0] = 1;
                            }else if(beishu ==37){
                                caipiao.getBeishu()[1] = 1;
                            }else if(beishu ==38){
                                caipiao.getBeishu()[2] = 1;
                            }else if(beishu >=39){
                                caipiao.getBeishu()[3] = 1;
                                if(beishu ==40){
                                    caipiao.getBeishu()[0] = 1;
                                }else if(beishu ==41){
                                    caipiao.getBeishu()[1] = 1;
                                }else if(beishu >=42){
                                    caipiao.getBeishu()[2] = 1;
                                    if(beishu ==43){
                                        caipiao.getBeishu()[0] = 1;
                                    }else if(beishu ==44){
                                        caipiao.getBeishu()[1] = 1;
                                    }else if(beishu ==45){
                                        caipiao.getBeishu()[0] = 1;
                                        caipiao.getBeishu()[1] = 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * 彩票倍数十位及百位处理
     * @param caipiao
     * @param beishu
     */
    private void changeBeishuShiwei(Caipiao caipiao,int beishu){
        if(beishu<6){//小于60的
            caipiao.getBeishu()[beishu+8] = 1;
        }else{
            caipiao.getBeishu()[13] = 1;
            if(beishu==6){
                caipiao.getBeishu()[9] = 1;
            }else if(beishu==7){
                caipiao.getBeishu()[10] = 1;
            }else if(beishu==8){
                caipiao.getBeishu()[11] = 1;
            }else if(beishu >=9){
                caipiao.getBeishu()[12] = 1;
                if(beishu==10){
                    caipiao.getBeishu()[9] = 1;
                }else if(beishu==11){
                    caipiao.getBeishu()[10] = 1;
                }else if(beishu>=12){
                    caipiao.getBeishu()[11] = 1;
                    if(beishu==13){
                        caipiao.getBeishu()[9] = 1;
                    }else if(beishu==14){
                        caipiao.getBeishu()[10] = 1;
                    }else if(beishu>=15){//大于150的
                        caipiao.getBeishu()[9] = 1;
                        caipiao.getBeishu()[10] = 1;
                    }
                }
            }
        }
    }

    /**
     * 赛事编号中的星期处理
     * @param saishi
     * @param week
     */
    private void changeWeek(SaiShi saishi,String week){
        if("周一".equals(week)){
            saishi.getWeek()[0]=1;
        }else if("周一".equals(week)){
            saishi.getWeek()[0]=1;
        }else if("周二".equals(week)){
            saishi.getWeek()[1]=1;
        }else if("周三".equals(week)){
            saishi.getWeek()[2]=1;
        }else if("周四".equals(week)){
            saishi.getWeek()[3]=1;
        }else if("周五".equals(week)){
            saishi.getWeek()[4]=1;
        }else if("周六".equals(week)){
            saishi.getWeek()[5]=1;
        }else if("周日".equals(week)){
            saishi.getWeek()[6]=1;
        }else if("作废".equals(week)){
            saishi.getWeek()[7]=1;
        }
    }

    /**
     * 赛事编号中的数字编号处理
     * @param saishi
     * @param bianhao
     */
    private void changeBianhao(SaiShi saishi,int bianhao){
        if(bianhao<10){
            changeBianhao2(saishi,bianhao,0);
        }else if(bianhao>=10&&bianhao<100){
            changeBianhao2(saishi,bianhao/10,4);//十位
            changeBianhao2(saishi,bianhao%10,0);//个位
        }else if(bianhao>=100&&bianhao<1000){
            changeBianhao2(saishi,bianhao/100,8);//百位
            changeBianhao2(saishi,bianhao%100/10,4);//十位
            changeBianhao2(saishi,bianhao%100%10,0);//个位
        }
    }

    /**
     * 赛事编号中的数字编号详细处理
     * @param saishi
     * @param bianhao
     * @param xiabiao
     */
    private void changeBianhao2(SaiShi saishi,int bianhao,int xiabiao){
        if(bianhao==1){
            saishi.getBianhao()[0+xiabiao] = 1;
        }else if(bianhao==2){
            saishi.getBianhao()[1+xiabiao] = 1;
        }else if(bianhao==3){
            saishi.getBianhao()[0+xiabiao] = 1;
            saishi.getBianhao()[1+xiabiao] = 1;
        }else if(bianhao==4){
            saishi.getBianhao()[2+xiabiao] = 1;
        }else if(bianhao==5){
            saishi.getBianhao()[3+xiabiao] = 1;
        }else if(bianhao==6){
            saishi.getBianhao()[0+xiabiao] = 1;
            saishi.getBianhao()[3+xiabiao] = 1;
        }else if(bianhao==7){
            saishi.getBianhao()[1+xiabiao] = 1;
            saishi.getBianhao()[3+xiabiao] = 1;
        }else if(bianhao==8){
            saishi.getBianhao()[0+xiabiao] = 1;
            saishi.getBianhao()[1+xiabiao] = 1;
            saishi.getBianhao()[3+xiabiao] = 1;
        }else if(bianhao==9){
            saishi.getBianhao()[2+xiabiao] = 1;
            saishi.getBianhao()[3+xiabiao] = 1;
        }
    }

    /**
     * 字符串拆分
     * @param str
     * @param split
     * @return
     */
    private String[] splitByStr(String str,String split){
        String retStr[]= str.split(split);
        return retStr;
    }

    private Object dataJson(Object data, int code) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("success", true);
        map.put("data", data);
        map.put("code", code);
        return map;
    }

}
