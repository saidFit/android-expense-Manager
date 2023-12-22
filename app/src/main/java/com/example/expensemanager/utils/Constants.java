package com.example.expensemanager.utils;

import com.example.expensemanager.R;

public class Constants {

     public static String INCOME = "INCOME";
     public static String EXPENSE = "EXPENSE";


     //-----tabLayout-----//

    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDER = 2;
    public static int SUMMARY = 3;
    public static int NOTE = 4;

    public static int tabLayout = 0;
    public static int statusLayout = 0;
    public static String statusLayoutType = INCOME;




    public static int colorAccount(String account){

        switch (account){
            case "Cash":
                return R.color.green_500;
            case "Bank":
                return R.color.teal_500;
            case "PayTM":
                return R.color.indigo_500;
            case "EasyPaisa":
                return R.color.sky_500;
            default:
                return R.color.blue_500;
        }

    }

}
