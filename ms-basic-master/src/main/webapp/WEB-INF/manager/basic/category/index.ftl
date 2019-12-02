<!DOCTYPE html>
<html>

<head>
	<title>分类表管理</title>
	<#include "../../include/head-file.ftl">
	<style>
		[v-cloak]{
			display:none;
		}
	</style>
	<link rel="stylesheet" href="//at.alicdn.com/t/font_1126301_ev4y5mf1wtm.css" />
</head>

<body style="overflow: hidden;">
	<div id="category" v-cloak >
		<el-header class="ms-header" height="50px">
			<el-col :span="12">
				<el-button type="primary" icon="el-icon-plus" size="mini" @click="save()">新增</el-button>
				<el-button type="danger" icon="el-icon-delete" size="mini" @click="del()" >删除</el-button>
			</el-col>
		</el-header>
 		<el-main class="ms-container">
			<el-table class="ms-table-pagination" row-key="categoryId" @selection-change="handleSelectionChange" :tree-props="{children: 'children', hasChildren: 'hasChildren'}" ref="multipleTable" :data="dataList" tooltip-effect="dark" :max-height="tableHeight" border default-expand-all="true">
				<el-table-column type="selection" align="center" width="40"></el-table-column>
				<el-table-column width="150" label="编号" prop="categoryId" align="center"></el-table-column>
			    <el-table-column label="${modelTitle}标题" prop="categoryTitle"> </el-table-column>
				<el-table-column width="150" align="center" label="排序" prop="categorySort"></el-table-column>
	            <el-table-column width="150" align="center" label="操作" fixed="right">
					<template slot-scope="scope">
						<a :href="manager+'/category/form.do?categoryId='+scope.row.categoryId+'&modelId='+modelId+'&modelTitle='+modelTitle">编辑</a>
					</template>
				</el-table-column>
			</el-table>
         </el-main>
	</div>
</body>

</html>
<script>
var categoryVue = new Vue({
	el: '#category',
	data:{
		dataList: [], //分类表列表
		selData: [], //选中列表
        modelId:0 ,
        modelTitle:"",
        manager: ms.manager,
	},
	computed:{
		//表格最大高度 用来自适应
		tableHeight:function () {
			return document.documentElement.clientHeight - 70;
		}
	},
	methods:{ 
	    //查询列表
	    list: function() {
	    	var that = this;
	    	ms.http.get(ms.manager+"/category/list.do",{
	    			modelId:that.modelId,
	    		}).then(
					function(data){
							that.total = data.total;
							that.dataList= treeData(data.rows, 'categoryId', 'categoryCategoryId', 'children');
						}).catch(function(err){
							console.log(err);
						});
				},
		//添加
		save(){
			window.location.href =  ms.manager +"/category/form.do?modelId="+this.modelId + "&modelTitle="+ this.modelTitle;
		},
		//删除
        del: function(){
        	var that = this;
        	if(that.selData.length > 0 ){
        		that.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
					    	confirmButtonText: '确定',
					    	cancelButtonText: '取消',
					    	type: 'warning'
					    }).then(() => {
					    	ms.http.post(ms.manager+"/category/delete.do", this.selData,{
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
				    }else{
				that.$message('请先选择公司');
			}         	
        },
        		

         //重置
        reset:function(){
       		this.searchForm.categoryTitle = "";
        	this.list();
        },
        //选中行，selection返回每一行选中行的对象
        handleSelectionChange: function(selection) {
            this.selData = selection;
        },
	},
	mounted(){
		//获取模型编号
		this.modelId = ${modelId};
		this.modelTitle = '${modelTitle}';
		this.list();
	},
})
</script>
<style>
	#category .iconfont{
		font-size: 12px;
		margin-right: 5px;
	}
	.ms-container {
	  height: calc(100vh - 73px);
	  padding: 14px 14px 0 14px;
	  background: #fff;
	}
	/* table 分页*/
	.ms-table-pagination {
	  height:100%;
	}
</style>