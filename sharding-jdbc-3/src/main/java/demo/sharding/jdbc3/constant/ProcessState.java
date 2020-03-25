package demo.sharding.jdbc3.constant;

import java.util.Arrays;
import java.util.List;
import org.springframework.util.StringUtils;

public enum ProcessState {
  RECEIVED, PARSING_ERROR, PUBLISH_ERROR, PUBLISHED,
  PROCESSING, PROCESSING_ERROR, COMPLETED, NONE;

  public static final List<ProcessState> errors = Arrays
      .asList(PARSING_ERROR, PUBLISH_ERROR, PROCESSING_ERROR);

  public static boolean isErrorState(String state) {
    if (StringUtils.isEmpty(state)) {
      return false;
    }
    return errors.contains(ProcessState.valueOf(state));
  }
}
