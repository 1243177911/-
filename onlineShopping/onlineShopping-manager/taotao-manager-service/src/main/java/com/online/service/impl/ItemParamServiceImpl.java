package com.online.service.impl;

import com.online.common.pojo.EasyUIDataGridResult;
import com.online.common.pojo.TaotaoResult;
import com.online.mapper.TbItemMapper;
import com.online.mapper.TbItemParamMapper;
import com.online.pojo.TbItemParam;
import com.online.pojo.TbItemParamExample;
import com.online.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper itemParamMapper;
    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public EasyUIDataGridResult getItemParam(Integer page,Integer rows) {

        Map<String,Object> data = new HashMap<>();
        if(page==1){
            data.put("page",0);
            data.put("rows",rows);
        }else {
            data.put("page",page*rows);
            data.put("rows",rows);
        }
        List<TbItemParam> itemParam = itemParamMapper.getItemParam(data);
        Long total = itemParamMapper.getTotal();
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setRows(itemParam);
        easyUIDataGridResult.setTotal(total);
        return easyUIDataGridResult;
    }

    @Override
    public TaotaoResult getItemParamByCid(Long cid) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(example);
        if(itemParams != null && itemParams.size()>0){
            return TaotaoResult.ok(itemParams.get(0));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertItemParam(Long cid, String paramData) {
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }
}
