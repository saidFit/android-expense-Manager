package com.example.expensemanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.databinding.RowTransitionBinding;
import com.example.expensemanager.models.Category;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.view.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    Context context;
    RealmResults<Transaction> transactions;

    public TransactionAdapter(Context context, RealmResults<Transaction> transactions){
        this.context = context;
        this.transactions = transactions;
    }


    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transition,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {

        Transaction transaction = transactions.get(position);

        holder.binding.icon.setImageResource(transaction.getIcon());

        holder.binding.categoryTransaction.setText(transaction.getCategory());
//
        holder.binding.accountTransaction.setText(transaction.getAccount());
//
        holder.binding.dateTransaction.setText(Helper.formData(transaction.getDateSelect()));
//

//
        holder.binding.accountTransaction.setBackgroundTintList(context.getColorStateList(Constants.colorAccount(transaction.getAccount())));

        if(transaction.getTransactionType().equals(Constants.INCOME)){
            holder.binding.amountTransaction.setText(String.valueOf(transaction.getAmount()));
            holder.binding.amountTransaction.setTextColor(context.getColor(R.color.green_600));
        }else if (transaction.getTransactionType().equals(Constants.EXPENSE)){
            holder.binding.amountTransaction.setText("-"+String.valueOf(transaction.getAmount()));
            holder.binding.amountTransaction.setTextColor(context.getColor(R.color.red));
        }

        Category category = Helper.matchCategory(Helper.getCategories(),transaction.getCategory());

           holder.binding.icon.setBackgroundTintList(context.getColorStateList(category.getBackgroundColor()));


           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   AlertDialog deleteTransaction = new AlertDialog.Builder(context).create();
                   deleteTransaction.setTitle("Delete Transaction");
                   deleteTransaction.setMessage("are you sure to delete this specific transaction?");
                   deleteTransaction.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                           ((MainActivity)context).transactionViewModel.deleteTransaction(transaction);
                           ((MainActivity)context).getTransactionInAddFragment();

                       }
                   });
                   deleteTransaction.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           deleteTransaction.dismiss();
                       }
                   });
                   deleteTransaction.show();
               }
           });

//           holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//               @Override
//               public boolean onLongClick(View view) {
//                   AlertDialog deleteTransaction = new AlertDialog.Builder(context).create();
//                   deleteTransaction.setTitle("Delete Transaction");
//                   deleteTransaction.setMessage("are you sure to delete this specific transaction?");
//                   deleteTransaction.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
//                       @Override
//                       public void onClick(DialogInterface dialogInterface, int i) {
//
//                       }
//                   });
//                   deleteTransaction.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
//                       @Override
//                       public void onClick(DialogInterface dialogInterface, int i) {
//                           deleteTransaction.dismiss();
//                       }
//                   });
//                   deleteTransaction.show();
//                   return false;
//               }
//           });


    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        RowTransitionBinding binding;

        public TransactionViewHolder(@NonNull View itemView){
            super(itemView);
            binding = RowTransitionBinding.bind(itemView);
        }

    }
}
