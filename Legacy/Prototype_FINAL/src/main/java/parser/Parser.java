package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * A Parser parses the data from a CSV and creates DataObject.
 * 
 * @author Walrus
 *
 */
public class Parser {
	BufferedReader reader;
	String filename = "";
	String headers[];

	/**
	 * Parse the CSV file and create a DataObject
	 * 
	 * @param fileName The filename to parse
	 * @return the DataObject
	 * @throws IOException
	 */
	public DataObject parse(String fileName) throws IOException {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		// read in the CSV file.
		reader = new BufferedReader(new FileReader("Logs//" + fileName));

		// save the headers to a global array so it can be used throughout the
		// program
		headers = reader.readLine().split(",");

		// save the FIRST line of data to an array so it can be used to store
		// into the HashMap
		String data[] = reader.readLine().split(",");

		// populate the HashMap with header and data
		for (int i = 0; i < headers.length; i++) {
			hashMap.put(headers[i], data[i]);
		}

		this.filename = fileName.substring(0, fileName.length() - 4);
		// return the DataObject
		return createObj(hashMap, filename);
	}

	/**
	 * Returns the name of the parser.
	 * @return the name of the parser
	 */
	public String getName()
	{
		return filename;
	}

	/**
	 * Update the DataObject with new data
	 * 
	 * @param dataObj the DataObject to update
	 * @throws IOException
	 */
	public boolean update(DataObject dataObj) throws IOException {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		// read in a new line of data from the CSV
		String line;

		if ((line = reader.readLine()) != null) {
			String data[] = line.split(",");

			// reinitialize the HashMap
			hashMap = new HashMap<String, String>();

			// populate the HashMap with header and data
			for (int i = 0; i < headers.length; i++) {
				hashMap.put(headers[i], data[i]);
			}

			// update the current DataObject's HashMap
			dataObj.updateData(hashMap);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Create the DataObject
	 * 
	 * @param hm the HashMap for the DataoOject
	 * @return the dataObject
	 */
	public DataObject createObj(HashMap<String, String> hm, String name) {
		DataObject dataObj = new DataObject(hm, name, this);

		return dataObj;
	}
}
