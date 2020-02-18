package dataflow.shiftjis;

import java.io.IOException;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.io.FileIO.ReadableFile;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

public class App {

  public interface Option extends PipelineOptions {
    String inputFile = "";
  }

  public static class DecodeShiftJISFiles extends DoFn<ReadableFile, String> {
    private static final long serialVersionUID = 1L;

    @ProcessElement
    public void process(ProcessContext c) throws IOException {
      byte[] bytes = c.element().readFullyAsBytes();
      String value = new String(bytes, "Shift-JIS");
      for (String line : value.split("\n")) {
        c.output(line);
      }
    }
  }

  public static class ParseCSV extends DoFn<String, String> {
    private static final long serialVersionUID = 1L;

    @ProcessElement
    public void process(ProcessContext c) throws IOException {
      System.out.println("========= f3");
      System.out.println(c.element());
      c.output("value");
    }
  }

  // https://medium.com/@darshan0mehta/how-to-read-file-with-apache-beam-1946d599e480
  public static void main(String[] args) {

    Option option = PipelineOptionsFactory.fromArgs(args).withValidation().as(Option.class);
    Pipeline p = Pipeline.create(option);

    // BeamTextCSVTable;
    p.apply(FileIO.match().filepattern("./shiftjis.txt"))
        .apply(FileIO.readMatches())
        .apply(ParDo.of(new DecodeShiftJISFiles()))
        .apply(ParDo.of(new ParseCSV()));
    p.run();
  }
}
