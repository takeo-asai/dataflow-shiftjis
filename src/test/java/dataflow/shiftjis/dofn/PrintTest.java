package dataflow.shiftjis.dofn;

import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Rule;
import org.junit.Test;

public class PrintTest {

  @Rule public final transient TestPipeline pipeline = TestPipeline.create();

  @Test
  public void WHEN_bean_is_created_THEN_Print_DoFn_works() {
    PCollection<String> a =
        pipeline
            .apply(Create.of(new Bean("this-is-name", "this-is-value")))
            .apply(ParDo.of(new Print()));

    PAssert.that(a).containsInAnyOrder("Bean{name='this-is-name', value='this-is-value'}");

    pipeline.run();
  }
}
