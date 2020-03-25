package demo.sharding.sphere.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class ReflectionUtil {
  @Nullable
  public static ParameterizedType getParameterizedType(@NonNull Class<?> interfaceClass, Class<?> implementClass) {
    Assert.isTrue(interfaceClass.isInterface(), "The first parameter should be an interface");

    if (implementClass == null) {
      return null;
    }

    ParameterizedType currentType = getParameterizedType(interfaceClass, implementClass.getGenericInterfaces());

    if (currentType != null) {
      return currentType;
    }

    return getParameterizedType(interfaceClass, implementClass.getSuperclass());
  }

  @Nullable
  public static ParameterizedType getParameterizedType(@NonNull Class<?> superClass, Type... genericTypes) {
    ParameterizedType currentType = null;
    for (Type genericType : genericTypes) {
      if (genericType instanceof ParameterizedType) {
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        if (parameterizedType.getRawType().getTypeName().equals(superClass.getTypeName())) {
          currentType = parameterizedType;
          break;
        }
      }
    }
    return currentType;
  }
}
