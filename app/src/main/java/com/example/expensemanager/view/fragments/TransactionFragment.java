package com.example.expensemanager.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.expensemanager.R;
import com.example.expensemanager.adapters.TransactionAdapter;
import com.example.expensemanager.databinding.FragmentTransactionBinding;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.view.activities.MainActivity;
import com.example.expensemanager.viewModel.TransactionViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import io.realm.RealmResults;


public class TransactionFragment extends Fragment {
    public static Calendar calendar;
    Calendar today;
    int totalOfIncome = 0;
    int totalOfExpense = 0;
    int total = 0;
    public TransactionViewModel transactionViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentTransactionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionBinding.inflate(inflater);

//        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class); // requireActivity to make the data when added as real time;

        calendar = Calendar.getInstance();
        today = Calendar.getInstance();
        currentDate();

        binding.prevBtn.setOnClickListener(b -> {
            if(Constants.tabLayout == Constants.DAILY){
                calendar.add(Calendar.DATE,-1);
            }else{
                calendar.add(Calendar.MONTH,-1);
            }

            currentDate();
        });

        binding.nextBtn.setOnClickListener(b ->{
            if(Constants.tabLayout == Constants.DAILY){
                calendar.add(Calendar.DATE,1);
            }else if(Constants.tabLayout == Constants.MONTHLY){
                calendar.add(Calendar.MONTH,1);
            }
            currentDate();
        });

        binding.floatingActionButton.setOnClickListener(view -> {
//            new AddTransitionFragment().show(getSupportFragmentManager(),null);
            new AddTransitionFragment().show(getParentFragmentManager(),null);
        });


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Daily")){
                    Constants.tabLayout = Constants.DAILY;
                    currentDate();
                }else if(tab.getText().equals("Monthly")){
                    Toast.makeText(getContext(), "Monthly", Toast.LENGTH_SHORT).show();
                    Constants.tabLayout = Constants.MONTHLY;
                    currentDate();
                }else if(tab.getText().equals("Summary")){
                    Constants.tabLayout = Constants.SUMMARY;
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


        // Observe changes in the transactions and update the UI
        binding.recyclerViewTransations.setLayoutManager(new LinearLayoutManager(getContext()));
//        transactionViewModel.transactions.observe(this, new Observer<RealmResults<Transaction>>() {
        transactionViewModel.transactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionAdapter transactionAdapter = new TransactionAdapter(getActivity(), transactions);

                binding.recyclerViewTransations.setAdapter(transactionAdapter);

                // Update total income and total expense and total
                totalOfIncome = 0;
                totalOfExpense = 0;
                total = 0;
                changeValue(transactions);
                binding.total.setText(String.valueOf(total));
                binding.totalOfIncome.setText(String.valueOf(totalOfIncome));
                binding.totalOfExpense.setText(String.valueOf(totalOfExpense));
            }
        });

        // Insert a sample transaction
//        insertSampleTransaction();
        transactionViewModel.getAllTransactions(calendar);

        return binding.getRoot();
    }



    public void currentDate(){


        if(Constants.tabLayout == Constants.MONTHLY){
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
            transactionViewModel.getAllTransactions(calendar);
        }
    }

    public void changeValue(RealmResults<Transaction> transactions){

        if(transactions.size() != 0){
            for (Transaction tra:transactions
            ) {
                if(tra.getTransactionType().equals(Constants.INCOME)){
                    totalOfIncome+=tra.getAmount();
                }else{
                    totalOfExpense+=tra.getAmount();
                }
                total+=tra.getAmount();
            }
            // If transactions are not empty, set visibility to GONE
            binding.emptyTransactions.setVisibility(View.GONE);
        } else {
            // If transactions are empty, set visibility to VISIBLE
            binding.emptyTransactions.setVisibility(View.VISIBLE);
        }


    }

    public void getTransactionInAddFragment(){
        transactionViewModel.getAllTransactions(calendar);
    }

}