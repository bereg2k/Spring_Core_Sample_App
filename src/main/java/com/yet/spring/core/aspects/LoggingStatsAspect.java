package com.yet.spring.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LoggingStatsAspect {
    public static final Logger LOG = LoggerFactory.getLogger(LoggingStatsAspect.class);

    private Map<Class<?>, Integer> counters = new HashMap<>();

    public Map<Class<?>, Integer> getCounters() {
        return counters;
    }

    public void printStats() {
        System.out.println("=====LOGGERS USAGE STATISTICS=====");
        getCounters().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach((entry) -> System.out.println(
                        entry.getKey().getSimpleName() + " : " +
                                entry.getValue())
                );
        System.out.println("==================================");
    }

    @Pointcut("execution(* com.yet.spring.core.loggers.*.logEvent(..))")
    public void allLogEventMethods() {
    }

    @Before("allLogEventMethods()")
    public void logBefore(JoinPoint joinPoint) {
        LOG.info("BEFORE: " + joinPoint.getTarget().getClass().getSimpleName() +
                " " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "allLogEventMethods()")
    public void count(JoinPoint joinPoint) {
        Class<?> clazz = joinPoint.getTarget().getClass();
        counters.put(clazz, counters.getOrDefault(clazz, 0) + 1);
    }

    @AfterThrowing(pointcut = "allLogEventMethods()", throwing = "ex")
    public void logAfter(Exception ex) {
        LOG.warn("Exception thrown: " + ex);
    }
}
