package org.sang.service;
import org.sang.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sang.mapper.EquipmentMapper;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;


@Service
@Transactional
public class EquipmentService {
    @Autowired
    EquipmentMapper equipmentMapper;
    SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");
    public List<Equipment> getEquipmentByPage(Integer page, Integer size, String keywords) {
        int start = (page - 1) * size;


        return equipmentMapper.getEquipmentByPage(start, size,keywords);
    }
    public Long getCountByKeywords(String keywords){
        return equipmentMapper.getCountByKeywords(keywords);
    }
    public List<Position> getAllTypes() {
        return equipmentMapper.getAllTypes();
    }

    public int updateEquipment(Equipment equipment) {

        return equipmentMapper.updateEquipment(equipment);
    }
    public int addDy(Yt yt) {

        return equipmentMapper.addDy(yt);
    }
    public int updateDy(Yt yt) {

        return equipmentMapper.updateDy(yt);
    }

    public int addEquipment(Equipment equipment) {

        return equipmentMapper.addEquipment(equipment);
    }

    public boolean deleteEquipmentById(String ids) {
        String[] split = ids.split(",");
        return equipmentMapper.deleteEquipmentById(split) == split.length;
    }
    public boolean deleteDyById(String ids) {
        String[] split = ids.split(",");
        return equipmentMapper.deleteDyById(split) == split.length;
    }

    public List<Yt> getYtBypage(Integer page, Integer size, String keywords){
        int start = (page - 1) * size;
        return equipmentMapper.getYtByPage(start,size,keywords);

    }
    public Long getCount1ByKeywords(String keywords){
        return equipmentMapper.getCount1ByKeywords(keywords);
    }

    public boolean updateDynums(Yt yt){


        return equipmentMapper.updateDynums(yt);
    }
    public List<Equipment> getAllEquipment() {
        return equipmentMapper.getEquipmentByPage(null, null, "");
    }
    public Long[] getTypecount(Position position){
        return equipmentMapper.getTypecount(position);
    }
    public List<Wxrcord> getrecorddata(){
        return equipmentMapper.getrecorddata();
    }
    public List<Czrecord> getrecorddataSSS(){
        return equipmentMapper.getrecorddataSSS();
    }

}
