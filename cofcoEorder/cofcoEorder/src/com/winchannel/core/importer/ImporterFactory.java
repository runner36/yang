package com.winchannel.core.importer;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Map;

import com.winchannel.core.importer.iterator.DataIterator;
import com.winchannel.core.importer.iterator.ExcelDataIterator;
import com.winchannel.core.importer.iterator.TextDataIterator;

/**
 * @author xianghui
 *
 */
public class ImporterFactory {
	
	public static Importer createImporter(String configFile, Map<String, Object> params) throws Exception {
		ImpConfigurator conf = getConfigurator(configFile);
		if (conf.getMlKey() != null) {
			return new MultiLevelDataImporter(conf, params);
		}
		else if(conf.getCustomDataImporter()!=null){  //自定义导入器,可以自己控制导入先后过程
			try {
				@SuppressWarnings("unchecked")
				Constructor<Importer> constructor = conf.getCustomDataImporter().getConstructor(ImpConfigurator.class, Map.class); 
				return constructor.newInstance(conf, params);
			} catch (Exception e) {
				e.printStackTrace();
				return new DefaultHibernateImporter(conf, params);
			}
		}
		else {
			return new DefaultHibernateImporter(conf, params);
		}
	}
		
	public static ImpConfigurator getConfigurator(String configFile) throws Exception {
		return ImpConfigurator.getConfigurator(configFile);
	}
	
	public static DataIterator getTextDataIterator(InputStream is, String regex) throws Exception {
		return new TextDataIterator(is, regex);
	}
	
	public static DataIterator getTextDataIterator(InputStream is, char delimiter) throws Exception {
		return new TextDataIterator(is, delimiter);
	}
	
	public static DataIterator getExcelDataIterator(InputStream is) throws Exception {
		return new ExcelDataIterator(is);
	}
}
