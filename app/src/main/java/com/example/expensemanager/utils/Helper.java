package com.example.expensemanager.utils;

import com.example.expensemanager.R;
import com.example.expensemanager.models.Category;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Helper {

    public static String formData(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);
       return dateFormat.format(date);
    }

    public static String formDateOfMonth(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, yyyy", Locale.ENGLISH);
        return dateFormat.format(date);
    }


    public static ArrayList<Category> getCategories(){

        ArrayList<Category> categories = new ArrayList<>();

        categories.add(new Category("Investment", R.drawable.ic_investment,R.color.orange_400));
        categories.add(new Category("Business",R.drawable.ic_business,R.color.rose_400));
        categories.add(new Category("Salary",R.drawable.ic_salary,R.color.yellow_400));
        categories.add(new Category("Loan",R.drawable.ic_loan,R.color.indigo_400));
        categories.add(new Category("Rent",R.drawable.ic_rent,R.color.sky_400));
        categories.add(new Category("Other",R.drawable.ic_other,R.color.green_400));

        return categories;
    }

    public static Category matchCategory(ArrayList<Category> category,String categoryName){

        for (Category cat: category) {

              if(cat.getName().equals(categoryName)){
                  return cat;
              }

        }
        return null;

    }

}
