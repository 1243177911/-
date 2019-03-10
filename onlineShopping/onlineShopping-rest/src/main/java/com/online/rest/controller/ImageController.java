package com.online.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;

/**
 * 获取本地图片
 * @author iu
 */
@Controller
public class ImageController {

    @RequestMapping("/images/")
    public String getImage(Model model) throws IOException {
        StringBuffer image = null;
        FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\iu\\Desktop\\taotaostore\\maven\\taotaoparent\\online-portal\\src\\main\\webapp\\images\\11.jpg"));
        int i = fileInputStream.available();
        byte[] buf = new byte[i];
        fileInputStream.read(buf);
        fileInputStream.close();
        model.addAttribute("image",buf.toString());
        return null;
    }
}
