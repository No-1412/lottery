/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.modules.erp.entity.ErpOrderRank;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 排行DAO接口
 *
 * @author RenHaipeng
 * @version 2018-03-08
 */
@Repository
public class ErpOrderRankDao extends BaseDao<ErpOrderRank> {

    public void deleteAll() {
        this.updateBySql("DELETE * FROM ErpOrderRank", null);
    }

    public List<Object> rank(Page<ErpOrderRank> page) {
        String sql = "";
        return findBySql(sql);
    }

    public Page findRank(Page<Map<String, String>> page, String beginDate, String endDate) {
        String tiaojian = " and cdorder.create_date BETWEEN '" + beginDate + "' and'" + endDate + "' ";
        System.out.println(beginDate);
        System.out.println(endDate);
        String sql = "SELECT csrp.asaleid, csrp.username, csrp.moneywithoutgendan, IFNULL(gd.moneygendan, 0.00) AS moneygendan, csrp.moneytotal, '0' AS num, csrp.createdate, csrp.role_name FROM ( SELECT csr.asaleid, csr.username, csr.createdate, csr.role_name, IFNULL(csr.moneytotal, 0.00) AS moneytotal, IFNULL(sp.moneywithoutgendan, 0.00) AS moneywithoutgendan FROM ( SELECT sr.asaleid, sr.username, sr.createdate, sr.role_name, pm.moneytotal FROM ( SELECT su.id AS asaleid, su. NAME AS username, su.create_date AS createdate, sr. NAME role_name FROM sys_role sr LEFT JOIN sys_user_role sur ON sr.id = sur.role_id LEFT JOIN sys_user su ON su.id = sur.user_id WHERE sr. NAME NOT IN ( '普通用户', '系统管理员' ) AND su.del_flag <> 1 ) sr LEFT JOIN ( SELECT cdorder.sale_id AS asaleid, sum(cdorder.total_price) AS moneytotal, su.`name` AS username, su.create_date AS createdate FROM cd_order cdorder LEFT JOIN sys_user su ON cdorder.sale_id = su.id WHERE 1 = 1 AND cdorder.create_date BETWEEN '\" + beginDate + \"' AND '\" + endDate + \"' GROUP BY sale_id ) pm ON pm.asaleid = sr.asaleid ) AS csr LEFT JOIN ( SELECT sum(cdorder.total_price) AS moneywithoutgendan, cdorder.sale_id AS csaleid FROM cd_order cdorder LEFT JOIN sys_user su ON cdorder.sale_id = su.id WHERE cdorder.issue <> '1' AND cdorder.create_date BETWEEN '\" + beginDate + \"' AND '\" + endDate + \"' GROUP BY su.id ) AS sp ON csr.asaleid = sp.csaleid ) csrp LEFT JOIN ( SELECT cdorder.sale_id AS dsaleid, sum(cdorder.total_price) AS moneygendan FROM cd_order AS cdorder WHERE issue = '1' AND cdorder.create_date BETWEEN '\" + beginDate + \"' AND '\" + endDate + \"' GROUP BY sale_id ) gd ON csrp.asaleid = gd.dsaleid ORDER BY csrp.moneywithoutgendan DESC";
        System.out.println(sql);
//        String sql = " SELECT  t.asaleid as saleid,t.username as salename,c.moneywithoutgendan,d.moneygendan,a.moneytotal,b.num,t.createdate,t.role_name as role_name from " +
//                //所有销售员
//                " (SELECT su.id as asaleid,su.name as username,su.create_date as createdate,sr.name role_name from sys_role sr  LEFT JOIN sys_user_role sur on  sr.id = sur.role_id " +
//                " LEFT JOIN sys_user su on su.id = sur.user_id where sr.name not in('普通用户' ,'系统管理员') and su.del_flag <>1 " +
//                //"or sr.name ='系统管理员' " +
//                ") t LEFT JOIN " +
//                //总业绩
//                " (SELECT cdorder.sale_id as asaleid, sum(cdorder.total_price) as moneytotal,su.`name` as username,su.create_date as createdate FROM cd_order cdorder " +
//                " LEFT JOIN sys_user su on cdorder.sale_id = su.id where 1=1 " + tiaojian + " GROUP BY sale_id) a on a.asaleid = t.asaleid LEFT JOIN " +
//                //被跟单量
//                "(SELECT sss.userid as bsaleid, sss.username,sss.createdate,SUM(sss.counts) as num FROM ( SELECT orderlist.userid,orderlist.username,orderlist.createdate,count(o.win) AS counts  " +
//                " FROM(SELECT cdorder.id AS orderid, alluser.userid AS userid, alluser.username AS username,alluser.createdate AS createdate FROM cd_order cdorder  INNER JOIN  " +
//                " (SELECT u.id AS userid,u. NAME AS username, u.create_date AS createdate,l.id AS luserid FROM cd_lottery_user l INNER JOIN sys_user u ON u.id = l.sale_id)  " +
//                " alluser ON alluser.luserid = cdorder.user_id) orderlist INNER JOIN cd_order o ON orderlist.orderid = o.win  " +
//                "  GROUP BY o.win ) sss GROUP BY sss.userid) b on a.asaleid = b.bsaleid LEFT JOIN " +
//                //-- 自己客户购买总额不含跟单
//                " (SELECT sum(cdorder.total_price) as moneywithoutgendan,cdorder.sale_id as csaleid FROM cd_order cdorder  LEFT JOIN" +
//                " sys_user su on cdorder.sale_id = su.id where cdorder.issue <> '1' " + tiaojian + "  GROUP BY su.id) c on c.csaleid = a.asaleid LEFT JOIN" +
//                //跟单业绩
//                " (SELECT cdorder.sale_id as dsaleid, sum(cdorder.total_price) as moneygendan FROM cd_order cdorder " +
//                " WHERE issue = '1' " + tiaojian + " GROUP BY sale_id) d on d.dsaleid = a.asaleid  ORDER BY a.moneytotal DESC";

      /*  2018-05-17 yhw SQL语句优化，暂时未启用
      SELECT  t.asaleid as saleid,t.username as salename,c.moneywithoutgendan,d.moneygendan,a.moneytotal,b.num,t.createdate
        from  (
                SELECT su.id as asaleid,su.name as username,su.create_date as createdate from sys_role sr
                LEFT JOIN sys_user_role sur on  sr.id = sur.role_id
                LEFT JOIN sys_user su on su.id = sur.user_id where sr.name ='销售员' ) t
        LEFT JOIN  (
                SELECT cdorder.sale_id as asaleid, sum(cdorder.total_price) as moneytotal	FROM cd_order cdorder
        where 1=1  " + tiaojian + " GROUP BY sale_id) a
        on a.asaleid = t.asaleid
        LEFT JOIN (
                SELECT sss.userid as bsaleid ,SUM(sss.counts) as num FROM (
                SELECT cdorder.user_id as userid,count(o.win) AS counts
                FROM cd_order cdorder
                INNER JOIN cd_order o
                ON cdorder.id = o.win
                where 1=1 " + tiaojian + " GROUP BY o.win ) sss
        GROUP BY sss.userid) b
        on a.asaleid = b.bsaleid
        LEFT JOIN  (
                SELECT sum(cdorder.total_price) as moneywithoutgendan,cdorder.sale_id as csaleid FROM cd_order cdorder
        where cdorder.issue <> '1'  " + tiaojian + "  GROUP BY cdorder.sale_id) c
        on c.csaleid = a.asaleid
        LEFT JOIN (
                SELECT cdorder.sale_id as dsaleid, sum(cdorder.total_price) as moneygendan FROM cd_order cdorder  WHERE issue = '1' " + tiaojian + "  GROUP BY sale_id) d
        on d.dsaleid = a.asaleid
        GROUP BY t.asaleid
        ORDER BY a.moneytotal DESC*/
        return findBySql(page, sql);
    }
}
