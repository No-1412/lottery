package com.youge.yogee.interfaces.lottery.util;

import com.youge.yogee.common.utils.SpringContextHolder;
import com.youge.yogee.interfaces.util.Calculations;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.cmagicorder.entity.CdMagicFollowOrder;
import com.youge.yogee.modules.cmagicorder.entity.CdMagicOrder;
import com.youge.yogee.modules.cmagicorder.service.CdMagicFollowOrderService;
import com.youge.yogee.modules.cmagicorder.service.CdMagicOrderService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;

import java.math.BigDecimal;

public class WinPriceUtil {
    private static CdMagicOrderService cdMagicOrderService = SpringContextHolder.getBean(CdMagicOrderService.class);
    private static CdLotteryUserService cdLotteryUserService = SpringContextHolder.getBean(CdLotteryUserService.class);
    private static CdMagicFollowOrderService cdMagicFollowOrderService = SpringContextHolder.getBean(CdMagicFollowOrderService.class);
    private static CdOrderWinnersService cdOrderWinnersService = SpringContextHolder.getBean(CdOrderWinnersService.class);
    private static CdOrderService cdOrderService = SpringContextHolder.getBean(CdOrderService.class);

    /**
     * 计算佣金
     *
     * @param orderNum 订单号
     * @param price    投注本金
     * @param award    奖金
     * @return
     */
    public static BigDecimal reckonCommission(String orderNum, String price, String award) {
        BigDecimal payCommission = new BigDecimal(0);
        //跟单记录里获取跟单信息
        CdMagicFollowOrder cdMagicFollowOrder = cdMagicFollowOrderService.findOrderByNumber(orderNum);
        //获取神单
        CdMagicOrder cdMagicOrder = cdMagicOrderService.get(cdMagicFollowOrder.getMagicOrderId());
        //本金*保底 小于奖金 支付佣金
        BigDecimal mulResult = new BigDecimal(price).multiply(new BigDecimal(cdMagicOrder.getTimes()));
        int compare = mulResult.compareTo(new BigDecimal(award));
        if (compare == -1) {
            //全部佣金
            BigDecimal commission = new BigDecimal(cdMagicOrder.getCharges()).multiply(new BigDecimal(0.01)).multiply(new BigDecimal(award));
            //发单用户
            CdLotteryUser cdLotteryUser = cdLotteryUserService.get(cdMagicOrder.getUid());
            //发单用户增加余额 收取20% 手续费
            cdLotteryUser.setBalance(cdLotteryUser.getBalance().add(commission.multiply(new BigDecimal(0.8))));
            cdLotteryUserService.save(cdLotteryUser);
            payCommission = commission;
        }
        return payCommission;
    }

    /**
     * 保存中奖记录
     *
     * @param orderNum 订单号
     * @param price    投注本金
     * @param award    奖金
     * @param uid      用户id
     * @param result   中奖结果
     */
    public static void saveOrderWinner(String orderNum, String price, String award, String uid, String result, String type) {
        CdOrderWinners cdOrderWinners = new CdOrderWinners();
        cdOrderWinners.setWinOrderNum(orderNum);//中奖单号
        cdOrderWinners.setWinPrice(award);//中奖金额
        cdOrderWinners.setUid(uid);//中奖用户
        BigDecimal awardBig = new BigDecimal(award);
        String repayPercent = Calculations.getRepayPercent(awardBig.doubleValue(), Double.parseDouble(price));
        cdOrderWinners.setRepayPercent(repayPercent);//回报率
        cdOrderWinners.setType(type);
        cdOrderWinners.setWallType("1");
        cdOrderWinners.setResult(result);
        cdOrderWinnersService.save(cdOrderWinners);
    }


    /**
     * 中奖增加用户余额
     *
     * @param award 奖金
     * @param uid   用户id
     * @param text  您购买的竞猜足球获得中奖金额" + award + "元"
     */
    public static void addBalanceToUser(String award, String uid) {
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        BigDecimal awardBig = new BigDecimal(award);
        BigDecimal balance = clu.getBalance();
        BigDecimal newBalance = balance.add(awardBig);
        clu.setBalance(newBalance);
        try {
            cdLotteryUserService.save(clu);
        } catch (Exception c) {
            System.out.println("中奖金额到账失败");
        }

    }

    /**
     * 更改订单总表
     *
     * @param orderNum 订单号
     * @param award    奖金
     */
    public static void changeTotalOrder(String orderNum, String award) {
        CdOrder co = cdOrderService.getOrderByOrderNum(orderNum);
        if (co != null) {
            BigDecimal finalAward = new BigDecimal(award.toString()).setScale(2, 1);
            co.setWinPrice(finalAward.toString());//奖金 用于反显
            co.setStatus("3");//中奖
            cdOrderService.save(co);
        }
    }


}
