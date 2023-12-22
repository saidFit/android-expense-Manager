package com.example.expensemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.databinding.AccountSelectionBinding;
import com.example.expensemanager.databinding.CategorySelectionBinding;
import com.example.expensemanager.models.Account;
import com.example.expensemanager.models.Category;

import java.util.ArrayList;


public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    Context context;
    ArrayList<Account> accounts;


    public interface AccountClickListener {
        void onAccountClickListener(Account account);
    }

    AccountClickListener accountClickListener;

    public AccountAdapter(Context context,ArrayList<Account> accounts,AccountClickListener accountClickListener){
        this.context = context;
        this.accounts = accounts;
        this.accountClickListener = accountClickListener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.account_selection,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {

        Account account = accounts.get(position);
        holder.binding.name.setText(account.getName());

        holder.itemView.setOnClickListener(i ->{
            accountClickListener.onAccountClickListener(account);
        });
    }


    @Override
    public int getItemCount() {
        return accounts.size(); // if you're return 0 means your categories items is 0 and will don't able to show the result;
    }


    public class AccountViewHolder extends RecyclerView.ViewHolder {

        AccountSelectionBinding binding; // this type of binding is name of xml item(category_selection.xml);


        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AccountSelectionBinding.bind(itemView);
        }
    }

}
