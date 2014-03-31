package com.winchannel.core.exporter;

import com.winchannel.core.bean.ListColumn;

public class ExporterFactory {

	public static Exporter createXlsExporter(ListColumn[] columns) {
		return new XlsExporter(columns);
	}
	
	public static Exporter createCsvExporter(ListColumn[] columns) {
		return new CsvExporter(columns);
	}
	
}
