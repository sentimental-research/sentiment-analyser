/**
 *
 */
package org.openpreservation.sentitment;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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

  /**
   * Test method for
   * {@link org.openpreservation.sentiment.Main#cleanup(java.lang.String)}
   * .
   */
  @Test
  public void testCleanupUsername() {
    String input = "@SoftwareSaved You're awesome";
    String expected = " You're awesome";

    String actual = Main.cleanup(input);

    assertEquals("failure - remove twitter username", expected, actual);
  }

  /**
   * Test method for
   * {@link org.openpreservation.sentiment.Main#cleanup(java.lang.String)}
   * .
   */
  @Test
  public void testCleanupHashtag() {
    String input = "SSI is awesome #collabw16";
    String expected = "SSI is awesome";

    String actual = Main.cleanup(input);

    assertEquals("failure - remove hashtag", expected, actual);
  }

  /**
   * Test method for
   * {@link org.openpreservation.sentiment.Main#cleanup(java.lang.String)}
   * .
   */
  @Test
  public void testCleanupURL() {
    String input = "Everyone take a look at the SSI: https://www.software.ac.uk!";
    String expected = "Everyone take a look at the SSI:!";

    String actual = Main.cleanup(input);

    assertEquals("failure - remove URL", expected, actual);
  }

  /**
   * Test method for
   * {@link org.openpreservation.sentiment.Main#cleanup(java.lang.String)}
   * .
   */
  @Test
  public void testCleanup() {
    String input = "@everyone take a look at the Sustainable Software Institute: https://www.software.ac.uk, it's awesome #collabw16";
    String expected = " take a look at the Sustainable Software Institute:, it's awesome";

    String actual = Main.cleanup(input);

    assertEquals("failure - remove URL", expected, actual);
  }
}

