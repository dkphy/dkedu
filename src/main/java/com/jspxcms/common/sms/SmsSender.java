package com.jspxcms.common.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信发送器
 * @author yz
 *
 */
public class SmsSender {

	public static final String account = "1001@501318520001";
	public static final String authKey = "242993BFFAA7DE5D27A44A693BA5938C";
	public static final String cgid = "7015";
	public static final String csid = "7276";

	private static final Logger logger = LoggerFactory.getLogger(SmsSender.class);

	/**
	 * 发送短信
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static boolean sendSms(String mobile, String content) {
		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(content)) {
			return false;
		}
		String urlFmt = "http://smsapi.c123.cn/OpenPlatform/OpenApi?action=sendOnce&ac=%1$s&authkey=%2$s&cgid=%3$s&csid=%4$s&c=%5$s&m=%6$s";
		String url = String.format(urlFmt, account, authKey, cgid, csid, content, mobile);
		String result = sendPost(url);
		logger.info(url);
		logger.debug("send sms result={}", result);
		if (result.contains("result=\"1\"")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @return 所代表远程资源的响应结果
	 */
	private static String sendPost(String url) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
