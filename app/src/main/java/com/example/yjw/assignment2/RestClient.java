package com.example.yjw.assignment2;



import android.util.Log;
import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by yjw on 2018/4/12.
 */

public class RestClient {

    private static final String BASE_URL =
            "http://api.openweathermap.org/data/2.5/weather?zip=";// +&units=metric&appid=
    private static final String APP_URL =
            "http://10.30.28.108:8080/assignment1/webresources/";//10.30.28.108
    private static final String MAP_URL =
            "https://maps.googleapis.com/maps/api/geocode/json?address=";


    public static String [] getPositionByAddress(String address){

        address = address.replace(" ","+");
        String userAddress = MAP_URL + address + "&key=AIzaSyAbY0KDKVNYycnavGuymozfDaXvC8zBmLc";
        JsonObject result = new JsonParser().parse(getResult(userAddress)).getAsJsonObject();

//        URL url = null;
//        HttpURLConnection conn = null;
//        JsonObject result = null;
//        String str = "";
//        address = address.replace(" ","+");
//        try{
//            url = new URL(MAP_URL + address + "&key=AIzaSyAbY0KDKVNYycnavGuymozfDaXvC8zBmLc");
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(10000);
//            conn.setConnectTimeout(15000);
//
//            conn.setRequestMethod("GET");
//
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Accept", "application/json");
//
//            Scanner inStream = new Scanner(conn.getInputStream());
//            while (inStream.hasNextLine()){
//
//                    str += inStream.nextLine();
//
//            }
//            result = new JsonParser().parse(str).getAsJsonObject();
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            conn.disconnect();
//        }
//        Gson gson = new Gson();
//        //gson.

        System.out.println(result);
        JsonArray jArray = result.getAsJsonArray("results");
        JsonObject jOb = jArray.get(0).getAsJsonObject();
        String lat = jOb.getAsJsonObject("geometry").getAsJsonObject("location").get("lat").toString();
        //String lat = result.getAsJsonArray("results").getAsJsonArray("0").getAsJsonObject("geometry").getAsJsonObject("location").get("lat").toString();
        String lng = jOb.getAsJsonObject("geometry").getAsJsonObject("location").get("lng").toString();
        String [] location = {lat, lng};
        //System.out.println(location[0]);
        return location;
    }

    public static String getUserInfo(String userName, String password){
        String userInfo = "";
        userName = userName.toLowerCase();
        String userUrl = APP_URL + "restws.credentials/findByUsername/" + userName;
        String userStr = getResult(userUrl);
        if (!userStr.equals("[]") ) {
            String pwUrl = APP_URL + "restws.credentials/findByPasswordhash/" + password;
            String pw = getResult(pwUrl);
            if (!pw.equals("[]")) {
                userInfo = userStr;
            } else
                userInfo = "";
        } else
            userInfo = "";
        return userInfo;
    }

    public static String getDailyUsageByResidAndDate(String id, String date) {
        String dailyUsage = "";
        String usageURL = APP_URL + "restws.usages/dailyUsageofAppliances/" + id + "/" + date;
        dailyUsage = getResult(usageURL);

        return dailyUsage;
    }

    public static String getHourlyDailyUsages(String id, String date, String type) {
        String usages = "";
        String usageURL = APP_URL + "restws.usages/hourlyDailyUsages/" + id + "/" + date + "/" + type;
        usages = getResult(usageURL);

        return usages;
    }

    public static void createResident(String input) {
        String resURL = "restws.residents";
        postData(resURL, input);
    }

    public static void createCredential(String input) {
        String creURL = "restws.credentials";
        postData(creURL, input);
    }

    public static void createUsage(ArrayList<String> recordArray) {
        String usageURL = "restws.usages";
        for (int i = 0; i < recordArray.size(); i++) {
            System.out.println(recordArray.get(i));
            postData(usageURL,recordArray.get(i));
        }
    }

    public static boolean isExist(String input, String type){
        boolean exist = true;
        switch(type) {
            case "userName":
                String userURL = APP_URL + "restws.credentials/findByUsername/" + input;
                String userStr = getResult(userURL);
                if (userStr.equals("[]")) {
                    exist = false;
                }
                break;
            case "email":
                break;
        }
        return exist;
    }

    public static String getTemp(String postCode){
        final String apiKey = "1ed6eca3f52d92a2f80ea6e4416d7a01";
        String location = postCode + ",au";
        String userTemp = BASE_URL + location + "&units=metric&appid=" + apiKey;
        String temp = "";
        JsonObject tempResult = new JsonParser().parse(getResult(userTemp)).getAsJsonObject();
        temp = tempResult.getAsJsonObject("main").get("temp").toString();

//        URL url = null;
//        HttpURLConnection conn = null;
//        JsonObject textResult = null;
//
//        String str = "";
//
//        try{
//            url = new URL(BASE_URL + location + "&units=metric&appid=" + apiKey);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(10000);
//            conn.setConnectTimeout(15000);
//
//            conn.setRequestMethod("GET");
//
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Accept", "application/json");
//
//            Scanner inStream = new Scanner(conn.getInputStream());
//
//            while (inStream.hasNextLine()){
//                str += inStream.nextLine();
//            }
//
//            textResult = new JsonParser().parse(str).getAsJsonObject();
//            temp = textResult.getAsJsonObject("main").get("temp").toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            conn.disconnect();
//        }

        return temp;
    }

    public static String getResult(String URL){
        URL url = null;
        HttpURLConnection conn = null;
        String str = "";
        //Making HTTP request
        try {
            url = new URL(URL);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                str += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return str;
    }

    public static void postData(String URL, String data){
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(APP_URL + URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(data.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(data);
            out.close();
            int responseCode = conn.getResponseCode();
            Log.i("error", new Integer(responseCode).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static int getMaxId(){
        int id = 0;
        URL url = null;
        String str = "";
        String idURL = APP_URL + "restws.residents/count/";
        HttpURLConnection conn = null;
        try {
            url = new URL(idURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                str += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        id = Integer.parseInt(str);
        return id;
    }

    public static String deQuoMark(String str){
        str = str.replaceAll("\"","");
        return str;
    }


}