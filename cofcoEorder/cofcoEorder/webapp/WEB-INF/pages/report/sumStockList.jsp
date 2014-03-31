<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ page import="java.util.*,java.text.*" %>

<script>
		function selectDist() {
			var form = document.treeForm;
			var v = openDistTree('${ctx}', '', '1', form.distId.value);
			if (v) {
				form.distId.value = v.id;
				form.distName.value = v.text;
			}
		}
		
	  function selectDictBrand(dictName,ids,names) {
	        var form = document.treeForm;
			var v = openDictTree('${ctx}', dictName,'2','2');
			if (v) {
				ids.value = v.id;
				names.value = v.text;
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
			var v = openOrgTree('${ctx}');
			if (v) {
				form.orgSubCode.value = v.subCode;
				form.orgName.value = v.text;
			}
		}
		
	   function onSubmit() {
		    var form = document.treeForm;
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
	    <td width="1%">经销商：</td>
		<td >
	    <html:hidden name="ec" property="distId"/>
		<html:text name="ec" property="distName"  onclick="selectDist()" readonly="true" styleClass="select_but"/>    </td>
	    <td width="1%">商品：</td>
		<td >
		<html:text name="ec" property="prodName"/>
		</td>
    </tr>
    
    <tr>
	    <td width="1%">&nbsp;&nbsp;&nbsp;品牌：</td>
		<td>
			 <html:hidden name="ec" property="brandId" />
			 <html:text   name="ec" property="brandName"  onclick="selectDictBrand('prodBrand',treeForm.brandId,treeForm.brandName)" styleClass="select_but"/>
		     <!-- 
			 <html:select name="ec" property="brandName" style="width: 105px">
			         <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="ABC">ABC</html:option>
				     <html:option value="ABC′sBB">ABC′sBB</html:option>
				     <html:option value="EC">EC</html:option>
				     <html:option value="Free">Free</html:option>
				     <html:option value="帮泰">帮泰</html:option>
				     <html:option value="快乐小妹">快乐小妹</html:option>
				     <html:option value="易洁">易洁</html:option>
		    </html:select>  
		     -->        
		</td>
		<td width="1%">&nbsp;&nbsp;&nbsp;类别：</td>
		<td>
			 <html:hidden name="ec" property="otherId" />
			 <html:text   name="ec" property="otherName"  onclick="selectDictBrand('prodOther',treeForm.otherId,treeForm.otherName)" styleClass="select_but"/>
			<!-- 
	       <html:select name="ec" property="otherName" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="A类">A类</html:option>
				     <html:option value="BB类">BB类</html:option>
				     <html:option value="C类">C类</html:option>
				     <html:option value="EC类">EC类</html:option>
				     <html:option value="F类">F类</html:option>
				     <html:option value="K类">K类</html:option>
				     <html:option value="L类">L类</html:option>
				     <html:option value="EC类">M类</html:option>
				     <html:option value="F类">R类</html:option>
				     <html:option value="K类">T类</html:option>
				     <html:option value="L类">U类</html:option>
				     <html:option value="EC类">Y类</html:option>
				     <html:option value="F类">成人活动裤</html:option>
				     <html:option value="K类">成人尿垫</html:option>
		    </html:select> 
		  -->   
		</td>
		<td width="1%">品类：</td>
		<td>
		
		     <html:hidden name="ec" property="typeId" />
			 <html:text   name="ec" property="typeName"  onclick="selectDictBrand('prodType',treeForm.typeId,treeForm.typeName)" styleClass="select_but"/>
					
		    <!--
		    <html:select name="ec" property="typeName" style="width: 105px">
		             <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="成人纸尿裤">成人纸尿裤</html:option>
				     <html:option value="护垫">护垫</html:option>
				     <html:option value="护理液">护理液</html:option>
				     <html:option value="普通湿巾">普通湿巾</html:option>
				     <html:option value="卫生巾">卫生巾</html:option>
				     <html:option value="卫生湿巾">卫生湿巾</html:option>
		    </html:select> 
		    -->
		</td>
		<td width="1%">表层：</td>
		<td>
		
		     <html:hidden name="ec" property="prodLayerId" />
			 <html:text   name="ec" property="memo1"  onclick="selectDictBrand('prodLayer',treeForm.prodLayerId,treeForm.memo1)" styleClass="select_but"/>
			<!-- 
		    <html:select name="ec" property="memo1" style="width: 105px">
		             <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="棉制">棉制</html:option>
				     <html:option value="网制">网制</html:option>
		    </html:select> 
		    -->
		</td>
    </tr>
    
    <tr>
	    <td width="1%">&nbsp;&nbsp;&nbsp;配方：</td>
		<td>
		     <html:hidden name="ec" property="prodFormulaId" />
			 <html:text   name="ec" property="memo2"  onclick="selectDictBrand('prodFormula',treeForm.prodFormulaId,treeForm.memo2)" styleClass="select_but"/>
		
		    <!-- 
		    <html:select name="ec" property="memo2" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="KMS配方">KMS配方</html:option>
				     <html:option value="KMS清爽因子配方">KMS清爽因子配方</html:option>
				     <html:option value="无配方">无配方</html:option>
				     <html:option value="芦荟配方">芦荟配方</html:option>
				     <html:option value="清凉配方">清凉配方</html:option>
				     <html:option value="泡沫配方">泡沫配方</html:option>
				     <html:option value="中药配方">中药配方</html:option>				
		    </html:select> 
		     -->
		</td>

		<td width="1%">&nbsp;&nbsp;&nbsp;形状：</td>
		<td>
		    <html:hidden name="ec" property="prodShapeId" />
			<html:text   name="ec" property="memo3"  onclick="selectDictBrand('prodShape',treeForm.prodShapeId,treeForm.memo3)" styleClass="select_but"/>
		
		    <!--  
		    <html:select name="ec" property="memo3" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="弧形护垫">弧形护垫</html:option>
				     <html:option value="护翼卫生巾">护翼卫生巾</html:option>
				     <html:option value="活动裤">活动裤</html:option>
				     <html:option value="减翼卫生巾">减翼卫生巾</html:option>
				     <html:option value="尿垫">尿垫</html:option>
				     <html:option value="亲柔立围卫生巾">亲柔立围卫生巾</html:option>
				     <html:option value="直条护垫">直条护垫</html:option>
				     <html:option value="纸尿裤">纸尿裤</html:option>
				     <html:option value="纸尿片">纸尿片</html:option>				
		    </html:select> 
		    -->
		</td>

		<td width="1%">厚度：</td>
		<td>
		    <html:hidden name="ec" property="prodThicknessId" />
			<html:text   name="ec" property="memo4"  onclick="selectDictBrand('prodThickness',treeForm.prodThicknessId,treeForm.memo4)" styleClass="select_but"/>
		    <!--  
		    <html:select name="ec" property="memo4" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="超薄">超薄</html:option>
				     <html:option value="超极薄">超极薄</html:option>
				     <html:option value="超吸">超吸</html:option>
				     <html:option value="丝薄">丝薄</html:option>
				     <html:option value="纤薄">纤薄</html:option>
		    </html:select> 
		    -->
		</td>
	
		<td width="1%">长度：</td>
		<td>
		    <html:hidden name="ec" property="prodLengthId" />
			<html:text   name="ec" property="memo5"  onclick="selectDictBrand('prodLength',treeForm.prodLengthId,treeForm.memo5)" styleClass="select_but"/>
		    <!--  
		    <html:select name="ec" property="memo5" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="163">163</html:option>
				     <html:option value="240">240</html:option>
				     <html:option value="280">280</html:option>
				     <html:option value="320">320</html:option>
				     <html:option value="380">380</html:option>
		    </html:select> 
		    -->
		</td>
    </tr>

    <tr>
	    <td width="1%">&nbsp;&nbsp;&nbsp;用途：</td>
		<td>
			<html:hidden name="ec" property="prodUseId" />
			<html:text   name="ec" property="memo6"  onclick="selectDictBrand('prodUse',treeForm.prodUseId,treeForm.memo6)" styleClass="select_but"/>
		
		
		    <!-- 
		 	<html:select name="ec" property="memo6" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="成人护理">成人护理</html:option>
				     <html:option value="儿童">儿童</html:option>
				     <html:option value="混搭">混搭</html:option>
				     <html:option value="加长夜用">加长夜用</html:option>
				     <html:option value="女士洁面">女士洁面</html:option>
				     <html:option value="日常护理">日常护理</html:option>
				     <html:option value="日常清洁">日常清洁</html:option>
				     <html:option value="日用">日用</html:option>
				     <html:option value="夜用">夜用</html:option>
				     <html:option value="隐私护理">隐私护理</html:option>
				     <html:option value="婴儿">婴儿</html:option>
		    </html:select> 
		    -->
		</td>
	
		<td width="1%">&nbsp;&nbsp;&nbsp;规格：</td>
		<td>
		    <html:hidden name="ec" property="prodspecificationId" />
			<html:text   name="ec" property="prod_spec"  onclick="selectDictBrand('prodspecification',treeForm.prodspecificationId,treeForm.prod_spec)" styleClass="select_but"/>
		
		    <!--  		
		    <html:select name="ec" property="prod_spec" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="10片">10片</html:option>
				     <html:option value="11片">11片</html:option>
				     <html:option value="12片">12片</html:option>
	                 <html:option value="13片">13片</html:option>
				     <html:option value="14片">14片</html:option>
				     <html:option value="15片">15片</html:option>
				     <html:option value="16片">16片</html:option>
	                 <html:option value="18片">18片</html:option>
	                 <html:option value="19片">19片</html:option>
				     <html:option value="20片">20片</html:option>
				     <html:option value="21片">21片</html:option>
	                 <html:option value="22片">22片</html:option>
	                 <html:option value="120片">120片</html:option>
				     <html:option value="160片">160片</html:option>
				     <html:option value="200片">200片</html:option>
	                 <html:option value="800片">800片</html:option>
		    </html:select> 
		    -->
		</td>
		<td width="1%">${mr['page.common.packageType']}</td>
		<td>
			<html:hidden name="ec" property="packId" />
			<html:text   name="ec" property="packName"  onclick="selectDictBrand('packType',treeForm.packId,treeForm.packName)" styleClass="select_but"/>
			<!--  
		    <html:select name="ec" property="packName" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="普通风琴包装">普通风琴包装</html:option>
				     <html:option value="单片装">单片装</html:option>
				     <html:option value="多片装">多片装</html:option>
	                 <html:option value="胶盒装">胶盒装</html:option>
				     <html:option value="拉绳索封包装">拉绳索封包装</html:option>
				     <html:option value="密封拉链包装">密封拉链包装</html:option>
				     <html:option value="瓶装">瓶装</html:option>
	                 <html:option value="易拉贴铝膜包装">易拉贴铝膜包装</html:option>
	                 <html:option value="纸盒装">纸盒装</html:option>			
		    </html:select> 
		    -->
		</td>
		<td width="1%">产品属性：</td>
		<td>
		    <html:hidden name="ec" property="prodId" />
			<html:text   name="ec" property="prodType"  onclick="selectDictBrand('prodAttributes',treeForm.prodId,treeForm.prodType)" styleClass="select_but"/>
			<!--  
		     <html:select name="ec" property="prodType" style="width: 105px">
	                 <html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
				     <html:option value="SKU">SKU</html:option>
				     <html:option value="组织装">组织装</html:option>
				     <html:option value="混合装">混合装</html:option>
		    </html:select> 
		    -->
		</td>
    </tr>
    
    <tr>
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
		<td width="1%">满足率：</td>
		<td>
		   <html:select name="ec" property="satisfyingRate" style="width: 105px">
					<html:option value="3.09">100%</html:option>
					<html:option value="1.65">95%</html:option>
					<html:option value="1.28">90%</html:option>
					<html:option value="1.04">85%</html:option>
					<html:option value="0.85">80%</html:option>
		  </html:select>
		
		</td>
		<td width="1%">配送时间：</td>
		<td>
		  <html:select name="ec" property="allocationDate" style="width: 105px">
		  <% 
		      for(int i=1;i<21;i++){		      
		  %>
					<html:option value="<%=String.valueOf(i+4)%>"><%=String.valueOf(i)%></html:option>
				
		 <%
		  }
		 %>			
		  </html:select>
		</td>
		
		<%
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DAY_OF_WEEK);
			cal.setTimeInMillis(cal.getTimeInMillis() - (5 + day) * 86400000L);
			SimpleDateFormat formatter   = new SimpleDateFormat("yyyy-MM-dd");
			String str = formatter.format(cal.getTime());

		%>
		<html:hidden name="ec" property="strDate" value="<%=str %>"/>
		<%
			Calendar cals = Calendar.getInstance();
			int days = cal.get(Calendar.DAY_OF_WEEK);
			cals.setTimeInMillis(cals.getTimeInMillis() - (days-1) * 86400000L);
			SimpleDateFormat formatters   = new SimpleDateFormat("yyyy-MM-dd");
			String strs = formatter.format(cals.getTime());

		%>
		<html:hidden name="ec" property="strsDate" value="<%=strs %>"/>
		

    </tr>
