/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cmagicorder.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.modules.cmagicorder.entity.CdMagicOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 神单订单DAO接口
 *
 * @author ZhaoYiFeng
 * @version 2018-03-05
 */
@Repository
public class CdMagicOrderDao extends BaseDao<CdMagicOrder> {

    public BigDecimal findJoinFoByNumber(String orderNumber) {
        String sql = "SELECT cmo.charges FROM cd_magic_order cmo JOIN cd_magic_follow_order cmfo ON cmo.id=cmfo.magic_order_id WHERE cmfo.order_num = '" + orderNumber + "'";
        List<CdMagicOrder> lists = new ArrayList<CdMagicOrder>();
        List<String> charges = findBySql(sql);
        if (charges == null) {
            return new BigDecimal("0");
        } else {
            return new BigDecimal(charges.get(0));
        }
    }

    public JSONArray queryRanking() {
        String sql = "SELECT clu.id,clu.name,clu.img FROM cd_lottery_user clu JOIN(SELECT user_id FROM cd_top_user WHERE del_flag=0 ORDER BY sort DESC) as ctu on clu.mobile=ctu.user_id UNION SELECT clu.id,clu.name,clu.img FROM (SELECT rln.user_id FROM (SELECT user_id,CONVERT(IFNULL(convert(SUM(total_price),decimal(15,2))/convert(SUM(win_price),decimal(15,2)),0),DECIMAL(10,2))*100 as retaliation\n" +
                "FROM cd_order WHERE issue=2 and date_sub(curdate(), INTERVAL 7 DAY) <= date(create_date) GROUP BY user_id)AS rln JOIN (SELECT tl.user_id,convert(win_tl.win_total/tl.total*100,decimal(10,2))as hit FROM (SELECT user_id,COUNT(1) AS total FROM cd_order WHERE issue=2 and date_sub(curdate(), INTERVAL 7 DAY) <= date(create_date) GROUP BY user_id) as tl\n" +
                "JOIN (SELECT user_id,COUNT(1)AS win_total FROM cd_order WHERE issue=2 and status=3 and date_sub(curdate(), INTERVAL 7 DAY) <= date(create_date) GROUP BY user_id) as win_tl ON tl.user_id=win_tl.user_id) AS ht JOIN (SELECT user_id,COUNT(1)AS win_total FROM cd_order WHERE issue=2 and date_sub(curdate(), INTERVAL 7 DAY) <= date(create_date) GROUP BY user_id) AS ttl JOIN (SELECT user_id,sum(total_price) AS total FROM cd_order WHERE issue=2 and date_sub(curdate(), INTERVAL 7 DAY) <= date(create_date) GROUP BY user_id) tlp ON rln.user_id=ht.user_id AND rln.user_id=ttl.user_id AND rln.user_id=tlp.user_id WHERE rln.retaliation>180 AND ht.hit>50 AND tlp.total>2000 AND ttl.win_total>=5 AND ttl.win_total<=12) as top JOIN cd_lottery_user clu ON top.user_id=clu.id LIMIT 8";
        List<Object[]> list = findBySql(sql);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONObject jb = new JSONObject();
            jb.put("user_id", list.get(i)[0]);
            jb.put("user_name", list.get(i)[1]);
            jb.put("user_head", list.get(i)[2]);
            jsonArray.add(jb);
        }
        return jsonArray;
    }
}
