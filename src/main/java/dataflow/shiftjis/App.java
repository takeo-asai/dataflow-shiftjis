package dataflow.shiftjis;

import dataflow.shiftjis.dofn.ParseShiftJISCSV;
import dataflow.shiftjis.dofn.Print;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;

public class App {

  public interface Option extends PipelineOptions {
    @Description("Path of the file to read from")
    @Default.String("./shiftjis.txt")
    String getInputFile();

    void setInputFile(String value);
  }

  public static void main(String[] args) {

    Option option = PipelineOptionsFactory.fromArgs(args).withValidation().as(Option.class);
    Pipeline p = Pipeline.create(option);

    p.apply(FileIO.match().filepattern(option.getInputFile()))
        .apply(FileIO.readMatches())
        .apply(ParDo.of(new ParseShiftJISCSV()))
        .apply(ParDo.of(new Print()));
    p.run();
  }
}
