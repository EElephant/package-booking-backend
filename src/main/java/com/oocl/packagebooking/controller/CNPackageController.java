package com.oocl.packagebooking.controller;

import com.oocl.packagebooking.entity.CNPackage;
import com.oocl.packagebooking.service.CNPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/package")
public class CNPackageController {

    @Autowired
    CNPackageService CNPackageService;

    @GetMapping
    public List<CNPackage> getPackage(@RequestParam(value = "status",defaultValue = "ALL") String status){
        List<CNPackage> cnPackages = CNPackageService.findPackage(status);
        return cnPackages;
    }

    @PostMapping
    public void addPackage(@RequestBody CNPackage CNPackage1){
        CNPackageService.addPackage(CNPackage1);
    }

    @PutMapping("/appointment")
    public void appointment(@RequestParam int id, @RequestParam String pickUpTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date =  format.parse(pickUpTime);
            CNPackageService.appointment(id,date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/pickUp")
    public void pickUp(@RequestParam int id){
        CNPackageService.pickUp(id);
    }
}
