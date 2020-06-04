package com.example.machine.impl;

import com.example.machine.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        Machine machine = new MachineImpl();
        MainMenu mainMenu = getMainMenu(machine, builder);
        mainMenu.open();
        Scanner scanner = new Scanner(System.in);
        Menu currentMenu = mainMenu;
        while (true) {
            try {
                int cmd = scanner.nextInt() - 1;
                Menu.MenuItem menuItem = currentMenu.getItem(cmd);
                if (menuItem instanceof Menu) {
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

    private static MainMenu getMainMenu(Machine machine, ConfigurationBuilder builder) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.addItem(new Menu.MenuItem() {
            @Override
            public String getName() {
                return "powerOn";
            }

            @Override
            public void select() {
                machine.powerOn();
                mainMenu.open();
            }
        });
        mainMenu.addItem(new Menu.MenuItem() {
            @Override
            public String getName() {
                return "powerOff";
            }

            @Override
            public void select() {
                machine.powerOff();
                mainMenu.open();
            }
        });
        mainMenu.addItem(new Menu.MenuItem() {
            @Override
            public String getName() {
                return "pause";
            }

            @Override
            public void select() {
                machine.pause();
                mainMenu.open();
            }
        });
        mainMenu.addItem(new Menu.MenuItem() {
            @Override
            public String getName() {
                return "start";
            }

            @Override
            public void select() {
                machine.start(builder.build(), new FinishTip());
                mainMenu.open();
            }
        });
        mainMenu.addItem(getLevelMenu(mainMenu, machine, builder));
        mainMenu.addItem(getModeMenu(mainMenu, machine, builder));
        mainMenu.addItem(getTimeMenu(mainMenu, machine, builder));
        mainMenu.addItem(getWaterVolumeMenu(mainMenu, machine, builder));
        mainMenu.addItem(new ExitMenuItem());
        return mainMenu;
    }

    private static SubMenu getLevelMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        SubMenu subMenu = new SubMenu(parent) {
            @Override
            public String getName() {
                return "LevelMenu";
            }
        };
        for (Level level : Level.values()) {
            subMenu.addItem(new Menu.MenuItem() {
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
                    subMenu.open();
                }
            });
        }
        return subMenu;
    }

    private static SubMenu getModeMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        SubMenu subMenu = new SubMenu(parent) {
            @Override
            public String getName() {
                return "ModeMenu";
            }
        };
        for (Mode mode : Mode.values()) {
            subMenu.addItem(new Menu.MenuItem() {
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
                    subMenu.open();
                }
            });
        }
        return subMenu;
    }

    private static SubMenu getTimeMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        SubMenu subMenu = new SubMenu(parent) {
            @Override
            public String getName() {
                return "TimeMenu";
            }
        };
        for (Time time : Time.values()) {
            subMenu.addItem(new Menu.MenuItem() {
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
                    subMenu.open();
                }
            });
        }
        return subMenu;
    }

    private static SubMenu getWaterVolumeMenu(Menu parent, Machine machine, ConfigurationBuilder builder) {
        SubMenu subMenu = new SubMenu(parent) {
            @Override
            public String getName() {
                return "WaterVolumeMenu";
            }
        };
        for (WaterVolume waterVolume : WaterVolume.values()) {
            subMenu.addItem(new Menu.MenuItem() {
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
                    subMenu.open();
                }
            });
        }
        return subMenu;
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
