<!DOCTYPE html>
<html>

<head>
    <title>自定义表管理</title>
    <#include "/include/head-file.ftl" /> 
    <!--#include virtual="../include/head-file.ftl" --> 
</head>

<body>
	<div id="app">
		<el-header class="ms-header" height="50px">
			<el-col :span="12">
				<el-button type="primary" icon="el-icon-edit" size="mini" @click="openForm()">新增</el-button>
				<el-button type="danger" icon="el-icon-delete" size="mini" @click="del()" >删除</el-button>
			</el-col>
		</el-header>
		<el-main class="ms-container">
			<el-table ref="multipleTable" :data="dataList" tooltip-effect="dark" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="50"></el-table-column>
                <el-table-column label="编号" width="100"><template slot-scope="scope">{{ scope.row.id }}</template></el-table-column>
                <el-table-column label="自定义表名"  width="300">
                	<template slot-scope="scope">
                		<a :href="ms.manager + '/mdiy/table/form.do?id='+scope.row.id">{{ scope.row.tableName }}</a>
                	</template>
                </el-table-column>
                <el-table-column label="主表或主业务关键字" width="300">
                	<template slot-scope="scope">
                		{{ scope.row.tableMaster }}
                	</template>
                </el-table-column>
                <el-table-column label="操作" width="300">
                	<template slot-scope="scope">
                		<a :href="ms.manager + '/mdiy/tableFiled/index.do?id='+scope.row.id">表字段</a>
                	</template>
                </el-table-column>
            </el-table>
             <el-pagination background :page-sizes="[5, 10, 20]" layout="total, sizes, prev, pager, next, jumper" :current-page="currentPage"  :page-size="pageSize"  :total="total" class="ms-pagination" @current-change='currentChange' @size-change="sizeChange">
		</el-main> 
	</div>
</body>

</html>
<script>
	var appVue = new Vue({
	el: '#app',
	data:{
		dataList: [], //微信列表
		selData: [], //选中列表
		total: 0, //总记录数量
        pageSize: 10, //页面数量
        currentPage:1, //初始页
        mananger: ms.manager,
	},
	methods: {
		//查询列表
		list: function() {
			var that = this;
			ms.http.get(ms.manager + "/mdiy/table/list.do",{
				pageNo:that.currentPage,
	            pageSize:that.pageSize
			}).then(
				function(data){
					that.total = data.total;
					that.dataList = data.rows;
				}).catch(function(err){
					console.log(err);
				});
		},
	    //选中行，selection返回每一行选中行的对象
        handleSelectionChange: function(selection) {
            this.selData = selection;
        },
        //新增
        openForm:function(){
        	location.href = ms.manager + "/mdiy/table/form.do";
        },
        //pageSize改变时会触发
        sizeChange:function(pageSize) {
            this.pageSize = pageSize;
            this.list();
        },
        //currentPage改变时会触发
        currentChange:function(currentPage) {
            this.currentPage = currentPage;
            this.list();
        },
        //删除
        del: function(){
        	var that = this;
        	that.$confirm('此操作将永久删除该表, 是否继续?', '提示', {
					    	confirmButtonText: '确定',
					    	cancelButtonText: '取消',
					    	type: 'warning'
					    }).then(() => {
					    	ms.http.post(ms.manager + "/mdiy/table/delete.do", this.selData,{
            					headers: {
                					'Content-Type': 'application/json'
                				}
            				}).then(
	            				function(data){
		            				if (data.result) { 	
										that.$notify({
						     				type: 'success',
						        			message: '删除成功!'
						    			});
					    				//删除成功，刷新列表
					      				that.list();
					      			}
	            				});
					    }).catch(() => {
					    	that.$notify({
					        	type: 'info',
					        	message: '已取消删除'
					    	});          
				    	});	            	
        },
	},	
	mounted(){
		this.list();
	}
})
</script>