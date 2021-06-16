package com.demo.LoadPoster.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class UserBehavior {
	private static String BaseUrl;
	private static AtomicInteger autoCount=new AtomicInteger(0);
	
	static {
		Properties properties;
		try {
			properties = PropertiesUtil.getProperties("config.peoperties");
			BaseUrl = properties.getProperty("request_url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String[] products = { "0PUK6V6EV0", "1YMWWN1N4O", "2ZYFJ3GM2N", "66VCHSJNUP", "6E92ZMYYFZ",
			"9SIQT8TOJO", "L9ECAV7KIM", "LS4PSXUNUM", "OLJCESPC7Z" };
	private static String[] currencies = { "EUR", "USD", "JPY", "CAD" };
	private static String[] quantity = {"1","2","3","4","5","10"};
	
	public static void doTask() {
		browseProduct();
		/*if(autoCount.getAndIncrement()>1000)
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println("start time" + df.format(new Date()));
		}
		int[] per = {1,1+2,3+1,4+9,13+11,24+1};
		int per_sum = 19;
		Random rand = new Random();
		int index = rand.nextInt(per_sum);
		if(index>=0&&index<per[0])
			index();
		else if(index>=per[0]&&index<per[1])
			setCurrency();
		else if(index>=per[1]&&index<per[2])
			browseProduct();
		else if(index>=per[2]&&index<per[3])
			viewCart();
		else if(index>=per[3]&&index<per[4])
			addToCart();
		else if(index>=per[3]&&index<per[4])
			checkout();*/
	}
	
	
	public static void index() {
		//System.out.println("index: "+BaseUrl);
		HttpClientUtil.get(BaseUrl, null);
	}

	public static void setCurrency() {
		//System.out.println("setCurrency: "+BaseUrl + "/setCurrency");
		Map<String, String> param = new HashMap<>();
		param.put("currency_code", randomChoice(currencies));
		HttpClientUtil.post(BaseUrl + "/setCurrency", param);
	}

	public static void browseProduct() {
		//System.out.println("browseProduct: "+BaseUrl+ "/product/");
		HttpClientUtil.get(BaseUrl + "/product/" + randomChoice(products), null);
	}

	public static void viewCart() {
		//System.out.println("viewCart: "+BaseUrl+"/cart");
		HttpClientUtil.get(BaseUrl + "/cart", null);
	}

	public static void addToCart() {
		//System.out.println("addToCart: "+BaseUrl+"/product/");
		String product = randomChoice(products);
		HttpClientUtil.get(BaseUrl+"/product/"+ product,null);
		Map<String, String> param = new HashMap<>();
		param.put("product_id", product);
		param.put("quantity", randomChoice(quantity));
		HttpClientUtil.post(BaseUrl+"/cart", param);
	}

	public static void checkout() {
		//System.out.println("checkout: "+BaseUrl+"/cart/checkout");
		Map<String, String> param = new HashMap<>();
		param.put("email", "someone@example.com");
		param.put("street_address", "1600 Amphitheatre Parkway");
		param.put("zip_code", "94043");
		param.put("city", "Mountain View");
		param.put("state", "CA");
		param.put("country","United States");
		param.put("credit_card_number", "4432-8015-6152-0454");
		param.put("credit_card_expiration_month", "1");
		param.put("credit_card_expiration_year", "2039");
		param.put("credit_card_cvv", "672");
		HttpClientUtil.post(BaseUrl+"/cart/checkout", param);
	}
	
	private static <T> T randomChoice(T[] strs) {
		Random rand = new Random();
		int index = rand.nextInt(strs.length);
		return strs[index];
	}

}