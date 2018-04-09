package com.youge.yogee.interfaces.yeepay;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class PayApiServlet
 */
public class PayApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public PayApiServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String orderId = request.getParameter("orderId");
		String orderAmount = request.getParameter("orderAmount");
		String timeoutExpress = request.getParameter("timeoutExpress");
		String requestDate = request.getParameter("requestDate");
		String redirectUrl = request.getParameter("redirectUrl");
		String notifyUrl = request.getParameter("notifyUrl");
		String goodsName = request.getParameter("goodsName");
		String goodsDesc = request.getParameter("goodsDesc");
		String paymentParamExt = request.getParameter("paymentParamExt");
		String bizSource = request.getParameter("bizSource");
		String bizEntity = request.getParameter("bizEntity");
		String memo = request.getParameter("memo");
		String riskParamExt = request.getParameter("riskParamExt");
		String csUrl = request.getParameter("csUrl");

		String timestamp = request.getParameter("timestamp");
		String directPayType = request.getParameter("directPayType");
		String cardType = request.getParameter("cardType");
		String appId = request.getParameter("appId");
		String openId = request.getParameter("openId");
		String clientId = request.getParameter("clientId");
		String userNo = request.getParameter("userNo");
		String userType = request.getParameter("userType");
		
		String goodsParamExt = "{\"goodsName\":\""+goodsName+"\",\"goodsDesc\":\""+goodsDesc+"\"}";
		String industryParamExt = "{\"bizSource\":\""+bizSource+"\",\"bizEntity\":\""+bizEntity+"\"}";
		String ext = "{\"appId\":\""+appId+"\",\"openId\":\""+openId+"\",\"clientId\":\""+clientId+"\"}";
		
		Map<String, String> params = new HashMap<>();
		params.put("orderId", orderId);
		params.put("orderAmount", orderAmount);
		params.put("timeoutExpress", timeoutExpress);
		params.put("requestDate", requestDate);
		params.put("redirectUrl", redirectUrl);
		params.put("notifyUrl", notifyUrl);
		params.put("goodsParamExt", goodsParamExt);
		params.put("paymentParamExt", paymentParamExt);
		params.put("industryParamExt", industryParamExt);
		params.put("memo", memo);
		params.put("riskParamExt", riskParamExt);
		params.put("csUrl", csUrl);
		
		Map<String, String> result = new HashMap<>();
		String uri = YeepayService.getUrl(YeepayService.TRADEORDER_URL);
		result = YeepayService.requestYOP(params, uri, YeepayService.TRADEORDER);

		String token = result.get("token");
		String codeRe = result.get("code");
		if(!"OPR00000".equals(codeRe)){
			String message = result.get("message");
			response.getWriter().write(message);
		}

		String parentMerchantNo = YeepayService.getParentMerchantNo();
		String merchantNo = YeepayService.getMerchantNo();
		
		params.put("parentMerchantNo", parentMerchantNo);
		params.put("merchantNo", merchantNo);
		params.put("orderId", orderId);
		params.put("token", token);
		params.put("timestamp", timestamp);
		params.put("directPayType", directPayType);
		params.put("cardType", cardType);
		params.put("userNo", userNo);
		params.put("userType", userType);
		params.put("ext", ext);
		
		String url = YeepayService.getUrl(params);
		System.out.println(url);
		response.sendRedirect(url.toString());
	}

}
