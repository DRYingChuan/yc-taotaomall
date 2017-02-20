package com.yc.common.pojo;

import java.util.List;

/**
 * Created by YcDr on 2017/2/19.
 */
public class EasyuiDataGridResult {
    private long total;
    private List<?> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
