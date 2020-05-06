package main.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger logger = Logger.getLogger(getClass().getName());


	// setup pointcut declarations for "controller" package
	@Pointcut("execution(* main.controller.*.*(..))")
	private void forControllerPackage() {}


	// setup pointcut declarations for "service" package
	@Pointcut("execution(* main.service.*.*(..))")
	private void forServicePackage() {}


	// setup pointcut declarations for "dao" package
	@Pointcut("execution(* main.dao.*.*(..))")
	private void forDaoPackage() {}

	// combining all
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}


	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {

		// display the method we are calling
		String methodName = joinPoint.getSignature().toShortString();
		logger.info("=====>>>> in @Before: calling method: " + methodName);


		// display the arguments to the method
		// get the arguments, loop through them and display them
		Object[] args = joinPoint.getArgs();
		for (Object arg: args) {
			logger.info("=====>>>> argument: " + arg);
		}
	}



	// add @AfterReturning advice
	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {

		// display method we are returning from
		String methodName = joinPoint.getSignature().toShortString();
		logger.info("=====>>>> in @AfterReturning: form method: " + methodName);

		// display data returned
		logger.info("=====>>>> result: " + result);

	}

}
