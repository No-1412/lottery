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
 * Servlet implementation class RefundApiServlet
 */
public class RefundApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefundApiServlet() {
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
		String refundRequestId = request.getParameter("refundRequestId");
		String refundAmount = request.getParameter("refundAmount");
		String description = request.getParameter("description");
		String memo = request.getParameter("memo");
		String notifyUrl = request.getParameter("notifyUrl");
		String accountDivided=request.getParameter("accountDivided");
		
		Map<String, String> params = new HashMap<>();
		params.put("orderId", orderId);
		params.put("uniqueOrderNo", uniqueOrderNo);
		params.put("refundRequestId", refundRequestId);
		params.put("refundAmount", refundAmount);
		params.put("description", description);
		params.put("memo", memo);
		params.put("notifyUrl", notifyUrl);
		params.put("accountDivided", accountDivided);
		System.out.println("description="+description);
		
		Map<String, String> result = new HashMap<>();
		String uri = YeepayService.getUrl(YeepayService.REFUND_URL);
		result = YeepayService.requestYOP(params, uri, YeepayService.REFUND);

		request.setAttribute("result", result);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/refundApiResponse.jsp");
		view.forward(request, response);
	}

}
