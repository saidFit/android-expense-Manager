package com.example.expensemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.databinding.CategorySelectionBinding;
import com.example.expensemanager.models.Category;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<Category> categories;


    public interface CategoryClickListener {
        void onCategoryClickListener(Category category);
    }

    CategoryClickListener categoryClickListener;

    public CategoryAdapter(Context context,ArrayList<Category> categories,CategoryClickListener categoryClickListener){
        this.context = context;
        this.categories = categories;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_selection,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category category = categories.get(position);
        holder.binding.categoryName.setText(category.getName());
        holder.binding.categoryImage.setImageResource(category.getImage());
        holder.binding.categoryImage.setBackgroundTintList(context.getColorStateList(category.getBackgroundColor()));

        holder.itemView.setOnClickListener(i ->{
            categoryClickListener.onCategoryClickListener(category);
        });
    }


    @Override
    public int getItemCount() {
        return categories.size(); // if you're return 0 means your categories items is 0 and will don't able to show the result;
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        CategorySelectionBinding binding; // this type of binding is name of xml item(category_selection.xml);


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CategorySelectionBinding.bind(itemView);
        }
    }

}
