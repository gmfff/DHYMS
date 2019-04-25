package org.sang.controller.equipment;
import org.sang.bean.*;


import org.sang.common.EmailRunnable;
import org.sang.common.poi.PoiUtils;
import org.sang.service.DepartmentService;
import org.sang.service.EquipmentService;
import org.sang.service.JobLevelService;
import org.sang.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/equipment/basic")
public class equipmentBasicController {
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    PositionService positionService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    JobLevelService jobLevelService;
    @RequestMapping(value = "/equipment", method = RequestMethod.GET)     //获取设备
    public Map<String, Object> getEmployeeByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String keywords
            ) {
        Map<String, Object> map = new HashMap<>();
        List<Equipment> equipmentByPage = equipmentService.getEquipmentByPage(page, size,keywords);
        Long count = equipmentService.getCountByKeywords(keywords);
        map.put("equipments", equipmentByPage);
        map.put("count", count);
        System.out.println(count);
        return map;
    }
    @RequestMapping(value = "/Typecount", method = RequestMethod.GET)
    public Map<String, Object> getTypecount(Position position){
        Map<String, Object> map = new HashMap<>();
        Long[] a=equipmentService.getTypecount(position);
        return map;

    }
    @RequestMapping(value = "/recorddata", method = RequestMethod.GET)
    public Map<String,Object> getrecorddata(){
        Map<String, Object> map = new HashMap<>();
        List<Wxrcord> wxrecords = equipmentService.getrecorddata();
        List<Czrecord> czrecords = equipmentService.getrecorddataSSS();

        map.put("wxrecords",wxrecords);
        map.put("czrecords",czrecords);
        return map;
    }

    @RequestMapping(value = "/yt", method = RequestMethod.GET)  //获取烟条
    public Map<String,Object> getYtBypage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String keywords

    ){
        Map<String, Object> map = new HashMap<>();
        List<Yt> ytByPage = equipmentService.getYtBypage(page,size,keywords);
        Long count = equipmentService.getCount1ByKeywords(keywords);
        map.put("yts", ytByPage);
        map.put("count", count);
        System.out.println(count);
        return map;
    }

    @RequestMapping(value = "/basicdata", method = RequestMethod.GET)  //获取所有单位编号和设备型号
    public Map<String, Object> getAllNations() {
        Map<String, Object> map = new HashMap<>();
        map.put("Types", positionService.getAllPos());
        map.put("deps", departmentService.getAllDeps());

        return map;
    }
    @RequestMapping(value = "/basicDydata", method = RequestMethod.GET) //获取单位编号和作业区编号
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("deps", departmentService.getAllDeps());
        map.put("joblevels", jobLevelService.getAllJobLevels());
        return map;
    }

    @RequestMapping(value = "/equipment", method = RequestMethod.POST) //添加设备
    public RespBean addEquipment(Equipment equipment) {
        System.out.println("添加来了"+equipment);
        if (equipmentService.addEquipment(equipment) == 1) {
            System.out.println("添加成功");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }


    @RequestMapping(value = "/equipment", method = RequestMethod.PUT)//更新设备
    public RespBean updateEquipement(Equipment equipment) {
        if (equipmentService.updateEquipment(equipment) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @RequestMapping(value = "/yt", method = RequestMethod.POST) //添加烟条
    public RespBean addDy(Yt yt) {

        if (equipmentService.addDy(yt) == 1) {
            System.out.println("添加成功");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @RequestMapping(value = "/yt", method = RequestMethod.PUT)//更新弹药
    public RespBean updateDy(Yt yt) {
        if (equipmentService.updateDy(yt) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @RequestMapping(value = "/equipment/{ids}", method = RequestMethod.DELETE) //删除设备
    public RespBean deleteEquipmentById(@PathVariable String ids) {
        if (equipmentService.deleteEquipmentById(ids)) {

            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
    @RequestMapping(value = "/dy/{ids}", method = RequestMethod.DELETE) //删除设备
    public RespBean deleteDyById(@PathVariable String ids) {
        if (equipmentService.deleteDyById(ids)) {

            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @RequestMapping(value = "/addDynums", method = RequestMethod.PUT )//入库
    public RespBean updateEquipement(Yt yt){

        if(equipmentService.updateDynums(yt)){
            return  RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");

    }
    @RequestMapping(value = "/exportEq", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportEmp() {
        return PoiUtils.exportEquipmentExcel(equipmentService.getAllEquipment());
    }
}
