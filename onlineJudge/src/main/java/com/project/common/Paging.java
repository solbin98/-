package com.project.common;

public class Paging {
    int nowPage; // 현재 페이지
    int perPage; // 한 페이지에 보여줄 오브젝트의 개수
    int total; // 오브젝트의 개수
    int lastPage; // 마지막 페이지
    int leftMostPage; // 가장 왼쪽 페이지
    int rightMostPage; // 가장 우측 페이지

    public Paging(int nowPage, int perPage, int total) {
        this.total = total;
        this.perPage = perPage;
        calcLastPage();
        setNowPage(nowPage);
        calcLeftAndRightMostPage();
    }

    void calcLastPage(){
        lastPage = (int)Math.ceil((double)total / (double)perPage);
    }

    void calcLeftAndRightMostPage(){
        this.rightMostPage = (int) Math.ceil((((double)nowPage)/(double)10.0))*10;
        leftMostPage = rightMostPage - 9;
        this.rightMostPage = Math.min(rightMostPage, lastPage);
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        if(nowPage < 1) this.nowPage = 1;
        else if(nowPage > this.lastPage) this.nowPage = lastPage;
        else this.nowPage = nowPage;
    }
    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getLeftMostPage() {
        return leftMostPage;
    }

    public void setLeftMostPage(int leftMostPage) {
        this.leftMostPage = leftMostPage;
    }

    public int getRightMostPage() {
        return rightMostPage;
    }

    public void setRightMostPage(int rightMostPage) {
        this.rightMostPage = rightMostPage;
    }

}
