package com.springmvc.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	/**
	 * 发送post请求--用于接口接收的参数为键值对
	 * 
	 * @param url
	 *            请求地址
	 * @param nameValuePairs
	 *            键值对
	 * @return
	 */
	public static String httpPost(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;

		List<NameValuePair> nameValuePairs = new ArrayList<>();
		if (params != null) {
			for (Entry<String, String> param : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			response = httpClient.execute(httpPost);

			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
				/* 读返回数据 */
				return EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public static String httpPost(String url) {
		return httpPost(url, null);
	}
	public static String httpGet(String url) {
		return httpGet(url, null);
	}

	public static String httpGet(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		StringBuilder sb = new StringBuilder();
		String queryString = "";
		
		CloseableHttpResponse response = null;
		try {
			if (params != null) {
				for (Entry<String, String> param : params.entrySet()) {
					sb.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(), "UTF-8")).append("&");
				}
				queryString = "?" + sb.toString().substring(0, sb.length() - 1);
			}
			HttpGet httpGet = new HttpGet(url + queryString);
//			httpGet.addHeader("content-type", "text/html;charset=utf-8");
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				//一定要以utf-8来解析返回报文
				return EntityUtils.toString(response.getEntity(),"utf-8");
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Map<String, String> params = new HashMap<>();
		params.put("wd", "张三");
//		String httpPost = httpPost("http://localhost:8080/mall_0525_sale_teacher/testHttpUtil.do", params);
//		System.out.println(httpPost);
		String httpGet = httpGet("http://localhost:8089/skuInfo/overSale/1");
		System.out.println(httpGet);
	}
}
