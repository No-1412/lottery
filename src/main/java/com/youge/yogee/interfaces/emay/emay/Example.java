package com.youge.yogee.interfaces.emay.emay;

import com.youge.yogee.interfaces.emay.emay.eucp.inter.http.v1.dto.request.*;
import com.youge.yogee.interfaces.emay.emay.eucp.inter.http.v1.dto.response.BalanceResponse;
import com.youge.yogee.interfaces.emay.emay.eucp.inter.http.v1.dto.response.MoResponse;
import com.youge.yogee.interfaces.emay.emay.eucp.inter.http.v1.dto.response.ReportResponse;
import com.youge.yogee.interfaces.emay.emay.eucp.inter.http.v1.dto.response.SmsResponse;
import com.youge.yogee.interfaces.emay.emay.util.AES;
import com.youge.yogee.interfaces.emay.emay.util.GZIPUtils;
import com.youge.yogee.interfaces.emay.emay.util.JsonHelper;
import com.youge.yogee.interfaces.emay.emay.util.http.*;

import java.util.HashMap;
import java.util.Map;

public class Example {


    /**
     * 获取余额
     *
     * @param isGzip 是否压缩
     */
    private static void getBalance(String appId, String secretKey, String host, String algorithm, boolean isGzip, String encode) {
        System.out.println("=============begin getBalance==================");
        BalanceRequest pamars = new BalanceRequest();
        ResultModel result = request(appId, secretKey, algorithm, pamars, "http://" + host + "/inter/getBalance", isGzip, encode);
        System.out.println("result code :" + result.getCode());
        if ("SUCCESS".equals(result.getCode())) {
            BalanceResponse response = JsonHelper.fromJson(BalanceResponse.class, result.getResult());
            if (response != null) {
                System.out.println("余额 : " + response.getBalance());
            }
        }
        System.out.println("=============end getBalance==================");
    }

    /**
     * 获取状态报告
     *
     * @param isGzip 是否压缩
     */
    private static void getReport(String appId, String secretKey, String host, String algorithm, boolean isGzip, String encode) {
        System.out.println("=============begin getReport==================");
        ReportRequest pamars = new ReportRequest();
        ResultModel result = request(appId, secretKey, algorithm, pamars, "http://" + host + "/inter/getReport", isGzip, encode);
        System.out.println("result code :" + result.getCode());
        if ("SUCCESS".equals(result.getCode())) {
            ReportResponse[] response = JsonHelper.fromJson(ReportResponse[].class, result.getResult());
            if (response != null) {
                for (ReportResponse d : response) {
                    System.out.println("result data : " + d.getExtendedCode() + "," + d.getMobile() + "," + d.getCustomSmsId() + "," + d.getSmsId() + "," + d.getState() + "," + d.getDesc()
                            + "," + d.getSubmitTime() + "," + d.getReceiveTime());
                }
            }
        }
        System.out.println("=============end getReport==================");
    }

    /**
     * 获取上行
     *
     * @param isGzip 是否压缩
     */
    private static void getMo(String appId, String secretKey, String host, String algorithm, boolean isGzip, String encode) {
        System.out.println("=============begin getMo==================");
        MoRequest pamars = new MoRequest();
        ResultModel result = request(appId, secretKey, algorithm, pamars, "http://" + host + "/inter/getMo", isGzip, encode);
        System.out.println("result code :" + result.getCode());
        if ("SUCCESS".equals(result.getCode())) {
            MoResponse[] response = JsonHelper.fromJson(MoResponse[].class, result.getResult());
            if (response != null) {
                for (MoResponse d : response) {
                    System.out.println("result data:" + d.getContent() + "," + d.getExtendedCode() + "," + d.getMobile() + "," + d.getMoTime());
                }
            }
        }
        System.out.println("=============end getMo==================");
    }

    /**
     * 发送单条短信
     *
     * @param isGzip 是否压缩
     */
    private static void setSingleSms(String appId, String secretKey, String host, String algorithm, String content, String customSmsId, String extendCode, String mobile, boolean isGzip, String encode) {
        System.out.println("=============begin setSingleSms==================");
        SmsSingleRequest pamars = new SmsSingleRequest();
        pamars.setContent(content);
        pamars.setCustomSmsId(customSmsId);
        pamars.setExtendedCode(extendCode);
        pamars.setMobile(mobile);
        ResultModel result = request(appId, secretKey, algorithm, pamars, "http://" + host + "/inter/sendSingleSMS", isGzip, encode);
        System.out.println("result code :" + result.getCode());
        if ("SUCCESS".equals(result.getCode())) {
            SmsResponse response = JsonHelper.fromJson(SmsResponse.class, result.getResult());
            if (response != null) {
                System.out.println("data : " + response.getMobile() + "," + response.getSmsId() + "," + response.getCustomSmsId());
            }
        }
        System.out.println("=============end setSingleSms==================");
    }

    /**
     * 发送批次短信
     *
     * @param isGzip 是否压缩
     */
    private static void setBatchOnlySms(String appId, String secretKey, String host, String algorithm, String content, String extendCode, String[] mobiles, boolean isGzip, String encode) {
        System.out.println("=============begin setBatchOnlySms==================");
        SmsBatchOnlyRequest pamars = new SmsBatchOnlyRequest();
        pamars.setMobiles(mobiles);
        pamars.setExtendedCode(extendCode);
        pamars.setContent(content);
        ResultModel result = request(appId, secretKey, algorithm, pamars, "http://" + host + "/inter/sendBatchOnlySMS", isGzip, encode);
        System.out.println("result code :" + result.getCode());
        if ("SUCCESS".equals(result.getCode())) {
            SmsResponse[] response = JsonHelper.fromJson(SmsResponse[].class, result.getResult());
            if (response != null) {
                for (SmsResponse d : response) {
                    System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
                }
            }
        }
        System.out.println("=============end setBatchOnlySms==================");
    }


    /**
     * 公共请求方法
     */
    public static ResultModel request(String appId, String secretKey, String algorithm, Object content, String url, final boolean isGzip, String encode) {
        Map<String, String> headers = new HashMap<String, String>();
        EmayHttpRequestBytes request = null;
        try {
            headers.put("appId", appId);
            headers.put("encode", encode);
            String requestJson = JsonHelper.toJsonString(content);
            System.out.println("result json: " + requestJson);
            byte[] bytes = requestJson.getBytes(encode);
            System.out.println("request data size : " + bytes.length);
            if (isGzip) {
                headers.put("gzip", "on");
                bytes = GZIPUtils.compress(bytes);
                System.out.println("request data size [com]: " + bytes.length);
            }
            byte[] parambytes = AES.encrypt(bytes, secretKey.getBytes(), algorithm);
            System.out.println("request data size [en] : " + parambytes.length);
            request = new EmayHttpRequestBytes(url, encode, "POST", headers, null, parambytes);
        } catch (Exception e) {
            System.out.println("加密异常");
            e.printStackTrace();
        }
        EmayHttpClient client = new EmayHttpClient();
        String code = null;
        String result = null;
        try {
            EmayHttpResponseBytes res = client.service(request, new EmayHttpResponseBytesPraser());
            if (res == null) {
                System.out.println("请求接口异常");
                return new ResultModel(code, result);
            }
            if (res.getResultCode().equals(EmayHttpResultCode.SUCCESS)) {
                if (res.getHttpCode() == 200) {
                    code = res.getHeaders().get("result");
                    if (code.equals("SUCCESS")) {
                        byte[] data = res.getResultBytes();
                        System.out.println("response data size [en and com] : " + data.length);
                        data = AES.decrypt(data, secretKey.getBytes(), algorithm);
                        if (isGzip) {
                            data = GZIPUtils.decompress(data);
                        }
                        System.out.println("response data size : " + data.length);
                        result = new String(data, encode);
                        System.out.println("response json: " + result);
                    }
                } else {
                    System.out.println("请求接口异常,请求码:" + res.getHttpCode());
                }
            } else {
                System.out.println("请求接口网络异常:" + res.getResultCode().getCode());
            }
        } catch (Exception e) {
            System.out.println("解析失败");
            e.printStackTrace();
        }
        ResultModel re = new ResultModel(code, result);
        return re;
    }

    public static void sendCode(String code,String phone) {
        // appId
        String appId = " EUCP-EMY-SMS0-JDXNK";//请联系销售，或者在页面中 获取
        // 密钥
        String secretKey = "43FD13FA4752200F";//请联系销售，或者在页面中 获取
        // 接口地址
        String host = "bjmtn.b2m.cn";//请联系销售获取
        // 加密算法
        String algorithm = "AES/ECB/PKCS5Padding";
        // 编码
        String encode = "UTF-8";
        // 是否压缩
        boolean isGizp = false;
        String str = "【凯旋彩票】您的验证码为：" + code;

        // 发送单条短信
        setSingleSms(appId, secretKey, host, algorithm, str, null, null, phone, isGizp, encode);
    }

    public static void main(String[] args) {
        // appId
        String appId = " EUCP-EMY-SMS0-JDXNK";//请联系销售，或者在页面中 获取
        // 密钥
        String secretKey = "43FD13FA4752200F";//请联系销售，或者在页面中 获取
        // 接口地址
        String host = "bjmtn.b2m.cn";//请联系销售获取
        // 加密算法
        String algorithm = "AES/ECB/PKCS5Padding";
        // 编码
        String encode = "UTF-8";
        // 是否压缩
        boolean isGizp = false;


        // 发送单条短信
        //setSingleSms(appId, secretKey, host, algorithm, "【凯旋彩票】您的验证码为：123468", null, null, "13756475758", isGizp, encode);
        getBalance(appId,secretKey,host,algorithm,isGizp,encode);
    }

}

