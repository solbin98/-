package com.project.util;

public class Paging {
    int nowPage; // 현재 페이지
    int perPage; // 한 페이지에 보여줄 오브젝트의 개수
    int total; // 오브젝트의 개수
    int lastPage; // 마지막 페이지
    int leftMostPage; // 가장 왼쪽 페이지
    int rightMostPage; // 가장 우측 페이지
    boolean leftPossible; // 페이지 -1 가능 여부
    boolean rightPossible; // 페이지 + 1 가능 여부

    public Paging(int nowPage, int perPage, int total) {
        this.nowPage = nowPage;
        this.perPage = perPage;
        this.total = total;
        calcLastPage();
        calcLeftAndRightMostPage();
        calcPossible();
    }

    void calcLastPage(){
        lastPage = (int)Math.ceil((double)total / (double)perPage);
    }

    void calcLeftAndRightMostPage(){
        this.rightMostPage = (int) Math.ceil((((double)nowPage)/(double)10.0))*10;
        leftMostPage = rightMostPage - 9;
        this.rightMostPage = Math.min(rightMostPage, lastPage);
    }

    void calcPossible(){
        if(leftMostPage <= 1) this.leftPossible = false;
        else this.leftPossible = true;

        if(rightMostPage <= nowPage) this.rightPossible = false;
        else this.rightPossible = true;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
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

    public boolean isLeftPossible() {
        return leftPossible;
    }

    public void setLeftPossible(boolean leftPossible) {
        this.leftPossible = leftPossible;
    }

    public boolean isRightPossible() {
        return rightPossible;
    }

    public void setRightPossible(boolean rightPossible) {
        this.rightPossible = rightPossible;
    }
}
