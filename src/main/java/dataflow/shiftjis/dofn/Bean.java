package dataflow.shiftjis.dofn;

import java.io.Serializable;

public class Bean implements Serializable {
  private String name;
  private String value;

  public Bean(String name, String value) {
    this.name = name;
    this.value = value;
  }

  private String name() {
    return this.name;
  }

  private String value() {
    return this.value;
  }

  @Override
  public String toString() {
    return "Bean{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
  }
}
