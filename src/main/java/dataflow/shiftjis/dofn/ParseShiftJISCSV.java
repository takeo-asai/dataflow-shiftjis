package dataflow.shiftjis.dofn;

import java.io.IOException;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ParseShiftJISCSV extends DoFn<FileIO.ReadableFile, Bean> {
  private static final long serialVersionUID = 1L;

  @ProcessElement
  public void process(ProcessContext c) throws IOException {
    byte[] bytes = c.element().readFullyAsBytes();
    String value = new String(bytes, "Shift-JIS");
    CSVParser parser = CSVParser.parse(value, CSVFormat.EXCEL.withHeader("name", "value"));
    for (CSVRecord r : parser) {
      c.output(new Bean(r.get("name"), r.get("value")));
    }
  }
}
