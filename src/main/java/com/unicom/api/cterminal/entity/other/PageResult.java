package com.unicom.api.cterminal.entity.other;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据
 * @param <T>
 */
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer pageNum;
    private Long total;
    private List<T> rows;

    public Integer getPageNo() {
        return pageNum;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNum = pageNo;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public PageResult(Integer pageNum, Long total, List<T> rows) {
        this.pageNum = pageNum;
        this.total = total;
        this.rows = rows;
    }
}
