package com.example.expensemanager.view.fragments;

import android.opengl.Visibility;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.expensemanager.R;
import com.example.expensemanager.databinding.FragmentStatusBinding;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.viewModel.TransactionViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;


public class StatusFragment extends Fragment {

    Calendar calendar;
    Calendar today;
    int totalOfIncome = 0;
    int totalOfExpense = 0;
    int total = 0;
/*
0 = Daily;
1 = Monthly;
2 = Calendar;
3 = Summary;
4 = Notes;
 */

    public TransactionViewModel transactionViewModel;

    FragmentStatusBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatusBinding.inflate(inflater);

        transactionViewModel = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class); // requireActivity to make the data when added as real time;

        calendar = Calendar.getInstance();
        today = Calendar.getInstance();
        currentDate();

        binding.prevBtn.setOnClickListener(b -> {
            if(Constants.statusLayout == Constants.DAILY){
                calendar.add(Calendar.DATE,-1);
            }else{
                calendar.add(Calendar.MONTH,-1);
            }

            currentDate();
        });

        transactionViewModel.getAllCategoryTransactions(calendar,Constants.statusLayoutType);



        binding.nextBtn.setOnClickListener(b ->{
            if(Constants.statusLayout == Constants.DAILY){
                calendar.add(Calendar.DATE,1);
            }else if(Constants.statusLayout == Constants.MONTHLY){
                calendar.add(Calendar.MONTH,1);
            }
            currentDate();
        });



        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Daily")){
                    Constants.statusLayout = Constants.DAILY;
                    currentDate();
                }else if(tab.getText().equals("Monthly")){
                    Toast.makeText(getContext(), "Monthly", Toast.LENGTH_SHORT).show();
                    Constants.statusLayout = Constants.MONTHLY;
                    currentDate();
                }else if(tab.getText().equals("Summary")){
                    Constants.statusLayout = Constants.SUMMARY;
                    currentDate();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        //TODO=====anyChart======//
        Pie pie = AnyChart.pie();

        transactionViewModel.categoryTransactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {

                if(transactions.size() > 0){

                    binding.emptyStatus.setVisibility(View.GONE);
                    binding.anychart.setVisibility(View.VISIBLE);
                    List<DataEntry> data = new ArrayList<>();

                    Map<String, Double> categoryMap = new HashMap<>();
                    for(Transaction transaction : transactions){
                        String category = transaction.getCategory();
                        Double amount  = (Double) transaction.getAmount();

                        if(categoryMap.containsKey(category)){
                            Double currentTotal = categoryMap.get(category).doubleValue();
                            currentTotal += amount;

                            categoryMap.put(category,currentTotal);
                        }else{
                            categoryMap.put(category,amount);
                        }
                    }


                    for(Map.Entry<String,Double> entry : categoryMap.entrySet()){
                        data.add(new ValueDataEntry(entry.getKey(),entry.getValue())); // how can devise percentage is Addition of numbers example(1159/219*100)
                    }
                    pie.data(data);
                }else{
                    binding.emptyStatus.setVisibility(View.VISIBLE);
                    binding.anychart.setVisibility(View.GONE);
                }
            }
        });


//        data.add(new ValueDataEntry("Apples", 6371664));
//        data.add(new ValueDataEntry("Pears", 789622));
//        data.add(new ValueDataEntry("Bananas", 7216301));
//        data.add(new ValueDataEntry("Grapes", 1486621));
//        data.add(new ValueDataEntry("Oranges", 1200000));


//
//        pie.title("Fruits imported in 2015 (in kg)");
//
//        pie.labels().position("outside");
//
//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("Retail channels")
//                .padding(0d, 0d, 10d, 0d);
//
//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);


        binding.anychart.setChart(pie);
        //TODO=====anyChart======//


        return binding.getRoot();
    }


    public void currentDate(){


        if(Constants.statusLayout == Constants.MONTHLY){
            if(calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                    &&calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)
            ){
                binding.currentDate.setText(Helper.formDateOfMonth(calendar.getTime()) + " (month)");
            }else {
                binding.currentDate.setText(Helper.formDateOfMonth(calendar.getTime()));
            }
        }else{

            if(calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                    && calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                    && calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                // The calendar date is today

                binding.currentDate.setText(Helper.formData(calendar.getTime()) + " (today)");
            }

            else {
                // The calendar date is not today
                binding.currentDate.setText(Helper.formData(calendar.getTime()));
            }
        }



        // Check for null before calling methods
        if (transactionViewModel != null) {
            transactionViewModel.getAllCategoryTransactions(calendar,Constants.statusLayoutType);
        }
    }
}