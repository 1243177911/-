package com.online.sso.controller;

import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.ExceptionUtil;
import com.online.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author iu
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletResponse response, HttpServletRequest request){
        try {
            TaotaoResult result = loginService.login(username, password, request, response);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }

    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public Object get(@PathVariable String token,String callback){
        try {
            TaotaoResult taotaoResult = loginService.getUserByToken(token);
            //支持jsonp
            if (!StringUtils.isEmpty(callback)) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }

            return taotaoResult;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable String token,String callback,HttpServletRequest request,HttpServletResponse response){
        try{
            TaotaoResult taotaoResult = loginService.logout(token,response,request);
            if(!StringUtils.isEmpty(callback)){
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            return taotaoResult;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }
}
