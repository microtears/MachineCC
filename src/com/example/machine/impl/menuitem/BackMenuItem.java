package com.example.machine.impl.menuitem;

import com.example.machine.Menu;

public class BackMenuItem implements Menu.MenuItem {
    private final Menu.SubMenu menu;

    public BackMenuItem(Menu.SubMenu menu) {
        this.menu = menu;
    }

    @Override
    public String getName() {
        return "back";
    }

    @Override
    public void select() {
        menu.back();
    }
}
