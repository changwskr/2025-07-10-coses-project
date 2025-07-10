package com.chb.coses.framework.transfer;

import java.util.List;

/**
 * Page DTO for pagination in the EPlaton Framework
 */
public class PageDTO<T> extends DTO {

    private List<T> data;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    public PageDTO() {
        super();
    }

    public PageDTO(List<T> data, int pageNumber, int pageSize, long totalElements) {
        super();
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
        this.hasNext = pageNumber < totalPages;
        this.hasPrevious = pageNumber > 1;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    /**
     * Get the start index for this page
     * 
     * @return start index
     */
    public int getStartIndex() {
        return (pageNumber - 1) * pageSize;
    }

    /**
     * Get the end index for this page
     * 
     * @return end index
     */
    public int getEndIndex() {
        return Math.min(pageNumber * pageSize, (int) totalElements);
    }
}