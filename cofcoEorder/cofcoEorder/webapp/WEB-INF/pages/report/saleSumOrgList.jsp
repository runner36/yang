<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ page import="java.util.*,java.text.*" %>

<script>
		function selectDist() {
			var form = document.treeForm;
			var v = openDistTree('${ctx}', '2', '2');
			if (v) {
				form.distId.value = v.id;
				form.distName.value = v.text;
			}
		} 
		
		//查询渠道
	    function selectDictC(dictName, id, name) {
	    	var form = document.treeForm;
			var v = openDictTree('${ctx}', dictName,'2','2');
			if (v) {
				form.channelId.value = v.id;
				form.channelName.value = v.text;
			}
		}
		
		
		//连锁系统
	    function selectDictCC(dictName, id, name) {
	    	var form = document.treeForm;
			var v = openDictTree('${ctx}', dictName,'2','2');
			if (v) {
				form.corpId.value = v.id;
				form.corpName.value = v.text;
			}
		}
		
				
		//查询渠道
	    function selectDictC1(dictName, id, name) {
	    	var form = document.treeForm;
			var v = openDictTree('${ctx}', dictName);
			if (v) {
				form.channelId1.value = v.id;
				form.channelName1.value = v.text;
			}
		}
		
		
				
		//查询渠道
	    function selectDictC2(dictName, id, name) {
	    	var form = document.treeForm;
			var v = openDictTree('${ctx}', dictName);
			if (v) {
				form.channelId2.value = v.id;
				form.channelName2.value = v.text;
			}
		}
		
		
		
		//查询地理区域
	    function selectDictG(dictName) {
	    	var form = document.treeForm;
			var v = openDictTree('${ctx}',dictName,'2','2');
			if (v) {
				form.geoId.value = v.id;
				form.geoName.value = v.text;
			}
		}
		
		
	    //查询商场类型
	    function selectDictT(dictName) {
	    	var form = document.treeForm;
			var v = openDictTree('${ctx}',dictName);
			if (v) {
				form.typeId.value = v.id;
				form.typeName.value = v.text;
			}
		}
		
		
		function selectClient() {
			var form = document.treeForm;
			var v = openClientTree('${ctx}', '1 ');
			if (v) {
				form.clientId.value = v.id;
				form.clientName.value = v.text;
			}
		}
		function selectOrg() {
			var form = document.treeForm;
			var v = openOrgTree('${ctx}','2','2');
			if (v) {
				form.orgSubCode.value = v.subCode;
				form.orgName.value = v.text;
			}
		}
		
		function selectStore() {
			var form = document.treeForm;
			var v = openStoreTree('${ctx}','1');
			if (v) {
				form.storeCode.value = v.subCode;
				form.storeName.value = v.text;
			}
		}
		
		 function selectDictUait(dictName,ids,names) {
	        var form = document.treeForm;
			var v = openDictTree('${ctx}', dictName);
			if (v) {
				ids.value = v.id;
				names.value = v.text;
			}
		}
		
	   function onSubmit() {
		    var form = document.treeForm;
	        if(form.Year.value==''){
	            alert("请选择开始年！");
	            return false;
	        }
	        if(form.Month.value==''){
	            alert("请选择开始月！");
	            return false;
	        }

            if(form.Year.value!=''){
               var years = form.Year.value;
               form.Years.value  = years-1;
            }
            
            if(form.Month.value!=''){
               var mm = form.Month.value;
               if(mm==01){
                  form.upMonth.value = 12;
               }else {
                 
                  form.upMonth.value  = mm-1;
               }
            }
            
   
            
           if(form.prodUnitId.value==''){
	          alert("请选择单位！");
	          return false;
	        }
            
		    return true;
	  }
    
	
</script>
	<tr>
        <html:hidden  name="ec" property="Years" />
		<td width="1%"><font color="#FF0000">＊</font>年：</td>
		<td >
			 <html:select name="ec" property="Year" style="width: 105px">
			         <html:option value="2011">2011</html:option>
				     <html:option value="2009">2009</html:option>
				     <html:option value="2010">2010</html:option>
				     <html:option value="2012">2012</html:option>
				     <html:option value="2013">2013</html:option>
				     <html:option value="2014">2014</html:option>
				     <html:option value="2015">2016</html:option>
		    </html:select> 
     </td>

    <html:hidden  name="ec" property="upMonth"/>
	<td width="1%"><font color="#FF0000">＊</font>月：</td>
	<td>
	     
		<%
		Date d = new Date();
		SimpleDateFormat fm = new SimpleDateFormat("MM");
		String monthStr = fm.format(d);
		String tmpStr =  (null == request.getParameter("Month")) ? "" : request.getParameter("Month");
		if(!"".equals(tmpStr)){
			monthStr = tmpStr;
		}
		
		%>	
				<html:select name="ec" property="Month">   
		           <option value="1" <%=monthStr.equals("01") ? "selected" : ""%>>1</option>  
		           <option value="2" <%=monthStr.equals("02") ? "selected" : ""%>>2</option>  
		           <option value="3" <%=monthStr.equals("03") ? "selected" : ""%>>3</option>  
		           <option value="4" <%=monthStr.equals("04") ? "selected" : ""%>>4</option>  
		           <option value="5" <%=monthStr.equals("05") ? "selected" : ""%>>5</option>  
		           <option value="6" <%=monthStr.equals("06") ? "selected" : ""%>>6</option>  
		           <option value="7" <%=monthStr.equals("07") ? "selected" : ""%>>7</option>  
		           <option value="8" <%=monthStr.equals("08") ? "selected" : ""%>>8</option>  
		           <option value="9" <%=monthStr.equals("09") ? "selected" : ""%>>9</option>  
		           <option value="10" <%=monthStr.equals("10") ? "selected" : ""%>>10</option>  
		           <option value="11" <%=monthStr.equals("11") ? "selected" : ""%>>11</option>  
		           <option value="12" <%=monthStr.equals("12") ? "selected" : ""%>>12</option>  
		         </html:select>
		         <html:hidden name="ec" property="Mon" />
		</td>
		
	    <td width="1%">经销商：</td>
		<td >
	    <html:hidden name="ec" property="distId"/>
		<html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>    </td>
		<td width="1%">地理结构：</td>
		<td>
			<html:hidden name="ec" property="geoId"/>
		    <html:text   name="ec" property="geoName"  onclick="selectDictG('geography')" styleClass="select_but"/>
		</td>
    </tr>
    
    <tr>
	    <td width="1%">门店：</td>
		<td>
		    <html:text name="ec" property="storeId"/>
		</td>
		<td width="1%">渠道：</td>
		<td>
		    <html:hidden name="ec" property="channelId"/>
		    <html:text   name="ec" property="channelName"  onclick="selectDictC('storeChannel')" styleClass="select_but"/>
		</td>
		
	    <td width="1%">门店创建开始日期：</td>
		<td>
		    <html:text name="ec" property="storeStartDate" onfocus="WdatePicker()"   readonly="true" styleClass="date_but"/>
		</td>    
		<td width="1%">门店创建结束日期：</td>
		<td>
		    <html:text name="ec" property="storeEndDate" onfocus="WdatePicker()"   readonly="true" styleClass="date_but"/>
		</td> 
    </tr>
    
    <tr>
	   <td width="1%">商场类型：</td>
		<td>
		    <!--<html:select name="ec" property="typeId" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="非直供">非直供</html:option>
				     <html:option value="直供">直供</html:option>
		    </html:select> -->
		    
		    <html:hidden name="ec" property="typeId"/>
		    <html:text   name="ec" property="typeName"  onclick="selectDictT('shoppingType')" styleClass="select_but"/>
	
		</td>
	
		<td width="1%">系统范围:</td>
		<td>
		     <!--<html:select name="ec" property="channelId1" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="全国性">全国性</html:option>
				     <html:option value="不跨市">不跨市</html:option>
				     <html:option value="跨省非全国">跨省非全国</html:option>
				     <html:option value="跨市不跨省">跨市不跨省</html:option>
		    </html:select> -->
		    <html:hidden name="ec" property="channelId1"/>
		    <html:text   name="ec" property="channelName1"  onclick="selectDictC1('systemWide')" styleClass="select_but"/>
		</td>
		
		<td width="1%">连锁属性:</td>
		<td>
		   <!--   <html:select name="ec" property="channelId2" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="普通连锁">普通连锁</html:option>
				     <html:option value="KA">KA</html:option>
				     <html:option value="单店">单店</html:option>
		    </html:select>-->
		    <html:hidden name="ec" property="channelId2"/>
		    <html:text   name="ec" property="channelName2"  onclick="selectDictC2('chainProperties')" styleClass="select_but"/>
		</td>

        <td width="1%">连锁系统：</td>
		<td>
		    <html:hidden name="ec" property="corpId"/>
		    <html:text   name="ec" property="corpName"  onclick="selectDictCC('chainSystem')" styleClass="select_but"/>
		</td>
    </tr>

    
    <tr>
		<td width="1%">促销员：</td>
		<td>
		  <html:checkbox name="ec" property="mon4" value=" " style="width: 15px"/> 无
		</td>
		<td width="1%">商品：</td>
		<td>
		    <html:text name="ec" property="prodName"/>	
		</td>
		<td width="1%"><font color="#FF0000">＊</font>单位：</td>
		<td>
		   <html:hidden name="ec" property="prodUnitId" />
		   <html:text   name="ec" property="prodUnit"  onclick="selectDictUait('prodUnit',treeForm.prodUnitId,treeForm.prodUnit)" styleClass="select_but"/>
		
		  <!--  
		  <html:select name="ec" property="prodUnit" style="width: 105px">
					<html:option value="箱">箱</html:option>
					<html:option value="元">元</html:option>
					<html:option value="包/套/瓶">包/套/瓶</html:option>
		  </html:select>
		  -->
		</td>
	    <td width="1%">&nbsp;&nbsp;&nbsp;退货：</td>
		<td>
           <html:checkbox name="ec" property="out" value=" and  dds.BOX <0  " style="width: 15px"/>退货
		</td>

    </tr>
