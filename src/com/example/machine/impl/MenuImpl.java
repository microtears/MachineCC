package com.example.machine.impl;

import com.example.machine.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuImpl implements Menu.SubMenu, Menu.MenuItem {
    protected final List<MenuItem> items = new ArrayList<>();
    private final boolean isSubMenu;
    private final Menu parent;

    public MenuImpl() {
        this(false, null);
    }

    public MenuImpl(Menu parent) {
        this(true, parent);
    }

    private MenuImpl(boolean isSubMenu, Menu parent) {
        this.isSubMenu = isSubMenu;
        this.parent = parent;
    }

    @Override
    public void open() {
        System.out.println(getName());
        if (isSubMenu) {
            items.removeIf(item -> item instanceof BackMenuItem);
            addItem(new BackMenuItem(this));
        }
        int index = 0;
        for (MenuItem item : items) {
            System.out.println(++index + " : " + item.getName());
        }
    }

    @Override
    public void close() {
        if (isSubMenu) {
            back();
        }
    }

    @Override
    public void addItem(MenuItem item) {
        items.add(item);
    }

    @Override
    public MenuItem getItem(int index) {
        return items.get(index);
    }

    @Override
    public void back() {
        if (isSubMenu && parent != null) {
            parent.open();
        }
    }

    @Override
    public Menu getParent() {
        return parent;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void select() {
        if (isSubMenu) {
            open();
        }
    }
}
