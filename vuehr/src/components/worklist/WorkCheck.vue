<template>
  <div>
  <h1>作业查询</h1>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="通过作业编号搜索,记得回车哦..."
            clearable
            @change="keywordsChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="mini"
            :disabled="advanceSearchViewVisible"
            @keyup.enter.native="searchEmp"
            prefix-icon="el-icon-search"
            v-model="keywords">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search" @click="searchEmp">搜索
          </el-button>
          <el-button slot="reference" type="primary" size="mini" style="margin-left: 5px"
                     @click="showAdvanceSearchView"><i
            class="fa fa-lg" v-bind:class="[advanceSearchViewVisible ? faangledoubleup:faangledoubledown]"
            style="margin-right: 5px"></i>高级搜索
          </el-button>
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddEmpView">
            添加作业信息
          </el-button>
        </div>
      </el-header>




      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <transition name="slide-fade">
            <div
              style="margin-bottom: 10px;border: 1px;border-radius: 5px;border-style: solid;padding: 5px 0px 5px 0px;box-sizing:border-box;border-color: #20a0ff"
              v-show="advanceSearchViewVisible">
              <el-row>
                <el-col :span="4">
                  作业点编号:
                  <el-input v-model="emp.nationId" style="width: 130px" size="mini" placeholder="请输入作业点编号">
                    
                  </el-input>
                </el-col>
                <el-col :span="4">
                  作业人员编号:
                  <el-input v-model="emp.posId" style="width: 130px" size="mini" placeholder="请输入作业人员编号">
                    
                  </el-input>
                </el-col>
                <el-col :span="4">
                  作业人员:
                  <el-input v-model="emp.jobLevelId" style="width: 130px" size="mini" placeholder="请作业人员">
                    
                  </el-input>
                </el-col>
                <el-col :span="10">
                  作业时间:
                  <el-date-picker
                    v-model="beginDateScope"
                    unlink-panels
                    size="mini"
                    type="daterange"
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                  </el-date-picker>
                </el-col>
                <el-col :span="5" :offset="4">
                  <el-button size="mini" @click="cancelSearch">取消</el-button>
                  <el-button icon="el-icon-search" type="primary" size="mini" @click="searchEmp">搜索</el-button>
                </el-col>
              </el-row>
            </div>
          </transition>





          <el-table
            :data="emps"
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
              prop="name"
              align="left"
              fixed
              label="编号"
              width="90">
            </el-table-column>
            <el-table-column
              prop="workID"
              width="85"
              align="left"
              label="作业点编号">
            </el-table-column>
            <el-table-column
              prop="gender"
              label="作业进度"
              width="50">
            </el-table-column>
            <el-table-column
              prop="idCard"
              width="150"
              align="left"
              label="温度">
            </el-table-column>
            <el-table-column
              width="85"
              align="left"
              label="作业时间">
              <template slot-scope="scope">{{ scope.row.birthday | formatDate}}</template>
            </el-table-column>
            <el-table-column
              prop="wedlock"
              width="70"
              label="炮弹编号">
            </el-table-column>
            <el-table-column
              width="50"
              prop="nation.name"
              label="作业类型">
            </el-table-column>
            <el-table-column
              prop="nativePlace"
              width="80"
              label="高炮用弹量">
            </el-table-column>
            <el-table-column
              prop="politicsStatus.name"
              label="火箭用弹量">
            </el-table-column>
            <el-table-column
              prop="email"
              width="180"
              align="left"
              label="火箭类型编号">
            </el-table-column>
            <el-table-column
              prop="phone"
              width="100"
              label="烟条用弹量">
            </el-table-column>
            <el-table-column
              prop="address"
              width="220"
              align="left"
              label="烟条类型编号">
            </el-table-column>
            <el-table-column
              prop="department.name"
              align="left"
              width="100"
              label="其他用弹量">
            </el-table-column>
            <el-table-column
              width="100"
              align="left"
              prop="position.name"
              label="AGI用弹量">
            </el-table-column>
            <el-table-column
              prop="jobLevel.name"
              width="100"
              align="left"
              label="时间1">
            </el-table-column>
            <el-table-column
              prop="engageForm"
              label="时间2">
            </el-table-column>
            <el-table-column
              width="85"
              align="left"
              label="范围">
              <!-- <template slot-scope="scope">{{ scope.row.beginDate | formatDate}}</template> -->
            </el-table-column>
            <el-table-column
              width="85"
              align="left"
              label="预警">
              <!-- <template slot-scope="scope">{{ scope.row.conversionTime | formatDate}}</template> -->
            </el-table-column>
            <el-table-column
              width="95"
              align="left"
              label="天气1">
              <!-- <template slot-scope="scope">{{ scope.row.beginContract | formatDate}}</template> -->
            </el-table-column>
            <el-table-column
              width="95"
              align="left"
              label="天气2">
              <!-- <template slot-scope="scope">{{ scope.row.endContract | formatDate}}</template> -->
            </el-table-column>
            <el-table-column
              align="left"
              width="70"
              label="面积">
              <!-- <template slot-scope="scope">{{ scope.row.contractTerm}}年</template> -->
            </el-table-column>
            <el-table-column
              align="left"
              width="70"
              label="效果">
              <!-- <template slot-scope="scope">{{ scope.row.contractTerm}}年</template> -->
            </el-table-column>
            <el-table-column
              align="left"
              prop="tiptopDegree"
              label="人员编号">
            </el-table-column>
            <el-table-column
              align="left"
              width="70"
              label="表名称">
              <!-- <template slot-scope="scope">{{ scope.row.contractTerm}}年</template> -->
            </el-table-column>
            <el-table-column
              align="left"
              width="70"
              label="表单位编号">
              <!-- <template slot-scope="scope">{{ scope.row.contractTerm}}年</template> -->
            </el-table-column>
            <el-table-column
              align="left"
              width="70"
              label="表时间">
              <template slot-scope="scope">{{ scope.row.contractTerm}}年</template>
            </el-table-column>
            <el-table-column
              align="left"
              width="70"
              label="COMMENT">
              <!-- <template slot-scope="scope">{{ scope.row.contractTerm}}年</template> -->
            </el-table-column>
            <el-table-column
              align="left"
              width="70"
              label="IF_YDZY">
              <!-- <template slot-scope="scope">{{ scope.row.contractTerm}}年</template> -->
            </el-table-column>
            <el-table-column
              align="left"
              width="70"
              label="IF_CHECK">
              <!-- <template slot-scope="scope">{{ scope.row.contractTerm}}年</template> -->
            </el-table-column>
            <el-table-column
              align="left"
              width="70"
              label="IF_UPLOAD">
              <!-- <template slot-scope="scope">{{ scope.row.contractTerm}}年</template> -->
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditEmpView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <el-button style="padding: 3px 4px 3px 4px;margin: 2px" type="primary"
                           size="mini">查看高级资料
                </el-button>
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deleteEmp(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>


          
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-button type="danger" size="mini" v-if="emps.length>0" :disabled="multipleSelection.length==0"
                       @click="deleteManyEmps">批量删除
            </el-button>
            <el-pagination
              background
              :page-size="10"
              :current-page="currentPage"
              @current-change="currentChange"
              layout="prev, pager, next"
              :total="totalCount">
            </el-pagination>
          </div>
        </div>
      </el-main>
    </el-container>




    <el-form :model="emp" :rules="rules" ref="addEmpForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible"
          width="77%">
          <el-row>
            <el-col :span="5">
              <div>
                <el-form-item label="编号:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.name" size="mini" style="width: 150px"
                            placeholder="编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="作业点编号:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.name" size="mini" style="width: 150px"
                            placeholder="请输入作业点编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="作业进度:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入作业进度"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="温度:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入温度"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="作业时间:" prop="birthday">
                  <el-date-picker
                    v-model="emp.birthday"
                    size="mini"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    style="width: 150px"
                    type="date"
                    placeholder="请输入作业时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="炮弹编号:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入炮弹编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="作业类型:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入作业类型"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="高炮用弹量:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入高炮用弹量"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="火箭用弹量:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入火箭用弹量"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="火箭类型编号:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入火箭类型编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="烟条用弹量:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入烟条用弹量"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="烟条类型编号:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入烟条类型编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="其他用弹量:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入其他用弹量"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="AGI用弹量:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入AGI用弹量"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="时间1:" prop="birthday">
                  <el-date-picker
                    v-model="emp.birthday"
                    size="mini"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    style="width: 150px"
                    type="date"
                    placeholder="请输入时间1">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="时间2:" prop="birthday">
                  <el-date-picker
                    v-model="emp.birthday"
                    size="mini"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    style="width: 150px"
                    type="date"
                    placeholder="请输入时间2">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="范围:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入范围"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="预警:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入预警"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="天气1:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入天气1"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="天气2:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入天气2"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="面积:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入面积"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="人员编号:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入人员编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="表名称:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入表名称"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="单位编号:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入表单位编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="表时间:" prop="birthday">
                  <el-date-picker
                    v-model="emp.birthday"
                    size="mini"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    style="width: 150px"
                    type="date"
                    placeholder="表时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="COMMENT:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入COMMENT"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="IF_YDZY:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入IF_YDZY"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="IF_CHECK:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入IF_CHECK"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="IF_UPLOAD:" prop="gender">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gender" size="mini" style="width: 150px"
                            placeholder="请输入IF_UPLOAD"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="cancelEidt">取 消</el-button>
    <el-button size="mini" type="primary" @click="addEmp('addEmpForm')">确 定</el-button>
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
        emps: [],
        keywords: '',
        fileUploadBtnText: '导入数据',
        beginDateScope: '',
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',
        dialogTitle: '',
        multipleSelection: [],
        depTextColor: '#c0c4cc',
        nations: [],
        politics: [],
        positions: [],
        joblevels: [],
        totalCount: -1,
        currentPage: 1,
        degrees: [{id: 4, name: '大专'}, {id: 5, name: '本科'}, {id: 6, name: '硕士'}, {id: 7, name: '博士'}, {
          id: 3,
          name: '高中'
        }, {id: 2, name: '初中'}, {id: 1, name: '小学'}, {id: 8, name: '其他'}],
        deps: [],
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
        emp: {
          name: '',
          gender: '',
          birthday: '',
          idCard: '',
          wedlock: '',
          nationId: '',
          nativePlace: '',
          politicId: '',
          email: '',
          phone: '',
          address: '',
          departmentId: '',
          departmentName: '所属部门...',
          jobLevelId: '',
          posId: '',
          engageForm: '',
          tiptopDegree: '',
          specialty: '',
          school: '',
          beginDate: '',
          workState: '',
          workID: '',
          contractTerm: '',
          conversionTime: '',
          notWorkDate: '',
          beginContract: '',
          endContract: '',
          workAge: ''
        },
        rules: {
          name: [{required: true, message: '必填:姓名', trigger: 'blur'}],
          gender: [{required: true, message: '必填:性别', trigger: 'blur'}],
          birthday: [{required: true, message: '必填:出生日期', trigger: 'blur'}],
          idCard: [{
            required: true,
            message: '必填:身份证号码',
            trigger: 'blur'
          }, {
            pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
            message: '身份证号码格式不正确',
            trigger: 'blur'
          }],
          wedlock: [{required: true, message: '必填:婚姻状况', trigger: 'blur'}],
          nationId: [{required: true, message: '必填:民族', trigger: 'change'}],
          nativePlace: [{required: true, message: '必填:籍贯', trigger: 'blur'}],
          politicId: [{required: true, message: '必填:政治面貌', trigger: 'blur'}],
          email: [{required: true, message: '必填:电子邮箱', trigger: 'blur'}, {
            type: 'email',
            message: '邮箱格式不正确',
            trigger: 'blur'
          }],
          phone: [{required: true, message: '必填:电话号码', trigger: 'blur'}],
          address: [{required: true, message: '必填:联系地址', trigger: 'blur'}],
          departmentId: [{required: true, message: '必填:部门', trigger: 'change'}],
          jobLevelId: [{required: true, message: '必填:职称', trigger: 'change'}],
          posId: [{required: true, message: '必填:职位', trigger: 'change'}],
          engageForm: [{required: true, message: '必填:聘用形式', trigger: 'blur'}],
          tiptopDegree: [{required: true, message: '必填:最高学历', trigger: 'change'}],
          specialty: [{required: true, message: '必填:专业', trigger: 'blur'}],
          workID: [{required: true, message: '必填:工号', trigger: 'blur'}],
          school: [{required: true, message: '必填:毕业院校', trigger: 'blur'}],
          beginDate: [{required: true, message: '必填:入职日期', trigger: 'blur'}],
          conversionTime: [{required: true, message: '必填:转正日期', trigger: 'blur'}],
          beginContract: [{required: true, message: '必填:合同起始日期', trigger: 'blur'}],
          endContract: [{required: true, message: '必填:合同终止日期', trigger: 'blur'}],
          workAge: [{required: true, message: '必填:工龄', trigger: 'blur'}]
        }
      };
    },
    mounted: function () {
      this.initData();
      this.loadEmps();
    },
    methods: {
      fileUploadSuccess(response, file, fileList){
        if (response) {
          this.$message({type: response.status, message: response.msg});
        }
        this.loadEmps();
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
        this.loadEmps();
      },
      showAdvanceSearchView(){
        this.advanceSearchViewVisible = !this.advanceSearchViewVisible;
        this.keywords = '';
        if (!this.advanceSearchViewVisible) {
          this.emptyEmpData();
          this.beginDateScope = '';
          this.loadEmps();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      deleteManyEmps(){
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
      deleteEmp(row){
        this.$confirm('此操作将永久删除[' + row.name + '], 是否继续?', '提示', {
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
        this.deleteRequest("/employee/basic/emp/" + ids).then(resp=> {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _
            _this.loadEmps();
          }
        })
      },
      keywordsChange(val){
        if (val == '') {
          this.loadEmps();
        }
      },
      searchEmp(){
        this.loadEmps();
      },
      currentChange(currentChange){
        this.currentPage = currentChange;
        this.loadEmps();
      },
      loadEmps(){
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/employee/basic/emp?page=" + this.currentPage + "&size=10&keywords=" + this.keywords + "&politicId=" + this.emp.politicId + "&nationId=" + this.emp.nationId + "&posId=" + this.emp.posId + "&jobLevelId=" + this.emp.jobLevelId + "&engageForm=" + this.emp.engageForm + "&departmentId=" + this.emp.departmentId + "&beginDateScope=" + this.beginDateScope).then(resp=> {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.emps = data.emps;
            _this.totalCount = data.count;
//            _this.emptyEmpData();
          }
        })
      },
      addEmp(formName){
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.emp.id) {
              //更新
              this.tableLoading = true;
              this.putRequest("/employee/basic/emp", this.emp).then(resp=> {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyEmpData();
                  _this.loadEmps();
                }
              })
            } else {
              //添加
              this.tableLoading = true;
              this.postRequest("/employee/basic/emp", this.emp).then(resp=> {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _
                  _this.dialogVisible = false;
                  _this.emptyEmpData();
                  _this.loadEmps();
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
        this.getRequest("/employee/basic/basicdata").then(resp=> {
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.nations = data.nations;
            _this.politics = data.politics;
            _this.deps = data.deps;
            _this.positions = data.positions;
            _this.joblevels = data.joblevels;
            _this.emp.workID = data.workID;
          }
        })
      },
      showEditEmpView(row){
        console.log(row)
        this.dialogTitle = "编辑员工";
        this.emp = row;
        this.emp.birthday = this.formatDate(row.birthday);
        this.emp.conversionTime = this.formatDate(row.conversionTime);
        this.emp.beginContract = this.formatDate(row.beginContract);
        this.emp.endContract = this.formatDate(row.endContract);
        this.emp.beginDate = this.formatDate(row.beginDate);
        this.emp.nationId = row.nation.id;
        this.emp.politicId = row.politicsStatus.id;
        this.emp.departmentId = row.department.id;
        this.emp.departmentName = row.department.name;
        this.emp.jobLevelId = row.jobLevel.id;
        this.emp.posId = row.position.id;
//        delete this.emp.department;
//        delete this.emp.jobLevel;
//        delete this.emp.position;
//        delete this.emp.nation;
//        delete this.emp.politicsStatus;
        delete this.emp.workAge;
        delete this.emp.notWorkDate;
        this.dialogVisible = true;
      },
      showAddEmpView(){
        this.dialogTitle = "添加员工";
        this.dialogVisible = true;
        var _this = this;
        this.getRequest("/employee/basic/maxWorkID").then(resp=> {
          if (resp && resp.status == 200) {
            _this.emp.workID = resp.data;
          }
        })
      },
      emptyEmpData(){
        this.emp = {
          name: '',
          gender: '',
          birthday: '',
          idCard: '',
          wedlock: '',
          nationId: '',
          nativePlace: '',
          politicId: '',
          email: '',
          phone: '',
          address: '',
          departmentId: '',
          departmentName: '所属部门...',
          jobLevelId: '',
          posId: '',
          engageForm: '',
          tiptopDegree: '',
          specialty: '',
          school: '',
          beginDate: '',
          workState: '',
          workID: '',
          contractTerm: '',
          conversionTime: '',
          notWorkDate: '',
          beginContract: '',
          endContract: '',
          workAge: ''
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
