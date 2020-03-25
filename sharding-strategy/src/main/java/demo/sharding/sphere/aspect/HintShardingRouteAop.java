package demo.sharding.sphere.aspect;

import demo.sharding.sphere.model.entity.CsbcMessage;
import org.apache.shardingsphere.api.hint.HintManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HintShardingRouteAop {

  @Pointcut("execution(public * demo.sharding.sphere.repository.CsbcMessageRepository.*(..))")
  private void hintPointCut() {
  }

  @Around("hintPointCut()")
  public Object hintRoute(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();
    CsbcMessage shardingValue = null;
    for (Object arg : args) {
      if (arg instanceof CsbcMessage) {
        shardingValue = (CsbcMessage) arg;
        break;
      }
    }
    HintManager hintManager = HintManager.getInstance();
    if (shardingValue != null) {
      hintManager.addTableShardingValue("csbc_msg", shardingValue);
    }
    try {
      return joinPoint.proceed();
    } finally {
      hintManager.close();
    }
  }
}
