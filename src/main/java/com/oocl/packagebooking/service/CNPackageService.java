package com.oocl.packagebooking.service;


import com.oocl.packagebooking.entity.CNPackage;
import com.oocl.packagebooking.repository.CNPackageRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CNPackageService {

    @Autowired
    CNPackageRepositroy CNPackageRepositroy;

    public List<CNPackage> findPackage(String status) {
        List<CNPackage> cnPackageList = CNPackageRepositroy.findAll();

        if(status.equals("ALL") ){
            return cnPackageList;
        }else if(status.equals("已预约")){
            return cnPackageList.stream().filter(item -> item.getStatus().equals("已预约")).collect(Collectors.toList());
        }else if(status.equals("已取件")){
            return cnPackageList.stream().filter(item -> item.getStatus().equals("已取件")).collect(Collectors.toList());
        }else if(status.equals("未取件")){
            return cnPackageList.stream().filter(item -> item.getStatus().equals("未取件")).collect(Collectors.toList());
        }else {
            return null;
        }
    }

    public void addPackage(CNPackage CNPackage1) {
        CNPackage1.setStatus("未取件");
        CNPackageRepositroy.save(CNPackage1);
    }

    public void appointment(int id, Date pickUpTime) {
        CNPackage cnPackage = CNPackageRepositroy.findById(id).get();
        if(!cnPackage.getStatus().equals("已预约")){
            cnPackage.setPickUpTime(new Date());
            cnPackage.setStatus("已预约");
            cnPackage.setAppotinmengTime(pickUpTime);
            CNPackageRepositroy.saveAndFlush(cnPackage);
        }
    }

    public void pickUp(int id) {
        CNPackage cnPackage = CNPackageRepositroy.findById(id).get();
        if(!cnPackage.getStatus().equals("已取件")){
            cnPackage.setPickUpTime(new Date());
            cnPackage.setStatus("已取件");
            CNPackageRepositroy.saveAndFlush(cnPackage);
        }
    }
}
