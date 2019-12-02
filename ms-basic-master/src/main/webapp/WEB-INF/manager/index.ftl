<!-- 主页 -->
<!DOCTYPE html>
<html>

<head>
	<title></title>
	<#include "/include/head-file.ftl"/> 
	<link rel="stylesheet" href="${base}/static/ms-admin/4.7.2/css/index.css">
</head>
<body>
    <#include "/reset-password.ftl"/>
    <#include "/exit-system.ftl"/> 
	<div id="app" class="index">
		<el-container>
			<!--头部-->
			<el-header class="ms-admin-header" v-cloak>
				<div class="ms-admin-logo">
					<img :src="ms.base+'/static/ms-admin/4.7.2/images/logo.png'" />
					<div>
						<img :src="ms.base+'/static/ms-admin/4.7.2/images/version.png'" />
						<span>v4.7.2</span>
					</div>
				</div>

				<!--头部menu-->
				<el-menu class="ms-admin-header-menu" :default-active="headMenuActive" mode="horizontal" >
					<el-menu-item class="ms-admin-menu-item" :index="menu.modelId+''" :data-index="menu.modelId+''" v-for="(menu,i) in mainParentMenuList"
					 :key='i' @click="openMenu(menu,i)" v-text="menu.modelTitle"></el-menu-item>
					<el-menu-item style="line-height: 45px !important;" class="ms-admin-menu-item" :index="menuList.length+''" @click=''>
						<i style="line-height: 45px !important;font-size: 20px;" v-if='parentMenuList.length>=6' class="iconfont icon-caidan" @click.stop.self='shortcutMenu = !shortcutMenu'></i>
					</el-menu-item>
					<!-- 快捷菜单 -->
					<ul class="ms-admin-shortcut-menu" v-show='shortcutMenu' >
						<li v-for="(item,index) of parentMenuList" :key='index' v-text='item.modelTitle' @click='openMenu(item,index)'></li>
						<i style="line-height: 10px !important;font-size: 20px;" class="iconfont icon-caidan" @click.stop.self='shortcutMenu = !shortcutMenu'></i>
					</ul>
				</el-menu>

				<!--头部右侧-->
				<div class="ms-admin-mstore">
					<!--登录-->
					<el-dropdown trigger="click" class="ms-admin-login" placement="top-start" @visible-change="loginDown = !loginDown">
						<span class="el-dropdown-link" :class="{'active':loginDown}">
							<div class="ms-admin-people-head" v-text="peopleInfo.managerName && peopleInfo.managerName.substr(0, 2)"></div>
							<span v-text='peopleInfo.managerName'></span>
						</span>
						<el-dropdown-menu class="ms-admin-login-down" slot="dropdown" @click.native='openModal'>
							<el-dropdown-item>修改密码</el-dropdown-item>
							<el-dropdown-item>退出</el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
					<!--mstore按钮-->
					<div class="ms-admin-mstore-icon" @click="open(mstore)">
						<span v-if="mstore.syncNum>0" v-text="mstore.syncNum"></span>
						<i style="line-height: 42px !important;font-size: 30px;" class="iconfont icon-fenxiang2"></i>
					</div>
				</div>

			</el-header>
			<el-container class="ms-admin-container">
				<!--左侧菜单-->
				<el-aside v-show="asideMenuList.length" style="border-right: solid 1px #e6e6e6;background-color: #FFF;" :class="['ms-admin-menu-aside',{'ms-admin-menu-aside-active':collapseMenu}]" v-cloak>
					<el-scrollbar style="height:100%">
					<el-menu  :class="['ms-admin-menu',{'ms-admin-menu-active':collapseMenu}]" :default-active="asideMenuActive"
					 :collapse="collapseMenu" mode='vertical' :collapse-transition='true' :unique-opened='true' ref='menu' @open="asideMenuOpen">
						<el-submenu :index="menu.modelId+''" :data-index="menu.modelId+''" v-for="(menu,i) in asideMenuList" :key='i'>
							<template slot="title">
								<i class='ms-admin-icon iconfont' :class="menu.modelIcon"></i>
								<span style="line-height: 1.5em;" v-text="menu.modelTitle"></span>
							</template>
							<!-- 子菜单 -->
							<el-menu-item :index="sub.modelId+''" :data-index="sub.modelId" v-for="(sub,index) in getSubMenu(menu.modelId)"
							 :key='sub.modelModelId' v-text="sub.modelTitle" @click.self='open(sub)'></el-menu-item>
						</el-submenu>
						<!-- 收缩按钮 -->
					</el-menu>
					<div class="ms-menu-expand" :class="[{'ms-menu-expand-active':collapseMenu}]" @click='collapseMenu = !collapseMenu'>
						<i class="iconfont icon-shousuo"></i>
					</div>
					</el-scrollbar>
				</el-aside>
				<!--内容-->
				<el-main class="ms-admin-main">
					<!--选项卡-->
					<el-tabs class="ms-admin-tabs" v-model="currentTab" type="card" closable @tab-remove="closeTab" @tab-click='tabClick'>
						<el-tab-pane v-for="(item, index) in editableTabs" :key="index" :label="item.modelTitle" :name="item.modelTitle"
						 :data-modelId='item.modelId' :data-modelModelId='item.modelModelId'>
							<iframe :src='item.isStore?item.modelUrl:ms.manager+"/"+item.modelUrl+(item.modelUrl.indexOf("?")==-1?"?":"&")+"modelId="+item.modelId+"&modelCode="+item.modelCode+"&modelTitle="+item.modelTitle'
							 :ref="item.modelTitle"></iframe>
						</el-tab-pane>
					</el-tabs>
				</el-main>
			</el-container>
		</el-container>
	</div>
</body>

</html>
<script>
	var indexVue = new Vue({
		el: "#app",
		data: {
			menuList: [], //菜单接口数据
			asideMenuList: [], //侧边菜单
			parentMenuList: [], //一级菜单
			subMenuList: [], //二级菜单 所有
			mainParentMenuList: [], //头部菜单显示主要的选项
			loginDown: false, //登录下拉
			asideMenuActive: "", //左侧选中菜单
			headMenuActive: '', //头部菜单激活
			editableTabsValue: '',
			editableTabs: [{"modelTitle":"主界面","modelUrl":"main.do"}], //当前打开的tab页面
			shortcutMenu: false, //快捷菜单显示状态
			collapseMenu: false, //菜单折叠，false不折叠
			currentTab: '主界面', //当前激活tab的name
			tabIndex: 1,
			//登录用户信息
			peopleInfo: {
				managerName: '' //账号
			},
			mstore: {},
		},
		watch: {
			menuList: function (n, o) {
				var that = this;
				n && n.forEach(function (item, index) {
					item.modelModelId == 0 ? that.parentMenuList.push(item) : that.subMenuList.push(item)
				})
			},
			parentMenuList: function (n, o) {
				this.mainParentMenuList = n.slice(0, 5);
			},
			editableTabs: {
				handler: function (n, o) {
					if (n.length) {
						var that = this;
						if (!document.querySelector('.el-icon-refresh')) {
							var i = document.createElement('i');
							i.className = "el-icon-refresh ms-admin-refresh"
							i.title = "点击刷新当前页"
							i.addEventListener('click', function () {
								var index = null
								Object.keys(that.$refs).forEach(function (item, i) {
									item.indexOf(that.currentTab) > -1 ? index = i : ''
								}, that)
								that.$refs[Object.keys(that.$refs)[index]][0].contentDocument.location.reload(true)
							})
							document.querySelector('.el-tabs__header').insertBefore(i, document.querySelector('.el-tabs__nav-wrap'))
						}
					} else {
						if(document.querySelector('.ms-admin-refresh')) {
							document.querySelector('.el-tabs__header').removeChild(document.querySelector('.ms-admin-refresh'))
						}
					}
				},
				deep: true
			}
		},
		methods: {
			// 菜单列表
			list: function () {
				var that = this;
				ms.http.get(ms.manager + "/model/list.do")
					.then((data) => {
						that.menuList = data.rows
					}, (err) => {
						that.$message.error(err);
					})
			},
			asideMenuOpen: function (index, indexPath) {},
			// 菜单打开页面
			open: function (sub) {
				var result = '';
				result = this.editableTabs.some(function (item, index) {
					return item.modelTitle == sub.modelTitle
				})
				
				if (sub.syncStoreUrl) {
					sub.modelUrl = sub.syncStoreUrl
					sub.modelTitle = 'mstore';
					sub.isStore = true; 
					!result ? this.editableTabs.push(sub) : ""
				} else {
					!result ? this.editableTabs.push(sub) : ""
				}
				
				this.currentTab = sub.modelTitle;
				this.headMenuActive = sub.modelModelId
				this.$nextTick(function () {
					this.asideMenuActive = sub.modelId;
				})
				// 处理其他逻辑
				setTimeout(function () {
					if (document.querySelector('.el-tabs__nav-prev')) {
						document.querySelector('.el-tabs__nav-wrap').style.padding = '0 40px'
					} else {
						document.querySelector('.el-tabs__nav-wrap').style.padding = '0'
					}
				}, 16)
			},
			tabClick: function (tab) {
				this.asideMenuActive = tab.$el.dataset.modelid
				this.headMenuActive = tab.$el.dataset.modelmodelid
			},
			// 获取当前菜单的子菜单
			getSubMenu: function (modelId) {
				var result = [];
				var that = this;
				that.subMenuList && that.subMenuList.forEach(function (item) {
					item.modelModelId == modelId ? result.push(item) : ''
				})
				return result;
			},
			//关闭tab标签页
			closeTab(targetName) {
				var that = this;
				// 关闭的面板是当前激活面板
				if (that.currentTab == targetName) {
					var modelModelId = null
					that.editableTabs.forEach(function (tab, index, arr) {
						if (tab.modelTitle == targetName) {
							modelModelId = arr[index].modelModelId
							var nextTab = arr[index + 1] || arr[index - 1];
							if (nextTab) {
								that.currentTab = nextTab.modelTitle
								that.asideMenuActive = nextTab.modelId
								that.headMenuActive = nextTab.modelModelId
							}
						}
					})
				}
				// 去掉关闭的tab
				that.editableTabs = that.editableTabs.filter(function (tab) {
					return tab.modelTitle !== targetName
				})
				// 关闭左侧父菜单
				if (that.editableTabs.length) {
					var result = that.editableTabs.every(function (item) {
						return item.modelModelId !== modelModelId
					})
					if (result) {
						that.asideMenuList.forEach(function (menu, index, arr) {
							if (menu.modelId == modelModelId) {
								arr.splice(index, 1)
							}
						})
					}
				} else {
					that.asideMenuList = []
				}

				// 判断是否出现左右箭头
				setTimeout(function () {
					if (document.querySelector('.el-tabs__nav-prev')) {
						document.querySelector('.el-tabs__nav-wrap').style.padding = '0 40px'
					} else {
						document.querySelector('.el-tabs__nav-wrap').style.padding = '0'
					}
				}, 16)
			},
			// 头部导航打开菜单
			openMenu: function (menu, index) {
				this.asideMenuList.some(function (item, index) {
					return item.modelId == menu.modelId
				}) || this.asideMenuList.push(menu)
				// this.getSubMenu(menu.modelId)[0] && this.$refs.menu.open(this.getSubMenu(menu.modelId)[0].modelTitle);
				var children = [];
				this.menuList.forEach(function (tab) {
					if (tab.modelModelId == menu.modelId) {
						children.push(tab)
					}
				})
				this.currentTab = children[0] && children[0].modelTitle;
				this.open(children[0]);
				var that = this;
				setTimeout(function () {
					that.shortcutMenu = false
				}, 50)
				that.$nextTick(function () {
					that.$refs.menu.open(String(menu.modelId))
				})
			},
			managerGet: function () {
				var that = this;
				ms.http.get(ms.manager + "/basic/manager/get.do")
					.then((data) => {
						that.peopleInfo = data
						resetPasswordVue.resetPasswordForm.managerName = that.peopleInfo.managerName
					}, (err) => {
						that.$message.error(err);
					})
			},
			//  打开修改密码，退出的模态框
			openModal: function () {
				event.target.innerText.indexOf('修改密码') > -1 ?
					resetPasswordVue.isShow = true : exitSystemVue.isShow = true
			},
			// 显示图标
			formmateIcon: function (icon) {
				return "<i class='ms-admin-icon iconfont'></i>"
			}
		},
		mounted: function () {
			// 菜单列表
			this.list();
			//获取登录用户信息
			this.managerGet();
			var that = this;
			ms.http.get(ms.manager + "/upgrader/sync.do").then(function (data) {
				if (data.syncStoreUrl != undefined) {
				    data.syncStoreUrl +="?client=${client}";
					that.mstore = data;
				}
			})
		},
	})
</script>