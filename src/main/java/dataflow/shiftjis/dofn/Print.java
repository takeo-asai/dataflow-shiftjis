package dataflow.shiftjis.dofn;

import java.io.IOException;
import org.apache.beam.sdk.transforms.DoFn;

public class Print extends DoFn<Bean, String> {
  private static final long serialVersionUID = 1L;

  @ProcessElement
  public void process(ProcessContext c) throws IOException {
    System.out.println(c.element());
    c.output(c.element().toString());
  }
}
