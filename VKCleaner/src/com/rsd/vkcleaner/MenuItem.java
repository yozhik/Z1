package com.rsd.vkcleaner;

public class MenuItem {
	public int id;
	public int icon;
    public String title;
    
    public MenuItem(){
        super();
    }
    
    public MenuItem(int id, int icon, String title) {
        super();
        this.id = id;
        this.icon = icon;
        this.title = title;
    }
}