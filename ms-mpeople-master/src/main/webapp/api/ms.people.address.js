/**
 * 普通用户收货地址信息
 */
(function() {
    /**
     * 用户收货地址列表
     * @param data
     * @param func
     * @returns
     */
    function list(data,func) {
        ms.http.get(ms.base + "/people/address/list.do",data
        		).then(func, (err) => {
                console.log(err)
            })
    }
    
    /**
     * 保存用户收货地址
     * @param data
     * @param func
     * @returns
     */
    function save(data,func) {
        ms.http.post(ms.base + "/people/address/save.do",data,{
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		}).then(func, (err) => {
                console.log(err)
            })
    }
    
    /**
     *  更新用户收货地址
     * @param data
     * @param func
     * @returns
     */
    function update(data,func) {
        ms.http.post(ms.base + "/people/address/update.do",data,{
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		}).then(func, (err) => {
                console.log(err)
            })
    }
    
    
    /**
     * 设置默认地址
     * @param data
     * @param func
     * @returns
     */
    function setDefault(data,func) {
        ms.http.post(ms.base + "/people/address/setDefault.do",data
        		).then(func, (err) => {
                console.log(err)
            })
    }
    
    /**
     * 通过peopleAddressId查询用户收货地址实体
     * @param data
     * @param func
     * @returns
     */
    function get(data,func) {
        ms.http.get(ms.base + "/people/address/get.do",data
        		).then(func, (err) => {
                console.log(err)
            })
    }

 
    /**
     * 根据收货地址id删除收货信息
     * @param data
     * @param func
     * @returns
     */
    function del(data,func) {
        ms.http.post(ms.base + "/people/address/delete.do",data
        		).then(func, (err) => {
                console.log(err)
            })
    }
    let address = {
    	list:list,
    	save:save,
    	update:update,
    	setDefault:setDefault,
    	get:get,
    	del:del
	}
    window.ms.people.address = address;
}());