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
 * Servlet implementation class CashierApiServlet
 */
public class CashierApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CashierApiServlet() {
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
		String timestamp = request.getParameter("timestamp");
		String directPayType = request.getParameter("directPayType");
		String cardType = request.getParameter("cardType");
		String userNo = request.getParameter("userNo");
		String userType = request.getParameter("userType");
		String appId = request.getParameter("appId");
		String openId = request.getParameter("openId");
		String clientId = request.getParameter("clientId");
		
		String ext = "{\"appId\":\""+appId+"\",\"openId\":\""+openId+"\",\"clientId\":\""+clientId+"\"}";
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("parentMerchantNo", parentMerchantNo);
		params.put("merchantNo", merchantNo);
		params.put("token", token);
		params.put("timestamp", timestamp);
		params.put("directPayType", directPayType);
		params.put("cardType", cardType);
		params.put("userNo", userNo);
		params.put("userType", userType);
		params.put("ext", ext);
		
		String url = YeepayService.getUrl(params);
		System.out.println(url);
		
		request.setAttribute("url", url);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/sendURL.jsp");
		view.forward(request, response);
	}

}
