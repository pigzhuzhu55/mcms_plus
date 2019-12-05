<@ms.html5>
	<@ms.nav title="标签对应多个sql语句管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
			<@ms.searchFormButton>
				 <@ms.queryButton onclick="search()"/> 
			</@ms.searchFormButton>			
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNavBtnGroup>
				<@shiro.hasPermission name="mdiy:tagSql:save"><@ms.panelNavBtnAdd id="addTagSqlBtn" title=""/></@shiro.hasPermission>
				<@shiro.hasPermission name="mdiy:tagSql:del"><@ms.panelNavBtnDel id="delTagSqlBtn" title=""/></@shiro.hasPermission>
			</@ms.panelNavBtnGroup>
		</div>
		<table id="tagSqlList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="get" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delTagSql" title="标签对应多个sql语句数据删除" >
		<@ms.modalBody>删除此标签对应多个sql语句
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认" class="btn btn-danger rightDelete"  id="deleteTagSqlBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>

	<!--弹窗编辑与新增-->
	<@ms.modal id="addTagSql" title="标签对应多个sql语句编辑"  resetFrom=true>
	    <@ms.modalBody>
			<@ms.form name="tagSqlForm" isvalidation=true action="${managerPath}/mdiy/tagSql/save.do" redirect="${managerPath}/mdiy/tagSql/index.do">
				<@ms.hidden name="tagId" value="${tagId?default('')}"/>
				<@ms.hidden name="id" value="${(tagSqlEntity.id)?default('')}"/>				
    			<@ms.textarea label="自定义sql" help="支持ftl写法" name="tagSql" value="" width="240px;"  placeholder="请输入自定义sql支持ftl写法" validation={"required":"true","maxlength":"1000","data-bv-stringlength-message":"自定义sql支持ftl写法长度不能超过一千个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="排序升序" name="sort" value=""  width="240px;" placeholder="请输入排序升序" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"排序升序长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
	    	</@ms.form>
	    </@ms.modalBody>
	    <@ms.modalButton>
	         <@ms.saveButton id= "saveOrUpdate"/>
	    </@ms.modalButton>
	</@ms.modal>
</@ms.html5>

<script>

	$(function(){
		$("#tagSqlList").bootstrapTable({
			url:"${managerPath}/mdiy/tagSql/list.do?tagId=${tagId}",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
	    	columns: [{ checkbox: true},
				    	{
				        	field: 'id',
				        	title: '自定义标签编号',
				        	width:'11',
				        	align: 'center',
				    	},
							    	{
				        	field: 'tagSql',
				        	title: '自定义sql支持ftl写法',
				        	width:'255',
				        	formatter:function(value,row,index) {
				        		return "<a onclick='update("+row.id+")' style='cursor:pointer;text-decoration:none;' >" + value + "</a>";
				        	}
				    	},
							    	{
				        	field: 'sort',
				        	title: '排序升序',
				        	width:'255',
				        	align: 'center',
				    	}
			]
	    })
	})
	//增加按钮
	$("#addTagSqlBtn").click(function(){
		$("#addTagSql").modal();
	})
	//删除按钮
	$("#delTagSqlBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#tagSqlList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delTagSql").modal();
		}
	})
	
	$("#deleteTagSqlBtn").click(function(){
		var rows = $("#tagSqlList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/mdiy/tagSql/delete.do",
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
	var url = "${managerPath}/mdiy/tagSql/save.do";
	if($("input[name = 'id']").val() > 0){
		url = "${managerPath}/mdiy/tagSql/update.do";
		$(".btn-success").text("更新");
	}
	
	//表单赋值
	function update(id){
		$(this).request({url:"${managerPath}/mdiy/tagSql/get.do?id="+id,func:function(tagSql) {
			if (tagSql.id > 0) {
				$("#tagSqlForm").attr("action","${managerPath}/mdiy/tagSql/update.do");
				$("#tagSqlForm input[name='id']").val(tagSql.id);
				$("#tagSqlForm [name='tagSql']").text(tagSql.tagSql);
				$("#tagSqlForm input[name='sort']").val(tagSql.sort);
				$("#addTagSql").modal();
			}					
		}});
	}
	
	//编辑按钮onclick
	$("#saveOrUpdate").click(function(){
		if($("#tagSqlForm input[name='id']").val() > 0){
			url = "${managerPath}/mdiy/tagSql/update.do";
			$(".btn-success").text("更新");
		}
		$("#tagSqlForm").data("bootstrapValidator").validate();
			var isValid = $("#tagSqlForm").data("bootstrapValidator").isValid();
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
			data:$("form[name = 'tagSqlForm']").serialize(),
			url:url,
			success: function(status) {
				if(status.id > 0) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/mdiy/tagSql/index.do?id=${tagId}";
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
        var params = $('#tagSqlList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}  
   	 	$("#tagSqlList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}
</script>