package com.cisco.cmad.event.aspect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 
 */

/**
 * @author tcheedel
 *
 */
@Aspect
@Component
public class EventsMonitorAspect {

    DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
    
    EventsMonitorAspect() {
        System.out.println("in EventsMonitorAspect");
    }

    //@Around("sampleMethodPointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("in aspect..."+joinPoint.toString());
		
		Object signatureArgs = joinPoint.getArgs()[0];
		
		System.out.println(signatureArgs.toString());
		
		String returnVal = df.format(new Date(signatureArgs.toString()));
		
		System.out.println("returnVal: "+returnVal);
		joinPoint.proceed(new Object[] {returnVal});
		return returnVal;
		
        /*
         * Object value = joinPoint.proceed(); System.out.println("Value in aop is "+
         * value); return value;
         */
	}

    //@Pointcut("execution(* com.cisco.cmad.event.dao.JsonEvent.setTimestamp(..))")
    public void sampleMethodPointcut() {

    }

}
