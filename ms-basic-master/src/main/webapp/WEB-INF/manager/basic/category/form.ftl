<!DOCTYPE html>
<html>
<head>
	<title>分类表编辑</title>
    <#include "../../include/head-file.ftl">
	<link rel="stylesheet" href="//at.alicdn.com/t/font_1126301_ev4y5mf1wtm.css" />
</head>

<body>
	<div id="category-form" style="overflow: hidden;">
		<el-header class="ms-header ms-tr" height="50px">
			<el-button type="primary" size="mini" @click="saveOrUpdate()"><i class="iconfont icon-icon-1"></i>保存</el-button>
            <el-button size="mini" plain onclick="javascript:history.go(-1)"><i class="iconfont icon-fanhui1"></i>返回</el-button>
		</el-header>
		<el-main class="ms-container">
			<el-form :model="categoryForm"  ref="categoryForm" :rules="rules"  label-width="150px" class="demo-ruleForm" size="mini">
				<el-row>
					<el-col :span="12">
						<el-form-item label="${modelTitle}名称" size="mini" prop="categoryTitle">
							<el-input class="input-name" v-model.trim="categoryForm.categoryTitle"></el-input>
						</el-form-item>
					</el-col>
				    <el-col :span="12">
                        <el-form-item label="所属栏目:" prop="categoryCategoryId">
                            <el-cascader v-model="categoryForm.categoryCategoryId" class="input el-input el-input--mini"
                                         :options="categoryList"
                                         :show-all-levels="false"
                                         :props="{checkStrictly :true,emitPath: false,
		                    	checkStrictly: true,
		                    	label: 'categoryTitle',
		                    	value:'categoryId'}"
                            >
                            </el-cascader>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
					<el-col :span="12">
						<el-form-item label="排序:" prop="categorySort">
							<el-input v-model.number="categoryForm.categorySort" class="input-width el-input el-input--mini"></el-input>
						</el-form-item>
					</el-col>
				 </el-row>
                <el-row>
					<el-col :span="24">
						<el-form-item label="略缩图:" prop="categorySmallImg">
							<el-upload
									:action=ms.base+"/file/upload.do"
									list-type="picture-card"
									:limit="1"
									:data={uploadFloderPath:"/mall/upload"}
									:on-preview="categorySmallimgHandlePreview"
									:on-remove="categorySmallimgHandleRemove"
									:on-success="categorySmallimgHandlePicSuccess"
									:before-upload="categorySmallimgBeforeAvatarUpload"
									:on-exceed="categoryhandleExceed"
									:file-list="categorySmallimgList">
								<i class="el-icon-plus"></i>
								<div class="el-upload__tip" slot="tip">
									最多上传一张图片，大小不能超过 2MB，格式:JPG、JPEG、PNG</div>
							</el-upload>
							<el-dialog :visible.sync="dialogVisible">
								<img width="100%" :src="categoryForm.categorySmallimg" alt="">
							</el-dialog>
						</el-form-item>
					</el-col>
				 </el-row>
                <el-row>
					<el-col :span="24">
						<el-form-item label="${modelTitle}描述:" prop="categoryDescription">
							<el-input :rows="4" type="textarea" v-model="categoryForm.categoryDescription"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
		</el-main>
	</div>
</body>

</html>
<script>
var formVue = new Vue({
	el: '#category-form',
	data: {
		categoryForm:{
        	categoryId:0,		  //类别id
        	categoryTitle: "",      //类别标题
        	categoryModelId:0,   //所属模块id
			categorySmallImg: "", //略缩图
			categorySort: 0,  //类别排序
			categoryDescription:  "",  //栏目描述
			categoryCategoryId: 0,  //父类别编号
        },
        categoryList:[], //所属栏目
		dialogVisible: false,//显示放大,图片
		categorySmallimgList: [], //上传文件列表
		modelId:0,
        rules:{
			categoryTitle:[
				{ required: true, message: '请输入分类名称', trigger: 'blur' },
				{ min: 1, max: 50, message: '长度不能超过50个字符', trigger: 'change' }
			],
			categorySort: [
				{ required: false, message: '请输入排序', trigger: 'blur' },
				{ pattern:/^[0-9]*$/, message: '请输入0-99999999之间的数', trigger: 'change' },
			],
			categoryDescription: [
				{ min:0, max:200 , message: '长度不能超过200个字符', trigger: 'change' },
			],
		}, //验证
		},
	methods: {
		//新增或者编辑
        saveOrUpdate:function(){
			var that = this;
			var url =ms.manager+"/category/save.do"
			that.categoryForm.categoryModelId = that.modelId;
			if(that.categoryForm.categoryId > 0){
				url =ms.manager+"/category/update.do";
			}
			this.$refs.categoryForm.validate((valid) => {
				if (valid) {
					ms.http.post(url,that.categoryForm).then(function(data){
						if(data.categoryId > 0){
							that.$notify({
				       			title: '成功',
				        		message: '保存成功',
				        		type: 'success'
		        			});
		            	 window.history.back(-1);
		            	 //刷新页面
		            	 self.location = document.referrer;
		            	 that.resetForm();
						}else{
							that.$notify({
			       				title: '失败',
			        			message: data.resultMsg,
			        			type: 'warning'
		        			});
						}
		            });	
				}else{
                    return false;
				}
			})
        },
    	//获取当前分类表
        get(categoryId){
        	var that = this;
        	ms.http.get(ms.manager+"/category/get.do", {"categoryId":categoryId}).then(function(data){
				if (data.categorySmallImg) {
					//显示图片
					that.categorySmallimgList = [{url:data.categorySmallImg}]
				}
        		that.categoryForm = data;
            }).catch(function(err) {
                console.log(err);
            });
        },
         //获取所有栏目
        getCategory: function () {
            var that = this;
            ms.http.get(ms.manager + "/category/list.do",{
            	modelId:that.modelId,
            }).then(
                function (data) {
                    that.categoryList = treeData(data.rows, 'categoryId', 'categoryCategoryId', 'children');
                }).catch(function (err) {
                console.log(err);
            });
        },
		//上传之前调用
		categorySmallimgBeforeAvatarUpload(file) {
			var fileType = false;
			if (file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/jpg') {
				fileType = true;
			}
			const isLt2M = file.size / 1024 / 1024 < 2;
			if (!fileType) {
				this.$message.error('上传商品缩略图只能是 JPG、JPEG、PNG 格式!');
			}
			if (!isLt2M) {
				this.$message.error('上传商品缩略图大小不能超过 2MB!');
			}
			return fileType && isLt2M;
		},
		//上传图片完成回调
		categorySmallimgHandlePicSuccess(res, file) {
			file.url = ms.base + file.response;
			this.categorySmallimgList.push(file);
			this.categoryForm.categorySmallImg= file.url;
		},
		//移除图片
		categorySmallimgHandleRemove(file, fileList) {
			var index = -1;
			index = this.categorySmallimgList.findIndex(text => text == file);
			if (index != -1) {
				this.categorySmallimgList.splice(index, 1);
			}
		},
		//放大图片
		categorySmallimgHandlePreview(file) {
			this.categoryForm.categorySmallimg = ms.base + file.response;
			this.dialogVisible = true;
		},
		//图片限制
		categoryhandleExceed(files, fileList) {
			this.$message.warning("最多上传一张图片");
		},
	},
	mounted(){
		this.categoryId = ms.util.getParameter("categoryId");
		this.modelId = ms.util.getParameter("modelId");
		this.modelId = ${modelId};
		if(this.categoryId > 0){
			this.get(this.categoryId);
		}
		this.getCategory();
	}
})
</script>
<style>
	body{
		overflow: hidden
	}
	#category-form  .iconfont{
		font-size: 12px;
		margin-right: 5px;
	}
	#category-form .ms-container {
	    height: calc(100vh - 72px);
	}
</style>