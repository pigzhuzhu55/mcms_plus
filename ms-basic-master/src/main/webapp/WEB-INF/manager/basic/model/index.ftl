<!DOCTYPE html>
<html>
<head>
	<title>菜单</title>
	<meta charset="utf-8">
	<!--浏览器小图标-->
	<script type="text/javascript" src="${base}/static/plugins/vue/2.6.9/vue.min.js"></script>
	<!--小图标-->
	<link rel="stylesheet" href="${base}/static/ms-admin/4.7.2/iconfont/iconfont.css"/>
	<link rel="stylesheet" href="${base}/static/ms-admin/4.7.2/iconfont/iconfont.json"/>
	<!-- 引入样式 -->
	<link rel="stylesheet" href="${base}/static/plugins/element-ui/2.12.0/index.css">
	<!-- 引入组件库 -->
	<script src="${base}/static/plugins/element-ui/2.12.0/index.js"></script>
	<!--图片懒加载-->
	<script src="${base}/static/plugins/vue.lazyload/1.2.6/vue-lazyload.js"></script>
	<!--网络请求框架-->
	<script src="${base}/static/plugins/axios/0.18.0/axios.min.js"></script>
	<script src="${base}/static/plugins/qs/6.6.0/qs.min.js"></script>
	<!--铭飞-->
	<script src="${base}/static/plugins/ms/1.0.0/ms.js"></script>
	<script src="${base}/static/plugins/ms/1.0.0/ms.http.js"></script>
	<script src="${base}/static/plugins/ms/1.0.0/ms.util.js"></script>
	<script src="${base}/static/plugins/vue-ueditor-wrap/vue-ueditor-wrap.min.js"></script>
	<!--通用样式-->
	<link rel="stylesheet" href="${base}/static/ms-admin/4.7.2/css/app.css"/>
	<script>
		ms.base = "${base}";
		ms.manager = "${managerPath}";
		ms.web = ms.base;

		//ms.base = "http://192.168.0.54:90/";
		//ms.manager = "http://192.168.0.54:90/apis/ms/";
		//ms.web = "http://192.168.0.54:90/apis/";
		//图片懒加载
		Vue.use(VueLazyload, {
			error: ms.base + '/static/ms-admin/4.7.2/images/error.png',
			loading: ms.base + '/static/ms-admin/4.7.2/images/loading.png',
		})
	</script>
	<style>
		.ms-admin-menu .is-active {
			border: 0px !important;
		}
	</style>
</head>
<body>
<div id="index" v-cloak>
	<el-header class="ms-header" height="50px">
		<el-col :span="24">
			<el-button type="primary" icon="el-icon-plus" size="mini" @click="editModal(0)">新增</el-button>
			<el-button type="danger" icon="el-icon-delete" size="mini" @click="del(selectionList)"  :disabled="!selectionList.length">删除</el-button>
			<el-button icon="iconfont icon-daoru" size="mini" @click="dialogImportVisible=true" style="float: right">导入</el-button>
		</el-col>
	</el-header>
	<el-dialog title="导入菜单" :visible.sync="dialogImportVisible" width="600px" append-to-body v-cloak>
			<el-popover style="position: absolute;left: 16%;top: 5.6%;" placement="top-start" title="提示" trigger="hover" content="可通过代码生成器编辑菜单中复制菜单获取">
				<i class="el-icon-question" slot="reference"></i>
			</el-popover>
		<el-form>
			<el-form-item>
				<el-input :rows="10" type="textarea" v-model="modelJson"></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer">
			<el-button size="mini" @click="dialogEditMenuVisible = false">取 消</el-button>
			<el-button size="mini" :disabled="saveDisabled" type="primary" @click="imputJson()">确 定</el-button>
		</div>
	</el-dialog>

	<el-main class="ms-container">
		<el-table ref="multipleTable"
				  height="calc(100vh - 102px)"
				  class="ms-table-pagination"
				  border :data="dataList"
				  tooltip-effect="dark"
				  @selection-change="handleSelectionChange"
				  row-key="modelId"
				  :tree-props="{children: 'children'}">
			<el-table-column type="selection" width="40"></el-table-column>
			<el-table-column label="菜单标题" width="200" align="left" prop="modelTitle">
			</el-table-column>
			<el-table-column label="菜单图标" width="100" align="center" prop="modelIcon">
				<template slot-scope="scope">
					<i style="font-size: 24px !important;" class="iconfont" :class="scope.row.modelIcon"></i>
				</template>
			</el-table-column>
			<el-table-column label="菜单连接地址" align="left" prop="modelUrl">
			</el-table-column>
			<el-table-column label="菜单类型" width="100" align="left" prop="isChild">
			</el-table-column>
			<el-table-column label="菜单排序" width="90" align="left" prop="modelSort">
			</el-table-column>
			<el-table-column label="是否是菜单"  width="130" align="center" prop="modelIsMenu">
				<template slot-scope="scope">
					<span v-if="scope.row.modelIsMenu == 1">是</span>
					<span v-else>否</span>
				</template>
			</el-table-column>
			<@shiro.hasPermission name="model:update">
				<el-table-column label="操作"  align="center" width="90">
					<template slot-scope="scope">
						<el-button size="medium" type="text" @click="editModal(scope.row.modelId)">编辑</el-button>
					</template>
				</el-table-column>
			</@shiro.hasPermission>
		</el-table>
	</el-main>
</div>
<#include "/component/icon.ftl">
<#include "/component/select-tree.ftl">
<#include "/basic/model/form.ftl">
</body>

</html>
<script>
	var indexVue = new Vue({
		el: '#index',
		data:{
			dataList: [], //列表
			selectionList:[],//列表选中
			total: 0, //总记录数量
			pageSize: 10, //页面数量
			currentPage:1, //初始页
			mananger: ms.manager,
			dialogImportVisible: false,
			modelJson:'',
			saveDisabled: true,
		},
		watch:{
			'modelJson': function (n,o) {
				if(n){
					this.saveDisabled=false;
				} else {
					this.saveDisabled=true;
				}
			},
			'dialogImportVisible': function (n,o) {
				if(!n){
					this.modelJson='';
				}
			},
		},
		methods:{
			//查询列表
			list: function() {
				var that = this;
				ms.http.get(ms.manager+"/model/list.do",{}).then(
						function(data){
							that.dataList = ms.util.treeData(data.rows,'modelId','modelModelId','children');
							form.modeldata = that.dataList;
						}).catch(function(err){
					console.log(err);
				});
			},
			//列表选中
			handleSelectionChange:function(val){
				this.selectionList = val;
			},
			//删除
			del: function(row){
				var that = this;
				that.$confirm('删除选中菜单，如果有子菜单也会一并删除', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var ids = "";
					for(var i=0;i<row.length;i++){
						if(ids == ""){
							ids = row[i].modelId;
						}else{
							ids = ids+","+row[i].modelId
						}
					}
					ms.http.post(ms.manager+"/model/delete.do",{
						ids:ids,
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
			//新增或编辑
			editModal: function(id){
				form.open(id);
			},
			imputJson: function () {
				var that = this;
				ms.http.post(ms.manager+"/upgrader/import.do",{
					menuStr: that.modelJson,
				}).then(function(data){
					if(data.result){
						window.location.href =  ms.manager +"/model/index.do";
					} else {
						that.$notify({
							title: '失败',
							message: data.resultMsg,
							type: 'warning'
						});
					}
				}).catch(function(err){
					console.log(err);
				});
			}
		},
		mounted(){
			this.list();
		},
	})
</script>
<style>
	#index .ms-search{
		background: #fff;
	}
	#index .iconfont{
		font-size: 12px !important;
		margin-right: 4px;
	}
	#index .ms-search .ms-search-footer{
		line-height: 60px;
		text-align: center;
	}
	#index .ms-container{
		height: calc(100vh - 75px);
	}
</style>