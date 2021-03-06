/**
 * 用户基础信息
 */
(function() {
    /**
    *退出
    ------
    * @callmethod people.quit(function(){...});
    * @param  {{type:function,have:true}}  回调方法 无返回值
    * @examples 
    * ...
    * <a href="#" class="quitLogin">退出</a>
    * ...
    * ...
    * @function 
    * 无返回值
    * @return {{type:none}} 无返回值
    */
    function quit(func) {
        ms.http.get(ms.base + "/people/quit.do"
        		).then(func, (err) => {
                console.log(err)
            })
    }
    /**
    * 设置密码
    ------
    * @callmethod people.resetPassword(data,function(returnJson){...});
    * @param {{type:String,have:true}} peoplePassword 用户密码
    * @param {{type:function}}  回调方法 返回值(returnJson)
    * @examples 
    * ...
    * ...
    * @function 
    * {"resultMsg":"","result":true}
    * @return {{type:resultMsg}} 提示信息
    * @return {{type:result}} true成功、false失败
    */
    function resetPassword(data, func) {
        if (validator.isEmpty(data+"")) {
            alert("数据不能为空");
            return;
        }
        if (validator.isEmpty(data.peoplePassword+"")) {
            alert("密码不能为空");
            return;
        }
        ms.http.post(ms.base + "/people/resetPassword.do", data,{
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		})
            .then(func, (err) => {
                console.log(err)
            })
    }
    /**
    * 验证用户接收的验证码
    ------
    * @callmethod people.checkPeopleCode(peopleCode,function(returnJson){...});
    * @param {{type:string,have:true}} peopleCode 短信、邮箱验证码 
    * @param {{type:function,have:true}}  回调方法 返回值(returnJson)
    * @examples 
    * ...
    * ...
    * @function
    * {code:"模块编码",result:true}
    * @return {{type:code}} 编码
    * @return {{type:result}} true成功、false失败
    */
    function checkPeopleCode(peopleCode, func) {
        if (validator.isEmpty(peopleCode+"")) {
            return;
        }
        ms.http.post(ms.base + "/people/checkPeopleCode.do", peopleCode,{
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		})
            .then(func, (err) => {
                console.log(err)
            })
    }
    /**
    * 获取用户基本信息
    ------
    * @callmethod people.info(function(returnJson){...});
    * @param {{type:function,have:true}}  回调方法 返回值(returnJson)
    * @examples 
    * ...
    * ...
    * @function
    * {
    *	"peopleAutoLogin":0,
    *	"peopleName":"mstest",
    *	"peopleDateTime":"2016-05-08 13:14:00",
    *	"peopleId":5201314,
    * }
    * @return {{type:peopleAutoLogin}} 自动登录多少天
    * @return {{type:peopleName}} 用户名
    * @return {{type:peopleDateTime}} 用户登录时间
    * @return {{type:peopleId}} 用户编号
    */
    function info(func) {
        ms.http.get(ms.base + "/people/info.do")
            .then(func, (err) => {
                console.log(err)
            })
    }
    /**
    * 修改密码
    ------
    * 如果修改密码，在输入新密码后需要再次填入确认密码，需要前端自行判断新密码与再次确认密码是否一致，接口未做判断
    * @callmethod people.changePassword(data,function(returnJson){...});
    * @param {{type:string,have:true}} peopleOldPassword 原密码
    * @param {{type:string,have:true}} peoplePassword 新密码
    * @param {{type:function,have:true}}  回调方法 返回值(returnJson)
    * @examples 
    * ...
    * ...
    * @function
    * {code:"模块编码",result:true,resultMsg:""}
    * @return {{type:code}} 模块编码
    * @return {{type:result}} true成功、false失败
    * @return {{type:resultMsg}} 错误信息
    */
     function changePassword(data, func) {
        if (validator.isEmpty(data+"")) {
            return;
        }
        if (validator.isEmpty(data.peopleOldPassword+"")) {
            alert("原密码不能为空");
            return;
        }
        if (validator.isEmpty(data.peoplePassword+"")) {
            alert("新密码不能为空");
            return;
        }
        ms.http.post(ms.base + "/people/changePassword.do", data,{
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		})
            .then(func, (err) => {
                console.log(err)
            })
    }
     /**
 	 * 验证用户名，手机号或邮箱
 	 * @param data
 	 * @returns
 	 */
 	function checkValidator(data) {
 		if (validator.isEmpty(data + "")) {
             return;
         }
 		if ((validator.isEmpty(data.peopleName +"") || typeof(data.peopleName) == "undefined") && 
 	            (validator.isEmpty(data.peoplePhone +"") || typeof(data.peoplePhone) == "undefined") &&
 	            (validator.isEmpty(data.peopleMail +"") || typeof(data.peopleMail) == "undefined")) {
 	            alert("用户名，手机号，邮箱必须有一个不为空");
 	            return;
 	        }
 		if(!validator.isEmpty(data.peoplePhone +"") && typeof(data.peoplePhone) != "undefined"){
             if (!validator.isMobilePhone(data.peoplePhone, 'zh-CN')) {
                 alert("请输入正确的手机号");
                 return;
             }
         }
         if(!validator.isEmpty(data.peopleMail+"") && typeof(data.peopleMail) != "undefined"){
             if (!validator.isEmail(data.peopleMail)) {
                 alert("请输入正确的邮箱");
                 return;
             }
         }
         if (validator.isEmpty(data.peoplePassword + "")) {
             alert("密码不能为空");
             return;
         }
 	}
     /**
     * 注册
     - -----
     * 用户可以用名称、手机号、邮箱三者之一进行注册
     * 几种注册流程的形式：
     * 1、普通用户名称、登录密码，优先用户名注册,登录密码最长度范围3～30个字符；
     * 2、邮箱、邮箱验证码、登录密码(邮箱必须是可接收验证码)；
     * 3、手机号、短信验证码、登录密码；
     * 注意： 1、注册页面必须存在图片验证码
     * 2、如果需要接收验证码操作，需要使用“发送验证码”配合使用才能完成注册流程
     * @callmethod register(data,function(returnJson){...});
     * @param {{type:string}} peoplePhone 手机号
     * @param {{type:string}} peopleName 用名称 用户名长度在3～30个字符之间，只能是字母数字混合
     * @param {{type:string}} peopleMail 邮箱
     * @param {{type:string,have:true}} peoplePassword 注册密码 
     * @param {{type:strings,have:true}} rand_code 验证码
     * @param {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     * ...
     * <form name="registe">
     *	<input type="text" name="peopleName" /> <!--注册用户名-->
     *	<input type="password" name="peoplePassword"/> <!--注册密码-->
     *	<input type="password" name="rePassword"/> <!--确认注册密码，需要做前端验证，此接口不提供验证-->
     *	<input type="text"  name="rand_code" /> <!--验证码-->
     *  	<img id="registeCode"/> 
     * </form>
     * ...
     * ...
     * @function 
     * {"resultMsg":"注册成功","result":true,"code":"07010100"}
     * @return {{type:resultMsg}} 提示信息
     * @return {{type:code}} 错误编码
     * @return {{type:result}} true成功、false失败
     */
 	function register(data, func) {	
 		checkValidator(data);//验证表单信息
         ms.http.post(ms.base + "/register.do", data, {
 			headers: {
 				'Content-Type': 'application/x-www-form-urlencoded'
 			}
 		}) .then(func, (err) => { 
                 console.log(err);
             })
     }
     /**
     *登录
     ------
     * 登录验证,登录必须存在验证码 
     * @callmethod checkLogin(data,function(returnJson){...});
     * @param {{type:string,have:true}} peopleName 用户名
     * @param {{type:string,have:true}} peoplePassword 登录密码
     * @param {{type:string,have:true}} rand_code 验证码
     * @param {{type:string}} peopleAutoLogin 自动登录 如果大于0表示开启自动登录，1表示自动登录保留1天
     * @param {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     * ...
     * <form name="login">
     *	<input type="text" name="peopleName" /> <!--登录用户名，手机号或邮箱-->
     *	<input type="password" name="peoplePassword"/> <!--登录密码-->
     *	<input type="text"  name="rand_code" /> <!--验证码-->
     *  	<img id="loginCode"/> 
     * ...
     * </form>
     * ...
     * ...
     * @function 
     * {"resultMsg":"{
     *	\"peopleAppId\":0,
     *	\"peopleAutoLogin\":0,
     *	\"peopleId\":9020,
     *	\"peopleMailCheck\":0,
     *	\"peopleName\":\"mstest\",
     *	\"peoplePhoneCheck\":0,
     *	\"peopleState\":0}",
     * "result":true,
     * "code":"07010200"}
     * @return {{type:code}} 错误编码
     * @return {{type:result}} true成功、false失败
     * @return {{type:resultMsg}} 提示信息
     * @return {{type:resultData}} {"peopleAutoLogin":自动登录多少天,"peopleName":用户,"peopleId":用户编号,"peopleMail ":用户邮箱}
     */
 	function checkLogin(data, func) {
 		if (validator.isEmpty(data+"")) {
             return;
         }
         if (validator.isEmpty(data.peopleName + "")) {
             alert("用户名不能为空");
             return;
         }
          if (validator.isEmpty(data.peoplePassword + "")) {
             alert("密码不能为空");
             return;
         }
          if (validator.isEmpty(data.rand_code + "")) {
             alert("验证码不能为空");
             return;
         }
         ms.http.post(ms.base + "/checkLogin.do", data,{
 			headers: {
 				'Content-Type': 'application/x-www-form-urlencoded'
 			}
 		}).then(func, (err) => {
                 console.log(err)
             })
     }
     /**
     *验证登录状态
     ------
     * @callmethod checkLoginStatus(function(returnJson){...});
     * @param {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     * ...
     * @function 
     * {result:"false"}
     * @return {{type:result}} true成功、false失败
     */
     function checkLoginStatus(func) {
         ms.http.post(ms.base + "/checkLoginStatus.do")
             .then(func, (err) => {
                 console.log(err)
             })
     }
     /**
     * 发送验证码
     ------
     * 用户发送验证码，可以通过邮箱或手机发送
     * @callmethod sendCode(data,function(returnJson){...});
     * @param {{type:string,have:true}} receive  接收地址，只能是邮箱或手机号，邮箱需要使用邮箱插件，手机号需要短信插件
     * @param {{type:string,have:true}} modelCode 对应邮件插件的模块编号
     * @param {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     * ...
     * <form name="sendEmailCode">
     *	<input type="text" name="receive" /> <!--接收地址，本案例为邮箱-->
     *	<input type="text" name="modelCode" type="hidden" value="后台邮件插件模块编号"/>
     * ...
     * </form>
     * ...
     * ...
     * @function 
     * {result:"true"}
     * @return {{type:result}} true成功、false失败
     */
     function sendCode(data, func) {
         if (validator.isEmpty(data+"")) {
             return;
         }
         if (validator.isEmpty(data.receive + "")) {
             alert("接收地址不能为空");
             return;
         }
         if (validator.isEmpty(data.modelCode + "")) {
             alert("对应邮件插件的模块编号不能为空");
             return;
         }
         ms.http.get(ms.base + "/sendCode.do", data
         		).then(func, (err) => {
                 console.log(err)
             })
     }
     /**
     * 验证用户接收的验证码
     ------
     * 验证用户输入的系统发送邮件或者短信验证码是否正确
     * @callmethod checkSendCode(data,function(returnJson){...});
     * @param {{type:string,have:true}} receive  接收地址，只能是邮箱或手机号，邮箱需要使用邮箱插件，手机号需要短信插件
     * @param {{type:string,have:true}} code 对应接收的验证码
     * @param  {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     * ...
     * <form name="sendEmailCode">
     *	<input type="text" name="receive" /> <!--接收地址，本案例为邮箱-->
     *	<input type="text" name="modelCode" type="hidden" value="后台邮件插件模块编号"/>
     * ...
     *	<input type="text" name="code" />
     * ...
     * </form>
     * ...
     * @function 
     * {result:"true"}
     * @return {{type:result}} true成功、false失败
     */
     function checkSendCode(data, func) {
         if (validator.isEmpty(data+"")) {
             return;
         }
         if (validator.isEmpty(data.receive + "")) {
             alert("接收地址不能为空");
             return;
         }
         if (validator.isEmpty(data.code + "")) {
             alert("接收的验证码不能为空");
             return;
         }
         ms.http.post(ms.base + "/checkSendCode.do", data,{
 			headers: {
 				'Content-Type': 'application/x-www-form-urlencoded'
 			}
 		})
             .then(func, (err) => {
                 console.log(err)
             })
     }
     /**
     * 解绑邮箱验证用户接收的验证码
     ------
     * 解绑邮箱时，验证用户输入的系统发送邮件或者短信验证码是否正确
     * @callmethod cancelBind(data,function(returnJson){...});
     * @param {{type:string,have:true}} receive  接收地址，只能是邮箱或手机号，邮箱需要使用邮箱插件，手机号需要短信插件
     * @param {{type:string,have:true}} code 对应接收的验证码
     * @param  {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     * ...
     * <form name="sendEmailCode">
     *	<input type="text" name="receive" /> <!--接收地址，本案例为邮箱-->
     *	<input type="text" name="modelCode" type="hidden" value="后台邮件插件模块编号"/>
     * ...
     *	<input type="text" name="code" />
     * ...
     * </form>
     * ...
     * @function 
     * {result:"true"}
     * @return {{type:result}} true成功、false失败
     */
     function cancelBind(data, func) {
         if (validator.isEmpty(data+"")) {
             return;
         }
         if (validator.isEmpty(data.receive + "")) {
             alert("接收的验证码不能为空");
             return;
         }
         if (validator.isEmpty(data.code + "")) {
             alert("接收的验证码不能为空");
             return;
         }
         ms.http.post(ms.base + "/cancelBind.do", data,{
 			headers: {
 				'Content-Type': 'application/x-www-form-urlencoded'
 			}
 		}).then(func, (err) => {
                 console.log(err)
             })
     }
     /**
     * 验证已保存用户
     ------
     * 验证用户名、手机号、邮箱是否已保存，同一时间只能判断一种，优先用户名称 
     * 适用场景:
     * 1、用户注册是对用户名、邮箱或手机号唯一性判断 
     * 2、用户取回密码是判断账号是否存在
     * @callmethod check(data,function(returnJson){...});
     * @param  {{type:string}}  peopleName 用户名称验证
     * @param  {{type:string}}  peopleMail 用户邮箱验证
     * @param  {{type:string}}  peoplePhone 用户手机验证
     * @param  {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     *...
     * <form>
     * ...
     *	<input type="text" name="peopleName" /> <!--接收地址，本案例为用户名判断-->
     * ...
     * </form>
     * ...
     * @function 
     * {result:"true"}
     * @return {{type:code}} 模块编码
     * @return {{type:result}} true存在｜false不存在或错误
     * @return {{type:resultMsg}} 错误信息
     */
     function check(data, func) {
         if (validator.isEmpty(data+"")) {
             return;
         }
         checkValidator(data);//验证表单
         ms.http.post(ms.base + "/check.do", data,{
 			headers: {
 				'Content-Type': 'application/x-www-form-urlencoded'
 			}
 		})
             .then(func, (err) => {
                 console.log(err)
             })
     }
     /**
     * 验证已绑定用户
     ------
     * 验证用户名、手机号、邮箱是否已保存并绑定，同一时间只能判断一种，优先用户名称 
     * 适用场景:
     * 1、用户注册是对用户名、邮箱或手机号唯一性判断 
     * 2、用户取回密码是判断账号是否存在
     * 3、用户绑定邮箱或者手机号验证，邮箱或手机号是否存在并已绑定
     * @callmethod isExists(data,function(returnJson){...});
     * @param  {{type:string}}  peopleName 用户名称验证
     * @param  {{type:string}}  peopleMail 用户邮箱验证，注意：只验证绑定成功的邮箱 
     * @param  {{type:string}}  peoplePhone 用户手机验证，注意：只验证绑定成功的手机  
     * @param  {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     * ...
     * <form>
     * ...
     *	<input type="text" name="peopleName" /> <!--接收地址，本案例为用户名判断-->
     * ...
     * </form>
     * ...
     * super.load(["super.people"],function(mpeople){
     *	mpeople.isExists($("form").serialize(),function(returnJson){
     *		alert(JSON.stringify(returnJson));
     *	});
     * })
     * @function 
     * {result:"true"}
     * @return {{type:code}} 模块编码
     * @return {{type:result}} true存在｜false不存在或错误
     * @return {{type:resultMsg}} 错误信息
     */
     function isExists(data, func) {
         if (validator.isEmpty(data+"")) {
             return;
         }
         checkValidator(data);//验证表单信息
         ms.http.post(ms.base + "/isExists.do", data,{
 			headers: {
 				'Content-Type': 'application/x-www-form-urlencoded'
 			}
 		}).then(func, (err) => {
                 console.log(err)
             })
     }
     /**
     *验证图片验证码
     ------
     *例如流程需要短信验证或邮箱验证，为有效防止恶意发送验证码。提供给ajax异步请求使用 
     *注意：页面提交对验证码表单属性名称必须是rand_code，否则无效
     * @callmethod checkCode(rand_code,function(returnJson){...});
     * @param {{type:strings,have:true}} rand_code 验证码
     * @param {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     * ...
     * <form name="picCode">
     *	<input type="text"  name="rand_code" /> <!--填写验证码-->
     *  	<img id="picCode"/> <!--图片验证码-->
     * </form>
     *...
     * @function 
     * {code:"错误编码",result:"true成功、false失败",resultMsg: "提示信息"}
     * @return {{type:code}} 错误编码
     * @return {{type:result}} true成功、false失败
     * @return {{type:resultMsg}} 提示信息
     */
     function checkCode(rand_code, func) {
         if (validator.isEmpty(rand_code+"")) {
             alert("接收的验证码不能为空");
             return;
         }
         ms.http.post(ms.base + "/checkCode.do", rand_code,{
 			headers: {
 				'Content-Type': 'application/x-www-form-urlencoded'
 			}
 		})
             .then(func, (err) => {
                 console.log(err)
             })
     }
     /**
     * 重置密码
     ------
     * 当用户忘记登录密码时可以通过注册绑定的邮箱或绑定的手机号进行取回，操作过程中需要通过邮件模块与短信模块发送验证码给用户
     * 业务场景：用户输入手机号(邮箱)，点击发送验证码,发送间隔时间为60秒,用户将接收到的验证码输入提交,此接口不会对用户再次输入新密码进行判断，需要开发者做前端判断
     * @callmethod resetPassword(data,function(returnJson){...});
     * @param {{type:string,have:true}} peoplePassword 用户新密码 
     * @param {{type:string,have:true}} rand_code  验证码
     * @param  {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     * ...
     * <form name="resetPassword">
     * ...
     *	<input type="text" name="peoplePassword"/>
     *	<input type="text"  name="peopleCode" /> <!--短信验证码验证码-->
     *	<input type="text"  name="rand_code" /> <!--验证码-->
     *  	<img id="resetPasswordCode"/> 
     * ...
     * </form>
     * ...
     * ...
     * @function 
     * {code:"0777700",result:true,resultMsg: "提示信息"}
     * @return {{type:code}} 错误编码
     * @return {{type:result}} true成功、false失败
     * @return {{type:resultMsg}} 提示信息
     */
     function resetPassword(data, func) {
         if (validator.isEmpty(data+"")) {
             return;
         }
         if (validator.isEmpty(data.peoplePassword + "")) {
             alert("用户新密码不能为空");
             return;
         }
         if (validator.isEmpty(data.peopleCode + "")) {
             alert("接收的验证码不能为空");
             return;
         }
         if (validator.isEmpty(data.rand_code + "")) {
             alert("图片验证码不能为空");
             return;
         }
         ms.http.post(ms.base + "/resetPassword.do", data,{
 			headers: {
 				'Content-Type': 'application/x-www-form-urlencoded'
 			}
 		})
             .then(func, (err) => {
                 console.log(err)
             })
     }
     /**
     * 验证重置密码收到的验证码
     ------
     * 忘记密码时需要将第一步验证用户时的接收验证码作为重置密码的验证码
     * @callmethod checkResetPasswordCode(data,function(returnJson){...});
     * @param {{type:string,have:true}} peopleCode  短信、邮箱验证码 
     * @param {{type:string,have:true}} rand_code  验证码，可能会传递经过多个流程，具体根据业务确定
     * @param  {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     * ...
     * <form name="resetPassword">
     * ...
     *	<input type="text" name="peopleCode"/>
     *	<input type="text"  name="rand_code" /> <!--验证码-->
     *  	<img id="checkResetPasswordCodeCode"/> 
     * ...
     * </form>
     * ...
     * @function 
     * {result:"true"}
     * @return {{type:result}} true成功、false失败
     */
     function  checkResetPasswordCode(data, func) {
         if (validator.isEmpty(data+"")) {
             return;
         }
         if (validator.isEmpty(data.peopleCode + "")) {
             alert("接收的验证码不能为空");
             return;
         }
         if (validator.isEmpty(data.rand_code + "")) {
             alert("图片验证码不能为空");
             return;
         }
         ms.http.post(ms.base + "/checkResetPasswordCode.do", data,{
 			headers: {
 				'Content-Type': 'application/x-www-form-urlencoded'
 			}
 		}).then(func, (err) => {
                 console.log(err)
             })
     }
    let people = {
    	quit:quit,
    	resetPassword:resetPassword,
    	checkPeopleCode:checkPeopleCode,
    	info:info,
    	changePassword:changePassword,
    	register:register,
    	sendCode:sendCode,
    	checkLogin:checkLogin,
    	checkLoginStatus:checkLoginStatus,
    	checkSendCode:checkSendCode,
    	cancelBind:cancelBind,
    	check:check,
    	isExists:isExists,
    	checkCode:checkCode,
    	resetPassword:resetPassword,
    	checkResetPasswordCode:checkResetPasswordCode,
	}
    
    if(typeof ms != "object") {
        window.ms = {};
    } 
    window.ms.people = people;
}());