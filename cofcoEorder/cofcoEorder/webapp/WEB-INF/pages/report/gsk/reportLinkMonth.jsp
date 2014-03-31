<%@ page contentType="text/html;charset=UTF-8"%>

	<script>
		function selectDict(dictName, id, name) {
			var v = openDictTree('${ctx}', dictName, 0, 2);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		
		function selectDist() {
			var form = document.listReportForm;
			var v = openDistTree('${ctx}', '', '1', form.distId.value);
			if (v) {
				form.distId.value = v.id;
				form.distName.value = v.text;
			}
		}
		function selectClient() {
			var form = document.listReportForm;
			var v = openClientTree('${ctx}', '1');
			if (v) {
				form.clientId.value = v.id;
				form.clientName.value = v.text;
			}
		}
		function selectLevelTree() {
			var form = document.listReportForm;
			var v = openLevelTree('${ctx}','0','2','3');
			if (v) {
				form.queryOrgIds.value = v.id;
				form.orgName.value = v.text;
			}
		}
		
		function selectProdTree(){
			var form = document.listReportForm;
			var v = openProdTree('${ctx}','0','0');
			if (v) {
				if(v.leaf=='1'){
					form.prodCode.value = v.prodCode;
					form.targetProdName.value = v.text;
					form.brandId.value = "";
				}else{
					form.brandId.value = v.id;
					form.prodCode.value = "";
					form.targetProdName.value = v.text;
				}
			}
		} 
	</script>
	<tr>
	<td onload="document.getElementById('showQuery_A').style.display='none'"></td></tr>
