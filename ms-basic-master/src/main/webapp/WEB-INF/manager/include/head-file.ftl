
    <meta charset="utf-8">
    <!--浏览器小图标-->
    <link rel="icon" href="http://cdn.mingsoft.net/global/images/ms.ico" type="x-icon">
    <script type="text/javascript" src="${base}/static/plugins/vue/2.6.9/vue.min.js"></script>
    <!-- 铭飞图标 -->
    <link rel="stylesheet" type="text/css" href="${base}/static/plugins/iconfont/1.0.0/iconfont.css" />
    <!--小图标-->
    <link rel="stylesheet" href="${base}/static/ms-admin/4.7.2/iconfont/iconfont.css"/>
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
<#--    树形下拉-->
    <script src="${base}/static/plugins/tree-select/tree.js"></script>
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