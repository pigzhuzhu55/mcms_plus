<!DOCTYPE html>
<html lang="zh">
   <head>
      <title>${app.appName}</title>
      <#include "/include/head-file.ftl"/>
      <script src="${base}/static/plugins/jquery/3.2.1/jquery.min.js"></script>
      <script src="${base}/static/plugins/jquery.cookie/1.4.1/jquery.cookie.min.js"></script>
      <link rel="stylesheet" href="${base}/static/ms-admin/4.7.2/css/login.css">
   </head>
   <body>
      <div id="login" class="login">
         <el-container class="ms-admin-login-container">
            <el-main class="ms-admin-login-main">
               <div class="ms-admin-login-warp" @keydown.13='checkLogin'>
                  <img :src="ms.base+'/static/ms-admin/4.7.2/images/login-slogn.png'">
                  <el-form :model="loginForm" status-icon :rules="loginFormRule" ref="loginForm" label-width="100px"  class="ms-admin-login-form">
                     <h1>
                        <span>账户登录 / </span>User login</h1>
                     <el-form-item class="ms-admin-form-item" prop="managerName">
                        <el-input type="text" placeholder='用户名' v-model="loginForm.managerName" autocomplete="off"></el-input>
                     </el-form-item>
                     <el-form-item class="ms-admin-form-item" prop="managerPassword">
                        <el-input type="password" placeholder='密码' v-model="loginForm.managerPassword" autocomplete="off"></el-input>
                     </el-form-item>
                     <el-form-item class="ms-admin-form-item ms-admin-form-item-code" prop="rand_code">
                        <el-input type="text" placeholder='验证码' v-model="loginForm.rand_code" autocomplete="off"></el-input>
                        <img :src="verifCode" class="code-img" @click="code" />
                        <p>
                           <span>看不清?</span>
                           <span @click="code">换一张</span>
                        </p>
                     </el-form-item>
                     <el-form-item class="ms-admin-form-item ms-admin-form-item-checkout">
                        <el-checkbox v-model="rememberPass">记住密码</el-checkbox>
                     </el-form-item>
                     <el-form-item class="ms-admin-form-item">
                        <el-button type="primary" @click="checkLogin" class="ms-admin-login-btn">登录</el-button>
                     </el-form-item>
                  </el-form>
               </div>
            </el-main>
         </el-container>
      </div>
   </body>

</html>
<script>
   var loginVue = new Vue({
      el: '#login',
      data: {
         loginForm: {
            managerName: '',
            managerPassword: "",
            rand_code: ''
         },
         verifCode: ms.manager + "/code?t=" + new Date().getTime(),
         rememberPass: '',
         loginFormRule: {
            managerName: [{
                  required: true,
                  message: '请输入用户名',
                  trigger: 'blur'
               },
               {
                  min: 1,
                  max: 30,
                  message: '长度在 1 到 30 个字符',
                  trigger: 'blur'
               }
            ],
            managerPassword: [{
                  required: true,
                  message: '请输入密码',
                  trigger: 'blur'
               },
               {
                  min: 1,
                  max: 30,
                  message: '长度在 1 到 30 个字符',
                  trigger: 'blur'
               }
            ],
            rand_code: [{
                  required: true,
                  message: '请填写验证码',
                  trigger: 'blur'
               },
               {
                  min: 1,
                  max: 4,
                  message: '长度在1 到 4 个字符',
                  trigger: 'blur'
               }
            ],
         }
      },
      methods: {
         // 登录
         checkLogin: function() {
            var that = this;
            this.$refs['loginForm'].validate((valid) => {
            	if(valid) {
            		ms.http.post(ms.manager + "/checkLogin.do", that.loginForm).then(function(data) {
		               if(data.result) {
		               	  if(that.rememberPass) {
		               	  	$.cookie('managerName',that.loginForm.managerName, { expires: 365 });
		               	  	$.cookie('managerPassword',that.loginForm.managerPassword, { expires: 365 });
		               	  } else {
		               	  	$.cookie('managerName',"");
		               	  	$.cookie('managerPassword',"");
		               	  }
		                  that.$notify({
		                     title: '成功',
		                     message: '登录成功',
		                     type: 'success'
		                  });
		                  location.href = ms.manager + "/index.do";
		               } else {
		                  that.$notify({
		                     title: '失败',
		                     message: data.resultMsg,
		                     type: 'warning'
		                  });
		                  that.code();
		               }
		            }, (err) => {
		               that.$message.error(data.resultMsg);
		            })  
            	} 
          
            });

         },
         code: function() {
            this.verifCode = ms.web + "/code?t=" + new Date().getTime();
         },

      },
      mounted: function() {
         this.verifCode = ms.web + "/code?t=" + new Date().getTime();
         this.loginForm.managerName = $.cookie('managerName');
		 this.loginForm.managerPassword = $.cookie('managerPassword');
         if (top.location != self.location){
			top.location = self.location;
         }
      }
   })

</script>