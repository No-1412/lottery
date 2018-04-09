package com.youge.yogee.interfaces.yeepay;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by songjinfengPC on 2017/4/24.
 * <p>
 * 手机支付的请求接口地址
 */
public class MobilePayServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;


    //获取token请求参数列表
    static String[] params = {"parentMerchantNo", "merchantNo", "orderId", "orderAmount", "timeoutExpress", "requestDate", "redirectUrl", "notifyUrl", "goodsParamExt", "paymentParamExt", "memo", "riskParamExt"};

    //数据存储
    static HashMap<String, String> orderData = new HashMap<String, String>();

    //请求数据存储
    static Map<String, String> requestBasicParams = new HashMap<String, String>();

    static {
        requestBasicParams.put("parentMerchantNo", Configuration.getInstance().getValue("merchantNo"));
        requestBasicParams.put("merchantNo", Configuration.getInstance().getValue("merchantNo"));
        requestBasicParams.put("notifyUrl", "http://104.224.186.152:8080/FengTools_war/ServiceNotice");
    }

    //测试地址
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.getWriter().write("SUCCESS");
        resp.setStatus(200);

    }

    /**
     * 接受请求
     *
     * @param request
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {


        request.setCharacterEncoding("utf-8");
        //预先获取请求信息
        String orderId = request.getParameter("orderId");
        //订单金额
        String orderAmount = request.getParameter("orderAmount");
        //订单有效期
        String timeoutExpress = "1440";
        //请求时间用于计算订单有效期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String requestDate = request.getParameter(sdf.format(new Date()));

        //要求以下三个字段是json格式
        String paymentParamExt = request.getParameter("paymentParamExt");
        String riskParamExt = request.getParameter("riskParamExt");
        String goodsParamExt = request.getParameter("goodsParamExt");
        System.out.println("goodsParamExt:"+goodsParamExt);

        //对账信息
        String memo = request.getParameter("memo");

        //支付商户编号
        String merchantNo= Configuration.getInstance().getValue("merchantNo");
        //时间戳
        long timeStamp=System.currentTimeMillis();
        //直连形式
        String directPayType=request.getParameter("directPayType");
        //支付卡种限制
        String cardType="";

        //经度和纬度获取
        String  longitude=request.getParameter("longitude");
        String  latitude=request.getParameter("latitude");

        //设置支付的userNo
        String userNo="test"+ System.currentTimeMillis()/1000;
        String userType="USER_ID";


        //填充信息
        Map<String, String> requesParams = new HashMap<String, String>();
        requesParams.putAll(requestBasicParams);
        requesParams.put("orderId", orderId);
        requesParams.put("orderAmount", orderAmount);
        requesParams.put("timeoutExpress", timeoutExpress);
        requesParams.put("requestDate", requestDate);
        requesParams.put("paymentParamExt", paymentParamExt);
        requesParams.put("riskParamExt", riskParamExt);
        requesParams.put("goodsParamExt", goodsParamExt);


        //获取YOP返回信息
        String[] signs = {"parentMerchantNo", "merchantNo", "orderId", "orderAmount", "timeoutExpress", "requestDate", "redirectUrl", "notifyUrl", "goodsParamExt", "paymentParamExt", "memo", "riskParamExt"};
        Map<String, String> response = YeepayService.requestYOP(requesParams, Configuration.getInstance().getValue("tradeOrderURI"), signs);

        response.get("token");
        System.out.println("token : " + response.get("token"));


        //签名数据信息


        //拼装签名字符串
        StringBuilder builder = new StringBuilder()
                .append("merchantNo=").append(merchantNo)
                .append("&token=").append(response.get("token"))
                .append("&timestamp=").append(timeStamp)
                .append("&directPayType=").append(directPayType)
                .append("&cardType=").append(cardType)
                .append("&userNo=").append(userNo)
                .append("&userType=").append(userType);

        System.out.println("签名字符串："+builder.toString());

        //生成签名
        String sign= YeepayService.getSign(builder.toString());
        System.out.println("sign:"+sign);
        //响应参数
        Map<String,String>  responseMap=new HashMap<String, String>();
        responseMap.put("wxAppId","");
        responseMap.put("token",response.get("token"));
        responseMap.put("merchantNo",merchantNo);
        responseMap.put("userNo",userNo);
        responseMap.put("userType",userType);
        responseMap.put("directPayType",directPayType);
        responseMap.put("cardType",cardType);
        responseMap.put("timeStamp",Long.toString(timeStamp));
        responseMap.put("latitude",latitude);
        responseMap.put("longitude",longitude);
        responseMap.put("sign",sign);

        StringBuilder content=new StringBuilder();
        Set<Map.Entry<String, String>> entry = responseMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entry.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> temp = iterator.next();
            content.append(temp.getKey()).append("=").append(URLEncoder.encode(temp.getValue()==null?"":temp.getValue(),"utf-8")).append(iterator.hasNext()?"&":"");
        }
        System.out.println(content.toString());
        resp.setStatus(200);
        resp.getWriter().write(content.toString());



    }


}
