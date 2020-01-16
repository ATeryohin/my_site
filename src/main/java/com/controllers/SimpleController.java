package com.controllers;

import com.entity.Driver;

import com.services.DriverService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class SimpleController {
    public String main_page(){
        return "index";
    }
}
