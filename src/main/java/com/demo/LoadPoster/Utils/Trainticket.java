package com.demo.LoadPoster.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;

public class Trainticket {
	private static String BaseUrl;
	private static AtomicInteger autoCount=new AtomicInteger(0);
	
	static {
		Properties properties;
		try {
			properties = PropertiesUtil.getProperties("config.peoperties");
			BaseUrl = properties.getProperty("train_url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String startstation = "nanjing";
	private static String[] endstation = { "shanghai", "taiyuan", "suzhou", "jinan"};
	
	
	public static void doTask() {
		searchTravel();
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

	public static void searchTravel() {
		//System.out.println("setCurrency: "+BaseUrl + "/setCurrency");
		JSONObject param = new JSONObject();
		param.put("startingPlace", "nanjing");
		param.put("endPlace", randomChoice(endstation));
		param.put("departureTime", "2021-02-26");

		HttpClientUtil.posttk(BaseUrl + "/api/v1/travel2service/trips/left", param);
	}

	
	private static <T> T randomChoice(T[] strs) {
		Random rand = new Random();
		int index = rand.nextInt(strs.length);
		return strs[index];
	}

}