package com.example.oenskeliste.Model;

public class Wish {

    private int wish_id;
    private int user_id;
    private int list_id;
    private String wish_name;
    private String wish_desc;
    private String wish_link;
    private double wish_price;
    private boolean reserved;

    public Wish(){

    }

    public Wish(int wish_id, int user_id, int list_id , String wish_name, String wish_desc, String wish_link,
                double wish_price, boolean reserved) {
        this.wish_id = wish_id;
        this.user_id = user_id;
        this.list_id = list_id;
        this.wish_name = wish_name;
        this.wish_desc = wish_desc;
        this.wish_link = wish_link;
        this.wish_price = wish_price;
        this.reserved = reserved;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public int getWish_id() {
        return wish_id;
    }

    public void setWish_id(int wish_id) {
        this.wish_id = wish_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getWish_name() {
        return wish_name;
    }

    public void setWish_name(String wish_name) {
        this.wish_name = wish_name;
    }

    public String getWish_desc() {
        return wish_desc;
    }

    public void setWish_desc(String wish_desc) {
        this.wish_desc = wish_desc;
    }

    public String getWish_link() {
        return wish_link;
    }

    public void setWish_link(String wish_link) {
        this.wish_link = wish_link;
    }

    public double getWish_price() {
        return wish_price;
    }

    public void setWish_price(double wish_price) {
        this.wish_price = wish_price;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

}
