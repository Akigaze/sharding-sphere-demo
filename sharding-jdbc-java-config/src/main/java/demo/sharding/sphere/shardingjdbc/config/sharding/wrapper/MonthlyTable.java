package demo.sharding.sphere.shardingjdbc.config.sharding.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class MonthlyTable implements Comparable<MonthlyTable> {
  private String tableName;

  private LocalDate month;

  @Override
  public int compareTo(MonthlyTable other) {
    if (other == null){
      return 1;
    }
    return month.compareTo(other.month);
  }

  public boolean isBefore(@NonNull LocalDate localDate) {
    return month.getYear() < localDate.getYear()
      || (month.getYear() == localDate.getYear() && month.getMonthValue() < localDate.getMonthValue());
  }

  public boolean isAfter(LocalDate localDate) {
    return month.getYear() > localDate.getYear()
      || (month.getYear() == localDate.getYear() && month.getMonthValue() > localDate.getMonthValue());

  }
}
