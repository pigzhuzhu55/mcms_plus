/**
 * 用户基础信息
 */
(function() {
    /**
     * 获取用户详情
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
         ms.http.get(ms.base + "/people/user/info.do")
             .then(func, (err) => {
                 console.log(err)
             })
     }
    /**
     * 更新手机号或邮箱
     ------
     * 更新用户信息中保存的手机号或者邮箱号，二者必须存在一个
     * @callmethod people.update(data,function(returnJson){...});
     * @param {{type:string,have:true}} peopleMail 邮箱 
     * @param {{type:string}} peoplePhone 手机号 
     * @param {{type:function,have:true}}  回调方法 返回值(returnJson)
     * @examples 
     * ...
     * <form name="update">
     * ...
     *	<input type="text" name="peoplePhone"/>
     * ...
     * </form>
     * ..
     * ...
     * @function
     * {code:"模块编码",result:true,resultMsg:""}
     * @return {{type:code}} 模块编码
     * @return {{type:result}} true成功、false失败
     * @return {{type:resultMsg}} 错误信息
     */
     function update(data, func) {
         if (validator.isEmpty(data+"")) {
             return;
         }
         if ((validator.isEmpty(data.peoplePhone +"") || typeof(data.peoplePhone) == "undefined") &&
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
         ms.http.post(ms.base + "/people/user/update.do", data,{
 			headers: {
 				'Content-Type': 'application/x-www-form-urlencoded'
 			}
 		})
             .then(func, (err) => {
                 console.log(err)
             })
     }
     function saveUserIcon(data, func) {
         if (validator.isEmpty(data+"")) {
             return;
         }
         ms.http.post(ms.base + "/people/user/saveUserIcon.do", data,{
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
             .then(func, (err) => {
                 console.log(err)
             })
     }
     function updateUserIcon(data, func) {
         if (validator.isEmpty(data+"")) {
             return;
         }
         ms.http.post(ms.base + "/people/user/updateUserIcon.do", data,{
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
             .then(func, (err) => {
                 console.log(err)
             })
     }
    let user = {
    	info:info,
    	update:update,
        saveUserIcon:saveUserIcon,
        updateUserIcon:updateUserIcon
	}
    window.ms.people.user = user;
}());