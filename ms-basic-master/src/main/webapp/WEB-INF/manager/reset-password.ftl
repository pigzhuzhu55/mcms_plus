<!-- 修改密码 -->
<div id="reset-password" class="reset-password">
        <el-dialog title="修改密码" :visible.sync="isShow" width="460px">
            <el-form :model="resetPasswordForm" ref="resetPasswordForm" :rules="resetPasswordFormRule" label-width='100px'>
                <el-form-item label="账号">
                    <el-input v-model="resetPasswordForm.managerName" autocomplete="off" readonly disabled></el-input>
                </el-form-item>
                <el-form-item label="旧密码" prop="oldManagerPassword">
                    <el-input v-model="resetPasswordForm.oldManagerPassword" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newManagerPassword">
                    <el-input v-model="resetPasswordForm.newManagerPassword" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="isShow = false;resetPasswordForm.oldManagerPassword = '';resetPasswordForm.newManagerPassword = ''">取 消</el-button>
                <el-button type="primary" @click="updatePassword">更新密码</el-button>
            </div>
    </el-dialog>
</div>
<script>
    var resetPasswordVue = new Vue({
        el: '#reset-password',
        data: {
            // 模态框的显示
            isShow: false,
            resetPasswordForm: {
                managerName: '',
                oldManagerPassword: '',
                newManagerPassword: '',
            },
            resetPasswordFormRule: {
	             oldManagerPassword: [{
	                  required: true,
	                  message: '请输入旧密码',
	                  trigger: 'blur'
	               },
	               {
	                  min: 6,
	                  max: 30,
	                  message: '长度在 6 到 30 个字符',
	                  trigger: 'blur'
	               }
	            ],      
	             newManagerPassword: [{
	                  required: true,
	                  message: '请输入新密码',
	                  trigger: 'blur'
	               },
	               {
	                  min: 6,
	                  max: 30,
	                  message: '长度在 6 到 30 个字符',
	                  trigger: 'blur'
	               }
	            ],	                 	
            }
        },
        methods: {
            // 更新密码
            updatePassword: function () {
            	var that = this;
            	this.$refs['resetPasswordForm'].validate((valid) => {
            		if(valid) {
		             	ms.http.post(ms.manager + "/updatePassword.do",that.resetPasswordForm)
		               .then((data)=>{
		               		if (data.result == true) {
			                	that.resetPasswordForm.oldManagerPassword = '';
			                	that.resetPasswordForm.newManagerPassword = '';
			                	that.isShow = false;
			                	that.$message.success("修改成功");
		               		} else {
		               			 that.$message.error(data.resultMsg);
		               		}
		               }, (err) => {
		                   that.$message.error(err);
		               })	
		             }           	
            	});

            }
        }
    })
</script>