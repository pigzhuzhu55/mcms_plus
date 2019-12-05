/**
 * 通用自定义表单
 */
(function() {
    /**
     * 保存
     * @param data
     * @param func
     * @returns
     */
    function save(data,func) {
        ms.http.post(ms.base + "/mdiy/form/{idBase64}.do",data,{
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		}).then(func, function(err){
                console.log(err)
            })
    }
    
    
    /**
     * 提供前端查询自定义表单提交数据
     * @param data
     * @param func
     * @returns
     */
    function list(data,func) {
    	ms.http.get(ms.base + "/mdiy/form/{idBase64}/queryData.do",data
    		).then(func, function(err){
            console.log(err)
        })
    }
    
    let form = {
    	save:save,
    	list:list
	}
    window.ms.mdiy.form = form;
}());