<template>
  <div>
    <div style="text-align: left">
      <el-input
        :placeholder="state=='position'?'添加装备...':'添加作业区...'"
        size="mini"
        @keyup.enter.native="addPosition"
        style="width: 300px;"
        prefix-icon="el-icon-plus"
        v-model="positionName">
      </el-input>
      <el-select v-model="titleLevel" v-if="state=='jobtitle'" size="mini" placeholder="地区">
        <el-option
          v-for="tl in titleLevels"
          :key="tl"
          :label="tl"
          :value="tl">
        </el-option>
      </el-select>
      <el-input
        placeholder="添加经度"
        size="mini"
        v-if="state=='jobtitle'"
        @keyup.enter.native="addPosition"
        style="width: 100px;"
        prefix-icon="el-icon-plus"
        v-model="addlng">
      </el-input>
      <el-input
        placeholder="添加纬度"
        size="mini"
        v-if="state=='jobtitle'"
        @keyup.enter.native="addPosition"
        style="width: 100px;"
        prefix-icon="el-icon-plus"
        v-model="addlat">
      </el-input>
      <el-button type="primary" icon="el-icon-plus" size="mini" @click="addPosition">添加</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table
        v-loading="loading"
        :data="posData"
        size="mini"
        stripe
        border
        @selection-change="handleSelectionChange"
        style="width: 100%">
        <el-table-column
          type="selection"
          width="55"
          align="left">
        </el-table-column>
        <el-table-column
          prop="id"
          label="编号"
          width="80"
          align="left">
        </el-table-column>
        <el-table-column
          prop="name"
          :label="state=='position'?'装备名称':'作业点名称'"
          width="180"
          align="left">
        </el-table-column>
        <el-table-column
          prop="titleLevel"
          label="地区"
          width="180"
          align="left" v-if="state=='jobtitle'">
        </el-table-column>
        <el-table-column
          prop="lng"
          label="lng"
          width="90"
          align="left" v-if="state=='jobtitle'">
        </el-table-column>
        <el-table-column
          prop="lat"
          label="lat"
          width="90"
          align="left" v-if="state=='jobtitle'">
        </el-table-column>
        <el-table-column
          prop="enabled"
          label="enabled"
          width="90"
          align="left" v-if="state=='jobtitle'">
        </el-table-column>
        <el-table-column
          width="180"
          label="创建时间"
          align="left">
          <template slot-scope="scope">{{ scope.row.createDate | formatDate}}</template>
        </el-table-column>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="text-align: left;margin-top: 10px">
      <el-button type="danger" size="mini" v-if="posData.length>0" :disabled="multipleSelection.length==0"
                 @click="deleteMany">批量删除
      </el-button>
    </div>
    <div style="text-align: left">
      <el-dialog
        :title="state=='position'?'编辑职位名称':'编辑作业区'"
        :visible.sync="dialogVisible"
        width="25%">
        <el-input v-model="updatePosName" size="mini" placeholder="请输入新的作业区名称..."></el-input>
        <el-select v-model="updateTitleLevel" v-if="state=='jobtitle'" style="margin-top: 10px" size="mini"
                   placeholder="作业区所在地区">
          <el-option
            v-for="tl in titleLevels"
            :key="tl"
            :label="tl"
            :value="tl">
          </el-option>
        </el-select>
        <el-input v-model="updatelng" size="mini" placeholder="请输入新的经度..." v-if="state=='jobtitle'" style="margin-top: 10px"></el-input>
        <el-input v-model="updatelat" size="mini" placeholder="请输入新的纬度..." v-if="state=='jobtitle'" style="margin-top: 10px"></el-input>
        <el-select v-model="updateEnabled" v-if="state=='jobtitle'" style="margin-top: 10px" size="mini"
                   placeholder="运行状态">
          <el-option
            v-for="t2 in Enableds"
            :key="t2"
            :label="t2"
            :value="t2">
          </el-option>
        </el-select>
        <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" size="mini" @click="updatePosNameExec">确 定</el-button>
  </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  export default{
    mounted: function () {
      this.loadTableData();
    },
    methods: {
      updatePosNameExec(){
        if (!this.isNotNullORBlank(this.updatePosName)) {
          this.$message.warning(this.state == 'position' ? '职位名称不能为空!' : '作业区名称不能为空!');
          return;
        }
        this.loading = true;
        var _this = this;
        this.putRequest(this.state=='position'?"/system/basic/position":"/system/basic/joblevel", {name: this.updatePosName, id: this.updatePosId,titleLevel:this.updateTitleLevel,lng: this.updatelng,lat: this.updatelat,enabled :this.updateEnabled}).then(resp=> {
          _this.loading = false;
          if (resp && resp.status == 200) {
            this.dialogVisible = false;
            var data = resp.data;
            _
            _this.loadTableData();
          }
        })
      },
      deleteMany(){
        var _this = this;
        this.$confirm('删除' + this.multipleSelection.length + '条数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var multipleSelection = _this.multipleSelection;
          var ids = '';
          multipleSelection.forEach(row=> {
            ids = ids + row.id + ',';
          })
          _this.doDelete(ids);
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      addPosition(){
        if (!this.isNotNullORBlank(this.positionName)) {
          this.$message.warning(this.state == 'position' ? '职位名称不能为空!' : '作业区名称不能为空!');
          return;
        }
       
        if (this.state == 'jobtitle') {
          if (!this.isNotNullORBlank(this.titleLevel)) {
            this.$message.warning('请选择作业区地区!');
            return;
          }
           if (!this.isNotNullORBlank(this.addlng)) {
          this.$message.warning("经度不能为空");
          return;
          }
          if (!this.isNotNullORBlank(this.addlat)) {
          this.$message.warning("纬度不能为空");
          return;
          }
        }
        var _this = this;
        this.loading = true;
        this.postRequest(this.state == 'position' ? "/system/basic/position" : "/system/basic/joblevel", {
          name: this.positionName,
          titleLevel: this.titleLevel,
          lng: this.addlng,
          lat: this.addlat
        }).then(resp=> {
          _this.loading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _
            _this.loadTableData();
            _this.positionName = '';
            _this.titleLevel = '';
            _this.addlat = '';
            _this.addlng = '';
          }
        });
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleEdit(index, row){
        this.updatePosName = row.name;
        this.updatePosId = row.id;
        this.updateTitleLevel=row.titleLevel;
        this.updatelng = row.lng;
        this.updatelat = row.lat;
        this.updateEnabled = row.enabled;
        this.dialogVisible = true;
      },
      handleDelete(index, row){
        var _this = this;
        this.$confirm('删除[' + row.name + '], 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.doDelete(row.id);
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      doDelete(ids){
        var _this = this;
        _this.loading = true;
        var url = this.state == 'position' ? '/system/basic/position/' : '/system/basic/joblevel/';
        this.deleteRequest(url + ids).then(resp=> {
          _this.loading = false;
          if (resp && resp.status == 200) {
            _this.loadTableData();
          }
        })
      },
      loadTableData(){
        var _this = this;
        this.loading = true;
        this.getRequest(this.state == 'position' ? "/system/basic/positions" : "/system/basic/joblevels").then(resp=> {
          _this.loading = false;
          if (resp && resp.status == 200) {
            _this.posData = resp.data;
            console.log(_this.posData);
          }
        })
      }
    },
    data(){
      return {
        updatelng: '',
        updatelat: '',
        updateEnabled: '',
        positionName: '',
        addlat: '',
        addlng: '',
        updatePosName: '',
        updateTitleLevel: '',
        titleLevel: '',
        updatePosId: -1,
        loading: false,
        dialogVisible: false,
        multipleSelection: [],
        type: [],
        titleLevels: ['乌鲁木齐市', '哈密市', '吐鲁番市', '克拉玛依市'],
        Enableds: [0,1],
        nameLabelName: this.state == 'position' ? '职位名称' : '职称名称',
        posData: []
      }
    },
    props: ['state']
  }
</script>
