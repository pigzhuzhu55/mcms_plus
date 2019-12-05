<@ms.html5>
	 <@ms.nav title="自定义表对应字段编辑" back=true>
	 	<#if tableFiledEntity.id??>
	 		<@ms.updateButton  onclick="saveOrUpdate()"/>
	 	<#else>
	 		<@ms.saveButton  onclick="saveOrUpdate()"/>
	 	</#if>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="tableFiledForm" isvalidation=true>
    		<@ms.hidden name="id" value="${tableFiledEntity.id?default('')}"/>
    		<@ms.hidden name="tableId" value="${tableId?default('')}"/>
			<@ms.text label="字段名称" name="tfName" value="${(tableFiledEntity.tfName)?default('')}"  width="240px;" placeholder="请输入字段名称" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"字段名称长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
			<@ms.text label="类型" name="tfType" value="${(tableFiledEntity.tfType)?default('')}"  width="240px;" placeholder="请输入类型" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"类型长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
			<@ms.text label="默认值" name="tfDefault" value="${(tableFiledEntity.tfDefault)?default('')}"  width="240px;" placeholder="请输入默认值" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"默认值长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
			<@ms.textarea label="描述" name="tfDescription" value="${(tableFiledEntity.tfDescription)?default('')}"  width="240px;" placeholder="请输入描述" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"描述长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
			<@ms.select name="tfUnique" label="是否唯一"  width="240" list=[{"id":0,"value":"不唯一"},{"id":1,"value":"唯一"}] listKey="id" value="${(tableFiledEntity.tfUnique)?default('')}" listValue="value"   />
			<@ms.select name="tfRequired" label="是否必填"  width="240"   list=[{"id":0,"value":"非必填"},{"id":1,"value":"必填"}] value="${(tableFiledEntity.tfRequired)?default('')}" listKey="id"  listValue="value"   />
			<@ms.textarea label="json配置" name="tfConfig" value="${(tableFiledEntity.tfConfig)?default('')}"  width="240px;" placeholder="请输入json配置" validation={"required":"true","maxlength":"100","data-bv-stringlength-message":"json配置长度不能超过一百个字符长度!", "data-bv-notempty-message":"必填项目"}/>
			<@ms.textarea label="帮助信息" name="tfHelp" value="${(tableFiledEntity.tfHelp)?default('')}"  width="240px;" placeholder="请输入帮助信息" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"帮助信息长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
		</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
   	var logoClass = "glyphicon-floppy-saved";
	var url = "${managerPath}/mdiy/tableFiled/save.do";
	if($("#tableFiledForm input[name = 'id']").val() > 0){
		logoClass = "glyphicon-open";
		url = "${managerPath}/mdiy/tableFiled/update.do";
	}
	//编辑按钮onclick
	function saveOrUpdate() {
		$("#tableFiledForm").data("bootstrapValidator").validate();
			var isValid = $("#tableFiledForm").data("bootstrapValidator").isValid();
			if(!isValid) {
				<@ms.notify msg= "数据提交失败，请检查数据格式！" type= "warning" />
				return;
		}
		var btnWord =$(".btn-success").text();
		$(".btn-success").text(btnWord+"中...");
		$(".btn-success").prop("disabled",true);
		$.ajax({
			type:"post",
			dataType:"json",
			data:$("form[name = 'tableFiledForm']").serialize(),
			url:url,
			success: function(data) {
				if(data.id > 0) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/mdiy/tableFiled/index.do?tableId=${tableId?default('')}";
				}
				else{
					$(".btn-success").html(btnWord+"<span class='glyphicon " + logoClass + "' style='margin-right:5px'></span>");
					$(".btn-success").text(btnWord);
					$(".btn-success").removeAttr("disabled");
					$('.ms-notifications').offset({top:43}).notify({
					   type:'danger',
					   message: { text:data.resultMsg }
					}).show();
				}
			}
		})
	}
</script>
