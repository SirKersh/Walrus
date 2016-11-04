package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Parser {
	BufferedReader reader;
	//HashMap<String, String> hashMap = new HashMap<String, String>();
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
		reader = new BufferedReader(new FileReader(fileName));

		// save the headers to a global array so it can be used throughout the
		// program
		headers = reader.readLine().split(",");

		// save the FIRST line of data to an array so it can be used to store
		// into the hashmap
		String data[] = reader.readLine().split(",");

		// populate the hashmap with header and data
		for (int i = 0; i < headers.length; i++) {
			hashMap.put(headers[i], data[i]);
		}

		// return the DataObject
		return createObj(hashMap);
	}

	/**
	 * Update the dataObject with new data
	 * @param dataObj the DataObject to update
	 * @throws IOException
	 */
	public boolean update(DataObject dataObj) throws IOException {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		// read in a new line of data from the CSV
		String line;

		if ((line = reader.readLine()) != null) {
			String data[] = line.split(",");

			// reinitialize the hashmap
			hashMap = new HashMap<String, String>();

			// populate the hashmap with header and data
			for (int i = 0; i < headers.length; i++) {
				hashMap.put(headers[i], data[i]);
			}

			// update the current DataObject's hashmap
			dataObj.updateData(hashMap);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Create the DataObject
	 * @param hm the hashmap for the DataoOject
	 * @return the dataObject
	 */
	public DataObject createObj(HashMap<String, String> hm) {
		DataObject dataObj = new DataObject(hm);

		return dataObj;
	}
}
