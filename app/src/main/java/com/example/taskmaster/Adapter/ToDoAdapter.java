package com.example.taskmaster.Adapter;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaster.AddNewTask;
import com.example.taskmaster.MainActivity;
import com.example.taskmaster.Model.ToDoModel;
import com.example.taskmaster.R;
import com.example.taskmaster.Utils.DataBaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyviewHolder> {
    private List<ToDoModel> mList;
    private MainActivity activity;
    private DataBaseHelper  myDB;
    public ToDoAdapter(DataBaseHelper myDB,MainActivity activity){
        this.activity=activity;
        this.myDB=myDB;

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklayout,parent,false);
        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
    final ToDoModel item=mList.get(position);
    holder.mCheckBox.setText(item.getTask());
    holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
    holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                myDB.updateStatus(item.getId(),1);
            }
            else{
                myDB.updateStatus(item.getId(),0);
            }
        }
    });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public boolean toBoolean(int num){
        return  num!=0;

    }
    public Context getContext(){
        return activity;
    }
    public void setTask(List<ToDoModel> myList){
        this.mList=myList;
        notifyDataSetChanged();
    }
    public void deleteTask(int position){
        ToDoModel item=mList.get(position);
        myDB.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        ToDoModel item=mList.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());

        AddNewTask task=new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());

    }
    public static class MyviewHolder extends ViewHolder{
    CheckBox mCheckBox;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox =itemView.findViewById(R.id.mcheckbox);
        }
    }
}
