package com.example.machine.impl.menuitem;

import com.example.machine.Machine;
import com.example.machine.Menu;
import com.example.machine.impl.ConfigurationBuilder;
import com.example.machine.impl.FinishTip;
import com.example.machine.impl.MenuImpl;

public class StartMenuItem implements Menu.MenuItem {
    private final Machine machine;
    private final ConfigurationBuilder builder;
    private final MenuImpl menu;

    public StartMenuItem(Machine machine, ConfigurationBuilder builder, MenuImpl menu) {
        this.machine = machine;
        this.builder = builder;
        this.menu = menu;
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public void select() {
        machine.start(builder.build(), new FinishTip());
        menu.open();
    }
}
