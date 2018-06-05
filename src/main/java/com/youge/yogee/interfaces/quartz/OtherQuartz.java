package com.youge.yogee.interfaces.quartz;

import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;
import com.youge.yogee.modules.cbbnotfinsh.entity.CdBbNotFinishCollection;
import com.youge.yogee.modules.cbbnotfinsh.service.CdBbNotFinishCollectionService;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNineOrder;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineOrderService;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinishCollection;
import com.youge.yogee.modules.cfbnotfinish.service.CdFbNotFinishCollectionService;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveOrder;
import com.youge.yogee.modules.cfiveawards.service.CdFiveOrderService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.clottoreward.entity.CdLottoOrder;
import com.youge.yogee.modules.clottoreward.service.CdLottoOrderService;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFailOrder;
import com.youge.yogee.modules.csuccessfail.service.CdSuccessFailOrderService;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeOrder;
import com.youge.yogee.modules.cthreeawards.service.CdThreeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.youge.yogee.interfaces.util.ListThreeWays.resultOfRightChooseOfFive;

//    "0/10 * * * * ?" 每10秒触发
//
//    "0 0 12 * * ?" 每天中午12点触发
//    "0 15 10 ? * *" 每天上午10:15触发
//    "0 15 10 * * ?" 每天上午10:15触发
//    "0 15 10 * * ? *" 每天上午10:15触发
//    "0 15 10 * * ? 2005" 2005年的每天上午10:15触发
//    "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
//    "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
//    "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
//    "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
//    "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
//    "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
//    "0 15 10 15 * ?" 每月15日上午10:15触发
//    "0 15 10 L * ?" 每月最后一日的上午10:15触发
//    "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
//    "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
//    "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
//    每隔5秒执行一次：*/5 * * * * ?
//    每隔1分钟执行一次：0 */1 * * * ?
//    每天23点执行一次：0 0 23 * * ?
//    每天凌晨1点执行一次：0 0 1 * * ?
//    每月1号凌晨1点执行一次：0 0 1 1 * ?
//    每月最后一天23点执行一次：0 0 23 L * ?
//    每周星期天凌晨1点实行一次：0 0 1 ? * L
//    在26分、29分、33分执行一次：0 26,29,33 * * * ?
//    每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
//定时轮询
//    @Scheduled(cron = "0/20 1 * * * ?")
//    @Scheduled(cron = "0 0 * * * ?")//1小时
@Component("OtherQuartz")
public class OtherQuartz {
    @Autowired
    private CdOrderWinnersService cdOrderWinnersService;
    @Autowired
    private CdBasketballFollowOrderService cdBasketballFollowOrderService;
    @Autowired
    private CdBasketballSingleOrderService cdBasketballSingleOrderService;
    @Autowired
    private CdChooseNineOrderService cdChooseNineOrderService;
    @Autowired
    private CdFiveOrderService cdFiveOrderService;
    @Autowired
    private CdFootballFollowOrderService cdFootballFollowOrderService;
    @Autowired
    private CdFootballSingleOrderService cdFootballSingleOrderService;
    @Autowired
    private CdLottoOrderService cdLottoOrderService;
    @Autowired
    private CdSuccessFailOrderService cdSuccessFailOrderService;
    @Autowired
    private CdThreeOrderService cdThreeOrderService;
    @Autowired
    private CdFbNotFinishCollectionService cdFbNotFinishCollectionService;
    @Autowired
    private CdBbNotFinishCollectionService cdBbNotFinishCollectionService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;

    @Scheduled(cron = "0 0 12 * * ?")//每天十二点
    public void awardWallUpType() throws ParseException {
//        System.out.println("大奖墙更新");
        List<CdOrderWinners> list = cdOrderWinnersService.findByWallType("1");
        Date date = new Date();
        long nowTime = date.getTime();
        if (list.size() > 0) {
            for (CdOrderWinners c : list) {
                //董宏  当前时间 与创建时间超过 24小时 将wallType ->2
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date createTime = df.parse(c.getCreateDate());
                if (nowTime - createTime.getTime() > 86400000) {
                    c.setWallType("2");
                }
                cdOrderWinnersService.save(c);
            }
        }

    }

    /**
     * 删除篮球串关没付款的订单1
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void delFollowBasketBallNotPay() {
        List<CdBasketballFollowOrder> list = cdBasketballFollowOrderService.findNotPay();
        if (list.size() > 0) {
            for (CdBasketballFollowOrder c : list) {
                cdBasketballFollowOrderService.delById(c.getId());
            }
        }
    }

    /**
     * 删除篮球单关没付款的订单2
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void delSingleBasketBallNotPay() {
        List<CdBasketballSingleOrder> list = cdBasketballSingleOrderService.findNotPay();
        if (list.size() > 0) {
            for (CdBasketballSingleOrder c : list) {
                cdBasketballSingleOrderService.delById(c.getId());
            }
        }
    }

    /**
     * 删除任选九没付款的订单3
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void delChooseNineNotPay() {
        List<CdChooseNineOrder> list = cdChooseNineOrderService.findNotPay();
        if (list.size() > 0) {
            for (CdChooseNineOrder c : list) {
                cdChooseNineOrderService.delById(c.getId());
            }
        }
    }

    /**
     * 删除排列五没付款的订单4
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void delFiveNotPay() {
        List<CdFiveOrder> list = cdFiveOrderService.findNotPay();
        if (list.size() > 0) {
            for (CdFiveOrder c : list) {
                cdFiveOrderService.delById(c.getId());
            }
        }
    }


    /**
     * 删除足球串关没付款的订单5
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void delFollowFootballNotPay() {
        List<CdFootballFollowOrder> list = cdFootballFollowOrderService.findNotPay();
        if (list.size() > 0) {
            for (CdFootballFollowOrder c : list) {
                cdFootballFollowOrderService.delById(c.getId());
            }
        }
    }


    /**
     * 删除足球单关没付款的订单6
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void delSingleFootballNotPay() {
        List<CdFootballSingleOrder> list = cdFootballSingleOrderService.findNotPay();
        if (list.size() > 0) {
            for (CdFootballSingleOrder c : list) {
                cdFootballSingleOrderService.delById(c.getId());
            }
        }
    }

    /**
     * 删除大乐透没付款的订单7
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void delLottoNotPay() {
        List<CdLottoOrder> list = cdLottoOrderService.findNotPay();
        if (list.size() > 0) {
            for (CdLottoOrder c : list) {
                cdLottoOrderService.delById(c.getId());
            }
        }
    }

    /**
     * 删除胜负彩没付款的订单8
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void delSuccessFailNotPay() {
        List<CdSuccessFailOrder> list = cdSuccessFailOrderService.findNotPay();
        if (list.size() > 0) {
            for (CdSuccessFailOrder c : list) {
                cdSuccessFailOrderService.delById(c.getId());
            }
        }
    }

    /**
     * 删除排列三没付款的订单9
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void delThreeNotPay() {
        List<CdThreeOrder> list = cdThreeOrderService.findNotPay();
        if (list.size() > 0) {
            for (CdThreeOrder c : list) {
                cdThreeOrderService.delById(c.getId());
            }
        }
    }

    /**
     * 删除足球没用的收藏
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void delFootBallNotCol() {
        List<CdFbNotFinishCollection> list = cdFbNotFinishCollectionService.findHasDel();
        if (list.size() > 0) {
            for (CdFbNotFinishCollection c : list) {
                cdThreeOrderService.delById(c.getId());
            }
        }
    }

    /**
     * 删除篮球没用的收藏
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void delBasketBallNotCol() {
        List<CdBbNotFinishCollection> list = cdBbNotFinishCollectionService.findHasDel();
        if (list.size() > 0) {
            for (CdBbNotFinishCollection c : list) {
                cdBbNotFinishCollectionService.delById(c.getId());
            }
        }
    }


    /**
     * 排列五订单计算详情
     */
    @Scheduled(cron = "0/10 * * * * ?")//每分钟
    //0 30 19 * * ? *
    public void addPerhapsToFive() {
        List<CdFiveOrder> list = cdFiveOrderService.findPerhapsIsNull();
        if (list.size() > 0) {
            for (CdFiveOrder c : list) {
                String nums = c.getNums();
                String allPerhaps = "";
                String[] numsStr = nums.split("\\|");
                //所有情况
                Set<String> set = resultOfRightChooseOfFive(numsStr[0], numsStr[1], numsStr[2], numsStr[3], numsStr[4]);
                for (String s : set) {
                    allPerhaps += s + "|";
                }
                c.setAllPerhaps(allPerhaps);
                cdFiveOrderService.save(c);
            }
        }
    }


    /**
     * 更新用户提现次数
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天一点
    public void upUserTryCashTimes() {
        List<CdLotteryUser> list = cdLotteryUserService.findCatchTimes();
        if (list.size() > 0) {
            for (CdLotteryUser c : list) {
                c.setCatchTimes("3");
                cdLotteryUserService.save(c);
            }
        }
    }

}
