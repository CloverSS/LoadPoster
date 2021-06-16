package com.demo.LoadPoster;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

class StressExecTask extends TimerTask {
	private FileInputStream fis = null;
	private InputStreamReader isr = null;
	private BufferedReader br = null;
	private String requestUrl;
	private ExecutorService ThreadPool;
	private String fileName;

	public StressExecTask(String fileName, String requestUrl, ExecutorService ThreadPool)
			throws FileNotFoundException, UnsupportedEncodingException {
		if (this.br == null) {
			this.fis = new FileInputStream(fileName);
			this.isr = new InputStreamReader(fis, "UTF-8");
			this.br = new BufferedReader(isr);
		}
		this.requestUrl = requestUrl;
		this.ThreadPool = ThreadPool;
		this.fileName = fileName;
	}

	public void run() {
		try {
				String line = null;
				if ((line = br.readLine()) != null) {
					Integer requestCount = Integer.valueOf(line);
				    //requestCount = 35;
					Integer perMin = 60;
					System.out.println(requestCount);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					System.out.println("start time" + df.format(new Date()));
					CountDownLatch requestCountDown = new CountDownLatch(perMin);
					Integer perCount = requestCount / perMin;
					Integer modCount = requestCount % perMin;
					for (int per = 1; per <= perMin; per++) {
						int perIndex = per;
						// new Thread(() -> {
						RequestThread requestThread = new RequestThread(requestUrl, requestCountDown,
								perCount + (perIndex <= modCount ? 1 : 0));
						ThreadPool.execute(requestThread);
						// }).start();
						if (per < perMin)
							Thread.sleep(10);
					}
					requestCountDown.await();
				} else {
					System.out.println("cancel");
					br.close();
					isr.close();
					fis.close();
					cancel();
				}
			
		} catch (Exception e) {
			System.out.println(e);
			cancel();
		}
	}
}