package com.winchannel.mdm.product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.mdm.product.model.MdmProduct;
import com.winchannel.mdm.product.model.MdmProductForOrder;

/**
 * @author xhchen
 * 产品主数据缓存，为了提高产品树的弹出速度，将产品主数据置于内存中
 *
 */
public class MdmProductCache {
	private static final Log LOG = LogFactory.getLog("MdmProductCache");
	/**productDatas用于存放产品缓存，分物料组进行存放以HashMap形式进行存放*/
	public final static Map<Long,LinkedHashMap<Long,MdmProduct>> 
		productDatas=new HashMap<Long,LinkedHashMap<Long,MdmProduct>>();
	
	/**newProductDatas用于存放产品缓存，分物料组进行存放以HashMap形式进行存放*/
	public final static Map<Long,LinkedHashMap<Long,MdmProductForOrder>> 
		newProductDatas=new HashMap<Long,LinkedHashMap<Long,MdmProductForOrder>>();
	
	/**prodBrandDatas用于存放产品品牌，产品品牌按物料组分类存放*/
	public final static Map<Long,LinkedHashMap<Long,BaseDictItem>>
		prodBrandDatas=new HashMap<Long,LinkedHashMap<Long,BaseDictItem>>();
	
	/**distRecentProducts用于存放经销商最近订购产品，分“经销商ID_物料组ID” 分类存放*/
	public final static Map<String,LinkedHashMap<Long,Long>>
		distRecentProducts=new HashMap<String,LinkedHashMap<Long,Long>>();
	
	
	/**判断是否需要初始化*/
	public synchronized static boolean needInit(){
		return productDatas.isEmpty();
	}
	
	/**初始化产品主数据
	 * */
	public synchronized static void init(MdmProductManager mdmProductManager,BaseDictManager baseDictManager){
		productDatas.clear();
		prodBrandDatas.clear();
		LOG.info("init products start.");
		/**查找所有物料组*/
		List<BaseDictItem> baseDictItemLst = baseDictManager.findEntity(" from BaseDictItem a where a.baseDict.dictId=? and a.levelCode=?", "prodSTRU",new Long(1));
		for (BaseDictItem baseDictItem : baseDictItemLst) {
			List<MdmProduct> prods = mdmProductManager.findEntity("from MdmProduct where prodSTRU.subCode like '" + baseDictItem.getSubCode() + "%' and state='1'");
			LinkedHashMap<Long,MdmProduct> mdmProductMap=new LinkedHashMap<Long,MdmProduct>();
			Map brandMap=new HashMap();
			for(MdmProduct mdmProduct:prods){
				mdmProductMap.put(mdmProduct.getProdId(), mdmProduct);
				//提取品牌
				if(mdmProduct.getItemBrand()!=null){
					brandMap.put(mdmProduct.getItemBrand().getItemCode(), mdmProduct.getItemBrand());
					if(mdmProduct.getItemBrand().getBaseDictItem()!=null){
						putProductBrand(brandMap, mdmProduct.getItemBrand());
					}
				}
			}
			productDatas.put(baseDictItem.getDictItemId(), mdmProductMap);
			
			 //产品品牌
			 Iterator brandIterator=brandMap.keySet().iterator();
			 if(brandMap.size()>0){
			     List<BaseDictItem >brandList=new  ArrayList();
				 StringBuffer sql=new StringBuffer("");
				 //拼产品品牌ID条件
				 while(brandIterator.hasNext()){
					 sql.append(((BaseDictItem)brandMap.get(brandIterator.next())).getDictItemId()+",");
				}
				 //查出产品品牌
				brandList = mdmProductManager.findEntity(BaseDictItem.class, "from BaseDictItem where   dictItemId in( "+sql.toString().substring(0, sql.length()-1)+" ) and baseDict.dictId= ?  order by levelCode ", "prodBrand");
				LinkedHashMap<Long,BaseDictItem> prodBrandMap=new LinkedHashMap<Long,BaseDictItem>();
				for(BaseDictItem branditem:brandList){
					prodBrandMap.put(branditem.getDictItemId(), branditem);
				}
				prodBrandDatas.put(baseDictItem.getDictItemId(), prodBrandMap);
			 }
		}
		LOG.info("init products end.");
	}
	
	/**初始化经销商最近订购产品
	 * */
	public synchronized static void initDistRecentProduct(MdmProductManager mdmProductManager){
		distRecentProducts.clear();
		LOG.info("init distRecentProduct start.");
		//经销商最近一个月订购产品，订购数量top 30
		List<Object[]> distRecentOrderList=mdmProductManager.executeSqlQuery("" +
				"SELECT convert(VARCHAR,trank.dist_id)+'_'+convert(VARCHAR,trank.group_id),trank.prod_id,trank.qrank FROM ( "+
				"		SELECT o.DIST_ID,o.GROUP_ID,i.PROD_ID,sum(i.QUANTITY) sumQuantity,rank() over(PARTITION BY o.DIST_ID,o.GROUP_ID order by sum(i.QUANTITY) desc) AS qrank"+
				"		FROM ORDER_ITEM i INNER JOIN ORDER_INFO o ON i.ORDER_ID=o.ORDER_ID"+
				"		WHERE o.ORDER_DATE BETWEEN convert(VARCHAR(10),dateadd(mm,-1,getdate()),23)+' 00:00:00' AND convert(VARCHAR(10),dateadd(dd,-1,getdate()),23)+' 23:59:59' "+
				"		GROUP BY o.DIST_ID,i.PROD_ID,o.GROUP_ID"+
				"	) trank WHERE trank.qrank<=30 AND trank.prod_id IS NOT null ORDER BY trank.dist_id,trank.group_id, trank.qrank");		
		
		for(Object[] newObj:distRecentOrderList){
			if(((Long)newObj[2]).longValue()!=1){
				distRecentProducts.get((String)newObj[0]).put(((BigDecimal)newObj[1]).longValue(), ((BigDecimal)newObj[1]).longValue());
			}else{
				LinkedHashMap<Long,Long> mdmProductMap=new LinkedHashMap<Long,Long>();
				mdmProductMap.put(((BigDecimal)newObj[1]).longValue(), ((BigDecimal)newObj[1]).longValue());
				distRecentProducts.put((String)newObj[0], mdmProductMap);
			}
		}
		LOG.info("init distRecentProduct end.");
	}
	
	/**初始化最近一个月创建的新产品
	 * */
	public synchronized static void initNewProduct(MdmProductManager mdmProductManager,BaseDictManager baseDictManager){
		newProductDatas.clear();
		LOG.info("init newProducts start.");
		List<BaseDictItem> baseDictItemLst = baseDictManager.findEntity(" from BaseDictItem a where a.baseDict.dictId=? and a.levelCode=?", "prodSTRU",new Long(1));
		for (BaseDictItem baseDictItem : baseDictItemLst) {
			List<Object[]> lstNewObj=mdmProductManager.executeSqlQuery("" +
					"select m.prod_id,m.PROD_CODE,m.prod_name,m.PRODSTRU_ID,ibrand.item_code,m.PROD_COUNTUNIT_ID,icountUnit.item_name,count(1) qcount from MDM_PRODUCT m " +
					"left join BASE_DICT_ITEM istru on m.PRODSTRU_ID=istru.dict_item_id " +
					"left join base_dict_item ibrand on m.BRAND_ID=ibrand.dict_item_id " +
					"left join base_dict_item icountUnit on m.PROD_COUNTUNIT_ID=icountUnit.dict_item_id " +
					"left join order_item i on i.prod_id=m.prod_id " +
					"where m.state='1' and m.CREATED BETWEEN dateadd(mm,-1,getdate()) AND getdate() "+
					"and istru.sub_code like '"+baseDictItem.getSubCode()+"%'"+
					"GROUP BY m.prod_id,m.PROD_CODE,m.prod_name,m.PRODSTRU_ID,ibrand.item_code,m.PROD_COUNTUNIT_ID,icountUnit.item_name ORDER BY qcount DESC");
			LinkedHashMap<Long,MdmProductForOrder> mdmProductMap=new LinkedHashMap<Long,MdmProductForOrder>();
			for(Object[] newObj:lstNewObj){
				MdmProductForOrder mdmProductForOrder=new MdmProductForOrder();
				mdmProductForOrder.setProdId(((BigDecimal)newObj[0]).longValue());
				mdmProductForOrder.setProdCode((String)newObj[1]);
				mdmProductForOrder.setProdName((String)newObj[2]);
				mdmProductForOrder.setProdSTRUId(((BigDecimal)newObj[3]).longValue());
				mdmProductForOrder.setBrandCode((String)newObj[4]);
				mdmProductForOrder.setCoutUintID(((BigDecimal)newObj[5]).longValue());
				mdmProductForOrder.setCountUintName((String)newObj[6]);				
				mdmProductMap.put(mdmProductForOrder.getProdId(), mdmProductForOrder);
			}
			newProductDatas.put(baseDictItem.getDictItemId(), mdmProductMap);
		}
		LOG.info("init newProducts end.");
	}
	
	public synchronized static void toInit(MdmProductManager mdmProductManager,BaseDictManager baseDictManager){
		if(MdmProductCache.needInit()){
			init(mdmProductManager,baseDictManager);
			initNewProduct(mdmProductManager,baseDictManager);
			initDistRecentProduct(mdmProductManager);
		}
	}
	/**构建产品树By物料组ID
	 * */
	public	static List createProdTreeByBrandId(Long prodGroupId,MdmProductManager mdmProductManager,BaseDictManager baseDictManager){
		toInit(mdmProductManager,baseDictManager);
		List tree=new ArrayList();		
		//产品品牌树的构建
		Iterator brandIterator=prodBrandDatas.get(prodGroupId).keySet().iterator();
		while(brandIterator.hasNext()){
			BaseDictItem b=prodBrandDatas.get(prodGroupId).get(brandIterator.next());
			String parentId = "";
			if(b.getBaseDictItem()!=null)
				parentId=b.getBaseDictItem().getItemCode()+"P";
			String id = b.getItemCode()+"P";

			String params = "id=" + id + "&leaf=1&prodName=" + b.getItemName() + "&prodCode=" + b.getItemCode()  ;
			String[] node = { parentId, id, b.getItemCode() + "_" + b.getItemName(), params, "", "", "" };
			tree.add(node);
		}
				
		//产品树构建，置于相应品牌下面
		Iterator mdmProductIterator=productDatas.get(prodGroupId).keySet().iterator();
		while(mdmProductIterator.hasNext()){
			MdmProduct prod=productDatas.get(prodGroupId).get(mdmProductIterator.next());
			if (prod.getProdSTRU() != null) {
				String parentId ="";
				if(prod.getItemBrand()!=null)
				parentId=prod.getItemBrand().getItemCode()+"P";
				String id = prod.getProdId().toString();

				String params = "id=" + id + "&leaf=1&prodName=" + prod.getProdName() + "&prodCode=" + prod.getProdCode() + "&countUnitId=" + prod.getProdCountUnit().getDictItemId()
						+ "&countUnitName=" + prod.getProdCountUnit().getItemName();
				String[] node = { parentId, id, prod.getProdCode() + "_" + prod.getProdName(), params, "", "", "" };
				tree.add(node);
			}		
		}
		
		return tree;
	}
	
	/**构建 新产品 树By物料组ID
	 * */
	public	static List createNewProdTreeByBrandId(Long prodGroupId,MdmProductManager mdmProductManager,BaseDictManager baseDictManager){
		toInit(mdmProductManager,baseDictManager);
		List tree=new ArrayList();
		//新产品树构建
		Iterator mdmProductIterator=newProductDatas.get(prodGroupId).keySet().iterator();
		while(mdmProductIterator.hasNext()){
			MdmProductForOrder prod=newProductDatas.get(prodGroupId).get(mdmProductIterator.next());
			if (prod.getProdSTRUId()!= null) {
				String parentId ="";
				if(prod.getBrandCode()!=null)
				parentId=prod.getBrandCode()+"P";
				String id = prod.getProdId().toString();

				String params = "id=" + id + "&leaf=1&prodName=" + prod.getProdName() + "&prodCode=" + prod.getProdCode() + "&countUnitId=" + prod.getCoutUintID()
						+ "&countUnitName=" + prod.getCountUintName();
				String[] node = { parentId, id, prod.getProdCode() + "_" + prod.getProdName(), params, "", "", "" };
				tree.add(node);
			}		
		}
		
		return tree;
	}
	
	/**构建 经销商最近订购产品树By物料组ID
	 * */
	public	static List createDistRecentProdTreeByBrandId(Long prodGroupId,Long distID,MdmProductManager mdmProductManager,BaseDictManager baseDictManager){
		toInit(mdmProductManager,baseDictManager);
		List tree=new ArrayList();
		LinkedHashMap<Long,Long> mdmProductMap=distRecentProducts.get(distID+"_"+prodGroupId);
		if(null!=mdmProductMap){
			Iterator mdmProductIterator=mdmProductMap.keySet().iterator();
			while(mdmProductIterator.hasNext()){
				MdmProduct prod=productDatas.get(prodGroupId).get(mdmProductIterator.next());
				if (null!=prod && prod.getProdSTRU() != null) {
					String parentId ="";
					if(prod.getItemBrand()!=null)
					parentId=prod.getItemBrand().getItemCode()+"P";
					String id = prod.getProdId().toString();

					String params = "id=" + id + "&leaf=1&prodName=" + prod.getProdName() + "&prodCode=" + prod.getProdCode() + "&countUnitId=" + prod.getProdCountUnit().getDictItemId()
							+ "&countUnitName=" + prod.getProdCountUnit().getItemName();
					String[] node = { parentId, id, prod.getProdCode() + "_" + prod.getProdName(), params, "", "", "" };
					tree.add(node);
				}
			}
		}				
		return tree;
	}
	
	
	private static void putProductBrand(Map brandMap,BaseDictItem b){
		if(b.getBaseDictItem()!=null){
			brandMap.put(b.getBaseDictItem().getItemCode(), b.getBaseDictItem());
			putProductBrand(brandMap,b.getBaseDictItem());
		}
	}
}
