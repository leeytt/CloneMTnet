package com.leeyunt.clonemtnet.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 分页插件
 * </p>
 *
 * @author leeyunt
 * @since 2020-03-19
 *
 */
@Data
public class Page implements Serializable {
    private static final long serialVersionUID = 1L;

    private int pageNow; // 当前页(页码)
    private int pageSize; // 每页显示条数(分页大小)
    private long totalCount; // 总的记录条数
    private long totalPageCount; // 总的页数
    private int startPos; // 开始位置，从0开始
    private boolean hasFirst;// 是否有首页
    private boolean hasPre;// 是否有前一页
    private boolean hasNext;// 是否有下一页
    private boolean hasLast;// 是否有最后一页

    /**
     * 通过构造函数 传入 总记录数（totalCount） 和 当前页（pageNow）
     *
     * @param totalCount
     * @param pageNow
     */
    public Page(long totalCount, int pageNow) {
        this.totalCount = totalCount;
        this.pageNow = pageNow;
    }

    /**
     * 取得总的页数，总的页数（totalPageCount）=总记录数（totalCount）/每页显示条数（pageSize）
     *
     * @return totalPageCount
     */
    public long getTotalPageCount() {
        totalPageCount = getTotalCount() / getPageSize();
        return (totalCount % pageSize == 0) ? totalPageCount : totalPageCount + 1;
    }

    /**
     * 取得选择记录的初始位置
     *
     * @return startPos
     */
    public int getStartPos() {
        return (pageNow - 1) * pageSize;
    }

    /**
     * 是否是第一页
     *
     * @return boolean
     */
    private boolean isHasFirst() {
        return pageNow != 1;
    }

    /**
     * 是否有前一页
     *
     * @return boolean
     */
    public boolean isHasPre() {
        // 如果有首页就有前一页，因为有首页就不是第一页
        return isHasFirst();
    }

    /**
     * 是否有下一页
     *
     * @return boolean
     */
    public boolean isHasNext() {
        // 如果有尾页就有下一页，因为有尾页表明不是最后一页
        return isHasLast();
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    /**
     * 是否有尾页
     *
     * @return boolean
     */
    private boolean isHasLast() {
        // 如果不是最后一页就有尾页
        return pageNow != getTotalPageCount();
    }

}
