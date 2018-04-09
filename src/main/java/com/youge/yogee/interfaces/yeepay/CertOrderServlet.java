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
 * Servlet implementation class CertOrderServlet
 */
public class CertOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CertOrderServlet() {
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
		String requestNo = request.getParameter("requestNo");
		String bankCardNo = request.getParameter("bankCardNo");
		String idCardNo = request.getParameter("idCardNo");
		String userName = request.getParameter("userName");
		String authType = request.getParameter("authType");
		String requestTime = request.getParameter("requestTime");
		String remark = request.getParameter("remark");
		
		Map<String, String> params = new HashMap<>();
		params.put("requestNo", requestNo);
		params.put("bankCardNo", bankCardNo);
		params.put("idCardNo", idCardNo);
		params.put("userName", userName);
		params.put("authType", authType);
		params.put("requestTime", requestTime);
		params.put("remark", remark);
	
		Map<String, String> result = new HashMap<>();
		String uri = YeepayService.getUrl(YeepayService.CERTORDER_URL);
		result = YeepayService.requestYOP(params, uri, YeepayService.CERTORDER);
	
		request.setAttribute("result", result);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/certOrderResponse.jsp");
		view.forward(request, response);
	}

}
