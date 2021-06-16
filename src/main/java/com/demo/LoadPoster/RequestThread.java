package com.demo.LoadPoster;
import com.demo.LoadPoster.Utils.*;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestThread implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(RequestThread.class);
	private CountDownLatch countDownLatch;
	private String requestUrl;
	private int timeCount;

	public RequestThread(String requestUrl, CountDownLatch countDownLatch,int timeCount) {
		this.requestUrl = requestUrl;
		this.countDownLatch = countDownLatch;
		this.timeCount = timeCount;
	}

	public void run() {
		try {
			for(int i=0;i<timeCount;i++)
				UserBehavior.doTask();
				//Trainticket.doTask();
			/*if (!isSuccess) {
				LOG.error("response error.....threadName:" + Thread.currentThread().getName());
			}*/
			countDownLatch.countDown();
		} catch (Exception e) {
			LOG.error("requset error.....threadName:" + Thread.currentThread().getName());
		}

	}

}