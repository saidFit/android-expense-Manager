package com.example.expensemanager.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.expensemanager.R;
import com.example.expensemanager.adapters.TransactionAdapter;
import com.example.expensemanager.databinding.ActivityMainBinding;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.view.fragments.AddTransitionFragment;
import com.example.expensemanager.view.fragments.StatusFragment;
import com.example.expensemanager.view.fragments.TransactionFragment;
import com.example.expensemanager.viewModel.TransactionViewModel;
import com.google.android.material.tabs.TabLayout;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.RealmResults;
import me.ibrahimsn.lib.OnItemSelectedListener;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Calendar calendar;
    Calendar today;
    int totalOfIncome = 0;
    int totalOfExpense = 0;
    int total = 0;
    public TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar().setTitle("Transitions");
        setContentView(binding.getRoot());

        // Initialize ViewModel
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        Log.d("randomId", java.util.UUID.randomUUID().toString());

        calendar = Calendar.getInstance();
        today = Calendar.getInstance();
//        currentDate();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new TransactionFragment());
        transaction.commit();


        binding.smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Handle item selection
                switch (i) {
                    case 0:
                        Toast.makeText(MainActivity.this, "transaction", Toast.LENGTH_SHORT).show();
                        // Handle click on the first tab(transaction)
                        transaction.replace(R.id.frameLayout, new TransactionFragment());
//                        transaction.addToBackStack(null);
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "status", Toast.LENGTH_SHORT).show();
                        // Handle click on the second tab(status)
                        transaction.replace(R.id.frameLayout, new StatusFragment());
                        transaction.addToBackStack(null);
                        break;
                    // Add more cases for other tabs if needed

                    case 2:
                        Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        // Handle click on the second tab(profile)
                        break;

                    default:
                        // handle click on the third tab(more)
                        Toast.makeText(MainActivity.this, "more", Toast.LENGTH_SHORT).show();
                        break;
                }
                transaction.commit();

                // Return true to animate tab selection, or false to disable animation
                return true;
            }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    public void getTransactionInAddFragment(){
        Log.d("mainA", String.valueOf(TransactionFragment.calendar.getTime()));
        transactionViewModel.getAllTransactions(TransactionFragment.calendar);
    }

}