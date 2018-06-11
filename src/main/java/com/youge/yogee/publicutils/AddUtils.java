package com.youge.yogee.publicutils;

import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.modules.cbankcard.entity.CdBankCard;
import com.youge.yogee.modules.cbankcard.service.CdBankCardService;
import com.youge.yogee.modules.cforum.entity.CdForum;
import com.youge.yogee.modules.cforum.service.CdForumService;
import com.youge.yogee.modules.cmessage.entity.CdMessage;
import com.youge.yogee.modules.cmessage.service.CdMessageService;
import com.youge.yogee.modules.cproductproposal.entity.CdProductProposal;
import com.youge.yogee.modules.cproductproposal.service.CdProductProposalService;

/**
 * Created by wjc on 2017-12-13 0013.工具类 用于所有实体类的添加.
 */
public class AddUtils {

    /**
     * wjc, 2017-09-27,添加短信记录（系统带有短信相关功能，暂时这个不用了）
     */
    public static void addMessage(CdMessageService cdMessageService, String phone, String sendNr,
                                  String sendZt, String effectiveTime, String checkZt, String code) {

        CdMessage cdMessage = new CdMessage();
        cdMessage.setPhone(phone);
        cdMessage.setSendNr(sendNr);
        cdMessage.setSendZt(sendZt);
        cdMessage.setEffectiveTime(effectiveTime);
        cdMessage.setCheckZt(checkZt);
        cdMessage.setCode(code);
        cdMessageService.save(cdMessage);
    }

    /**
     * wjc, 2017-09-27,添加论坛信息记录
     */
    public static void addForum(CdForumService cdForumService, String name,String id, String userId,
                                  String userName, String imgList, String content, String isPosts,String parentsId,String parentsUser,
                                  String forumType) {

        CdForum cdForum = new CdForum();
        cdForum.setId(id);
        cdForum.setName(name); //标题
        cdForum.setContent(content); //内容
        cdForum.setUserId(userId);
        cdForum.setUserName(userName);
        cdForum.setImgList(imgList); //图片
        cdForum.setIsPosts(isPosts); //是否是帖子(1帖子  0回复内容)
        cdForum.setDianzanCount("0");
        cdForum.setIsUse("1");
        cdForum.setCreateDate(DateUtils.getDateTime());
        cdForum.setDelFlag(CdForum.DEL_FLAG_NORMAL);
        cdForum.setParentsId(parentsId);
        cdForum.setParentsUser(parentsUser);
        cdForum.setForumType(forumType);
        cdForumService.save(cdForum);
    }

    /**
     * @param num
     * @return
     * @author wjc
     * @function 生成num位的随机字符串(数字、大写字母随机混排)
     */
    public static String createBigSmallLetterStrOrNumberRadom(int num) {

        String str = "";
        for (int i = 0; i < num; i++) {
            int intVal = (int) (Math.random() * 58 + 65);
            if (intVal >= 91 && intVal <= 96) {
                i--;
            }
            if (intVal < 91 || intVal > 96) {
                if (intVal % 2 == 0) {
                    str += (char) intVal;
                } else {
                    str += (int) (Math.random() * 10);
                }
            }
        }
        return str;
    }

    /**
     * anbo,2017-10-10,添加用户银行卡
     */
    public static void addBankCard(CdBankCardService abBankCardService, String userId, String userName, String selectbank, String receivebank,
                                   String selectbankId, String cardno, String selectProvince, String selectCity, String bankImg, String kefuTel, String bankNo, String idCard) {

        CdBankCard b = new CdBankCard();

        b.setUserId(userId);
        b.setUserName(userName);
        b.setName(userName);
        b.setSelectbank(selectbank);
        b.setReceivebank(receivebank);
        b.setSelectbankId(selectbankId);
        b.setCardno(cardno);
        b.setSelectProvince(selectProvince);
        b.setSelectCity(selectCity);
        b.setImg(bankImg);
        b.setKefuTel(kefuTel);
        b.setCardMode(bankNo);
        b.setIdCard(idCard);
        abBankCardService.save(b);
    }
    /**
     *产品建议
     */
    public static void productProposal(CdProductProposalService cdProductProposalService,String img, String content,String name){
        CdProductProposal pp = new CdProductProposal();
        pp.setImg(img);
        pp.setContent(content);
        pp.setName(name);
        cdProductProposalService.save(pp);
    }

}
