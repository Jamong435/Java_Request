package com.kim9212.ex76request;

public class BoardItem {
    String no;
    String name;
    String message;
    String date;

    public BoardItem(String no, String name, String message, String date) {
        this.no = no;
        this.name = name;
        this.message = message;
        this.date = date;
    }

    public BoardItem() {
    }
}