package com.dj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fzq15 on 2019/9/1.
 */

@Controller
public class WelcomeController {

    @ResponseBody
    @RequestMapping("/check")
    public Map<String,String> welcome(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("status","ok");
        return map;
    }

    @RequestMapping("/toWelcome")
    public String toWelcome(){
        return "welcome";
    }
}
