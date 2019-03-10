package com.online.pojo;

import java.util.Date;

public class TbItemParamItem {
    private Long id;

    private Long item_id;

    private Date created;

    private Date updated;

    private String param_data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return item_id;
    }

    public void setItemId(Long itemId) {
        this.item_id = itemId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getParamData() {
        return param_data;
    }

    public void setParamData(String paramData) {
        this.param_data = paramData == null ? null : paramData.trim();
    }
}