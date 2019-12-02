<el-dialog id="form" :title="(addEditForm.modelIsMenu == 1) ? '菜单编辑' : '按钮编辑'" :visible.sync="dialogVisible" width="50%">
    <el-form ref="addEditForm" :model="addEditForm" :rules="rules" label-width="100px" size="mini">
        <el-form-item  label="标题" prop="modelTitle">
            <el-input v-model="addEditForm.modelTitle" placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item  label="父级菜单" prop="modelModelId">
            <tree-select ref="treeselect"
                    :props="props"
                    :options="modelList"
                    :value="addEditForm.modelModelId"
                    :clearable="isClearable"
                    :accordion="isAccordion"
                    @get-value="getValue($event)"></tree-select>
        </el-form-item>
        <el-form-item  label="是否为菜单" prop="modelIsMenu">
            <el-radio-group v-model="addEditForm.modelIsMenu">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="0">否</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item  label="图标" prop="modelIcon" v-if="addEditForm.modelIsMenu == 1">
            <ms-icon v-model="addEditForm.modelIcon"></ms-icon>
        </el-form-item>
        <el-form-item  :label="(addEditForm.modelIsMenu==1) ? '链接地址' : '权限标识'" prop="modelUrl">
            <el-input v-model="addEditForm.modelUrl" :placeholder="(addEditForm.modelIsMenu==1) ? '请输入链接地址' : '请输入权限标识'"></el-input>
        </el-form-item>
        <el-form-item  label="模块编码" prop="modelCode">
            <el-input v-model="addEditForm.modelCode" placeholder="请输入模块编码"></el-input>
        </el-form-item>
        <el-form-item  label="排序" prop="modelSort">
            <el-input v-model.number="addEditForm.modelSort" maxlength="11" placeholder="请输入排序"></el-input>
        </el-form-item>
    </el-form>    <div slot="footer">
        <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="save()" :loading="saveDisabled">保存</el-button>
    </div>
</el-dialog>
<script>
    var form = new Vue({
        el: '#form',
        data() {
            return {
                isClearable:false,     // 可清空（可选）
                isAccordion:true,     // 可收起（可选）
                modelTitle:'',
                props:{               // 配置项（必选）
                    value: 'modelId',
                    label: 'modelTitle',
                    children: 'children',
                    // disabled:true
                },
                modelList:[],//菜单数据
                modeldata:[],
                saveDisabled: false,
                dialogVisible:false,
                //表单数据
                addEditForm: {
                    modelId:0,
                    modelModelId:'',
                    modelTitle:'',
                    modelIcon:'',
                    modelUrl:'',
                    modelCode:'',
                    modelSort:0,
                    modelIsMenu:0,
                },
                rules:{
                    modelTitle:[{required: true, message: '请输入标题', trigger: 'blur'},{min: 1, max: 20, message: '长度不能超过20个字符', trigger: 'change'}],
                    modelUrl:[{min: 0, max: 30, message: '长度不能超过30个字符', trigger: 'change'}],
                    modelCode:[{min: 0, max: 20, message: '长度不能超过20个字符', trigger: 'change'}],
                    modelSort:[{ type: 'number', message: '排序必须为数字值'}],
                },

            }
        },
        watch: {
            'dialogVisible': function(n,o){
                if(n){
                }else {
                    this.$refs.addEditForm.resetFields();
                }
            },
            'modeldata': function(n,o){
                if(n){
                   this.modelList.push({
                       modelTitle:'顶级菜单',
                       modelId:0,
                       children: this.modeldata,
                   })
                }
            }
        },
        methods: {
            open(id){
                this.addEditForm.modelId = 0;
                this.addEditForm.modelModelId = '';
                if (id > 0) {
                    this.get(id);
                }
                this.$nextTick(function () {
                    this.dialogVisible = true;
                })
            },
            save() {
                var that = this;
                var url = ms.manager + "/model/save.do"
                if (that.addEditForm.modelId > 0) {
                    url = ms.manager + "/model/update.do";
                }
                //按钮没有图标
                if(that.addEditForm.modelIsMenu == 0){
                    that.addEditForm.modelIcon = '';
                }
                that.$refs.addEditForm.validate((valid) => {
                    if (valid) {
                        that.saveDisabled = true;
                        var data = JSON.parse(JSON.stringify(that.addEditForm));
                        ms.http.post(url, data).then(function (data) {
                            if (data.result) {
                                that.$notify({
                                    title: '成功',
                                    message: '保存成功',
                                    type: 'success'
                                });
                                that.saveDisabled = false;
                                that.dialogVisible = false;
                                window.location.href =  ms.manager +"/model/index.do";
                            } else {
                                that.$notify({
                                    title: '失败',
                                    message: data.resultMsg,
                                    type: 'warning'
                                });
                                that.saveDisabled = false;
                            }
                        });
                    } else {
                        return false;
                    }
                })
            },
            getValue(data){
                if(data.modelIsMenu == 0){
                    this.$notify({
                        title: '提示',
                        message: '不能将功能按钮添加为菜单',
                        type: 'info'
                    });
                } else {
                    this.addEditForm.modelModelId = data.modelId;
                    this.$refs.treeselect.valueId = data[this.props.value];
                    this.$refs.treeselect.valueTitle = data[this.props.label];
                }
            },
            //获取当前任务
            get(id) {
                var that = this;
                ms.http.get(ms.manager + "/model/get.do", {
                    modelId:id
                }).then(function (data) {
                    if(data.model){
                        that.addEditForm = data.model;
                        delete that.addEditForm.modelDatetime;
                    }
                }).catch(function (err) {
                    console.log(err);
                });
            },

        },
        created() {
        }
    });
</script>
<style>
    #form .el-select{
        width: 100%;
    }
</style>