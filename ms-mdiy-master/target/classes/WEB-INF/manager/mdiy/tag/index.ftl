<@ms.html5>
	<@ms.nav title="标签管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
	<@ms.text label="标签编号"  title="请输入标签编号"  placeholder="请输入标签编号" value=""   />
			<@ms.searchFormButton>
				 <@ms.queryButton onclick="search()"/> 
			</@ms.searchFormButton>			
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNav>
				<@ms.panelNavBtnGroup>
					<@shiro.hasPermission name="mdiy:tag:save"><@ms.panelNavBtnAdd id="addTagBtn" title=""/></@shiro.hasPermission> 
					<@shiro.hasPermission name="mdiy:tag:del"><@ms.panelNavBtnDel id="delTagBtn" title=""/></@shiro.hasPermission> 
				</@ms.panelNavBtnGroup>
			</@ms.panelNav>
		</div>
		<table id="tagList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="get" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delTag" title="标签数据删除" >
		<@ms.modalBody>删除此标签
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认" class="btn btn-danger rightDelete"  id="deleteTagBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>

	<!--弹窗编辑与新增-->
	<@ms.modal id="addTag" title="标签编辑"  resetFrom=true>
	    <@ms.modalBody>
			<@ms.form name="tagForm" isvalidation=true action="${managerPath}/mdiy/tag/save.do" redirect="${managerPath}/mdiy/tag/index.do">
				<@ms.hidden name="id" value="${(tagEntity.id)?default('')}"/>
    			<@ms.text label="标签名称" name="tagName" value="${(tagEntity.tagName)?default('')}"  width="240px;" placeholder="请输入标签名称" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"标签名称长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.select  name="tagType"  label="标签类型"  width="240"   list=[{"id":0,"value":"单条记录标签"},{"id":1,"value":"多记录标签"},{"id":2,"value":"功能标签"},{"id":3,"value":"逻辑表"}] listKey="id"  listValue="value"   />	
    			<@ms.textarea label="描述" name="tagDescription" value="${(tagEntity.tagDescription)?default('')}"  width="240px;" placeholder="请输入描述" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"描述长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
	    	</@ms.form>
	    </@ms.modalBody>
	    <@ms.modalButton>
	         <@ms.saveButton id= "saveOrUpdate"/>
	    </@ms.modalButton>
	</@ms.modal>
</@ms.html5>

<script>
	$(function(){
		$("#tagList").bootstrapTable({
			url:"${managerPath}/mdiy/tag/list.do",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
	    	columns: [{ checkbox: true},
				    	{
				        	field: 'id',
				        	title: '编号',
				        	width:'11',
				        	align: 'center',
				    	},
							    	{
				        	field: 'tagName',
				        	title: '标签名称',
				        	width:'255',
				        	formatter:function(value,row,index) {
				        		return "<a onclick='update("+row.id+")' style='cursor:pointer;text-decoration:none;' >" + value + "</a>";
				        	}
				    	},
							    	{
				        	field: 'tagType',
				        	title: '标签类型',
				        	width:'255',
				        	align: 'center',
				       		formatter:function(value,row,index) {
		        				if(value == "0"){
		        					return "单条记录标签";
		        				}else if(value == "1"){
		        					return "多记录标签";
		        				}
		        				else if(value == "2"){
		        					return "功能标签";
		        				}else if(value == "3"){
		        					return "逻辑表";
		        				}
		        			}
				    	},
							    	{
				        	field: 'tagDescription',
				        	title: '描述',
				        	width:'255',
				    	},
				   					{
				        	field: '',
				        	title: '操作',
				        	width:'255',
				        	align: 'center',
				        	formatter:function(value,row,index) {		
				        		var url = "${managerPath}/mdiy/tagSql/index.do?id="+row.id;
				        		return "<a href=" +url+ " target='_self'>" + "查看sql" + "</a>";
				        	}
				    	}
			]
	    })
	})
	//增加按钮
	$("#addTagBtn").click(function(){
		$("#addTag").modal();
	})
	//删除按钮
	$("#delTagBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#tagList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delTag").modal();
		}
	})
	
	$("#deleteTagBtn").click(function(){
		var rows = $("#tagList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/mdiy/tag/delete.do",
			data: JSON.stringify(rows),
			dataType: "json",
			contentType: "application/json",
			success:function(msg) {
				if(msg.result == true) {
					<@ms.notify msg= "删除成功" type= "success" />
				}else {
					<@ms.notify msg= "删除失败" type= "danger" />
				}
				location.reload();
			}
			
			
		})
	});
	var url = "${managerPath}/mdiy/tag/save.do";
	if($("input[name = 'id']").val() > 0){
		url = "${managerPath}/mdiy/tag/update.do";
		$(".btn-success").text("更新");
	}
	
	//表单赋值
	function update(id){
		$(this).request({url:"${managerPath}/mdiy/tag/get.do?id="+id,method:"get",func:function(tag) {
			if (tag.id > 0) {
				$("#tagForm").attr("action","${managerPath}/mdiy/tag/update.do");
				$("#tagForm input[name='id']").val(tag.id);
				$("#tagForm input[name='tagName']").val(tag.tagName);
				$("#tagForm [name='tagType']").val(tag.tagType);
				$("#tagForm [name='tagDescription']").val(tag.tagDescription);
				$("#addTag").modal();
			}
		}});
	}
	
	//编辑按钮onclick
	$("#saveOrUpdate").click(function(){
		if($("#tagForm input[name = 'id']").val() > 0){
			url = "${managerPath}/mdiy/tag/update.do";
			$(".btn-success").text("更新");
		}
		$("#tagForm").data("bootstrapValidator").validate();
			var isValid = $("#tagForm").data("bootstrapValidator").isValid();
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
			data:$("form[name = 'tagForm']").serialize(),
			url:url,
			success: function(status) {
				if(status.id > 0) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/mdiy/tag/index.do";
				}
				else{
					$(".btn-success").text(btnWord);
					$(".btn-success").removeAttr("disabled");
					<@ms.notify msg= "保存或更新失败！" type= "danger" />
				}
			}
		})
	})
	//查询功能
	function search(){
		var search = $("form[name='searchForm']").serializeJSON();
        var params = $('#tagList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}
   	 	$("#tagList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}
</script>