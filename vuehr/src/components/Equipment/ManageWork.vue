<template>
	<div style="margin-left: 100px;margin-top:50px;">
		<el-row>
			<el-col :span="10">
				<el-card class="box-card">
					<div slot="header" class="clearfix">
						<span>维修记录</span>
						<el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
					</div>
					<div v-for="o in record1" :key="o" class="text item">
						{{'设备编号：' +o.sb_BH+'&nbsp&nbsp&nbsp&nbsp&nbsp维修信息：'+o.information+'----------维修人：'+o.wx_RY+'&nbsp&nbsp&nbsp'}}{{o.wx_DATE|formatDate}}
					</div>
				</el-card>
			</el-col>
			<el-col :span="12">
				<el-card class="box-card">
					<div slot="header" class="clearfix">
						<span>操作记录</span>
						<el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
					</div>
					<div v-for="o in record2" :key="o" class="text item">
						{{'设备编号：' +o.sb_BH+'&nbsp&nbsp&nbsp&nbsp&nbsp操作信息：'+o.information+'----------操作人：'+o.operator+'&nbsp&nbsp&nbsp'}}{{o.opera_time|formatDate}}
					</div>
				</el-card>
			</el-col>
		</el-row>
	</div>

</template>
<script>
	export default{
		data(){
			return{
				record1:[],
				record2:[],
			}
		},
		mounted: function () {
	      this.initData();
	     
   		 },
   		 methods: {
	   		initData(){
	   			var _this = this;
	        	this.getRequest("/equipment/basic/recorddata").then(resp=> {
	         	 if (resp && resp.status == 200) {
	            	var data = resp.data;
	            
	            	_this.record1 = data.wxrecords;
	            	_this.record2 = data.czrecords;
	            	console.log(_this.record);
	           		 
	                  
	          }
	        })
	   		}
	   	}
	}
	
</script>

<style>
.text {
	font-size: 14px;
}

.item {
	margin-bottom: 18px;
	float: left;
}

.clearfix:before,
.clearfix:after {
	display: table;
	content: "";
}
.clearfix:after {
	clear: both
}

.box-card {
	width: 650px;
	

}
</style>