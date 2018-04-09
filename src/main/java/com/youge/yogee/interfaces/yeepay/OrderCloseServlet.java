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
 * Servlet implementation class OrderCloseServlet
 */
public class OrderCloseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderCloseServlet() {
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
		String orderId = request.getParameter("orderId");
		String uniqueOrderNo = request.getParameter("uniqueOrderNo");
		String description = request.getParameter("description");
		
		Map<String, String> params = new HashMap<>();
		params.put("orderId", orderId);
		params.put("uniqueOrderNo", uniqueOrderNo);
		params.put("description", description);
	
		Map<String, String> result = new HashMap<>();
		String url = YeepayService.getUrl(YeepayService.ORDERCLOSE_URL);
		result = YeepayService.requestYOP(params, url, YeepayService.ORDERCLOSE);
		
		request.setAttribute("result", result);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/orderCloseResponse.jsp");
		view.forward(request, response);
	}

}
