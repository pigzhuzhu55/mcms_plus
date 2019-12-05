<@ms.html5>
	<@ms.nav title="自定义表对应字段管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
	<@ms.text label="自定义字段"  title="请输入自定义字段"  placeholder="请输入自定义字段" value=""/>
			<@ms.searchFormButton>
				 <@ms.queryButton onclick="search()"/> 
			</@ms.searchFormButton>			
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNavBtnGroup>
				<@shiro.hasPermission name="mdiy:tableFiled:save"><@ms.panelNavBtnAdd id="addTableFiledBtn" title=""/></@shiro.hasPermission> 
				<@shiro.hasPermission name="mdiy:tableFiled:del"><@ms.panelNavBtnDel id="delTableFiledBtn" title=""/></@shiro.hasPermission> 
			</@ms.panelNavBtnGroup>
		</div>
		<table id="tableFiledList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="get" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delTableFiled" title="自定义表对应字段数据删除" >
		<@ms.modalBody>删除此自定义表对应字段
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认" class="btn btn-danger rightDelete"  id="deleteTableFiledBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>
</@ms.html5>

<script>
	$(function(){
		$("#tableFiledList").bootstrapTable({
			url:"${managerPath}/mdiy/tableFiled/list.do?tableId=${tableId}",
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
				        	field: 'tableId',
				        	title: '自定义表编号',
				        	width:'11',
				        	align: 'center',
				    	},
							    	{
				        	field: 'tfName',
				        	title: '字段名称',
				        	width:'255',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/mdiy/tableFiled/form.do?tableId=${tableId}&&id="+row.id;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},
							    	{
				        	field: 'tfType',
				        	title: '类型',
				        	width:'255',
				        	align: 'center',
				    	},
							    	{
				        	field: 'tfDefault',
				        	title: '默认值',
				        	width:'255',
				    	},
							    	{
				        	field: 'tfDescription',
				        	title: '描述',
				        	width:'255',
				    	},
							    	{
				        	field: 'tfUnique',
				        	title: '是否唯一',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		if(value == "0"){
		        					return "不唯一";
		        				}else if(value == "1"){
		        					return "唯一";
		        				}
				        	}
				    	},
							    	{
				        	field: 'tfRequired',
				        	title: '是否必填',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				   				if(value == "0"){
		        					return "非必填";
		        				}else if(value == "1"){
		        					return "必填";
		        				}
				        	}
				    	},
							    	{
				        	field: 'tfConfig',
				        	title: 'json配置',
				        	width:'255',
				        	align: 'center',
				    	},
							    	{
				        	field: 'tfHelp',
				        	title: '帮助信息',
				        	width:'255',
				        	align: 'center',
				    	},
			]
	    })
	})
	//增加按钮
	$("#addTableFiledBtn").click(function(){
		location.href ="${managerPath}/mdiy/tableFiled/form.do?tableId=${tableId}"; 
	})
	//删除按钮
	$("#delTableFiledBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#tableFiledList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delTableFiled").modal();
		}
	})
	
	$("#deleteTableFiledBtn").click(function(){
		var rows = $("#tableFiledList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/mdiy/tableFiled/delete.do",
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
	//查询功能
	function search(){
		var search = $("form[name='searchForm']").serializeJSON();
        var params = $('#tableFiledList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}  
   	 	$("#tableFiledList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}
</script>