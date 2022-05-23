package com.android.zpp.viewbnidtest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.zpp.viewbnidtest.databinding.ListYtemBinding;

import java.util.ArrayList;

/**
 * @ProjectName: ViewBnidTest
 * @Package: com.android.zpp.viewbnidtest
 * @ClassName: MyAdapter
 * @Description:
 * @Author: zpp
 * @CreateDate: 2022/5/6 11:01
 * @UpdateUser:
 * @UpdateDate: 2022/5/6 11:01
 * @UpdateRemark:
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ListYtemBinding listYtemBinding;
    ArrayList<String> arrayList;
    Context context;

    public MyAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        listYtemBinding = ListYtemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(listYtemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        listYtemBinding.itemText.setText(arrayList.get(position));
        listYtemBinding.itemText.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setClass(context,NavigationActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull ListYtemBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
