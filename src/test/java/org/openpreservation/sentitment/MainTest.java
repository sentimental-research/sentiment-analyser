/**
 *
 */
package org.openpreservation.sentitment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.junit.Test;
import org.openpreservation.sentiment.Main;

import com.opencsv.CSVReader;

/**
 * @author Gary Macindoe
 */
public class MainTest {

	/**
	 * Test method for
	 * {@link org.openpreservation.sentiment.Main#main(java.lang.String[])} .
	 */
	@Test
	public void testMain() throws IOException, FileNotFoundException {
		ClassLoader classLoader = getClass().getClassLoader();
		File input = new File(classLoader.getResource("tweets.csv").getFile());
		File output = new File(input.getParent() + File.separator + "output_test.csv");
		if (output.exists())
			output.delete();
		assertFalse(output.exists());

		Reader reader = new BufferedReader(new FileReader(input));
		Writer writer = new BufferedWriter(new FileWriter(output));

		Main.annotateCsv(reader, writer);
		System.out.println("finished");
		assertTrue(output.exists());

		try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(output)), ',', '"')) {

			String[] line;
			int lineCounter = 0;
			while ((line = csvReader.readNext()) != null) {
				lineCounter++;
				assertTrue(lineCounter + ":" + line.length, line.length == 5);
			}
		}
	}

	/**
	 * Test method for
	 * {@link org.openpreservation.sentiment.Main#cleanup(java.lang.String)} .
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
	 * {@link org.openpreservation.sentiment.Main#cleanup(java.lang.String)} .
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
	 * {@link org.openpreservation.sentiment.Main#cleanup(java.lang.String)} .
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
	 * {@link org.openpreservation.sentiment.Main#cleanup(java.lang.String)} .
	 */
	@Test
	public void testCleanup() {
		String input = "@everyone take a look at the Sustainable Software Institute: https://www.software.ac.uk, it's awesome #collabw16";
		String expected = " take a look at the Sustainable Software Institute:, it's awesome";

		String actual = Main.cleanup(input);

		assertEquals("failure - remove URL", expected, actual);
	}
}
