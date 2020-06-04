package com.example.machine;

public interface Menu {
    void open();

    void close();

    void addItem(MenuItem item);

    MenuItem getItem(int index);

    interface SubMenu extends Menu {
        void back();

        Menu getParent();
    }

    interface MenuItem {
        String getName();

        void select();
    }
}
