package com.yc.taotao.portal.pojo;

import com.yc.taotao.pojo.TbItem;

/**
 * Created by YcDr on 2017/3/18.
 */
public class PortalItem extends TbItem {
    public String[] getImages(){
        String images=this.getImage();
        if (images!=null&&!images.equals("")){
            String[] imgs=images.split(",");
            return imgs;
        }
        return null;
    }
}
