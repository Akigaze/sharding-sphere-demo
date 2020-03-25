package demo.sharding.sphere.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class BeanUtil {
  public static void updateProperties(@NonNull Object source, @NonNull Object target) {
    try {
      BeanUtils.copyProperties(source, target);
    } catch (Exception exception) {
      throw new RuntimeException("Fail when copyProperties", exception);
    }
  }

  @Nullable
  public static <T> T transformFrom(@Nullable Object source, @NonNull Class<T> targetClass) {
    if (source == null) {
      return null;
    }
    try {
      T target = targetClass.newInstance();
      BeanUtil.updateProperties(source, target);
      return target;
    } catch (Exception exception) {
      throw new RuntimeException("Fail to new " + targetClass.getName() + "instance or copy properties", exception);
    }
  }
}
