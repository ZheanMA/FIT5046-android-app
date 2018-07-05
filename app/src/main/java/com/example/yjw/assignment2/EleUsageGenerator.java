package com.example.yjw.assignment2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yjw on 2018/4/27.
 */

public class EleUsageGenerator {

    private static int hour = 0;
    private static int washMachFlag = 0;
    private static int washMachCounter = 0;
    private static String fridgeUsage;
    private static String aircondusage;
    private static String washmachusage;
    private static String temp;

    public static void resetScheduleAtMidnight() {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
        long oneDay = 24 * 60 * 60 * 1000;
        long initDelay = getTimeMillis("00:00:00") - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        //Start when first run the app. All usages will be recorded at the midnight of the second day run the app
        //Will reset the midnight clock everyday for accuracy.
        //schedule.scheduleAtFixedRate(new MainActivity.SetMidNight(),initDelay,oneDay, TimeUnit.MILLISECONDS);
        /**For Testing: Cut the Time Scale.*/
        schedule.scheduleAtFixedRate(new MainActivity.SetMidNight(),0,24, TimeUnit.SECONDS);
}

    public static void generateEleHourly() {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
        schedule.scheduleAtFixedRate(new MainActivity.SetEleUsage(),0,1,TimeUnit.HOURS);
        /**For Testing: Cut the Time Scale.*/
        //schedule.scheduleAtFixedRate(new MainActivity.SetEleUsage(),0,1,TimeUnit.SECONDS);
    }

    public static String generatorController(int hour) {
        String hourlyRecord = "";
        switch (hour) {
            case 0:setFridgeUsage(fridgeUsage() + "");setAircondusage(0 + ""); setWashmachusage(0 + ""); break;
            case 1:setFridgeUsage(fridgeUsage() + "");setAircondusage(0 + ""); setWashmachusage(0 + ""); break;
            case 2:setFridgeUsage(fridgeUsage() + "");setAircondusage(0 + ""); setWashmachusage(0 + ""); break;
            case 3:setFridgeUsage(fridgeUsage() + "");setAircondusage(0 + ""); setWashmachusage(0 + ""); break;
            case 4:setFridgeUsage(fridgeUsage() + "");setAircondusage(0 + ""); setWashmachusage(0 + ""); break;
            case 5:setFridgeUsage(fridgeUsage() + "");setAircondusage(0 + ""); setWashmachusage(0 + ""); break;
            case 6:setFridgeUsage(fridgeUsage() + "");setAircondusage(0 + ""); setWashmachusage(washMachUsage() + ""); break;
            case 7:setFridgeUsage(fridgeUsage() + "");setAircondusage(0 + ""); setWashmachusage(washMachUsage() + ""); break;
            case 8:setFridgeUsage(fridgeUsage() + "");setAircondusage(0 + ""); setWashmachusage(washMachUsage() + ""); break;
            case 9:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 10:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 11:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 12:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 13:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 14:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 15:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 16:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 17:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 18:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 19:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 20:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 21:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 22:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;
            case 23:setFridgeUsage(fridgeUsage() + "");setAircondusage(airConUsage() + ""); setWashmachusage(washMachUsage() + ""); break;


        }
        //System.out.println(hourlyRecord);
        return hourlyRecord;
    }

    public static double washMachUsage() {
        double usage = 0;
        //washMachFlag == 1: will not run in this hour; == 2: running continuously; == 3: will not run again today;
        if (washMachFlag != 3) {
            if (washMachFlag == 2) {
                if (washMachCounter >= 1) {
                    usage = randomGenerator("washmach");
                    washMachCounter --;
                } else
                    washMachFlag = 3;
            } else {
                Random builder = new Random();
                //random 1 or 2--1 will not run this hour; 2 will run continuously
                washMachFlag = builder.nextInt(2) + 1;
                if (washMachFlag == 2) {//flag == 2, will run
                    //generate the number of hours will run: 1~3
                    washMachCounter = builder.nextInt(3) + 1;
                    usage = randomGenerator("washmach");
                    washMachCounter --;
                }
            }
        }
        return usage;
    }

    public static double airConUsage() {
        int airConFlag = 0;
        Random builder = new Random();
        airConFlag = builder.nextInt(2);
        if (airConFlag == 0)
            return 0;
        else
            return randomGenerator("aircon");
    }

    public static double fridgeUsage() {
        double usage = randomGenerator("fridge");
        return usage;
    }

    public static double randomGenerator(String type) {
        double usage = 0;
        Random generator = new Random();
        int adjust = 0;
        int number = 0;
        switch (type) {
            //fridge: 0.3~0.8
            case "fridge": number = 6; adjust = 3; break;
            //aircon: 1.0~5.0
            case "aircon": number = 41; adjust = 10; break;
            //washMach:0.4~1.3
            case "washmach": number = 10; adjust = 4; break;
        }
        double randomNumber = generator.nextInt(number) + adjust;
        usage = randomNumber/10;
        return usage;
    }

    public static void hourIncrease() {
        hour ++;
    }

    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static int getHour() {
        return hour;
    }

    public static void setHour(int hour) {
        EleUsageGenerator.hour = hour;
    }

    public static int getWashMachFlag() {
        return washMachFlag;
    }

    public static void setWashMachFlag(int washMachFlag) {
        EleUsageGenerator.washMachFlag = washMachFlag;
    }

    public static int getWashMachCounter() {
        return washMachCounter;
    }

    public static void setWashMachCounter(int washMachCounter) {
        EleUsageGenerator.washMachCounter = washMachCounter;
    }

    public static String getFridgeUsage() {
        return fridgeUsage;
    }

    public static void setFridgeUsage(String fridgeUsage) {
        EleUsageGenerator.fridgeUsage = fridgeUsage;
    }

    public static String getAircondusage() {
        return aircondusage;
    }

    public static void setAircondusage(String aircondusage) {
        EleUsageGenerator.aircondusage = aircondusage;
    }

    public static String getWashmachusage() {
        return washmachusage;
    }

    public static void setWashmachusage(String washmachusage) {
        EleUsageGenerator.washmachusage = washmachusage;
    }

    public static String getTemp() {
        return temp;
    }

    public static void setTemp(String temp) {
        EleUsageGenerator.temp = temp;
    }
}
