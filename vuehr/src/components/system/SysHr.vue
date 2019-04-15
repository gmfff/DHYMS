<template>
  <div style="margin-top: 10px" v-loading="fullloading">
    <div  v-show= "formVisible1" style="margin-bottom: 10px;display: flex;justify-content: center;align-items: center">
       <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddadmin" >
            添加员工
          </el-button>
      <el-input
        placeholder="默认展示部分用户，可以通过用户名搜索更多用户..."
        prefix-icon="el-icon-search"
        size="small"
        style="width: 400px;margin-right: 10px;margin-left: 60px;"
        v-model="keywords">
      </el-input>
      <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
    </div>
   
    <div v-show= "formVisible1" style="display: flex;justify-content: space-around;flex-wrap: wrap;text-align: left">
      <el-card style="width: 350px;margin-bottom: 20px" v-for="(item,index) in hrs" :key="item.id"
               v-loading="cardLoading[index]">
        <div slot="header" class="clearfix">
          <span>{{item.name}}</span>
          <el-button type="text"
                     style="color: #f6061b;margin: 0px;float: right; padding: 3px 0;width: 15px;height:15px"
                     icon="el-icon-delete" @click="deleteHr(item.id)"></el-button>
        </div>
        <div>
          <div style="width: 100%;display: flex;justify-content: center">
            <img :src="item.userface" alt="item.name" style="width: 70px;height: 70px;border-radius: 70px">
          </div>
          <div style="margin-top: 20px">
            <div><span class="user-info">用户名:{{item.name}}</span></div>
            <div><span class="user-info">手机号码:{{item.phone}}</span></div>
            <div><span class="user-info">电话号码:{{item.telephone}}</span></div>
            <div><span class="user-info">地址:{{item.address}}</span></div>
            <div class="user-info" style="display: flex;align-items: center;margin-bottom: 3px">
              用户状态:
              <el-switch
                style="display: inline;margin-left: 5px"
                v-model="item.enabled"
                active-color="#13ce66"
                inactive-color="#aaaaaa"
                active-text="启用"
                :key="item.id"
                @change="switchChange(item.enabled,item.id,index)"
                inactive-text="禁用">
              </el-switch>
            </div>
            <div class="user-info">
              用户角色:
              <el-tag
                v-for="role in item.roles"
                :key="role.id"
                type="success"
                size="mini"
                style="margin-right: 5px"
                :disable-transitions="false">{{role.nameZh}}
              </el-tag>
              <el-popover
                v-loading="eploading[index]"
                placement="right"
                title="角色列表"
                width="200"
                @hide="updateHrRoles(item.id,index)"
                :key="item.id"
                trigger="click">
                <el-select v-model="selRoles" multiple placeholder="请选择角色">
                  <el-option
                    v-for="ar in allRoles"
                    :key="ar.id"
                    :label="ar.nameZh"
                    :value="ar.id">
                  </el-option>
                </el-select>
                <el-button type="text" icon="el-icon-more" style="color: #09c0f6;padding-top: 0px" slot="reference"
                           @click="loadSelRoles(item.roles,index)" :disabled="moreBtnState"></el-button>
<!--                <i class="el-icon-more" style="color: #09c0f6;cursor: pointer" slot="reference"
                   @click="loadSelRoles(item.roles,index)" disabled="true"></i>-->
              </el-popover>
            </div>
            <div><span class="user-info">备注:{{item.remark}}</span></div>
          </div>
        </div>
      </el-card>

    </div>
    <div v-show="formVisible" style="width:50%;display: flex; margin: auto;">
     <el-form ref="regform" :model="regform"  :rules="rules"  label-width="80px"  style="margin: 0px;padding: 0px;"  >
      <div style="margin: left">
      <el-form-item label="用户名"  prop="username">
      <el-input v-model="regform.username"></el-input>
      </el-form-item>
      <el-form-item label="密碼" prop="password">
      <el-input type="password" v-model="regform.password"></el-input>
    </el-form-item>
      <el-form-item label="姓名" prop="name">
      <el-input  v-model="regform.name"></el-input>
    </el-form-item>
      <el-form-item label="电话" prop="phone">
      <el-input  v-model="regform.phone"></el-input>
    </el-form-item>
      <el-form-item label="单位编号" prop="telephone">
      <el-input  v-model="regform.telephone"></el-input>
    </el-form-item>
      <el-form-item label="地址" >
      <el-input  v-model="regform.address"></el-input>
    </el-form-item>
      <el-form-item label="头像">
      <el-upload
        class="avatar-uploader"
        action="https://jsonplaceholder.typicode.com/posts/"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
        <img v-if="regform.userface" :src="regform.userface" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      </el-form-item>
      <el-form-item>
      <el-button type="primary" @click="onSubmit('regform')">立即创建</el-button>
      <el-button @click="quxiao">取消</el-button>
      </el-form-item>
    </div>
    </el-form>
  </div>




    

  </div>
</template>
<script>
  export default{
    data(){
      return {
        regform: {
          username: [],
          password: [],
          name: [],
          phone: [],
          telephone: [],
          address: [],
          userface: [],

        },
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 7, message: '长度在 3 到 7 个字符', trigger: 'blur' }
            
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '请输入电话', trigger: 'blur' },
            
          ],
          telephone: [
            { required: true, message: '请输入电话', trigger: 'blur' },
           
          ]

        },
        keywords: '',
        fullloading: false,
        hrs: [],
        cardLoading: [],
        eploading: [],
        allRoles: [],
        moreBtnState:false,
        selRoles: [],
        selRolesBak: [],
        formVisible: false,
        formVisible1: true,
        
        }
      
    },
    mounted: function () {
      this.initCards();
      this.loadAllRoles();
    },
    methods: {
      searchClick(){
        this.initCards();
        this.loadAllRoles();
      },
       handleAvatarSuccess(res, file) {
        this.regform.userface = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
      emptyInfo(){
        this.regform = {

           username: [],
          password: [],
          name: [],
          phone: [],
          telephone: [],
          address: [],
          userface: [],

        }


      },
    
      onSubmit(formName){
        var _this = this;
        this.loading = true;
        this.$refs[formName].validate((valid) => {
          if (valid) {
                alert('submit');
                console.log("tijiao");
                this.postRequest('/system/hr/reg', {
                    username: this.regform.username,
                    password: this.regform.password,
                    name: this.regform.name,
                    phone: this.regform.phone,
                    address: this.regform.address,
                    telephone: this.regform.telephone,
                    userface: this.regform.userface

                  }).then(resp=> {
                    this.formVisible = false;
                    this.formVisible1 = true;
                    _this.emptyInfo();
                    console("ssss");
                  })
                }else{ 
          
            console.log('error submit!!');
            return false;
          }
        });
          
        
        console.log(this.regform.username);
        
      },
      quxiao(){
        this.formVisible = false;
        this.formVisible1 = true;

      },
      showAddadmin() {
        
        this.formVisible = true;
        this.formVisible1 = false;

      },
      updateHrRoles(hrId, index){
        this.moreBtnState = false;
        var _this = this;
        if (this.selRolesBak.length == this.selRoles.length) {
          for (var i = 0; i < this.selRoles.length; i++) {
            for (var j = 0; j < this.selRolesBak.length; j++) {
              if (this.selRoles[i] == this.selRolesBak[j]) {
                this.selRolesBak.splice(j, 1);
                break;
              }
            }
          }
          if (this.selRolesBak.length == 0) {
            return;
          }
        }
        this.eploading.splice(index, 1, true)
        this.putRequest("/system/hr/roles", {
          hrId: hrId,
          rids: this.selRoles
        }).then(resp=> {
          _this.eploading.splice(index, 1, false);
          if (resp && resp.status == 200) {
            var data = resp.data;
            _
            if (data.status == 'success') {
              _this.refreshHr(hrId, index);
            }
          }
        });
      },
      refreshHr(hrId, index){
        var _this = this;
        _this.cardLoading.splice(index, 1, true)
        this.putRequest("/system/hr/id/" + hrId).then(resp=> {
          _this.cardLoading.splice(index, 1, false)
          _this.hrs.splice(index, 1, resp.data);
           
        })
      },
      loadSelRoles(hrRoles, index){
        this.moreBtnState = true;
        this.selRoles = [];
        this.selRolesBak = [];
        hrRoles.forEach(role=> {
          this.selRoles.push(role.id)
          this.selRolesBak.push(role.id)
        })
      },
      loadAllRoles(){
        var _this = this;
        this.getRequest("/system/basic/roles").then(resp=> {
          _this.fullloading = false;
          if (resp && resp.status == 200) {
            _this.allRoles = resp.data;
          }
        })
      },
      switchChange(newValue, hrId, index){
        var _this = this;
        _this.cardLoading.splice(index, 1, true)
        this.putRequest("/system/hr/", {
          enabled: newValue,
          id: hrId
        }).then(resp=> {
          _this.cardLoading.splice(index, 1, false)
          if (resp && resp.status == 200) {
            var data = resp.data;
            _
            if (data.status == 'error') {
              _this.refreshHr(hrId, index);
            }
          } else {
            _this.refreshHr(hrId, index);
          }
        })
      },
      initCards(){
        this.fullloading = true;
        var _this = this;
        var searchWords;
        if (this.keywords === '') {
          searchWords = 'all';
        } else {
          searchWords = this.keywords;
        }
        this.getRequest("/system/hr/" + searchWords).then(resp=> {
          if (resp && resp.status == 200) {
            _this.hrs = resp.data;
            var length = resp.data.length;
            _this.cardLoading = Array.apply(null, Array(length)).map(function (item, i) {
              return false;
            });
            _this.eploading = Array.apply(null, Array(length)).map(function (item, i) {
              return false;
            });
          }
        })
      },
      deleteHr(hrId){
        var _this = this;
        this.fullloading = true;
        this.deleteRequest("/system/hr/" + hrId).then(resp=> {
          _this.fullloading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _
            if (data.status == 'success') {
              _this.initCards();
              _this.loadAllRoles();
            }
          }
        })
      }
    }
  }
</script>
<style>
  .user-info {
    font-size: 12px;
    color: #09c0f6;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
