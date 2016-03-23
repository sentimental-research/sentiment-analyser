/**
 *
 */
package org.openpreservation.sentitment;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openpreservation.sentiment.Main;

import java.io.*;
import com.opencsv.CSVReader;

/**
 * @author Gary Macindoe
 */
public class MainTest {

  /**
   * Test method for
   * {@link org.openpreservation.sentiment.Main#main(java.lang.String[])}
   * .
   */
  @Test
  public void testMain() throws IOException, FileNotFoundException {
    ClassLoader classLoader = getClass().getClassLoader();
    File input = new File(classLoader.getResource("output_got.csv").getFile());
    File output = new File(input.getParent() + File.separator + "output_test.csv");

    String[] args = { input.getAbsolutePath(), output.getAbsolutePath() };

    assert(!output.exists());

    Main.main(args);

    assert(output.exists());

    CSVReader reader = new CSVReader(new BufferedReader(new FileReader(output)));

    String[] line;
    while ((line = reader.readNext()) != null)
      assert(line.length == 5);

    reader.close();
  }
}

