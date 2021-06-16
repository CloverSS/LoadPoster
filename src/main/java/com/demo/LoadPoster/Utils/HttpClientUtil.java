package com.demo.LoadPoster.Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Dsl.*;
import org.asynchttpclient.Response;
import org.json.JSONObject;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	
	private static AsyncHttpClient httpAsyncClient = new DefaultAsyncHttpClient();
	private static CloseableHttpAsyncClient httpClient =  HttpAsyncClients.custom()
			.setMaxConnTotal(1000).setMaxConnPerRoute(1000)//此处为多并发设置
			.build();
	private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

	static {
		httpClient.start();
	}
	
	public static boolean get(String url, Map<String, String> param) {
		// String result = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			httpClient.execute(httpGet,null);
			
			//Future<Response> f = httpAsyncClient.prepareGet(url).execute();
			
			/*if (response.getStatusLine().getStatusCode() == 200) {
				System.out.println("success get "+uri);
				// result = EntityUtils.toString(response.getEntity(), "utf-8");
				return true;
			} else {
				System.out.println("error when get "+uri);
				LOG.error("Request is unsuccess,responseCode:" + response.getStatusLine().getStatusCode()
						+ " ,responseMsg:" + EntityUtils.toString(response.getEntity(), "utf-8"));
			}*/
			return true;
		} catch (Exception e) {
			LOG.error("Request error,exceptionInfo:" + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static String post(String url, Map<String, String> param) {
		String result = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost post = new HttpPost(url);
			post.addHeader("Content-type", "application/json; charset=utf-8");
			requestSetEntity(post, param);
			httpClient.execute(post,null);
			//Future<Response> f = httpClient.preparePost(uri).execute();
			/*
			 * if (response.getStatusLine().getStatusCode() == 200) {
			 * System.out.println("success post "+url); result =
			 * EntityUtils.toString(response.getEntity(), "utf-8"); } else {
			 * System.out.println("error when post "+url);
			 * LOG.error("Request is unsuccess,responseCode:" +
			 * response.getStatusLine().getStatusCode()); //+ " ,responseMsg:" +
			 * EntityUtils.toString(response.getEntity(), "utf-8")); }
			 */

		} catch (Exception e) {
			LOG.error("Request error,exceptionInfo:" + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static String posttk(String url, JSONObject param) {
		String result = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost post = new HttpPost(url);
			post.addHeader("Content-type", "application/json");
			requestSetEntity(post, param.toString());
			httpClient.execute(post,null);
			//Future<Response> f = httpClient.preparePost(uri).execute();
			/*
			 * if (response.getStatusLine().getStatusCode() == 200) {
			 * System.out.println("success post "+url); result =
			 * EntityUtils.toString(response.getEntity(), "utf-8"); } else {
			 * System.out.println("error when post "+url);
			 * LOG.error("Request is unsuccess,responseCode:" +
			 * response.getStatusLine().getStatusCode()); //+ " ,responseMsg:" +
			 * EntityUtils.toString(response.getEntity(), "utf-8")); }
			 */

		} catch (Exception e) {
			LOG.error("Request error,exceptionInfo:" + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static String post(String url, String param) {
		String result = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost post = new HttpPost(url);
			requestSetEntity(post, param);
			httpClient.execute(post,null);
			/*if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			} else {
				LOG.error("Request is unsuccess,responseCode:" + response.getStatusLine().getStatusCode()
						+ " ,responseMsg:" + EntityUtils.toString(response.getEntity(), "utf-8"));
			}*/
		} catch (Exception e) {
			LOG.error("Request error,exceptionInfo:" + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private static void requestSetEntity(HttpEntityEnclosingRequestBase request, Map<String, String> data) {
		if (data != null && !data.isEmpty()) {
			List<NameValuePair> list = new ArrayList<>();
			for (String key : data.keySet()) {
				list.add(new BasicNameValuePair(key, data.get(key)));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity encodedFormEntity;
				try {
					encodedFormEntity = new UrlEncodedFormEntity(list, "utf-8");
					request.setEntity(encodedFormEntity);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}

			}
		}
	}

	private static void requestSetEntity(HttpEntityEnclosingRequestBase request, String param) {
		if (StringUtils.isNotEmpty(param)) {
			request.setEntity(new StringEntity(param, "utf-8"));
		}
	}
	
	static class Back implements FutureCallback<HttpResponse>{

        private long start = System.currentTimeMillis();
        Back(){
        }

        public void completed(HttpResponse httpResponse) {
        	//System.out.println("sucess request");
            /*try {
                System.out.println("cost is:"+(System.currentTimeMillis()-start)+":"+EntityUtils.toString(httpResponse.getEntity()));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        public void failed(Exception e) {
        	LOG.error("Request failed");
            System.err.println(" cost is:"+(System.currentTimeMillis()-start)+":"+e);
        }

        public void cancelled() {

        }
    }
}