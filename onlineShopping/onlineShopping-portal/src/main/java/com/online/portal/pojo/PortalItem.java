package com.online.portal.pojo;

import com.online.pojo.TbItem;

public class PortalItem extends TbItem {

    public String[] getImages(){
        String images = this.getImage();
        if(images!=null && !images.equals("")){
            String[] split = images.split(",");
            return split;
        }
        return null;
    }
}
