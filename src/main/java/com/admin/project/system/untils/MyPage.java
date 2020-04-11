package com.admin.project.system.untils;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;
@Data
public class MyPage<T> {
    private Long total = 0L;
    private int pageSize = 30;
    private int currentPage = 1;
    private List<T> data;

    public MyPage(List<T> list) {
        PageInfo pageInfo = new PageInfo<>(list);
        this.total = pageInfo.getTotal();
        this.pageSize = pageInfo.getPageSize();
        this.currentPage = pageInfo.getPageNum();
        this.data = list;
    }

}
