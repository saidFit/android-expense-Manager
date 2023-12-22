package com.example.expensemanager.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.expensemanager.R;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import io.realm.Realm;
import io.realm.RealmResults;

public class TransactionViewModel extends AndroidViewModel {



   public MutableLiveData<RealmResults<Transaction>> transactions = new MutableLiveData<>();
   public MutableLiveData<RealmResults<Transaction>> categoryTransactions = new MutableLiveData<>();
     Realm realm;
     Calendar calendar;
    public TransactionViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setUpDb();
    }

    void setUpDb(){
        realm = Realm.getDefaultInstance();
    }

    // Method to insert a transaction into the database
    public void insertTransaction(Transaction transaction) {
        realm.beginTransaction();

        Calendar ThreeDaysAgo = Calendar.getInstance();
        ThreeDaysAgo.add(Calendar.DATE,-3);

        realm.copyToRealmOrUpdate(transaction);
//        realm.copyToRealmOrUpdate(new Transaction("INCOME","Investment","Cash",600,ThreeDaysAgo.getTime(),R.drawable.ic_investment));
//        realm.copyToRealmOrUpdate(new Transaction("EXPENSE","Salary","PayTM",200,new Date(),R.drawable.ic_salary));
//        realm.copyToRealmOrUpdate(new Transaction("INCOME","Business","Bank",300,new Date(), R.drawable.ic_business));
//        realm.copyToRealmOrUpdate(new Transaction("EXPENSE","Business","Card",760,ThreeDaysAgo.getTime(),R.drawable.ic_business));

        realm.commitTransaction();

    }

    // Method to retrieve all transactions from the database
//    public void getAllTransactions(Calendar calendar) {
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//
//        RealmResults<Transaction> results = realm.where(Transaction.class)
//                .greaterThanOrEqualTo("dateSelect", calendar.getTime())
//                .lessThanOrEqualTo("dateSelect", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
//                .findAll();
//        transactions.setValue(results);
//    }

    public void getAllCategoryTransactions(Calendar calendar,String typeOfTransaction) {
        this.calendar = calendar;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        RealmResults<Transaction> results = null;
        if (Constants.statusLayout == 0) {
            // If tabLayout is 0 (daily), get transactions for the selected day
            // Next day
            results = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("dateSelect", calendar.getTime())
                    .lessThanOrEqualTo("dateSelect", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("transactionType",typeOfTransaction)
                    .findAll();

        }
        else if (Constants.statusLayout == 1) {
            // If tabLayout is 1 (monthly), get transactions for the entire month
            calendar.set(Calendar.DAY_OF_MONTH, 1); // Set to the first day of the month
            Date startCalendar = calendar.getTime();
            calendar.add(Calendar.MONTH, 1); // Next month
            calendar.add(Calendar.DAY_OF_MONTH, -1); // Set to the last day of the current month
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date endCalendar = calendar.getTime();

            results = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("dateSelect", startCalendar)
                    .lessThanOrEqualTo("dateSelect", endCalendar)
                    .equalTo("transactionType",typeOfTransaction)
                    .findAll();
        }



        categoryTransactions.setValue(results);
    }

    public void getAllTransactions(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        RealmResults<Transaction> results = null;
        Log.d("DateDebug", "Start Date: " + calendar.getTime());
        Log.d("DateDebug", String.valueOf(Constants.tabLayout));

        if (Constants.tabLayout == 0) {
            // If tabLayout is 0 (daily), get transactions for the selected day
             // Next day
              results = realm.where(Transaction.class)
                .greaterThanOrEqualTo("dateSelect", calendar.getTime())
                .lessThanOrEqualTo("dateSelect", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                .findAll();

        }
        else if (Constants.tabLayout == 1) {
            // If tabLayout is 1 (monthly), get transactions for the entire month
            calendar.set(Calendar.DAY_OF_MONTH, 1); // Set to the first day of the month
            Date startCalendar = calendar.getTime();
            calendar.add(Calendar.MONTH, 1); // Next month
            calendar.add(Calendar.DAY_OF_MONTH, -1); // Set to the last day of the current month
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date endCalendar = calendar.getTime();
            Log.d("startV", String.valueOf(startCalendar));
            Log.d("endV", String.valueOf(endCalendar));
            results = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("dateSelect", startCalendar)
                    .lessThanOrEqualTo("dateSelect", endCalendar)
                    .findAll();
        }

//        else if (Constants.tabLayout == 1) {
//            // If tabLayout is 1 (monthly), get transactions for the entire month
//            calendar.set(Calendar.DAY_OF_MONTH,0);
//            Date startCalendar = calendar.getTime();
//            calendar.add(Calendar.MONTH, 1); // Next month
//            Date endCalendar = calendar.getTime();
//            Log.d("startV", String.valueOf(startCalendar));
//            Log.d("endV", String.valueOf(endCalendar));
//             results = realm.where(Transaction.class)
//                    .greaterThanOrEqualTo("dateSelect", startCalendar)
//                    .lessThanOrEqualTo("dateSelect", endCalendar)
//                    .findAll();
//        }



        transactions.setValue(results);
    }



    public void deleteTransaction(Transaction transaction){
        realm.beginTransaction();
        transaction.deleteFromRealm();
        realm.commitTransaction();
  }


    // Method to generate a unique ID for the primary key
    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (realm != null) {
            realm.close();
        }
    }
}
