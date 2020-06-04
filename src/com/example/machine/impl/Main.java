package com.example.machine.impl;

import com.example.machine.*;

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
            }
        }
    }

    private static MenuImpl getMainMenu(Machine machine, ConfigurationBuilder builder) {
        MenuImpl MenuImpl = new MenuImpl();
        MenuImpl.addItem(new Menu.MenuItem() {
            @Override
            public String getName() {
                return "powerOn";
            }

            @Override
            public void select() {
                machine.powerOn();
                MenuImpl.open();
            }
        });
        MenuImpl.addItem(new Menu.MenuItem() {
            @Override
            public String getName() {
                return "powerOff";
            }

            @Override
            public void select() {
                machine.powerOff();
                MenuImpl.open();
            }
        });
        MenuImpl.addItem(new Menu.MenuItem() {
            @Override
            public String getName() {
                return "pause";
            }

            @Override
            public void select() {
                machine.pause();
                MenuImpl.open();
            }
        });
        MenuImpl.addItem(new Menu.MenuItem() {
            @Override
            public String getName() {
                return "start";
            }

            @Override
            public void select() {
                machine.start(builder.build(), new FinishTip());
                MenuImpl.open();
            }
        });
        MenuImpl.addItem(getLevelMenu(MenuImpl, machine, builder));
        MenuImpl.addItem(getModeMenu(MenuImpl, machine, builder));
        MenuImpl.addItem(getTimeMenu(MenuImpl, machine, builder));
        MenuImpl.addItem(getWaterVolumeMenu(MenuImpl, machine, builder));
        MenuImpl.addItem(new ExitMenuItem());
        return MenuImpl;
    }

    private static MenuImpl getLevelMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        MenuImpl MenuImpl = new MenuImpl(parent) {
            @Override
            public String getName() {
                return "LevelMenu";
            }
        };
        for (Level level : Level.values()) {
            MenuImpl.addItem(new Menu.MenuItem() {
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
                    MenuImpl.open();
                }
            });
        }
        return MenuImpl;
    }

    private static MenuImpl getModeMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        MenuImpl MenuImpl = new MenuImpl(parent) {
            @Override
            public String getName() {
                return "ModeMenu";
            }
        };
        for (Mode mode : Mode.values()) {
            MenuImpl.addItem(new Menu.MenuItem() {
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
                    MenuImpl.open();
                }
            });
        }
        return MenuImpl;
    }

    private static MenuImpl getTimeMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        MenuImpl MenuImpl = new MenuImpl(parent) {
            @Override
            public String getName() {
                return "TimeMenu";
            }
        };
        for (Time time : Time.values()) {
            MenuImpl.addItem(new Menu.MenuItem() {
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
                    MenuImpl.open();
                }
            });
        }
        return MenuImpl;
    }

    private static MenuImpl getWaterVolumeMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        MenuImpl MenuImpl = new MenuImpl(parent) {
            @Override
            public String getName() {
                return "WaterVolumeMenu";
            }
        };
        for (WaterVolume waterVolume : WaterVolume.values()) {
            MenuImpl.addItem(new Menu.MenuItem() {
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
                    MenuImpl.open();
                }
            });
        }
        return MenuImpl;
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
