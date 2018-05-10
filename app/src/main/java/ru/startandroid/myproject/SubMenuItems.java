package ru.startandroid.myproject;

/**
 * Created by Администратор on 18.04.2018.
 */

class SubMenuItems {
    String title;
    int id;
    String uri;
    String icon;
    int sidebarOrder;
    boolean isActive;

    public SubMenuItems(String title, int id, String uri, String icon,
                        int sidebarOrder, boolean isActive){
        this.title = title;
        this.id = id;
        this.icon = icon;
        this.uri = uri;
        this.sidebarOrder = sidebarOrder;
        this.isActive = isActive;
    }
}
