package shardingjdbc4.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 在 Repository 类上使用该注解，则可以把目标类的数据源指定成 sharding-jdbc 构建的数据源
 */
public @interface Sharding {

}
