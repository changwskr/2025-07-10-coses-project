package com.skcc.oversea.framework.transfer;

import java.util.ArrayList;
import java.util.List;

/**
 * List DTO for handling collections
 * Replaces com.chb.coses.framework.transfer.ListDTO
 */
public class ListDTO<T> extends DTO {

    private static final long serialVersionUID = 1L;

    private List<T> list;
    private int totalCount;
    private int pageSize;
    private int currentPage;

    /**
     * Default constructor
     */
    public ListDTO() {
        this.list = new ArrayList<>();
    }

    /**
     * Constructor with list
     */
    public ListDTO(List<T> list) {
        this.list = list != null ? list : new ArrayList<>();
        this.totalCount = this.list.size();
    }

    /**
     * Get the list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * Set the list
     */
    public void setList(List<T> list) {
        this.list = list != null ? list : new ArrayList<>();
        this.totalCount = this.list.size();
    }

    /**
     * Add item to list
     */
    public void add(T item) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.list.add(item);
        this.totalCount = this.list.size();
    }

    /**
     * Remove item from list
     */
    public boolean remove(T item) {
        if (this.list != null) {
            boolean removed = this.list.remove(item);
            this.totalCount = this.list.size();
            return removed;
        }
        return false;
    }

    /**
     * Get total count
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Set total count
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * Get page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Set page size
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Get current page
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Set current page
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Get size of list
     */
    public int size() {
        return list != null ? list.size() : 0;
    }

    /**
     * Check if list is empty
     */
    public boolean isEmpty() {
        return list == null || list.isEmpty();
    }

    /**
     * Clear the list
     */
    public void clear() {
        if (list != null) {
            list.clear();
            totalCount = 0;
        }
    }

    @Override
    public DTO clone() {
        ListDTO<T> cloned = new ListDTO<>();
        cloned.setList(new ArrayList<>(this.list));
        cloned.setTotalCount(this.totalCount);
        cloned.setPageSize(this.pageSize);
        cloned.setCurrentPage(this.currentPage);
        return cloned;
    }

    @Override
    public String toString() {
        return "ListDTO{" +
                "list=" + list +
                ", totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        ListDTO<?> listDTO = (ListDTO<?>) obj;

        if (totalCount != listDTO.totalCount)
            return false;
        if (pageSize != listDTO.pageSize)
            return false;
        if (currentPage != listDTO.currentPage)
            return false;
        return list != null ? list.equals(listDTO.list) : listDTO.list == null;
    }

    @Override
    public int hashCode() {
        int result = list != null ? list.hashCode() : 0;
        result = 31 * result + totalCount;
        result = 31 * result + pageSize;
        result = 31 * result + currentPage;
        return result;
    }
}