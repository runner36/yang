<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>


<script>
		function selectDist() {
			var form = document.listReportForm;
			var v = openDistTree('${ctx}', '2', '2');
			if (v) {
				form.distId.value = v.id;
				form.distName.value = v.text;
			}
		} 
		
		//查询渠道
	    function selectDictC(dictName, id, name) {
	    	var form = document.listReportForm;
			var v = openDictTree('${ctx}', dictName,'2','2');
			if (v) {
				form.channelId.value = v.id;
				form.channelName.value = v.text;
			}
		}
		
		
		//连锁系统
	    function selectDictCC(dictName, id, name) {
	    	var form = document.listReportForm;
			var v = openDictTree('${ctx}', dictName,'2','2');
			if (v) {
				form.corpId.value = v.id;
				form.corpName.value = v.text;
			}
		}
		
				
		//查询渠道
	    function selectDictC1(dictName, id, name) {
	    	var form = document.listReportForm;
			var v = openDictTree('${ctx}', dictName);
			if (v) {
				form.channelId1.value = v.id;
				form.channelName1.value = v.text;
			}
		}
		
		
				
		//查询渠道
	    function selectDictC2(dictName, id, name) {
	    	var form = document.listReportForm;
			var v = openDictTree('${ctx}', dictName);
			if (v) {
				form.channelId2.value = v.id;
				form.channelName2.value = v.text;
			}
		}
		
		
		
		//查询地理区域
	    function selectDictG(dictName) {
	    	var form = document.listReportForm;
			var v = openDictTree('${ctx}',dictName,'2','2');
			if (v) {
				form.geoId.value = v.id;
				form.geoName.value = v.text;
			}
		}
		
		
	    //查询商场类型
	    function selectDictT(dictName) {
	    	var form = document.listReportForm;
			var v = openDictTree('${ctx}',dictName);
			if (v) {
				form.typeId.value = v.id;
				form.typeName.value = v.text;
			}
		}
		
		
		function selectDictUait(dictName,ids,names) {
	        var form = document.listReportForm;
			var v = openDictTree('${ctx}', dictName);
			if (v) {
				ids.value = v.id;
				names.value = v.text;
			}
		}
		
		
		
		function selectClient() {
			var form = document.listReportForm;
			var v = openClientTree('${ctx}', '1 ');
			if (v) {
				form.clientId.value = v.id;
				form.clientName.value = v.text;
			}
		}
		function selectOrg() {
			var form = document.listReportForm;
			var v = openOrgTree('${ctx}','2','2');
			if (v) {
				form.orgSubCode.value = v.subCode;
				form.orgName.value = v.text;
			}
		}
		
		function selectStore() {
			var form = document.listReportForm;
			var v = openStoreTree('${ctx}','1');
			if (v) {
				form.storeCode.value = v.subCode;
				form.storeName.value = v.text;
			}
		}
		
	   function onSubmit() {
		    var form = document.listReportForm;
	        if(form.startDate.value==''){
	            alert("请选择开始时间！");
	            return false;
	        }
	        if(form.endDate.value==''){
	            alert("请选择结束时间！");
	            return false;
	        }
	        if(form.endDate.value<form.startDate.value){
	           alert("开始时间不能大于结束时间！");
	           return false;
	        }
	        if(form.prodUnitId.value==''){
	          alert("请选择单位！");
	          return false;
	        }
	        
		    return true;
	  }
      
     
	  
	
</script>
	<tr>
		<td width="1%"><font color="#FF0000">＊</font>开始时间：</td>
		<td ><html:text name="ec" property="startDate" onfocus="WdatePicker()"   readonly="true" styleClass="date_but"/></td>
		<td width="1%"><font color="#FF0000">＊</font>结束时间：</td>
		<td ><html:text name="ec" property="endDate" onfocus="WdatePicker()" readonly="true"    styleClass="date_but"/></td>
		<td width="1%">${mr['page.common.organization']}</td>
		<td >
	        <html:hidden property="orgSubCode"/>
			<html:text name="ec" property="orgName" styleClass="select_but" onclick="selectOrg()" readonly="true"/> 
		</td>
	    <td width="1%">${mr['page.common.distName']}</td>
		<td >
		    <html:hidden name="ec" property="distId"/>
			<html:text  name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>    
		</td>
    </tr>
    
    <tr>
	    <td width="1%">门店创建开始日期：</td>
		<td>
		    <html:text name="ec" property="storeStartDate" onfocus="WdatePicker()"   readonly="true" styleClass="date_but"/>
		</td>    
		<td width="1%">门店创建结束日期：</td>
		<td>
		    <html:text name="ec" property="storeEndDate" onfocus="WdatePicker()"   readonly="true" styleClass="date_but"/>
		</td>  
		<td width="1%">门店：</td>
		<td>
		    <html:text name="ec" property="storeId"/>
		</td>
		<td width="1%">地理结构：</td>
		<td>
			<html:hidden name="ec" property="geoId"/>
		    <html:text   name="ec" property="geoName"  onclick="selectDictG('geography')" styleClass="select_but"/>
		</td>
    </tr>
    
    <tr>
        <td width="1%">渠道：</td>
		<td>
		    <html:hidden name="ec" property="channelId"/>
		    <html:text   name="ec" property="channelName"  onclick="selectDictC('storeChannel')" styleClass="select_but"/>
		</td>
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
    </tr>
    
        <tr>
        <td width="1%">连锁系统：</td>
		<td>
		    <html:hidden name="ec" property="corpId"/>
		    <html:text   name="ec" property="corpName"  onclick="selectDictCC('chainSystem')" styleClass="select_but"/>
		</td>
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
		   <html:text   name="ec" property="prodUnitName"  onclick="selectDictUait('prodUnit',listReportForm.prodUnitId,listReportForm.prodUnitName)" styleClass="select_but"/>
		
		  <!-- 
		  <html:select name="ec" property="prodUnit" style="width: 105px">
					<html:option value="箱">箱</html:option>
					<html:option value="元">元</html:option>
					<html:option value="包/套/瓶">包/套/瓶</html:option>
		  </html:select>
		   -->
		</td>
    </tr>
    
    
		


