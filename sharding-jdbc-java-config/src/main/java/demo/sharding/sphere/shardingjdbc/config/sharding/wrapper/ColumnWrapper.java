package demo.sharding.sphere.shardingjdbc.config.sharding.wrapper;

public interface ColumnWrapper<T extends Comparable<?>> {
  String getColumnName();

  T getValue();
}
