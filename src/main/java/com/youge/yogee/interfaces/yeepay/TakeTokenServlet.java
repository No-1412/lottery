package com.youge.yogee.interfaces.yeepay;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class TakeTokenServlet
 */
public class TakeTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TakeTokenServlet() {
        super();
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		
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
		String fundProcessType=request.getParameter("fundProcessType");
		String divideDetail=request.getParameter("divideDetail");
		String divideNotifyUrl=request.getParameter("divideNotifyUrl");
		String goodsParamExt = "{\"goodsName\":\""+goodsName+"\",\"goodsDesc\":\""+goodsDesc+"\"}";
		String industryParamExt = "{\"bizSource\":\""+bizSource+"\",\"bizEntity\":\""+bizEntity+"\"}";
				System.out.println("goodsParamExt之json:"+goodsParamExt);
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
		params.put("fundProcessType", fundProcessType);
		params.put("divideDetail", divideDetail);
		params.put("divideNotifyUrl", divideNotifyUrl);
		System.out.println(params);
		String uri = YeepayService.getUrl(YeepayService.TRADEORDER_URL);
		Map<String, String> result = YeepayService.requestYOP(params, uri, YeepayService.TRADEORDER);
		System.out.println(result.get("token"));
		request.setAttribute("result", result);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/takeTokenResponse.jsp");
		view.forward(request, response);
	}

}
