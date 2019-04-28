package org.sang.controller.worklist;

import org.sang.bean.WorkList;
import org.sang.bean.Test;
import org.sang.bean.Employee;
import org.sang.bean.Position;
import org.sang.bean.RespBean;
import org.sang.common.EmailRunnable;
import org.sang.common.poi.PoiUtils;
import org.sang.service.DepartmentService;
import org.sang.service.EmpService;
import org.sang.service.WorService;
import org.sang.service.JobLevelService;
import org.sang.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by sang on 2018/1/12.
 */
@RestController
@RequestMapping("/worklist/basic")
public class WorkListController {
    @Autowired
    WorService worklistService;
    @Autowired
    EmpService empService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    PositionService positionService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    ExecutorService executorService;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    JavaMailSender javaMailSender;

    @RequestMapping(value = "/basicdata", method = RequestMethod.GET)
    public Map<String, Object> getAllNations() {
        Map<String, Object> map = new HashMap<>();
        map.put("nations", empService.getAllNations());
        map.put("politics", empService.getAllPolitics());
        map.put("deps", departmentService.getDepByPid(-1L));
        map.put("positions", positionService.getAllPos());
        map.put("joblevels", jobLevelService.getAllJobLevels());
        map.put("workID", String.format("%08d", empService.getMaxWorkID() + 1));
        return map;
    }

    @RequestMapping("/maxWorkID")
    public int maxWorkID() {
        return worklistService.getMaxWorkID() + 1;
    }
//添加

    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    public RespBean addWor(WorkList worklist) {
        System.out.println("&&&&&&&&&&&&&&&&&&&&"+worklist.getZyd_bh());
        if (worklistService.addWor(worklist) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @RequestMapping(value = "/emp1", method = RequestMethod.POST)
    public RespBean addWor(Test a) {
        System.out.println("&&&&&&&&&&&&&&&&&&&&"+a.getTest());
        return RespBean.error("添加!");
    }
//条件查找
    @RequestMapping(value = "/emp", method = RequestMethod.PUT)
    public RespBean updateWor(WorkList worklist) {
        System.out.println("&&&&&&&&&&&&&&&&&&&&"+worklist.getZyd_bh());
        if (worklistService.updateWor(worklist) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
//删除
    @RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteWorById(@PathVariable String ids) {
        if (worklistService.deleteWorById(ids)) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
//查找
    @RequestMapping(value = "/emp", method = RequestMethod.GET)
    public Map<String, Object> getWorklistByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String keywords, String zydbh, String rybh,
             String beginDateScope) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("*****************************************************1"+page+" "+size+" "+keywords+" "+zydbh+" "+rybh+" "+beginDateScope);
        List<WorkList> worklistByPage = worklistService.getWorklistByPage(page, size,
                keywords, zydbh,rybh, beginDateScope);
        Long count = worklistService.getCountByKeywords(keywords, zydbh,
                rybh,beginDateScope);
        map.put("emps", worklistByPage);
        map.put("count", count);
        System.out.println("*****************************************************4");
        return map;
    }

    @RequestMapping(value = "/exportEmp", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportEmp() {
        return PoiUtils.exportEmp2Excel(empService.getAllEmployees());
    }

    @RequestMapping(value = "/importEmp", method = RequestMethod.POST)
    public RespBean importEmp(MultipartFile file) {
        List<Employee> emps = PoiUtils.importEmp2List(file,
                empService.getAllNations(), empService.getAllPolitics(),
                departmentService.getAllDeps(), positionService.getAllPos(),
                jobLevelService.getAllJobLevels());
        if (empService.addEmps(emps) == emps.size()) {
            return RespBean.ok("导入成功!");
        }
        return RespBean.error("导入失败!");
    }
}
