package com.winchannel.core.exporter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.winchannel.core.bean.ListColumn;
import com.winchannel.core.utils.StringUtils;

class CsvExporter extends Exporter {
	
	public static final String DEFAULT_DELIMITER = ",";
	private StringBuilder plainData = new StringBuilder();
	
	public CsvExporter(ListColumn[] columns) {
		super(columns);
	}

	protected void setContentType() {
		this.contentType = "csv";
	}
	
	public void createTitle(String title) {
		
	}

	public void createHeader() {
		for (int i = 0; i < columns.length; i++) {
    		if (i != 0) {
    			plainData.append(DEFAULT_DELIMITER);
    		}
    		
//    		plainData.append(columns[i].getTitle().replaceAll(DEFAULT_DELIMITER, "/"));
    		plainData.append(StringUtils.replace(columns[i].getTitle(), DEFAULT_DELIMITER, ""));
		}
		plainData.append("\r\n");
	}

	/*public void createBody(List body, int bodyStart, int leftHeader) {
    	for (int i = 0; i < body.size(); i++) {
    		Object[] objects = (Object[]) body.get(i);
    		for (int j = bodyStart; j < objects.length; j++) {
        		if (j != bodyStart) {
        			plainData.append(DEFAULT_DELIMITER);
        		}
    			plainData.append(columns[j - bodyStart].format(objects[j]));
    		}
    		plainData.append("\r\n");
    	}
	}*/
	
	public void createBody(List body, int bodyStart, int leftHeader) {
		for (int i = 0; i < body.size(); i++) {
    		Object[] objects = (Object[]) body.get(i);
    		
			for (short cellnum = 0; cellnum < columns.length; cellnum++) {
				ListColumn column = columns[cellnum];
				if (cellnum > 0) {
					plainData.append(DEFAULT_DELIMITER);
				}
				if (StringUtils.isNotBlank(column.getProperty())) {
					int valueIndex = Integer.parseInt(column.getProperty()) + bodyStart;
	    			String value = column.format(objects[valueIndex]);
	    			plainData.append(value);
				}
			}
			plainData.append("\r\n");
		}
	}

	public void createFooter(String footer) {
		
	}

	public void write(OutputStream outputStream) throws IOException {
		outputStream.write(plainData.toString().getBytes("GBK"));
	}

}
