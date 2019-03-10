package com.online.controller;

import com.online.service.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WxController {

    @Autowired
    private WxService wxService;

    @RequestMapping("/wx")
    @ResponseBody
    public String check(@RequestParam("signature") String signature,
                        @RequestParam("timestamp") String timestamp,
                        @RequestParam("nonce") String nonce,
                        @RequestParam("echostr") String echostr){

        return wxService.check(signature, timestamp, nonce, echostr);
    }
}
