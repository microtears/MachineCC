package com.example.machine.impl;

import com.example.machine.*;
import com.example.machine.impl.menuitem.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        Machine machine = new MachineImpl();
        Menu mainMenu = getMainMenu(machine, builder);
        mainMenu.open();
        Scanner scanner = new Scanner(System.in);
        Menu currentMenu = mainMenu;
        while (true) {
            try {
                int cmd = scanner.nextInt() - 1;
                Menu.MenuItem menuItem = currentMenu.getItem(cmd);
                if (menuItem instanceof Menu.SubMenu) {
                    currentMenu = (Menu) menuItem;
                }
                if (menuItem instanceof BackMenuItem) {
                    currentMenu = ((Menu.SubMenu) currentMenu).getParent();
                }
                menuItem.select();
            } catch (Exception e) {
                e.printStackTrace();
                mainMenu.open();
            }
        }
    }

    private static MenuImpl getMainMenu(Machine machine, ConfigurationBuilder builder) {
        MenuImpl menu = new MenuImpl() {
            @Override
            public String getName() {
                return "MainMenu";
            }
        };
        menu.addItem(new PowerOnMenuItem(machine, menu));
        menu.addItem(new PowerOffMenuItem(machine, menu));
        menu.addItem(new PauseMenuItem(machine, menu));
        menu.addItem(new StartMenuItem(machine, builder, menu));
        menu.addItem(getLevelMenu(menu, machine, builder));
        menu.addItem(getModeMenu(menu, machine, builder));
        menu.addItem(getTimeMenu(menu, machine, builder));
        menu.addItem(getWaterVolumeMenu(menu, machine, builder));
        menu.addItem(new ExitMenuItem());
        return menu;
    }

    private static MenuImpl getLevelMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        MenuImpl menu = new MenuImpl(parent) {
            @Override
            public String getName() {
                return "LevelMenu";
            }
        };
        for (Level level : Level.values()) {
            menu.addItem(new Menu.MenuItem() {
                @Override
                public String getName() {
                    return level.name();
                }

                @Override
                public void select() {
                    if (!checkMachineStat(machine)) {
                        System.out.println("select " + getName());
                        builder.setLevel(level);
                    }
                    menu.open();
                }
            });
        }
        return menu;
    }

    private static MenuImpl getModeMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        MenuImpl menu = new MenuImpl(parent) {
            @Override
            public String getName() {
                return "ModeMenu";
            }
        };
        for (Mode mode : Mode.values()) {
            menu.addItem(new Menu.MenuItem() {
                @Override
                public String getName() {
                    return mode.name();
                }

                @Override
                public void select() {
                    if (!checkMachineStat(machine)) {
                        System.out.println("select " + getName());
                        builder.setMode(mode);
                    }
                    menu.open();
                }
            });
        }
        return menu;
    }

    private static MenuImpl getTimeMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        MenuImpl menu = new MenuImpl(parent) {
            @Override
            public String getName() {
                return "TimeMenu";
            }
        };
        for (Time time : Time.values()) {
            menu.addItem(new Menu.MenuItem() {
                @Override
                public String getName() {
                    return time.name();
                }

                @Override
                public void select() {
                    if (!checkMachineStat(machine)) {
                        System.out.println("select " + getName());
                        builder.setTime(time);
                    }
                    menu.open();
                }
            });
        }
        return menu;
    }

    private static MenuImpl getWaterVolumeMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        MenuImpl menu = new MenuImpl(parent) {
            @Override
            public String getName() {
                return "WaterVolumeMenu";
            }
        };
        for (WaterVolume waterVolume : WaterVolume.values()) {
            menu.addItem(new Menu.MenuItem() {
                @Override
                public String getName() {
                    return waterVolume.name();
                }

                @Override
                public void select() {
                    if (!checkMachineStat(machine)) {
                        System.out.println("select " + getName());
                        builder.setWaterVolume(waterVolume);
                    }
                    menu.open();
                }
            });
        }
        return menu;
    }

    private static boolean checkMachineStat(Machine machine) {
        if (!machine.isPowerOn()) {
            // do nothing
            System.out.println("需要开启机器(仅命令行提示)");
            return true;
        }
        return false;
    }

}
