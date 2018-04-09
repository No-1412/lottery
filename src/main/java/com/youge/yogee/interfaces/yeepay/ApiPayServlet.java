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
 * Servlet implementation class ApiPayServlet
 */
public class ApiPayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApiPayServlet() {
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String parentMerchantNo = YeepayService.getParentMerchantNo();
		String merchantNo = YeepayService.getMerchantNo();

		String token = request.getParameter("token");
		String payTool = request.getParameter("payTool");
		String payType = request.getParameter("payType");
		String userNo = request.getParameter("userNo");
		String userType = request.getParameter("userType");
		String appId = request.getParameter("appId");
		String openId = request.getParameter("openId");
		String payEmpowerNo = request.getParameter("payEmpowerNo");
		String merchantTerminalId = request.getParameter("merchantTerminalId");
		String merchantStoreNo = request.getParameter("merchantStoreNo");
		String userIp = request.getParameter("userIp");
		String version = request.getParameter("version");

		Map<String, String> params = new HashMap<>();
		params.put("token", token);
		params.put("payTool", payTool);
		params.put("payType", payType);
		params.put("userNo", userNo);
		params.put("userType", userType);
		params.put("appId", appId);
		params.put("openId", openId);
		params.put("payEmpowerNo", payEmpowerNo);
		params.put("merchantTerminalId", merchantTerminalId);
		params.put("merchantStoreNo", merchantStoreNo);
		params.put("userIp", userIp);
		params.put("version", version);

		String uri = YeepayService.getUrl(YeepayService.APICASHIER_URI);
		Map<String, String> result = YeepayService.requestYOP(params, uri, YeepayService.APICASHIER);
		request.setAttribute("result", result);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/apiPayResponse.jsp");
		view.forward(request, response);
	}

}
