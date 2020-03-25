package demo.sharding.sphere.common.model.base;


import demo.sharding.sphere.common.util.BeanUtil;
import demo.sharding.sphere.common.util.ReflectionUtil;
import java.lang.reflect.ParameterizedType;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public interface InputConverter<E> {

  @SuppressWarnings("unchecked")
  default E convertTo() {
    ParameterizedType entityType = this.parameterizedType();
    Assert.notNull(entityType, "Can't get actual type because parameterized type is null");

    Class<E> entityClass = (Class<E>) entityType.getActualTypeArguments()[0];
    return BeanUtil.transformFrom(this, entityClass);
  }

  default void update(E entity) {
    BeanUtil.updateProperties(this, entity);
  }

  @Nullable
  default ParameterizedType parameterizedType() {
    return ReflectionUtil.getParameterizedType(InputConverter.class, this.getClass());
  }
}

