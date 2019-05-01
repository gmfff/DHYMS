<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="通过设备编号搜索,记得回车哦..."
            clearable
            @change="keywordsChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="mini"
            :disabled="advanceSearchViewVisible"
            @keyup.enter.native="searchEquipment"
            prefix-icon="el-icon-search"
            v-model="keywords">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search" @click="searchEquipment">搜索
          </el-button>
          <el-button slot="reference" type="primary" size="mini" style="margin-left: 5px"
                     @click="showAdvanceSearchView"><i
            class="fa fa-lg" v-bind:class="[advanceSearchViewVisible ? faangledoubleup:faangledoubledown]"
            style="margin-right: 5px"></i>高级搜索
          </el-button>
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-upload
            :show-file-list="false"
            accept="application/vnd.ms-excel"
            action="/employee/basic/importEmp"
            :on-success="fileUploadSuccess"
            :on-error="fileUploadError" :disabled="fileUploadBtnText=='正在导入'"
            :before-upload="beforeFileUpload" style="display: inline">
            <el-button size="mini" type="success" :loading="fileUploadBtnText=='正在导入'"><i class="fa fa-lg fa-level-up"
                                                                                          style="margin-right: 5px"></i>{{fileUploadBtnText}}
            </el-button>
          </el-upload>
          <el-button type="success" size="mini" @click="exportEqs"><i class="fa fa-lg fa-level-down"
                                                                       style="margin-right: 5px"></i>导出数据
          </el-button>
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddEqView">
            添加设备
          </el-button>
        </div>
      </el-header>
     
      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          
          <el-table
            :data="equipments"
            v-loading="tableLoading"
            border
            stripe
            @selection-change="handleSelectionChange"
            size="mini"
            style="width: 100%">
            <el-table-column
              type="selection"
              align="left"
              width="30">
            </el-table-column>
            <el-table-column
              prop="id"
              align="left"
              fixed
              label="ID"
              width="90">
            </el-table-column>
            <el-table-column
              prop="sb_BH"
              align="left"
              fixed
              label="设备编号"
              width="150">
            </el-table-column>
            <el-table-column
              prop="sb_BDID"
              width="250"
              align="left"
              label="设备BDID">
            </el-table-column>
            <el-table-column
              prop="sb_SIMID"
              label="设备SIMID"
              align="left"
              width="400">
            </el-table-column>
            <el-table-column
              prop="dw_BH"
              width="200"
              align="left"
              label="单位编号">
            </el-table-column>
            <el-table-column
              prop="tdsl"
              width="100"
              align="left"
              label="TDSL">
            </el-table-column>
            <el-table-column
              prop="sb_XH"
              width="150" 
              align="left"
              label="设备型号">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditEqView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <el-button style="padding: 3px 4px 3px 4px;margin: 2px" type="primary"
                           size="mini">查看高级资料
                </el-button>
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deleteEquipment(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-button type="danger" size="mini"  :disabled="multipleSelection.length==0"
                       @click="deleteManyEquipments">批量删除
            </el-button>
            <el-pagination
              background
              :page-size="10"
              :current-page="currentPage"
              @current-change="currentChange"
              layout="prev,pager, next"
              :total="totalCount">
            </el-pagination>
          </div>
         
        </div>
        <!--  <div style="display: flex; margin-left: 200px;" class="hello" >
            <p>设备型号占比图：</p> 
             <chart ref="chart1" :options="orgOptions" :auto-resize="true"></chart>
           </div> -->
      </el-main>
    </el-container>
      <el-form :model="equipment" :rules="rules" ref="addEquipmentForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible"
          width="20%">
          <el-row>
            <el-col >
              <div>
                <el-form-item label="设备编号:" prop="sb_BH">
                  <el-input prefix-icon="el-icon-edit" v-model="equipment.sb_BH"  size="mini" style="width: 150px"
                            placeholder="请输入设备编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <div>
                <el-form-item label="设备BDID:" prop="sb_BDID">
                  <el-input prefix-icon="el-icon-edit" v-model="equipment.sb_BDID" size="mini" style="width: 150px"
                            placeholder="请输入设备BDID"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col >
              <div>
                <el-form-item label="设备SIMID:" prop="sb_SIMID">
                  <el-input prefix-icon="el-icon-edit" v-model="equipment.sb_SIMID" size="mini" style="width: 150px"
                            placeholder="请输入设备SIMID"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col >
              <div>
                <el-form-item label="所属部门:" prop="dw_BH">
                 <el-select v-model="equipment.dw_BH" style="width: 200px" size="mini" placeholder="单位编号">
                    <el-option
                      v-for="item in deps"
                      :key="item.id"
                      :label="item.name"
                      :value="item.name">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
           <el-col >
              <div>
                <el-form-item label="TDSL:" prop="tdsl">
                  <el-radio-group v-model="equipment.tdsl">
                    <el-radio label=0>0</el-radio>
                    <el-radio style="margin-left: 15px" label=1>1</el-radio>
                  </el-radio-group>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col >
              <div>
                <el-form-item label="设备型号:" prop="sb_XH">
                  <el-select v-model="equipment.sb_XH" style="width: 200px" size="mini" placeholder="设备型号">
                    <el-option
                      v-for="item in Types"
                      :key="item.id"
                      :label="item.name"
                      :value="item.name">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            
          </el-row>
         
          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="cancelEidt">取 消</el-button>
    <el-button size="mini" type="primary" @click="addEquipment('addEquipmentForm')">确 定</el-button>
   </span>
        </el-dialog>
      </div>
    </el-form>
    
      
  </div>
</template>
<script>
  export default {
    data() {
      return {
        equipments: [],
        keywords: '',
        fileUploadBtnText: '导入数据',
        beginDateScope: '',
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',
        dialogTitle: '',
        multipleSelection: [],
        depTextColor: '#c0c4cc',
        Types: [],
        deps:[],
        orgOptions:'',
        totalCount: -1,
        currentPage: 1,
        defaultProps: {
          label: 'name',
          isLeaf: 'leaf',
          children: 'children'
        },
        dialogVisible: false,
        tableLoading: false,
        advanceSearchViewVisible: false,
        showOrHidePop: false,
        showOrHidePop2: false,
        equipment: {
          sb_BH: '',
          sb_BDID: '',
          sb_SIMID: '',
          dw_BH: '',
          tdsl: '',
          sb_XH: '',
        },
        rules: {
          sb_BH: [{required: true, message: '必填:设备编号', trigger: 'blur'}],
          sb_BDID: [{required: true, message: '必填:设备BDID', trigger: 'blur'},
                {
           pattern:/(^\d{8}$)/, message: 'BDID长度为8位' 
        }
          ],
          sb_SIMID:[{required: true, message: '必填:设备SIMID', trigger: 'blur'},
                {
           pattern:/(^\d{12}$)/, message: 'SIMID长度为12位' 
        }
          ],
          dw_BH:[{required: true, message: '必填:单位编号', trigger: 'blur'}],
          sb_XH:[{required: true, message: '必填:设备型号', trigger: 'blur'}],
         
        }
      };
    },
    mounted: function () {
      this.initData();
      this.loadEquipments();
      this.portray();
      this.test();
    },
    methods: {
      fileUploadSuccess(response, file, fileList){
        if (response) {
          this.$message({type: response.status, message: response.msg});
        }
        this.loadEquipments();
        this.fileUploadBtnText = '导入数据';
      },
      fileUploadError(err, file, fileList){
        this.$message({type: 'error', message: "导入失败!"});
        this.fileUploadBtnText = '导入数据';
      },
      beforeFileUpload(file){
        this.fileUploadBtnText = '正在导入';
      },
      exportEqs(){
        window.open("/equipment/basic/exportEq", "_parent");
      },
      cancelSearch(){
        this.advanceSearchViewVisible = false;
        this.emptyEmpData();
        this.beginDateScope = '';
        this.loadEquipments();
      },
      showAdvanceSearchView(){
        this.advanceSearchViewVisible = !this.advanceSearchViewVisible;
        this.keywords = '';
        if (!this.advanceSearchViewVisible) {
          this.emptyEmpData();
          this.beginDateScope = '';
          this.loadEquipments();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      deleteManyEquipments(){
        this.$confirm('此操作将删除[' + this.multipleSelection.length + ']条数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var ids = '';
          for (var i = 0; i < this.multipleSelection.length; i++) {
            ids += this.multipleSelection[i].id + ",";
          }
          this.doDelete(ids);
        }).catch(() => {
        });
      },
      deleteEquipment(row){
        this.$confirm('此操作将永久删除[' + row.id + '], 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.doDelete(row.id);
        }).catch(() => {
        });
      },
      doDelete(ids){
        this.tableLoading = true;
        var _this = this;
        this.deleteRequest("/equipment/basic/equipment/" + ids).then(resp=> {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.loadEquipments();
          }
        })
      },
      keywordsChange(val){
        if (val == '') {
          this.loadEquipments();
        }
      },
      searchEquipment(){
        this.loadEquipments();
      },
      currentChange(currentChange){
        this.currentPage = currentChange;
        this.loadEquipments();
      },
      loadEquipments(){
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/equipment/basic/equipment?page=" + this.currentPage + "&size=10&keywords=" + this.keywords).then(resp=> {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.equipments = data.equipments;
            
            _this.totalCount = data.count;
            console.log(_this.totalCount);
//            _this.emptyEmpData();
          }
        })
      },
      test(){
        var sendData="hello java,this is mqttdata"
        this.postRequest("/test/sendMqtt?sendData="+sendData).then(resp=> {
          this.tableLoading = false;
          
        })
      },
      addEquipment(formName){
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.equipment.id) {
              console.log(this.equipment);
              //更新
              this.tableLoading = true;
              this.putRequest("/equipment/basic/equipment", this.equipment).then(resp=> {   //put
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyEquipmentData();
                  _this.loadEquipments();
                }
              })
            } else {
              //添加

              this.tableLoading = true;
              console.log(this.equipment);
              
              this.postRequest("/equipment/basic/equipment", this.equipment).then(resp=> { //post
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  console.log("怎么回事");
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyEquipmentData();
                  _this.loadEquipments();
                }
              })
            }
          } else {
            return false;
          }
        });
      },
      cancelEidt(){
        this.dialogVisible = false;
        this.emptyEmpData();
      },
      showDepTree(){
        this.showOrHidePop = !this.showOrHidePop;
      },
      showDepTree2(){
        this.showOrHidePop2 = !this.showOrHidePop2;
      },
      handleNodeClick(data) {
        this.emp.departmentName = data.name;
        this.emp.departmentId = data.id;
        this.showOrHidePop = false;
        this.depTextColor = '#606266';
      },
      handleNodeClick2(data) {
        this.emp.departmentName = data.name;
        this.emp.departmentId = data.id;
        this.showOrHidePop2 = false;
        this.depTextColor = '#606266';
      },
      initData(){
        var _this = this;
        this.getRequest("/equipment/basic/basicdata").then(resp=> {
          if (resp && resp.status == 200) {
            var data = resp.data;
            
            _this.Types = data.Types;
            _this.deps = data.deps;
                  
          }
        })
      },
      showEditEqView(row){
        console.log(row)
        this.dialogTitle = "编辑设备";
        this.equipment = row;
        console.log(this.equipment.id);
        this.dialogVisible = true;
      },
      showAddEqView(){
        this.dialogTitle = "添加设备";
        this.dialogVisible = true;
      },
      emptyEquipmentData(){
        this.equipment = {
          sb_BH: '',
          sb_BDID: '',
          sb_SIMID: '',
          dw_BH: '',
          tdsl: '',
          sb_XH: '',
        }
      },
      portray(){
             this.orgOptions = {
    
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: this.Types,
                    },
                    series : [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:[
                                {value:0, name:'类型一'},
                                {value:2, name:'类型二'},
                                {value:2, name:'类型三'},
                                {value:3, name:'类型四'},
                                {value:18, name:'类型五'},
                                
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
            }
           
         
      }
    }
  };
</script>
<style>
  .el-dialog__body {
    padding-top: 0px;
    padding-bottom: 0px;
  }

  .slide-fade-enter-active {
    transition: all .8s ease;
  }

  .slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }

  .slide-fade-enter, .slide-fade-leave-to {
    transform: translateX(10px);
    opacity: 0;
  }
</style>
