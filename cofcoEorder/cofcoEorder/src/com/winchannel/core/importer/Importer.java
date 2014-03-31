package com.winchannel.core.importer;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winchannel.core.importer.iterator.DataIterator;

/**
 * @author xianghui
 *
 */
public interface Importer {
	
	public static final Log log = LogFactory.getLog("Importer");
	
	public ImpInfo imp(DataIterator iterator) throws Exception;
	public ImpInfo test(DataIterator iterator) throws Exception;
	public void template(HttpServletResponse response) throws Exception;
	
}
