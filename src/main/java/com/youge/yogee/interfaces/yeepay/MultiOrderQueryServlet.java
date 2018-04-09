package com.youge.yogee.interfaces.yeepay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * Servlet implementation class MultiOrderQueryServlet
 */
public class MultiOrderQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MultiOrderQueryServlet() {
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
		String status 			= request.getParameter("status");
		String requestDateBegin = request.getParameter("requestDateBegin");
		String requestDateEnd 	= request.getParameter("requestDateEnd");
		String pageNo			= request.getParameter("pageNo");
		String pageSize 		= request.getParameter("pageSize");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("status", status);
		params.put("requestDateBegin", requestDateBegin);
		params.put("requestDateEnd", requestDateEnd);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		
		System.out.println(params);
		
		String multiOrderQueryURL = YeepayService.getUrl(YeepayService.MULTIORDERQUERY_URL);
		Map<String, String> jsonMap = YeepayService.requestYOP(params, multiOrderQueryURL, YeepayService.MULTIORDERQUERY);
		Map<String, Object> result = new HashMap<>();
		
		String code = jsonMap.get("code");
		String message = jsonMap.get("message");
		result.put("code", code);
		result.put("message", message);
		
		if(jsonMap.get("orderList") != null){
			String orderListString = jsonMap.get("orderList");
			orderListString = orderListString.substring(1, orderListString.length()-1);
			String[] order = orderListString.split("},");
			List<Map<String, Object>> orderList = new ArrayList<>();
			for (int i = 0; i < order.length; i++) {
				if(i != order.length-1)
					order[i] = order[i] + "}"; 
				System.out.println(order[i]);
				Map<String, Object> orderMap  = new HashMap<>();
				orderMap = JSON.parseObject(order[i],
						new TypeReference<TreeMap<String, Object>>() {});
				String goodsParamExt = (String) orderMap.get("goodsParamExt");
				Map<String, String> goodsParamExtMap = JSON.parseObject(goodsParamExt,
						new TypeReference<TreeMap<String, String>>() {});
				orderMap.put("goodsParamExt", goodsParamExtMap);
				orderList.add(orderMap);
			}
			
			String parentMerchantNoR = jsonMap.get("parentMerchantNo");
			String merchantNoR = jsonMap.get("merchantNo");
		
			String totalRecords = jsonMap.get("totalRecords");
			String pageNoR = jsonMap.get("pageNo");
			String pageSizeR = jsonMap.get("pageSize");
			
			result.put("parentMerchantNo", parentMerchantNoR);
			result.put("totalRecords", totalRecords);
			result.put("pageNo", pageNoR);
			result.put("pageSize", pageSizeR);
			result.put("orderList", orderList);
			result.put("requestDateBegin", requestDateBegin);
			result.put("requestDateEnd", requestDateEnd);
			result.put("merchantNo", merchantNoR);
			result.put("status", status);
			
		}
		
		System.out.println(result);
		request.setAttribute("result", result);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/multiOrderQuery.jsp");
		view.forward(request, response);
	}

}
