package com.example.machine.impl.menuitem;

import com.example.machine.Machine;
import com.example.machine.Menu;
import com.example.machine.impl.MenuImpl;

public class PowerOnMenuItem implements Menu.MenuItem {
    private final Machine machine;
    private final MenuImpl menu;

    public PowerOnMenuItem(Machine machine, MenuImpl menu) {
        this.machine = machine;
        this.menu = menu;
    }

    @Override
    public String getName() {
        return "powerOn";
    }

    @Override
    public void select() {
        machine.powerOn();
        menu.open();
    }
}
