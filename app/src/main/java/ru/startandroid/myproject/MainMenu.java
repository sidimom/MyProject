package ru.startandroid.myproject;


import java.util.ArrayList;

class MainMenu{
    String title;
    int id;
    String icon;
    String uri;
    int sidebarOrder;
    boolean isCategory;
    boolean isOpen;
    boolean isActive;
    ArrayList<SubMenuItems> subMenuItems;


    public MainMenu(String title, int id, String icon, String uri, int sidebarOrder,
                    boolean isCategory, boolean isOpen, boolean isActive, ArrayList<SubMenuItems> subMenuItems){
        this.title = title;
        this.id = id;
        this.icon = icon;
        this.uri = uri;
        this.sidebarOrder = sidebarOrder;
        this.isCategory = isCategory;
        this.isOpen = isOpen;
        this.isActive = isActive;
        this.subMenuItems = subMenuItems;
    }
}
