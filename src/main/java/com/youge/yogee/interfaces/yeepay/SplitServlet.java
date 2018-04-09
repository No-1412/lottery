package com.youge.yogee.interfaces.yeepay;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SplitServlet extends HttpServlet{

	  @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // TODO Auto-generated method stub
	        super.doPost(request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // TODO Auto-generated method stub
	        request.setCharacterEncoding("utf-8");
	        response.setCharacterEncoding("utf-8");
	        Map<String, String> result = new HashMap<String, String>();

	        String ledgerNo = format(request.getParameter("ledgerNo"));
	        String ledgerNoArr[] = ledgerNo.split("&");
	        String ledgerName = format(request.getParameter("ledgerName"));


	        //判断ledgerName是否为空
	        String[] ledgerNameArr = new String[]{};
	        if (ledgerName != null && !"".equals(ledgerName)) {
	            ledgerNameArr = ledgerName.split("&");
	        }

	        ledgerName = conactStringArr(ledgerNameArr);


	       
	        String dividemode = format(request.getParameter("dividemode"));

	        String amount = format(request.getParameter("amount"));
	        String amountArr[] = amount.split("&");
	        String proportion = format(request.getParameter("proportion"));
	        String proportionArr[] = proportion.split("&");

	        //首先判断是否输入对应
	        if (dividemode.equals("amount")) {
	            if (ledgerNoArr.length == amountArr.length) {
	                System.out.println("分账方数量和分账值数量对应");
	                StringBuffer sb = new StringBuffer();
	                sb.append("[");
	                System.out.println("sb:" + sb);
	                for (int i = 0; i < ledgerNoArr.length; i++) {
	                    sb.append("{");
	                    sb.append("\"ledgerNo\":\"").append(ledgerNoArr[i]).append("\",");

	                    if (ledgerName != null && !"".equals(ledgerName)) {
	                        sb.append("\"ledgerName\":\"").append(ledgerName).append("\",");
	                    }

	                    sb.append("\"amount\":\"").append(amountArr[i]).append("\"");


	                    sb.append("},");
	                }
	                sb.replace(sb.length() - 1, sb.length(), "");
	                sb.append("]");
	                System.out.println(sb);
	                result.put("dividelist", sb.toString());
	            } else {
	                if (ledgerNoArr.length > amountArr.length) {
	                    System.err.println("分账方数量和分账值数量不对应");
	                    result.put("errorMsg", "输入的分账方数量超过分账值的数量，请重新确认!");
	                } else {
	                    result.put("errorMsg", "输入的分账方数量少于分账值的数量，请重新确认！");
	                }
	            }
	        } else {

	            if (ledgerNoArr.length == proportionArr.length) {
	                System.out.println("分账方数量和分账值数量对应");
	                StringBuffer sb = new StringBuffer();
	                sb.append("[");
	                System.out.println("sb:" + sb);

	                for (int i = 0; i < ledgerNoArr.length; i++) {
	                    sb.append("{");
	                    sb.append("\"ledgerNo\":\"" + ledgerNoArr[i] + "\",\"ledgerName\":\"" + ledgerName + "\",\"proportion\":\"" + proportionArr[i] + "\"");
	                    sb.append("},");
	                }
	                sb.replace(sb.length() - 1, sb.length(), "");
	                sb.append("]");
	                System.out.println(sb);
	                result.put("dividelist", sb.toString());
	            } else {
	                if (ledgerNoArr.length > proportionArr.length) {
	                    System.err.println("分账方数量和分账值数量不对应");
	                    result.put("errorMsg", "输入的分账方数量超过分账值的数量，请重新确认!");
	                } else {
	                    result.put("errorMsg", "输入的分账方数量少于分账值的数量，请重新确认！");
	                }
	            }
	        }
	        //结果
	        if (!result.isEmpty() || result.size() != 0) {
	            System.out.println("错误信息：" + result.get("errorMsg"));
	            request.setAttribute("result", result);
	            RequestDispatcher view = request.getRequestDispatcher("/jsp/splitoolresponse.jsp");
	            view.forward(request, response);


	        }

	    }

	    public String format(String text) {
	        return text == null ? "" : text.trim();
	    }

	    private String conactStringArr(String[] arr) {
	        String string = "";
	        for (String str : arr) {
	            string += str;
	        }
	        return string;
	    }
	}
