package com.pigtrax.batch.notification;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.beans.DataIntegrityLog;
import com.pigtrax.batch.dao.interfaces.DataIntegrityLogDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.exception.ErrorBeanCollection;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;

@Component
public class Consumer implements Runnable{

	private static final Logger logger = Logger.getLogger(Consumer.class);
	
    private  LinkedBlockingQueue<ErrorBeanCollection> sharedQueue;  

    public LinkedBlockingQueue<ErrorBeanCollection> getSharedQueue() {
		return sharedQueue;
	}

    @Autowired
	public void setSharedQueue(LinkedBlockingQueue<ErrorBeanCollection> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}
    
    @Autowired
    DataIntegrityLogDao logDao;
    
	@Override
    public void run() {
        while(true){
            try {
            	
            	ErrorBeanCollection errorBeanCollection = sharedQueue.take();
                String eventType = errorBeanCollection.getEventType();
            	if(errorBeanCollection != null)
            	{
            		Map<Mapper, List<ErrorBean>> errorMap = errorBeanCollection.getErrorBeanList();
            		if(errorMap != null)
            		{
            			Iterator<Mapper> itr = errorMap.keySet().iterator();
            			Mapper mapper = null;
            			List<ErrorBean> errorBeanList = null;
            			int i = 0;
            			while (itr.hasNext()) {
            				i++;
            				mapper = (itr.next());
            				errorBeanList = errorMap.get(mapper);
            				for (ErrorBean errBean : errorBeanList) {	
            					DataIntegrityLog log = populateDataIntegrityBean(eventType, mapper, errBean);
            					logDao.insert(log);
            					}
            				}
            			}
            		}
            	
                
            } catch (InterruptedException ex) {
            	ex.printStackTrace();            	
            }
            
        }
    }
  
	
	private DataIntegrityLog populateDataIntegrityBean(String eventType, Mapper mapper, ErrorBean errBean)
	{
		
		DataIntegrityLog log = new DataIntegrityLog();
		log.setEventType(eventType);
		if(errBean.isRecoverable())
			log.setErrorType("Integrity Error");
		else
			log.setErrorType("Structural Error");
		log.setEventDate(DateUtil.getCurrentDate());
		log.setErrorDescription(errBean.getCode()+" : "+errBean.getMessage());
		return log;
	}
  
	
	
}


