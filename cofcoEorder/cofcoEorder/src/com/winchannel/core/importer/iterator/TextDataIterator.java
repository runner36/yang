package com.winchannel.core.importer.iterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xianghui
 *
 */
public class TextDataIterator implements DataIterator {

	public static final char DEFAULT_DELIMITER = ',';
	private BufferedReader reader;
	private int index;
	private char delimiter;
	private String regex;
	
	public TextDataIterator(InputStream is, char delimiter) throws Exception {
		reader = new BufferedReader(new InputStreamReader(is));
		this.delimiter = delimiter;
	}
	
	public TextDataIterator(InputStream is, String regex) throws Exception {
		reader = new BufferedReader(new InputStreamReader(is));
		this.regex = regex;
	}

	public boolean hasNext() {
		try {
			return reader.ready();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Map<String, String> next() {
		index++;
		Map<String, String> rowMap = new HashMap<String, String>();
		try {
			String line = reader.readLine();
			String[] row = null;
			if (regex == null) {
				row = this.parseLine(line);
			}
			else {
				row = line.split(regex);
			}
			
			for (int m = 0; m < row.length; m++) {
				rowMap.put((m+1) + "", row[m].trim());
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return rowMap;
	}


	public void remove() {
	}


	public int getIndex() {
		return index;
	}
	
	private String[] parseLine(String line) throws IOException {
		List<String> list = new ArrayList<String>();
//		char delimiter = this.delimiter.charAt(0);
		char quote = '"';
		char[] c = line.toCharArray();
		int len = c.length;
		int idx = 0;
		while (idx < len) {
			int start = idx;
			int end = -1;
			char fc = c[idx++];
			
			if (fc == quote) {
				int delimiterIndex = -1;
				while (idx < len && end == -1) {
					if (c[idx] == delimiter) {
						if (delimiterIndex == -1) {
							delimiterIndex = idx;
						}
					}
					else if (c[idx] == quote) {
						int next = idx + 1;
						if (next < len && c[next] == delimiter || next == len) {
							start++;
							end = idx++;
						}
					}
					idx++;
				}
				if (end == -1) {
					if (delimiterIndex == -1) {
						end = len;
					}
					else {
						end = delimiterIndex;
						idx = delimiterIndex + 1;
					}
				}
			}
			else if (fc == delimiter) {
				end = start;
			}
			else {
				while (idx < len && end == -1) {
					if (c[idx] == delimiter) {
						end = idx;
					}
					idx++;
				}
				if (end == -1) {
					end = len;
				}
			}
			list.add(new String(c, start, end - start));
		}
		
		
		String[] row = new String[list.size()];
		list.toArray(row);
		return row;
	}

}
