<template>
  <div>
  <baidu-map class="map" center="乌鲁木齐" :scroll-wheel-zoom="true" :zoom="5">
  	<bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']" anchor="BMAP_ANCHOR_TOP_LEFT"></bm-map-type>
  	<bm-scale anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-scale>
  	<bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
  	<bm-city-list anchor="BMAP_ANCHOR_BOTTOM_RIGHT" ></bm-city-list>
  	<bm-boundary name="新疆" :strokeWeight="2" strokeColor="blue"></bm-boundary>
    <div v-for="tl in mapData" :key="tl.id">
  	<bm-marker  :position="{lng: tl.lng, lat: tl.lat}" :dragging="true" v-if="tl.enabled==1"  animation="BMAP_ANIMATION_BOUNCE" @click="infoWindowOpen(tl)" >  
      <bm-label :content="tl.name" :labelStyle="{color: 'red', fontSize : '24px'}" :offset="{width: -35, height: 30}"/>
   </bm-marker>
  </div>
  <div v-for="tl in mapData" :key="tl.id">
   <bm-marker    :position="{lng: tl.lng, lat: tl.lat}" :dragging="true" v-if="tl.enabled==0" @click="infoWindowOpen" >    
      <bm-label :content="tl.name" :labelStyle="{color: 'red', fontSize : '24px'}" :offset="{width: -35, height: 30}"/>
   </bm-marker> 
    
     </div>
  </baidu-map>
</div>

</template>
<style>
.map {

  width: 100%;
  height: 700px;
}
  
</style>

<script>
export default {
  mounted: function () {
      this.loadData();
    },
  data () {
    return {
      show: false,
      mapData: [],
      name: '',
      lng: '',
      lat: ''
     
    }
  },
  methods: {
    infoWindowClose () {
      this.show = false
      this.name = '';
      this.lng = '';
      this.lat = '';
    },
    infoWindowOpen (show) {
      this.show = true
      this.name = show.name;
      this.lng = show.lng;
      this.lat = show.lat;

      
    },
    loadData(){
        var _this = this;
        this.loading = true;
        this.getRequest("/system/basic/joblevels").then(resp=> {
        _this.loading = false;
        if (resp && resp.status == 200) {
            _this.mapData = resp.data;
            

          }
        })
    }
  }
}
</script>