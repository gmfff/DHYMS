<template>
  <div>
  <h1>作业记录</h1>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="输入表名称,记得回车哦..."
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
            添加作业记录
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
                  <el-input v-model="emp.zyd_bh" style="width: 130px" size="mini" placeholder="请输入作业点编号">
                    
                  </el-input>
                </el-col>
                <el-col :span="4">
                  作业人员编号:
                  <el-input v-model="emp.zyxx_rybh" style="width: 130px" size="mini" placeholder="请输入作业人员编号">
                    
                  </el-input>
                </el-col>
                <el-col :span="8">
                  作业时间:
                  <el-date-picker
                    v-model="beginDateScope"
                    unlink-panels
                    size="mini"
                    type="daterange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                  </el-date-picker>
                </el-col>
                <el-col :span="10" :offset="4">
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
              prop="zyxx_id"
              align="left"
              fixed
              label="编号"
              width="90">
            </el-table-column>
            <el-table-column
              prop="zyd_bh"
              width="85"
              align="left"
              label="作业点编号">
            </el-table-column>
            <el-table-column
              prop="zyxx_jd"
              label="作业进度"
              width="50">
            </el-table-column>
            <el-table-column
              prop="zyxx_wd"
              width="150"
              align="left"
              label="温度">
            </el-table-column>
            <el-table-column
              width="85"
              align="left"
              label="作业时间">
              <template slot-scope="scope">{{ scope.row.zyxx_date | formatDate}}</template>
            </el-table-column>
            <el-table-column
              prop="pdlx_bh"
              width="70"
              label="炮弹编号">
            </el-table-column>
            <el-table-column
              width="50"
              prop="zyxx_zylx"
              label="作业类型">
            </el-table-column>
            <el-table-column
              prop="gpydl"
              width="80"
              label="高炮用弹量">
            </el-table-column>
            <el-table-column
              prop="hjydl"
              label="火箭用弹量">
            </el-table-column>
            <el-table-column
              prop="hjlx_bh"
              width="180"
              align="left"
              label="火箭类型编号">
            </el-table-column>
            <el-table-column
              prop="ytydl"
              width="100"
              label="烟条用弹量">
            </el-table-column>
            <el-table-column
              prop="ytlx_bh"
              width="220"
              align="left"
              label="烟条类型编号">
            </el-table-column>
            <el-table-column
              prop="qtydl"
              align="left"
              width="100"
              label="其他用弹量">
            </el-table-column>
            <el-table-column
              width="100"
              align="left"
              prop="agiydl"
              label="AGI用弹量">
            </el-table-column>
            <el-table-column
              width="100"
              align="left"
              label="时间1">
              <template slot-scope="scope">{{ scope.row.zyxx_time1}}年</template>
            </el-table-column>
            <el-table-column
              label="时间2">
              <template slot-scope="scope">{{ scope.row.zyxx_time2}}年</template>
            </el-table-column>
            <el-table-column
              prop="zyxx_fw"
              width="85"
              align="left"
              label="范围">
          
            </el-table-column>
            <el-table-column
              prop="zyxx_yj"
              width="85"
              align="left"
              label="预警">
    
            </el-table-column>
            <el-table-column
              prop="zyxx_tq1"
              width="95"
              align="left"
              label="天气1">
            </el-table-column>
            <el-table-column
              prop="zyxx_tq2"
              width="95"
              align="left"
              label="天气2">
           
            </el-table-column>
            <el-table-column
              prop="zy_mj"
              align="left"
              width="70"
              label="面积">
            
            </el-table-column>
            <el-table-column
              prop="zy_xg"
              align="left"
              width="70"
              label="效果">
             
            </el-table-column>
            <el-table-column
              align="left"
              prop="zyxx_rybh"
              label="人员编号">
            </el-table-column>
            <el-table-column
              prop="tb_name"
              align="left"
              width="70"
              label="表名称">
              
            </el-table-column>
            <el-table-column
              prop="tb_dwbh"
              align="left"
              width="70"
              label="表单位编号">
             
            </el-table-column>
            <el-table-column
              align="left"
              width="70"
              label="表时间">
              <template slot-scope="scope">{{ scope.row.tb_date}}年</template>
            </el-table-column>
            <el-table-column
              prop="comment"
              align="left"
              width="70"
              label="COMMENT">
              
            </el-table-column>
            <el-table-column
              prop="if_ydzy"
              align="left"
              width="70"
              label="IF_YDZY">
              
            </el-table-column>
            <el-table-column
              prop="if_check"
              align="left"
              width="70"
              label="IF_CHECK">
              
            </el-table-column>
            <el-table-column
              prop="if_upload"
              align="left"
              width="70"
              label="IF_UPLOAD">
             
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
          <el-col :span="4">
              <div>
                <el-form-item label="编号:" prop="zyxx_id">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zyxx_id" disabled size="mini" style="width: 150px"
                            placeholder="请输入编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="作业点编号:" prop="zyd_bh">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zyd_bh" size="mini" style="width: 150px"
                            placeholder="请输入作业点编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="作业进度:" prop="zyxx_jd">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zyxx_jd" size="mini" style="width: 150px"
                            placeholder="请输入作业进度"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="温度:" prop="zyxx_wd">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zyxx_wd" size="mini" style="width: 150px"
                            placeholder="请输入温度"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="作业时间:" prop="zyxx_date">
                  <el-date-picker
                    v-model="emp.zyxx_date"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    style="width: 150px"
                    type="date"
                    placeholder="请输入作业时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="炮弹编号:" prop="pdlx_bh">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.pdlx_bh" size="mini" style="width: 150px"
                            placeholder="请输入炮弹编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="作业类型:" prop="zyxx_zylx">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zyxx_zylx" size="mini" style="width: 150px"
                            placeholder="请输入作业类型"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="高炮用弹量:" prop="gpydl">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.gpydl" size="mini" style="width: 150px"
                            placeholder="请输入高炮用弹量"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="火箭用弹量:" prop="hjydl">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.hjydl" size="mini" style="width: 150px"
                            placeholder="请输入火箭用弹量"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="火箭类型编号:" prop="hjlx_bh">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.hjlx_bh" size="mini" style="width: 150px"
                            placeholder="请输入火箭类型编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="烟条用弹量:" prop="ytydl">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.ytydl" size="mini" style="width: 150px"
                            placeholder="请输入烟条用弹量"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="烟条类型编号:" prop="ytlx_bh">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.ytlx_bh" size="mini" style="width: 150px"
                            placeholder="请输入烟条类型编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="其他用弹量:" prop="qtydl">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.qtydl" size="mini" style="width: 150px"
                            placeholder="请输入其他用弹量"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="AGI用弹量:" prop="agiydl">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.agiydl" size="mini" style="width: 150px"
                            placeholder="请输入AGI用弹量"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="时间1:" prop="zyxx_time1">
                  <el-date-picker
                    v-model="emp.zyxx_time1"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    style="width: 150px"
                    type="date"
                    placeholder="请输入时间1">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="时间2:" prop="zyxx_time2">
                  <el-date-picker
                    v-model="emp.zyxx_time2"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    style="width: 150px"
                    type="date"
                    placeholder="请输入时间2">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="范围:" prop="zyxx_fw">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zyxx_fw" size="mini" style="width: 150px"
                            placeholder="请输入范围"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="预警:" prop="zyxx_yj">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zyxx_yj" size="mini" style="width: 150px"
                            placeholder="请输入预警"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="天气1:" prop="zyxx_tq1">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zyxx_tq1" size="mini" style="width: 150px"
                            placeholder="请输入天气1"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="天气2:" prop="zyxx_tq2">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zyxx_tq2" size="mini" style="width: 150px"
                            placeholder="请输入天气2"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="面积:" prop="zy_mj">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zy_mj" size="mini" style="width: 150px"
                            placeholder="请输入面积"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="效果:" prop="zy_xg">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zy_xg" size="mini" style="width: 150px"
                            placeholder="请输入效果"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="人员编号:" prop="zyxx_rybh">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.zyxx_rybh" size="mini" style="width: 150px"
                            placeholder="请输入人员编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="表名称:" prop="tb_name">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.tb_name" size="mini" style="width: 150px"
                            placeholder="请输入表名称"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="单位编号:" prop="tb_dwbh">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.tb_dwbh" size="mini" style="width: 150px"
                            placeholder="请输入表单位编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="表时间:" prop="tb_date">
                  <el-date-picker
                    v-model="emp.tb_date"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    style="width: 150px"
                    type="date"
                    placeholder="表时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="COMMENT:" prop="comment">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.comment" size="mini" style="width: 150px"
                            placeholder="请输入COMMENT"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="IF_YDZY:" prop="if_ydzy">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.if_ydzy" size="mini" style="width: 150px"
                            placeholder="请输入IF_YDZY"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="IF_CHECK:" prop="if_check">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.if_check" size="mini" style="width: 150px"
                            placeholder="请输入IF_CHECK"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="IF_UPLOAD:" prop="if_upload">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.if_upload" size="mini" style="width: 150px"
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
          zyxx_id: '',
          zyd_bh: '',
          zyxx_jd: '',
          zyxx_wd: '',
          zyxx_date: '',
          pdlx_bh: '',
          zyxx_zylx: '',
          gpydl: '',
          hjydl: '',
          hjlx_bh: '',
          ytydl: '',
          ytlx_bh: '',
          qtydl: '',
          agiydl: '',
          zyxx_time1: '',
          zyxx_time2: '',
          zyxx_fw: '',
          zyxx_yj: '',
          zyxx_tq1: '',
          zyxx_tq2: '',
          zy_mj: '',
          zy_xg: '',
          zyxx_rybh: '',
          tb_name: '',
          tb_dwbh: '',
          tb_date:'',
          comment:'',
          if_ydzy:'',
          if_check:'',
          if_upload:'',
        },
        rules: {
          zyxx_id: [{required: true, message: '必填:id', trigger: 'blur'}],
          zyd_bh: [{required: true, message: '必填:作业点编号', trigger: 'blur'}],
          zyxx_jd: [{required: true, message: '必填:作业进度', trigger: 'blur'}],
          zyxx_wd: [{required: true, message: '必填:温度', trigger: 'blur'}],
          zyxx_date: [{required: true, message: '必填:作业时间', trigger: 'blur'}],
          pdlx_bh: [{required: true, message: '必填:炮弹类型编号', trigger: 'blur'}],
          zyxx_zylx: [{required: true, message: '必填:作业类型', trigger: 'blur'}],
          gpydl: [{required: true, message: '必填:高炮用弹量', trigger: 'blur'}],
          hjydl: [{required: true, message: '必填:火箭用弹量', trigger: 'blur'}],
          hjlx_bh: [{required: true, message: '必填:火箭类型编号', trigger: 'blur'}],
          ytydl: [{required: true, message: '必填:烟条用弹量', trigger: 'blur'}],
          ytlx_bh: [{required: true, message: '必填:烟条类型编号', trigger: 'blur'}],
          qtydl: [{required: true, message: '必填:其他用弹量', trigger: 'blur'}],
          agiydl: [{required: true, message: '必填:AGI用弹量', trigger: 'blur'}],
          zyxx_time1: [{required: true, message: '必填:时间1', trigger: 'blur'}],
          zyxx_time2: [{required: true, message: '必填:时间2', trigger: 'blur'}],
          zyxx_fw: [{required: true, message: '必填:范围', trigger: 'blur'}],
          zyxx_yj: [{required: true, message: '必填:预警', trigger: 'blur'}],
          zyxx_tq1: [{required: true, message: '必填:天气1', trigger: 'blur'}],
          zyxx_tq2: [{required: true, message: '必填:天气2', trigger: 'blur'}],
          zy_mj: [{required: true, message: '必填:面积', trigger: 'blur'}],
          zy_xg: [{required: true, message: '必填:效果', trigger: 'blur'}],
          zyxx_rybh: [{required: true, message: '必填:人员编号', trigger: 'blur'}],
          tb_name: [{required: true, message: '必填:表名称', trigger: 'blur'}],
          tb_dwbh: [{required: true, message: '必填:单位编号', trigger: 'blur'}],
          tb_date: [{required: true, message: '必填:表时间', trigger: 'blur'}],
          comment: [{required: true, message: '必填:COMMENT', trigger: 'blur'}],
          if_ydzy: [{required: true, message: '必填:IF_YDZY', trigger: 'blur'}],
          if_check: [{required: true, message: '必填:IF_CHECK', trigger: 'blur'}],
          if_upload: [{required: true, message: '必填:IF_UPLOAD', trigger: 'blur'}],
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
      /*addtest(){
        var test='1';
        this.postRequest("/worklist/basic/emp1", test).then(resp=> {
                this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  this.dialogVisible = false;
                  this.emptyEmpData();
                  this.loadEmps();
                }
              })
      },*/
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
        this.$confirm('此操作将永久删除作业编号[' + row.zyxx_id + '], 是否继续?', '提示', {
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
        this.deleteRequest("/worklist/basic/emp/" + ids).then(resp=> {
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
        this.getRequest("/worklist/basic/emp?page=" + this.currentPage + "&size=10&keywords=" + this.keywords + "&zydbh=" + this.emp.zyd_bh + "&rybh=" + this.emp.zyxx_rybh +  "&beginDateScope=" + this.beginDateScope).then(resp=> {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.emps = data.emps;
            console.log(_this.emps);
            _this.totalCount = data.count;
//            _this.emptyEmpData();
          }
        })
      },
      addEmp(formName){
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
           /* alert(this.emp.id+"*"+this.emp.zyxx_id+"*"+this.emp.zyd_bh+"*"+this.emp.zyxx_jd+"*"+this.emp.zyxx_wd+"*"+this.emp.zyxx_date+"*"+this.emp.pdlx_bh+"*"+this.emp.zyxx_rybh+"*"+this.emp.gpydl+"*"+this.emp.hjydl+"*"+this.emp.hjlx_bh+"*"+this.emp.ytydl+"*"+this.emp.ytlx_bh+"*"+this.emp.qtydl+"*"+this.emp.agiydl+"*"+this.emp.zyxx_time1+"*"+this.emp.zyxx_time2+"*"+this.emp.zyxx_fw+"*"+this.emp.zyxx_yj+"*"+this.emp.zyxx_tq1+"*"+this.emp.zyxx_tq2+"*"+this.emp.zy_mj+"*"+this.emp.zy_xg+"*"+this.emp.zyxx_rybh+"*"+this.emp.tb_name+"*"+this.emp.tb_dwbh+"*"+this.emp.tb_date+"*"+this.emp.comment+"*"+this.emp.if_ydzy+"*"+this.emp.if_check+"*"+this.emp.if_upload)*/
            console.log(this.emp);
            if (this.emp.id) {
              //更新
              this.tableLoading = true;
              this.putRequest("/worklist/basic/emp", this.emp).then(resp=> {
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
              this.postRequest("/worklist/basic/emp", this.emp).then(resp=> {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
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
            /*_this.emp.workID = data.workID;*/
          }
        })
      },
      showEditEmpView(row){
        console.log(row)
        this.dialogTitle = "编辑员工";
        this.emp = row;
        /*this.emp.birthday = this.formatDate(row.birthday);
        this.emp.conversionTime = this.formatDate(row.conversionTime);
        this.emp.beginContract = this.formatDate(row.beginContract);
        this.emp.endContract = this.formatDate(row.endContract);
        this.emp.beginDate = this.formatDate(row.beginDate);
        this.emp.nationId = row.nation.id;
        this.emp.politicId = row.politicsStatus.id;
        this.emp.departmentId = row.department.id;
        this.emp.departmentName = row.department.name;
        this.emp.jobLevelId = row.jobLevel.id;
        this.emp.posId = row.position.id;*/
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
        this.dialogTitle = "添加工作记录";
        this.dialogVisible = true;
        var _this = this;
        this.getRequest("/worklist/basic/maxWorkID").then(resp=> {
          if (resp && resp.status == 200) {
            _this.emp.zyxx_id = resp.data;
          }
        })
      },
      emptyEmpData(){
        this.emp = {
          id:'',
          zyxx_id: '',
          zyd_bh: '',
          zyxx_jd: '',
          zyxx_wd: '',
          zyxx_date: '',
          pdlx_bh: '',
          zyxx_zylx: '',
          gpydl: '',
          hjydl: '',
          hjlx_bh: '',
          ytydl: '',
          ytlx_bh: '',
          qtydl: '',
          agiydl: '',
          zyxx_time1: '',
          zyxx_time2: '',
          zyxx_fw: '',
          zyxx_yj: '',
          zyxx_tq1: '',
          zyxx_tq2: '',
          zy_mj: '',
          zy_xg: '',
          zyxx_rybh: '',
          tb_name: '',
          tb_dwbh: '',
          tb_date:'',
          comment:'',
          if_ydzy:'',
          if_check:'',
          if_upload:'',
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
