package com.online.service;

/**
 * @author iu
 */
public interface WxService {

    String check(String signature,String timestamp,String nonce,String echostr);
}
