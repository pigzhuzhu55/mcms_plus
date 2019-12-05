<!DOCTYPE html>
<html>
<head>
    <title>自定义表编辑</title>
     <#include "/include/head-file.ftl"> 
    <!--<!--#include virtual="include/head-file.ftl"/> --> 
</head>

<body>
	<div id="app" style="width:100%">
		<el-header class="ms-header ms-tr" height="46px">
		 	<el-button type="success" icon="el-icon-edit" size="mini" @click="save('form')">保存</el-button>
            <el-button @click="resetForm('form')" size="mini">重置</el-button>
            <el-button size="mini" icon="el-icon-back" plain onclick="javascript:history.go(-1)">返回</el-button>
		</el-header>
		<el-main class="ms-container" style="margin: 0px;">
			<el-form :model="form" :rules="rules" ref="form" label-width="160px" class="demo-ruleForm" size="small">
				<el-form-item label="自定义表名:" prop="tableName"><el-input v-model="form.tableName"></el-input></el-form-item>
                <el-form-item label="主表或主业务关键字:" prop="tableMaster"><el-input v-model="form.tableMaster"></el-input></el-form-item>
                <el-form-item label="数据编号:" prop="tableMasterId"><el-input v-model="form.tableMasterId"></el-input></el-form-item>
			</el-form>
		</el-main>
	</div>
</body>

</html>
<script>
var formVue = new Vue({
	el: '#app',
	data: {
		form: {
			id: 0, //编号
			tableName:"", //自定义表名
			tableMaster:"", //主表名或主业务关键字
			tableMasterId:"", //数据编号
		},
		//表单验证
		rules: {
			tableName: [
				{ required: true, message: '请输入自定义表名', trigger: 'blur' },
                { min: 1, max: 30, message: '长度在 1 到 50 个字符', trigger: 'blur' }
			],
			tableMaster: [
				{ required: true, message: '主表名或主业务关键字', trigger: 'blur' },
                { min: 1, max: 30, message: '长度在 1 到 50 个字符', trigger: 'blur' }
			],
			tableMasterId: [
				{ required: true, message: '数据编号', trigger: 'blur' },
                { min: 1, max: 30, message: '长度在 1 到 11 个字符', trigger: 'blur' }
			],
		}
	},
	methods: {
		save(formName){
			var that = this;
			var url =ms.manager +"/mdiy/table/save.do"
			if(that.id > 0){
				url =ms.manager +"/mdiy/table/update.do";
			}
			this.$refs[formName].validate((valid) => {
				if (valid) {
					ms.http.post(url,this.form,{
						headers: {
	                		'Content-Type': 'application/x-www-form-urlencoded'
	                	}
					}).then(function(data){
						if(data){
							that.$notify({
				       			title: '成功',
				        		message: '保存成功',
				        		type: 'success'
		        			});
		            		location.href =ms.manager +"/mdiy/table/index.do"; 
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
	    //重置
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        get(id){
        	var that = this;
        	ms.http.get(ms.manager +"/mdiy/table/get.do", {"id":id}).then(function(data){
	            that.form = data;
            }).catch(function(err) {
                console.log(err);
            });
        },
	},
	mounted(){
		this.id =  ms.util.getParameter("id");
		if(this.id > 0){
			this.get(this.id);
		}
	}
})
</script>