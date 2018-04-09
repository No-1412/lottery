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
 * Servlet implementation class DownloadFileServle
 */
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFileServlet() {
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
		
		String method = request.getParameter("method");
		//String date = request.getParameter("date");
		String dataType = request.getParameter("dataType");
		
		String dateday=request.getParameter("dateday");
		String datemonth=request.getParameter("datemonth");
		System.out.println(method);
		//System.out.println(date);
		System.out.println(dataType);
		System.out.println(dateday);
		System.out.println(datemonth);
		//判断装入map的字段
		Map<String, String> params = new HashMap<>();
		if(method.equals("trademonth")){	
			params.put("method", method+"download");
			params.put("datemonth", datemonth);
			params.put("dataType", dataType);
		}else{
			params.put("method", method+"download");
			params.put("dateday", dateday);
			params.put("dataType", dataType);
		}
		
		//获得项目绝对路径	
		String realPath 	= this.getServletConfig().getServletContext().getRealPath("/"); 
				
		//对账文件的存储路径
		String path			= realPath + "downloadFile";
		
		System.out.println("path===="+path);
		//获取对账文件
		String filePath		= YeepayService.yosFile(params, path);

		System.out.println(filePath);
		
		request.setAttribute("filePath", filePath);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/downloadResponse.jsp");
		view.forward(request, response);
	}

}
