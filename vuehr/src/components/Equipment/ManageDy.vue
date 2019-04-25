
<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="通过仓库名称搜索,记得回车哦..."
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
          <el-button type="success" size="mini" @click="exportEmps"><i class="fa fa-lg fa-level-down"
                                                                       style="margin-right: 5px"></i>导出数据
          </el-button>
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddCkView">
            添加仓库
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
              prop="repository"
              align="left"
              fixed
              label="仓库名称"
              width="150">
            </el-table-column>
            <el-table-column
              prop="allnums"
              width="150"
              align="left"
              label="总烟条数">
            </el-table-column>
            <el-table-column
              prop="remainnums"
              label="剩余烟条数"
              align="left"
              width="100">
            </el-table-column>
            <el-table-column
              prop="zyd_BH"
              width="200"
              align="left"
              label="作业点编号">
            </el-table-column>
            <el-table-column
              prop="dw_BH"
              width="100"
              align="left"
              label="单位编号">
            </el-table-column>
            <el-table-column
              prop="inDate"
              width="300" 
              align="left"
              label="最后入库时间">
              <template slot-scope="scope">{{ scope.row.inDate| formatDate}}</template>
            </el-table-column>
            <el-table-column
              prop="outDate"
              width="300" 
              align="left"
              label="最后出库时间">
              <template slot-scope="scope">{{ scope.row.outDate| formatDate}}</template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button type="primary" style="padding: 3px 4px 3px 4px;margin: 2px"  size="mini" @click="showEditDyView(scope.row)">入库</el-button>
                <el-button type="success" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"  @click="showEditDyView1(scope.row)">出库</el-button>
                <el-button @click="showEditDyView2(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
               
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deleteDy(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-button type="danger" size="mini"  :disabled="multipleSelection.length==0"
                       @click="deleteManyDys">批量删除
            </el-button>
            <el-pagination
              background
              :page-size="10"s
              :current-page="currentPage"
              @current-change="currentChange"
              layout="prev,pager, next"
              :total="totalCount">
            </el-pagination>
          </div>
           <div style="display: flex;" class="hello" >
              <p>仓库烟条剩余数：</p>
             <chart ref="chart1" :options="orgOptions" :auto-resize="true"></chart>
           </div>

        </div>
      </el-main>
     
    </el-container>
       <el-form :model="dy" :rules="rules" ref="addCkForm" style="margin: 0px;padding: 0px;">
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
                <el-form-item label="仓库名称:" prop="repository">
                  <el-input prefix-icon="el-icon-edit" v-model="dy.repository"  size="mini" style="width: 150px"
                            placeholder="请输入仓库名称"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <div>
                <el-form-item label="总烟条数:" prop="repository">
                  <el-input prefix-icon="el-icon-edit" v-model="dy.allnums" size="mini" style="width: 150px"
                            placeholder="请输入总烟条数"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col >
              <div>
                <el-form-item label="剩余烟条数:" prop="remainnums">
                  <el-input prefix-icon="el-icon-edit" v-model="dy.remainnums" size="mini" style="width: 150px"
                            placeholder="请输入剩余烟条数"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col >
              <div>
                <el-form-item label="所属单位:" prop="dw_BH">
                 <el-select v-model="dy.dw_BH" style="width: 200px" size="mini" placeholder="单位编号">
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
                <el-form-item label="作业点编号:" prop="zyd_BH">
                  <el-select v-model="dy.zyd_BH" style="width: 200px" size="mini" placeholder="设备型号">
                    <el-option
                      v-for="item in zyds"
                      :key="item.id"
                      :label="item.zyd_BH"
                      :value="item.zyd_BH">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            
          </el-row>
         
          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="cancelEidt">取 消</el-button>
    <el-button size="mini" type="primary" @click="addDy('addCkForm')">确 定</el-button>
   </span>
        </el-dialog>
      </div>
    </el-form>

    <el-form  :model="dy" :rules="rules" ref="addDyForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="dialogVisibleru"
          width="20%"
        >
          <el-row>
            <el-col >
              <div>
                <el-form-item label="入库数量:" prop="rknums">
                  <el-input prefix-icon="el-icon-edit" v-model="rknums"  size="mini" style="width: 150px"
                            placeholder="请输入入库数量"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
          <el-button size="mini" @click="cancelEidtru">取 消</el-button>
          <el-button size="mini" type="primary" @click="rkopera(rknums)">确 定</el-button>
        </span>
        </el-dialog>
      </div>

    </el-form>
    <el-form  :model="dy" :rules="rules" ref="addDyForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="dialogVisiblechu"
          width="20%"
        >
          <el-row>
            <el-col >
              <div>
                <el-form-item label="出库数量:" prop="cknums">
                  <el-input prefix-icon="el-icon-edit" v-model="cknums"  size="mini" style="width: 150px"
                            placeholder="请输入出库数量"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
          <el-button size="mini" @click="cancelEidtchu">取 消</el-button>
          <el-button size="mini" type="primary" @click="ckopera(cknums)">确 定</el-button>
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
        rknums: '',
        cknums: '',
        keywords: '',
        fileUploadBtnText: '导入数据',
        beginDateScope: '',
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',
        dialogTitle: '',
        multipleSelection: [],
        depTextColor: '#c0c4cc',
        zyds: [],
        deps:[],
        totalCount: -1,
        currentPage: 1,
        defaultProps: {
          label: 'name',
          isLeaf: 'leaf',
          children: 'children'
        },
        dialogVisible: false,
        dialogVisibleru: false,
        dialogVisiblechu: false,
        tableLoading: false,
        advanceSearchViewVisible: false,
        showOrHidePop: false,
        showOrHidePop2: false,
        orgOptions: {},
        dy: {
          allnums: '',
          dw_BH: '',
          inDate: '',
          outDate: '',
          remainnums: '',
          repository: '',
          zyd_BH: ''
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
      exportEmps(){
        window.open("/employee/basic/exportEmp", "_parent");
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
      deleteManyDys(){
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
      deleteDy(row){
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
        this.deleteRequest("/equipment/basic/dy/" + ids).then(resp=> {
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
        this.getRequest("/equipment/basic/yt?page=" + this.currentPage + "&size=10&keywords=" + this.keywords).then(resp=> {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.equipments = data.yts;
            
            _this.totalCount = data.count;
             this.orgOptions = {
                  title: {
                          text: '仓库剩余烟条数',
                          
                      },
                 color: ['#3398DB'],
                  tooltip : {
                      trigger: 'axis',
                      axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                          type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                      }
                  },
                  grid: {
                      left: '3%',
                      right: '4%',
                      bottom: '3%',
                      containLabel: true
                  },
                  xAxis : [
                      {
                          type : 'category',
                          data : [this.equipments[0].repository, this.equipments[1].repository, this.equipments[2].repository, this.equipments[3].repository, this.equipments[4].repository, this.equipments[5].repository, this.equipments[6].repository],
                          axisTick: {
                              alignWithLabel: true
                          }
                      }
                  ],
                  yAxis : [
                      {
                          type : 'value'
                      }
                  ],
                  series : [
                      {
                          name:'直接访问',
                          type:'bar',
                          barWidth: '60%',
                          data:[this.equipments[0].remainnums, this.equipments[1].remainnums, this.equipments[2].remainnums, this.equipments[3].remainnums, this.equipments[4].remainnums, this.equipments[5].remainnums, this.equipments[6].remainnums]
                      }
                  ]
    }
          
//            _this.emptyEmpData();
          }
        })
      },
      addDy(formName){
        var _this = this;
        console.log(this.dy);
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.dy.id) {
              console.log(this.dy.zyd_BH);
              //更新
              this.tableLoading = true;
              this.putRequest("/equipment/basic/yt", this.dy).then(resp=> {   //put
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyDyData();
                  _this.loadEquipments();
                }
              })
            } else {
              //添加

              this.tableLoading = true;
              console.log(this.dy);
              
              this.postRequest("/equipment/basic/yt", this.dy).then(resp=> { //post
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyDyData();
                  _this.loadEquipments();
                }
              })
            }
          } else {
            return false;
          }
        });
      },
      rkopera(rknums){
         var _this = this;
         var myDate = new Date();
        this.dy.inDate = myDate.toLocaleDateString();
        this.dy.inDate =this.formatDate(this.dy.inDate);
        console.log(this.dy.inDate);
        this.dy.remainnums=Number(this.dy.remainnums)+Number(rknums);
        console.log(this.dy.remainnums);
         if(this.dy.remainnums<=this.dy.allnums){
              this.putRequest("/equipment/basic/addDynums",this.dy).then(resp=> {
                  this.tableLoading = true;
                   if (resp && resp.status == 200) {
                    var data = resp.data;
                     _this.dialogVisibleru = false;
                      _this.dialogVisiblechu = false;
                    _this.emptyDyData();
                    _this.loadEquipments();
                }
                  else{
                    return false
                }
              })
          }
          else{

            alert("容量不足");
            _this.dialogVisibleru = false;
            _this.dialogVisiblechu = false;
            _this.emptyDyData();
            _this.loadEquipments();
          }
        },
        ckopera(cknums){
          var _this = this;
          var myDate = new Date();
          this.dy.outDate = myDate.toLocaleDateString();
          this.dy.outDate =this.formatDate(this.dy.outDate);
          this.dy.remainnums=Number(this.dy.remainnums)-Number(cknums);
         if(this.dy.remainnums>0){
              this.putRequest("/equipment/basic/addDynums",this.dy).then(resp=> {
                  this.tableLoading = true;
                   if (resp && resp.status == 200) {
                    var data = resp.data;
                     _this.dialogVisibleru = false;
                      _this.dialogVisiblechu = false;
                    _this.emptyDyData();
                    _this.loadEquipments();
                }
                  else{
                    return false
                }
              })
          }
          else{

            alert("库存不足");
            _this.dialogVisibleru = false;
            _this.dialogVisiblechu = false;
            _this.emptyDyData();
            _this.loadEquipments();
          }

        },
        cancelEidt(){
          this.dialogVisible = false;
          this.emptyDyData();
        },

      cancelEidtru(){
        this.dialogVisibleru = false;
        this.emptyDyData();
      },
      cancelEidtchu(){
        this.dialogVisiblechu = false;
        this.emptyDyData();
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
        this.getRequest("/equipment/basic/basicDydata").then(resp=> {
          if (resp && resp.status == 200) {
            var data = resp.data;
            
            _this.deps = data.deps;
            _this.zyds = data.joblevels;
            console.log(_this.zyds);
          }
        })
      },
      showEditDyView2(row){
        console.log(row)
        this.dialogTitle = "编辑设备";
        this.dy = row;
        console.log(this.dy.id);
        this.dialogVisible = true;
      },
      showEditDyView(row){
        console.log(row)
        this.dialogTitle = "入库";
        this.dy = row;
        
        this.dialogVisibleru = true;
      },
      showEditDyView1(row){
        console.log(row)
        this.dialogTitle = "出库";
        this.dy = row;
        
        this.dialogVisiblechu = true;
      },
      showAddCkView(){
        this.dialogTitle = "添加仓库";
        this.dialogVisible = true;
      },
      emptyDyData(){
        this.dy = {
          allnums: '',
          dw_BH: '',
          inDate: '',
          outDate: '',
          remainnums: '',
          repository: '',
          zyd_BH: ''
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
