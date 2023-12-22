package com.example.expensemanager.view.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.expensemanager.R;
import com.example.expensemanager.adapters.AccountAdapter;
import com.example.expensemanager.adapters.CategoryAdapter;
import com.example.expensemanager.databinding.FragmentAddTransitionBinding;
import com.example.expensemanager.databinding.ListDialogAccountBinding;
import com.example.expensemanager.databinding.ListDialogCategoryBinding;
import com.example.expensemanager.models.Account;
import com.example.expensemanager.models.Category;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.view.activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTransitionFragment extends BottomSheetDialogFragment {

    Transaction transaction;

    public AddTransitionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAddTransitionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTransitionBinding.inflate(inflater);

        Helper.getCategories();

        transaction = new Transaction();

        binding.icomeBtn.setOnClickListener(view ->{
            binding.icomeBtn.setBackground(getContext().getDrawable(R.drawable.selected_income));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.rounded_incm_exps));
            transaction.setTransactionType(Constants.INCOME);
        });

        binding.expenseBtn.setOnClickListener(view ->{
            binding.icomeBtn.setBackground(getContext().getDrawable(R.drawable.rounded_incm_exps));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.selected_expense));
            transaction.setTransactionType(Constants.EXPENSE);
        });

        binding.selectDateInputEditText.setOnClickListener(view ->{
            showDatePickerDialog();
        });


        binding.addTransitionBtn.setOnClickListener(v-> {

            int amount = (int) Double.parseDouble(binding.amountInputEditText.getText().toString());
            String note   = binding.noteInputEditText.getText().toString();
            Log.d("result", transaction.getAccount()+"/"+transaction.getAmount()+"/"+transaction.getCategory()+"/"+transaction.getTransactionType()+"/"+transaction.getIcon()+"/"+transaction.getDateSelect());
            if(transaction.getTransactionType().equals(Constants.EXPENSE)){
                transaction.setAmount(amount*-1);
            }else{
                transaction.setAmount(amount);
            }

            transaction.setId(java.util.UUID.randomUUID().toString());

            ((MainActivity)getActivity()).transactionViewModel.insertTransaction(transaction);
            ((MainActivity)getActivity()).getTransactionInAddFragment();
            dismiss();

        });

        binding.categoryInputEditText.setOnClickListener(view ->{
            ListDialogCategoryBinding dialogCategoryBinding = ListDialogCategoryBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogCategoryBinding.getRoot());



            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Helper.getCategories(), new CategoryAdapter.CategoryClickListener() {
                @Override
                public void onCategoryClickListener(Category category) {
                    binding.categoryInputEditText.setText(category.getName());
                    transaction.setCategory(category.getName());
                    transaction.setIcon(category.getImage());
                    categoryDialog.dismiss();
                }
            });
            dialogCategoryBinding.recyclerViewListDialog.setLayoutManager(new GridLayoutManager(getContext(),3));
            dialogCategoryBinding.recyclerViewListDialog.setAdapter(categoryAdapter);

            categoryDialog.show();
        });




        binding.accountInputEditText.setOnClickListener(v -> {

            ListDialogAccountBinding listDialogAccountBinding = ListDialogAccountBinding.inflate(inflater);
            AlertDialog accountDialog = new AlertDialog.Builder(getContext()).create();
            accountDialog.setView(listDialogAccountBinding.getRoot());

            ArrayList<Account> accounts = new ArrayList<>();

            accounts.add(new Account(0,"Cash"));
            accounts.add(new Account(0,"Bank"));
            accounts.add(new Account(0,"PayTM"));
            accounts.add(new Account(0,"EasyPaisa"));
            accounts.add(new Account(0,"Other"));

            AccountAdapter accountAdapter = new AccountAdapter(getContext(), accounts, new AccountAdapter.AccountClickListener() {
                @Override
                public void onAccountClickListener(Account account) {
                    binding.accountInputEditText.setText(account.getName());
                    transaction.setAccount(account.getName());
                    accountDialog.dismiss();
                }
            });

            listDialogAccountBinding.recyclerViewListDialogAccount.setLayoutManager(new LinearLayoutManager(getContext()));
            listDialogAccountBinding.recyclerViewListDialogAccount.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
            listDialogAccountBinding.recyclerViewListDialogAccount.setAdapter(accountAdapter);

            accountDialog.show();



        });


        return binding.getRoot();
    }



    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Format the selected date

                        calendar.set(Calendar.DAY_OF_MONTH,view.getDayOfMonth());
                        calendar.set(Calendar.MONTH,view.getMonth());
                        calendar.set(Calendar.YEAR,view.getYear());

                        transaction.setDateSelect(calendar.getTime());

                        String formattedDate = formatDate(year, month, dayOfMonth);// format date like this(04, May, 2016);
//                        selectDateInputEditText.setText(formattedDate);
                        binding.selectDateInputEditText.setText(formattedDate);
                    }
                }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private String formatDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);
        return dateFormat.format(calendar.getTime());
    }

}