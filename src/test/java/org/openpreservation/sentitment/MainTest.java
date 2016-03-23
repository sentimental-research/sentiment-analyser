/**
 *
 */
package org.openpreservation.sentitment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		File input = new File(classLoader.getResource("output_got.csv").getFile());
		File output = new File(input.getParent() + File.separator + "output_test.csv");
		assertFalse(output.exists());

		Reader reader = new BufferedReader(new FileReader(input));
		Writer writer = new BufferedWriter(new FileWriter(output));

		Main.annotateCsv(reader, writer);
		System.out.println("finished");
		assertTrue(output.exists());

		try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(output)))) {

			String[] line;
			while ((line = csvReader.readNext()) != null)
				assertTrue(line.length == 5);

		}
	}
}
