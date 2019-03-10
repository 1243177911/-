package com.online.service;

import com.online.common.pojo.EasyUIDataGridResult;
import com.online.common.pojo.TaotaoResult;

/**
 * @author iu
 */
public interface ItemParamService {

    EasyUIDataGridResult getItemParam(Integer page,Integer row);
    TaotaoResult getItemParamByCid(Long cid);
    TaotaoResult insertItemParam(Long cid,String paramData);

}
