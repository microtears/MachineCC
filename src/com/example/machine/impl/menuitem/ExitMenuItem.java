package com.example.machine.impl.menuitem;

import com.example.machine.Menu;

public class ExitMenuItem implements Menu.MenuItem {
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public void select() {
        System.out.println("退出系统");
        System.exit(0);
    }
}
