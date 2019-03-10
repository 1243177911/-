package com.online.sso.controller;

import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.ExceptionUtil;
import com.online.pojo.TbUser;
import com.online.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author iu
 */
@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object ckeckData(@PathVariable String param, @PathVariable Integer type, String callback){
        try{
        TaotaoResult taotaoResult = registerService.checkData(param,type);
        if(!StringUtils.isEmpty(callback)){
            //请求为jsonp调用
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return taotaoResult;
    }catch(Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user){
        try {
            TaotaoResult register = registerService.register(user);
            return register;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(400,ExceptionUtil.getStackTrace(e));
        }

    }
}
