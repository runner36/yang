package com.winchannel.core.importer.iterator;

import java.util.Iterator;
import java.util.Map;

public interface DataIterator extends Iterator<Map<String, String>> {
	
	public int getIndex();

}
