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
 * Servlet implementation class CertOrderQueryServlet
 */
public class CertOrderQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CertOrderQueryServlet() {
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
		String ybOrderId = request.getParameter("ybOrderId");
		
		Map<String, String> params = new HashMap<>();
		params.put("requestNo", requestNo);
		params.put("ybOrderId", ybOrderId);

		Map<String, String> result = new HashMap<>();
		String uri = YeepayService.getUrl(YeepayService.CERTORDERQUERY_URL);
		result = YeepayService.requestYOP(params, uri, YeepayService.CERTORDERORDER);
	
		request.setAttribute("result", result);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/certOrderQueryResponse.jsp");
		view.forward(request, response);
	}

}
