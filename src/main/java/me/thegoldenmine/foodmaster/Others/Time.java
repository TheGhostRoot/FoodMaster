package me.thegoldenmine.foodmaster.Others;

import me.thegoldenmine.foodmaster.FoodMaster;

public class Time {
    private final FoodMaster plugin;

    public Time(FoodMaster main) {
        plugin = main;
    }

    public String getTime(int totalTime) {
        if ((totalTime % 60) == 0) {
            // just a full minute
            int justMinute = totalTime / 60;
            if (justMinute == 1) {
                return justMinute + " minute";
            } else if (justMinute > 1) {
                return justMinute + " minutes";
            }
        } else {
            // minute and seconds
            if (totalTime > 60) {
                int justMin = totalTime / 60;
                int minInSec = justMin * 60;
                int seconds = totalTime - minInSec;
                if (seconds >= 10) {
                    if (justMin > 1) {
                        return justMin + ":" + seconds + " minutes";
                    } else {
                        return justMin + ":" + seconds + " minute";
                    }
                } else {
                    if (justMin > 1) {
                        return justMin + ":0" + seconds + " minutes";
                    } else {
                        return justMin + ":0" + seconds + " minute";
                    }
                }
            } else {
                return String.valueOf(totalTime);
            }
        }
        return null;
    }
}
