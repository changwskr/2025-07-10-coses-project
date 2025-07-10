package com.chb.coses.framework.transfer;

import java.util.List;
import java.util.ArrayList;

/**
 * List DTO for handling collections in the EPlaton Framework
 */
public class ListDTO<T> extends DTO {

    private List<T> items;
    private int totalCount;
    private int pageSize;
    private int currentPage;

    public ListDTO() {
        super();
        this.items = new ArrayList<>();
    }

    public ListDTO(List<T> items) {
        super();
        this.items = items != null ? items : new ArrayList<>();
        this.totalCount = this.items.size();
    }

    public ListDTO(List<T> items, int totalCount) {
        super();
        this.items = items != null ? items : new ArrayList<>();
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items != null ? items : new ArrayList<>();
        this.totalCount = this.items.size();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Add item to the list
     * 
     * @param item item to add
     */
    public void addItem(T item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        totalCount = items.size();
    }

    /**
     * Remove item from the list
     * 
     * @param item item to remove
     * @return true if removed, false otherwise
     */
    public boolean removeItem(T item) {
        if (items != null) {
            boolean removed = items.remove(item);
            if (removed) {
                totalCount = items.size();
            }
            return removed;
        }
        return false;
    }

    /**
     * Get item at index
     * 
     * @param index index
     * @return item at index
     */
    public T getItem(int index) {
        if (items != null && index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    /**
     * Check if list is empty
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }

    /**
     * Get list size
     * 
     * @return list size
     */
    public int size() {
        return items != null ? items.size() : 0;
    }
}