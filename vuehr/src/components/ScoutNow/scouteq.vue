<template>
	<div>
		
  <el-row style="margin-top: 20px;">
  	<el-col :span="4">
  <el-card class="box-card">
  <div slot="header" class="clearfix">
    <span>总设备数</span>
  </div>
  <div class="text item">
    <span>{{totalCount}}</span>
  </div>

</el-card>
</el-col >
<el-col :span="4" style="margin-left: 80px;">
  <el-card class="box-card">
  <div slot="header" class="clearfix">
    <span>工作中</span>
  
  </div>
  <div  class="text item">
    <span>{{totalCountWork}}</span>
  </div>

</el-card>
</el-col>
<el-col :span="4" style="margin-left: 80px;">
  <el-card class="box-card">
  <div slot="header" class="clearfix">
    <span>维修中</span>

  </div>
  <div class="text item">
    <span>{{totalCountRepair}}</span>
  </div>

</el-card>
</el-col>

<el-col :span="4" style="margin-left: 80px;">
  <el-card class="box-card">
  <div slot="header" class="clearfix">
    <span>闲置中</span>

  </div>
  <div class="text item">
    <span>{{totalCountUnused}}</span>
  </div>

</el-card>
</el-col>
<el-col :span="4">
	<div ><img src="../img/ta.jpg" style="margin-left: 80px;" width="200px"></div>
</el-col>
</el-row>
<el-progress ></el-progress>
<el-row>
	</el-row>
	<el-row style="margin-top:30px">
		<el-col :span="2.5" v-for="o in equipments" :key="o.id" :offset="index" style="margin-top:20px; ">
			<el-card class="box-card1" style="margin-left: 20px;" v-if="o.sb_ZT=='repair'">
		  <div slot="header" class="clearfix">
		    <span>{{o.sb_BH}}</span>

		  </div>
		  <div class="text item">
		    <img src="../img/weixiu.png">
		  </div>
			</el-card>
			<el-card class="box-card2" style="margin-left: 20px;" v-if="o.sb_ZT=='work'">
		  <div slot="header" class="clearfix">
		    <span>{{o.sb_BH}}</span>

		  </div>		  
		  <div v-if="o.sb_ZT=='work'"class="text item">
		    <img src="../img/work.png">
		  </div>	  

			</el-card>
			<el-card class="box-card3" style="margin-left: 20px;" v-if="o.sb_ZT=='unused'">
		  <div slot="header" class="clearfix">
		    <span>{{o.sb_BH}}</span>

		  </div>
		  <div v-if="o.sb_ZT=='unused'"class="text item">
		    <img src="../img/unused.png">
		  </div>

			</el-card>
		</el-col>
	</el-row>

</div>
</template>
<script >
	export default{
		data(){
			return{
				equipments:[],
				totalCount:'',
				totalCountWork:'',
				totalCountRepair:'',
				totalCountUnused:'',

			}
		},

		mounted:function(){
			this.loadEqzt();


		},
		methods:{

			loadEqzt(){
				var _this = this;
				this.getRequest("/equipment/basic/equipmentZt").then(resp=> {
					          if (resp && resp.status == 200) {
					            var data = resp.data;
					            _this.equipments = data.equipments;
					            
					            _this.totalCount = data.count;
					            _this.totalCountWork = data.count1;
					            _this.totalCountRepair = data.count2;
					            _this.totalCountUnused = data.count3;
					            console.log(_this.equipments);
					            console.log(_this.totalCount);
					            console.log(_this.totalCountWork);
					            console.log(_this.totalCountRepair);
					            console.log(_this.totalCountUnused);
					//            _this.emptyEmpData();
					          }
					        })
			}

		}


	}

</script>
<style type="text/css">
	.box-card{
		background: #eef9fb;
	}
	.box-card1{
		background:#eafbe8;
	}
	.box-card2{
		background:#f8e6e6;
	}

</style>
