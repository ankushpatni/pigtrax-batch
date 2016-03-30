package com.pigtrax.batch.notification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.pigtrax.batch.exception.ErrorBeanCollection;

public class NotificationManager {
	private static final Logger logger = Logger.getLogger(NotificationManager.class);

	private LinkedBlockingQueue<ErrorBeanCollection> blockingQueue;

	private final ExecutorService executorService = Executors.newFixedThreadPool(1);
	
	Consumer consumer;
	
	public Consumer getConsumer() { 
		return consumer;
	}
	
	public LinkedBlockingQueue<ErrorBeanCollection> getBlockingQueue() {
		return blockingQueue;
	}


	@Autowired
	public void setBlockingQueue(
			LinkedBlockingQueue<ErrorBeanCollection> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}



	@Autowired
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
		executorService.execute(consumer);
	}

	
	
	public String put(ErrorBeanCollection be) {
		try {
			blockingQueue.put(be);
			logger.info("hello : "+blockingQueue);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "successs";
	}

}
