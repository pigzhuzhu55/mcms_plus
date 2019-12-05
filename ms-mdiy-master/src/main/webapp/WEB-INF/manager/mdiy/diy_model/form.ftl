<!DOCTYPE html>
<html>
   <head>
      <title></title>
      <!--#include virtual="head-file.ftl"-->
      <link rel="stylesheet" href="../../../../../static/mdiy/css/model-form.css">
   </head>
   <body>
      <div id="model-form" class="ms-mdiy-model-form">
         <el-container>
            <el-header class="ms-header" height="50px">
               <el-row class="ms-row">
                  <span>
                     <i class="iconfont icon-zidingyi1"></i>
                     <span></span>
                  </span>
                  <el-button class="ms-fr" size="small" icon="iconfont icon-fanhui" @click="">返回</el-button>
                  <el-button class="ms-fr" type="success" size="small" icon="iconfont icon-iconset0238">保存</el-button>
               </el-row>
            </el-header>
            <el-container>
               <el-aside class="ms-editor-type-layout">
                  <div class="ms-header-title">控件</div>
                  <div id="ms-type-list" class="ms-type-list">
                     <li v-for="type in typeList" :data-type="type.id" :data-title="type.title" @click="formItemList.push({type: type.id,title: type.title,downList: [],downActiveList: [],set: [],unit: '',fieldType: ''})">
                        <i class="iconfont" :class="type.icon"></i>
                        <span v-text="type.title"></span>
                     </li>
                  </div>
               </el-aside>
               <el-main class="ms-editor-body-layout">
                  <el-form id="ms-input-list" size="mini" class="form">
                     <!--控件类型下拉-->
                     <!--绑定一个自增长ID否则富文本会无法渲染-->
                     <el-form-item @click.native="formItemActive = formItem;formItemActive.id = index" class="ms-form-item" :class="{'active':formItemActive == formItem}" :label="formItem.title" :label-width="'90px'" v-for="(formItem,index) in formItemList">
                        <!--单行-->
                        <el-input v-if="formItem.type == '1' && formItem.fieldType != '1-2' && formItem.fieldType != '1-3'" maxlength="20" type="text" v-model="formItem.default" placeholder="选填，1-20，字符" autocomplete="off"></el-input>
                        <!--多行-->
                        <el-input v-if="formItem.fieldType == '1-2'" maxlength="20" type="textarea" :rows="4" v-model="formItem.default" placeholder="选填，1-20，字符" autocomplete="off"></el-input>
                        <div v-show="formItem.fieldType == '1-3'" style="width: 100%;">
                           <script :id="'ms-ueditor-'+formItemActive.id" type="text/plain" name="taskDescription"></script>
                        </div>
                        <!--号码-->
                        <el-input v-if="formItem.type == '2'" maxlength="11" type="number" v-model="formItem.default" placeholder="选填，1-11，数字" autocomplete="off"></el-input>
                        <!--金额-->
                        <el-input class="unit-input" v-if="formItem.type == '3'" maxlength="20" type="number" v-model="formItem.default" placeholder="选填" autocomplete="off">
                           <template :slot="formItem.fieldType == '3-1'?'prepend':'append'">{{formItem.unit}}</template>
                        </el-input>

                        <!--数值-->
                        <el-input v-if="formItem.type == '4'" max="9999" type="number" v-model="formItem.default" placeholder="选填，数字" autocomplete="off"></el-input>

                        <!--日期和时间-->
                        <el-date-picker v-if="formItem.type == '5' && formItem.fieldType != '5-2' && formItem.fieldType != '5-3'" style="width: 100%;" v-model="formItem.default" type="datetime" placeholder="选择日期时间">
                        </el-date-picker>
                        <!--仅日期-->
                        <el-date-picker v-if="formItem.fieldType == '5-2'" style="width: 100%;" v-model="formItem.default" type="date" placeholder="选择日期">
                        </el-date-picker>
                        <!--仅时间-->
                        <el-time-picker v-if="formItem.fieldType == '5-3'" style="width: 100%;" v-model="formItem.default" placeholder="选择时间">
                        </el-time-picker>

                        <!--选项-->
                        <!--单选-->
                        <template v-if="formItem.type == '6' && formItem.fieldType != '6-2'">
                           <el-radio v-for="(down,index) in formItem.downList" v-model="formItem.downActive" :label="index">{{down.value}}</el-radio>
                        </template>

                        <!--多选-->
                        <el-checkbox-group v-model="formItem.downActiveList" v-if="formItem.fieldType == '6-2'">
                           <el-checkbox v-for="(down,index) in formItem.downList" :label="index">{{down.value}}</el-checkbox>
                        </el-checkbox-group>
                        <!--下拉菜单-->
                        <!--单选-->
                        <el-select style="width: 100%;" v-model="formItem.downActive" placeholder="请选择" v-if="formItem.type == '7' && formItem.fieldType != '7-2'">
                           <el-option v-for="(down,index) in formItem.downList" :value="index" :label="down.value">
                           </el-option>
                        </el-select>

                        <!--多选-->
                        <el-select style="width: 100%;" multiple v-model="formItem.downActiveList" placeholder="请选择" v-if="formItem.fieldType == '7-2'">
                           <el-option v-for="(down,index) in formItem.downList" :value="index" :label="down.value">
                           </el-option>
                        </el-select>

                        <!--上传图片-->
                        <el-upload v-if="formItem.type == '8'" action="https://jsonplaceholder.typicode.com/posts/" multiple>
                           <el-button icon="el-icon-upload">上传附件</el-button>
                        </el-upload>

                        <!--上传附件-->
                        <el-upload v-if="formItem.type == '9'" action="https://jsonplaceholder.typicode.com/posts/" multiple list-type="picture-card">
                           <i class="el-icon-plus"></i>
                        </el-upload>
                        <!--删除按钮-->
                        <el-button class="del-but" type="text" @click.stop="formItemList.splice(index,1)">删除</el-button>
                     </el-form-item>
                     <div class="ms-no-data" v-if="formItemList.length<1">暂无控件</div>
                  </el-form>
               </el-main>
               <el-aside class="ms-editor-attr-layout">
                  <div id="ms-editor-attr" class="ms-editor-attr">
                     <div class="ms-header-title">控件属性</div>
                     <div class="ms-no-data" v-if="formItemActive.type == undefined">暂无属性</div>
                     <el-form label-position="top" size="mini" class="form" v-if="formItemActive.type != undefined">
                        <!--字段名-->
                        <el-form-item label="标题" :label-width="'70px'">
                           <el-input maxlength="5" v-model="formItemActive.title" placeholder="选填，1-5，字符" autocomplete="off"></el-input>
                        </el-form-item>

                        <!--数字类型-->
                        <!--数值-->
                        <el-form-item label="默认值" :label-width="'70px'" v-if="formItemActive.type === '4'">
                           <el-input max="9999" type="number" v-model="formItemActive.default" placeholder="选填，数字" autocomplete="off"></el-input>
                        </el-form-item>

                        <!--时间类型-->
                        <el-form-item label="字段类型" :label-width="'70px'" v-if="formItemActive.type === '5'">
                           <el-radio v-model="formItemActive.fieldType" label="5-1">日期和时间</el-radio>
                           <el-radio v-model="formItemActive.fieldType" label="5-2">仅日期</el-radio>
                           <el-radio v-model="formItemActive.fieldType" label="5-3">仅时间</el-radio>
                        </el-form-item>
                        <!--日期和时间-->
                        <el-form-item label="默认值" :label-width="'70px'" v-if="formItemActive.type === '5' && formItemActive.fieldType != '5-2' && formItemActive.fieldType != '5-3'">
                           <el-date-picker style="width: 100%;" v-model="formItemActive.default" type="datetime" placeholder="选择日期时间">
                           </el-date-picker>
                        </el-form-item>
                        <!--仅日期-->
                        <el-form-item label="默认值" :label-width="'70px'" v-if="formItemActive.fieldType == '5-2'">
                           <el-date-picker style="width: 100%;" v-model="formItemActive.default" type="date" placeholder="选择日期">
                           </el-date-picker>
                        </el-form-item>
                        <!--仅时间-->
                        <el-form-item label="默认值" :label-width="'70px'" v-if="formItemActive.fieldType == '5-3'">
                           <el-time-picker style="width: 100%;" v-model="formItemActive.default" placeholder="选择时间">
                           </el-time-picker>
                        </el-form-item>

                        <!--字符串类型-->
                        <!--文本-->
                        <el-form-item label="字段类型" :label-width="'70px'" v-if="formItemActive.type === '1'">
                           <el-radio v-model="formItemActive.fieldType" label="1-1">单行</el-radio>
                           <el-radio v-model="formItemActive.fieldType" label="1-2">多行</el-radio>
                           <el-radio v-model="formItemActive.fieldType" label="1-3">带格式</el-radio>
                        </el-form-item>
                        <el-form-item label="默认值" :label-width="'70px'" v-if="formItemActive.type === '1'">
                           <!--输入框判断选中的是多行还是单行-->
                           <el-input v-if="formItemActive.fieldType != '1-2' && formItemActive.fieldType != '1-3'" maxlength="20" type="text" v-model="formItemActive.default" placeholder="选填，1-20，字符" autocomplete="off"></el-input>
                           <el-input v-if="formItemActive.fieldType == '1-2' || formItemActive.fieldType == '1-3'" maxlength="20" type="textarea" :rows="4" v-model="formItemActive.default" placeholder="选填，1-20，字符" autocomplete="off"></el-input>
                        </el-form-item>

                        <!--号码-->
                        <el-form-item label="默认值" :label-width="'70px'" v-if="formItemActive.type === '2'">
                           <el-input maxlength="11" type="number" v-model="formItemActive.default" placeholder="选填，1-11，数字" autocomplete="off"></el-input>
                        </el-form-item>

                        <!--金额-->
                        <el-form-item label="单位" :label-width="'70px'" v-if="formItemActive.type === '3'">
                           <el-input autocomplete="off" placeholder="选填" maxlength="20" type="text" v-model="formItemActive.unit" class="input-with-select">
                              <el-select v-model="formItemActive.fieldType" slot="append" placeholder="请选择">
                                 <el-option label="前缀" value="3-1"></el-option>
                                 <el-option label="后缀" value="3-2"></el-option>
                              </el-select>
                           </el-input>
                        </el-form-item>
                        <el-form-item label="保留几位小数" :label-width="'70px'" v-if="formItemActive.type === '3'">
                           <el-input min="0" max="9" type="number" v-model="formItemActive.data4" placeholder="选填，数字" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="默认值" :label-width="'70px'" v-if="formItemActive.type === '3'">
                           <el-input max="9999" type="number" v-model="formItemActive.default" placeholder="选填，数字" autocomplete="off"></el-input>
                        </el-form-item>

                        <!--选项和下拉菜单-->
                        <el-form-item label="字段类型" :label-width="'70px'" v-if="formItemActive.type === '6' || formItemActive.type === '7'">
                           <el-radio v-model="formItemActive.fieldType" :label="formItemActive.type+'-1'">单选</el-radio>
                           <el-radio v-model="formItemActive.fieldType" :label="formItemActive.type+'-2'">多选</el-radio>
                        </el-form-item>

                        <el-form-item label="备选项" :label-width="'70px'" v-show="(formItemActive.type === '6' || formItemActive.type === '7') && (formItemActive.fieldType == formItemActive.type+'-1' || formItemActive.fieldType == formItemActive.type+'-2')">
                           <ul class="ms-down-list" id="ms-down-list">
                              <!--单选-->
                              <li class="ms-down-item" v-for="(down,index) in formItemActive.downList" v-show="formItemActive.fieldType == formItemActive.type+'-1'">
                                 <i class="iconfont icon-tuodong"></i>
                                 <el-tooltip class="item" effect="dark" content="设为默认值" placement="top" transition="none">
                                    <el-radio class="ms-radio" v-model="formItemActive.downActive" :label="index"></el-radio>
                                 </el-tooltip>
                                 <el-input maxlength="20" class="ms-input" v-model="down.value" placeholder="选填，1-20，字符" autocomplete="off"></el-input>
                                 <i class="el-icon-delete" @click="formItemActive.downList.splice(index,1)"></i>
                              </li>
                              <!--多选-->
                              <el-checkbox-group id="ms-down-checkbox-list" v-model="formItemActive.downActiveList" v-show="formItemActive.fieldType == formItemActive.type+'-2'">
                                 <li class="ms-down-item" v-for="(down,index) in formItemActive.downList">
                                    <i class="iconfont icon-tuodong"></i>
                                    <el-tooltip class="item" effect="dark" content="设为默认值" placement="top" transition="none">
                                       <el-checkbox class="ms-radio" :label="index"></el-checkbox>
                                    </el-tooltip>
                                    <el-input maxlength="20" class="ms-input" v-model="down.value" placeholder="选填，1-20，字符" autocomplete="off"></el-input>
                                    <i class="el-icon-delete" @click="formItemActive.downList.splice(index,1)"></i>
                                 </li>
                              </el-checkbox-group>
                              <div class="ms-plus" @click="formItemActive.downList.push({})" v-if="formItemActive.fieldType == formItemActive.type+'-1' || formItemActive.fieldType == formItemActive.type+'-2'">
                                 <i class="el-icon-plus"></i>添加
                              </div>
                           </ul>
                        </el-form-item>

                        <!--上传图片-->
                        <el-form-item label="图片数量限制" :label-width="'70px'" v-if="formItemActive.type === '9'">
                           <el-input max="9" type="number" v-model="formItemActive.default" placeholder="选填，数字" autocomplete="off"></el-input>
                        </el-form-item>

                        <!--上传附件-->
                        <!--<el-form-item label="默认值" :label-width="'70px'" v-if="formItemActive === '3-7'">
            </el-form-item>-->

                        <!--设置-->
                        <el-form-item label="设置" :label-width="'70px'">
                           <el-checkbox-group v-model="formItemActive.set">
                              <el-checkbox label="必填"></el-checkbox>
                              <el-checkbox label="唯一" v-if="formItemActive.type != 5"></el-checkbox>
                           </el-checkbox-group>
                        </el-form-item>

                        <!--说明-->
                        <el-form-item label="填写说明" :label-width="'70px'">
                           <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="formItemActive.explain">
                           </el-input>
                        </el-form-item>
                     </el-form>

                  </div>
               </el-aside>
            </el-container>
         </el-container>
      </div>
   </body>
</html>
<script>
   var modelFormVue = new Vue({
      el: "#model-form",
      data: {
         typeList: [{
            title: "文本",
            icon: "icon-guidang",
            id: "1",
         }, {
            title: "号码",
            icon: "icon-plus-numberfill",
            id: "2",
         }, {
            title: "金额",
            icon: "icon-jine",
            id: "3",
         }, {
            title: "数值",
            icon: "icon-sifaleizhibanshi",
            id: "4",
         }, {
            title: "日期和时间",
            icon: "icon-riqi",
            id: "5",
         }, {
            title: "选项",
            icon: "icon-shaixuan",
            id: "6",
         }, {
            title: "下拉菜单",
            icon: "icon-xiala",
            id: "7",
         }, {
            title: "附件",
            icon: "icon-fujian",
            id: "8",
         }, {
            title: "图片",
            icon: "icon-tupian",
            id: "9",
         }], //右侧表单项类型
         formItemList: [{
            title: "文本", //标题
            value: "", //内容
            type: "1", //表单项的类型
            downList: [], //下拉列表
            downActiveList: [], //多选列表
            set: [], //设置
            unit: '', //金额单位
            fieldType: '', //字段类型
         }, {
            title: "号码",
            value: "",
            type: "2",
            downList: [],
            downActiveList: [],
            set: [],
            unit: '',
            fieldType: '',
         }, {
            title: "金额",
            value: "",
            type: "3",
            downList: [],
            downActiveList: [],
            set: [],
            unit: '',
            fieldType: '',
         }, {
            title: "数值",
            value: "",
            type: "4",
            downList: [],
            downActiveList: [],
            set: [],
            unit: '',
            fieldType: '',
         }, {
            title: "日期和时间",
            value: "",
            type: "5",
            downList: [],
            downActiveList: [],
            set: [],
            unit: '',
            fieldType: '',
         }, {
            title: "选项",
            value: "",
            type: "6",
            downList: [],
            downActiveList: [],
            set: [],
            unit: '',
            fieldType: '',
         }, {
            title: "下拉菜单",
            value: "",
            type: "7",
            downList: [],
            downActiveList: [],
            set: [],
            unit: '',
            fieldType: '',
         }, {
            title: "附件",
            value: "",
            type: "8",
            downList: [],
            downActiveList: [],
            set: [],
            unit: '',
            fieldType: '',
         }, {
            title: "图片",
            value: "",
            type: "9",
            downList: [],
            downActiveList: [],
            set: [],
            unit: '',
            fieldType: '',
         }], //表单项列表
         formItemActive: {
            downList: [],
            downActiveList: [],
            set: [],
            unit: '',
            fieldType: '',
         }, //选中表单项
      },

      computed: {
         //辅助监听对象的属性
         fieldType() {　　　　
            return this.formItemActive.fieldType;
         }
      },

      watch: {
         //监听切换表单字段类型
         fieldType: function(data) {
            //监听如果切换到发布选项卡
            if(data == '1-3') {
               let that = this;
               //富文本加载
               var URL = window.UEDITOR_HOME_URL || "http://mpm.mingsoft.net/static/plugins/ueditor/1.4.3.1/";
               if(this.formItemActive.editor == null) {
                  //保存这个示例以免在次执行
                  this.formItemActive.editor = UE.getEditor('ms-ueditor-' + this.formItemActive.id, {
                     toolbars: [
                        ['fullscreen', 'undo', 'redo', '|', 'bold', 'italic', 'underline', 'strikethrough',
                           'removeformat', 'blockquote',
                           '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', '|', 'attachment', 'simpleupload', 'link'
                        ]
                     ],
                     imageScaleEnabled: true,
                     // 服务器统一请求接口路径
                     serverUrl: URL + "jsp/msController.jsp?jsonConfig=%7BvideoUrlPrefix:'http://mpm.mingsoft.net/',fileUrlPrefix:'http://mpm.mingsoft.net/',imageUrlPrefix:'http://mpm.mingsoft.net/',imagePathFormat:'/upload/pm/editor/%7Btime%7D',filePathFormat:'/upload/pm/editor/%7Btime%7D',videoPathFormat:'/upload/pm/editor/%7Btime%7D'%7D",
                     autoHeightEnabled: true,
                     autoFloatEnabled: true,
                     scaleEnabled: false,
                     compressSide: 0,
                     maxImageSideLength: 2000,
                     maximumWords: 80000,
                     zIndex: 10000,
                     elementPathEnabled: false,
                     wordCount: false,
                     initialFrameWidth: '100%',
                     initialFrameHeight: 160,
                  });
               }
            }
         },
         //监听切换表单元素
         formItemActive: function(data) {
            //切换控件类型时
            if(data.type == '6' || data.type == '7') {
               this.$nextTick(function() {
                  if(this.downListSortable == null) {
                     this.downListSortable = new Sortable(document.getElementById("ms-down-list"), {
                        animation: 150,
                        draggable: '.ms-down-item',
                        handle: '.icon-tuodong', //设置只有小图标可以拖动
                        ghostClass: 'sortable-ghost', //设置拖动时候显示的样式
                     });
                  }
                  if(this.downListCheckboxSortable == null) {
                     this.downListCheckboxSortable = new Sortable(document.getElementById("ms-down-checkbox-list"), {
                        animation: 150,
                        draggable: '.ms-down-item',
                        handle: '.icon-tuodong', //设置只有小图标可以拖动
                        ghostClass: 'sortable-ghost', //设置拖动时候显示的样式
                     });
                  }
               })
            }
         }
      },
      methods: {},
      mounted: function() {
         var that = this;
         new Sortable(document.getElementById("ms-type-list"), {
            group: {
               name: 'shared',
               pull: 'clone',
               ghostClass: 'sortable-ghost', //设置拖动时候显示的样式
            },
            animation: 150,
            sort: false,
            onStart: function(event) {
               //准备拖动时保存拖动选项的数据
               that.addInputType = {
                  type: event.item.dataset.type,
                  title: event.item.dataset.title,
                  downList: [], //下拉列表
                  downActiveList: [], //多选列表
                  set: [], //设置
                  unit: '', //金额单位
                  fieldType: '', //字段类型
               };
            },
         });
         new Sortable(document.getElementById("ms-input-list"), {
            group: {
               name: 'shared',
               pull: false,
               ghostClass: 'sortable-ghost', //设置拖动时候显示的样式
            },
            animation: 150,
            onAdd: function(event) {
               //拖动成功后删除拖动过来的元素
               event.item.parentNode.removeChild(event.item);
               //想素组添加拖动过来的选项选项
               modelFormVue.formItemList.splice(event.newIndex, 0, that.addInputType);
            },
         });
      }
   })
</script>