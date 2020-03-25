package demo.sharding.sphere.common.model.base;


import demo.sharding.sphere.common.util.BeanUtil;
import org.springframework.lang.NonNull;

public interface OutputConverter<D extends OutputConverter<D, E>, E> {

  @SuppressWarnings("unchecked")
  @NonNull
  default D convertFrom(@NonNull E entity) {
    BeanUtil.updateProperties(entity, this);
    return (D) this;
  }
}

