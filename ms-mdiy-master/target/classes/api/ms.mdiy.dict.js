/**
 * 通用自定义字典
 */
(function() {
    /**
     * 查询字典表列表
     * @param type 字典类型
     * @param func
     * @returns
     */
    function list(type,func) {
    	let data ={
    		dictType:type,//类型类型
    	};
    	ms.http.get(ms.base + "/mdiy/dict/list.do",data,{
		}).then(func, function (err){
                console.log(err)
            })
    }

    let dict = {
    	list:list,
	}
    window.ms.mdiy.dict = dict;
}());