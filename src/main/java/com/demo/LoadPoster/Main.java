package com.demo.LoadPoster;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.demo.LoadPoster.Utils.PropertiesUtil;

public class Main 
{
	private static Integer threadCount = 0;
	private static Integer threadExecCount = 0;
	private static String requestUrl = null;
	private static Integer requestCount = 0;
	private static ExecutorService ThreadPool = null;
	private static ExecutorService ThreadPoolExec = null;
	private static String fileName = null;

	static {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			System.out.println("start execute.....");
			exec();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void init() throws IOException {
		Properties properties = PropertiesUtil.getProperties("config.peoperties");
		fileName = properties.getProperty("config_file");
		threadCount = Integer.valueOf(properties.getProperty("thread_count"));
		threadExecCount = Integer.valueOf(properties.getProperty("thread_exec_count"));
		requestCount = Integer.valueOf(properties.getProperty("request_count"));
		requestUrl = properties.getProperty("request_url");
		ThreadPoolExec = Executors.newFixedThreadPool(threadExecCount);
		ThreadPool = Executors.newFixedThreadPool(threadCount);
	}

	private static void exec() throws InterruptedException, FileNotFoundException, UnsupportedEncodingException {
		//while(true) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new StressExecTask(fileName, requestUrl, ThreadPool), 0, 1000);
		//Thread.sleep(1000*60*115);
		//}

	}

}
