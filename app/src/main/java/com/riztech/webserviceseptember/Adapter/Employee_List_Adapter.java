package com.riztech.webserviceseptember.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riztech.webserviceseptember.R;
import com.riztech.webserviceseptember.model.Employee;

import java.util.List;

/**
 * Created by felix-its on 08-09-2018.
 */

public class Employee_List_Adapter extends RecyclerView.Adapter<EmployeeView_Holder> {
    List<Employee> mEmployee_list;
    EmployeeClickListener mListener;

    public Employee_List_Adapter(List<Employee> employees, EmployeeClickListener listener) {
        mEmployee_list = employees;
        mListener = listener;
    }

    @NonNull
    @Override
    public EmployeeView_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_data_listlayout, parent, false);
        EmployeeView_Holder employeeView_holder = new EmployeeView_Holder(view,mListener);
        return employeeView_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeView_Holder holder, int position) {
        Employee employee = mEmployee_list.get(position);
        holder.txtName.setText(employee.getName());
        holder.txtAddress.setText(employee.getAddress());
        holder.txtPhone.setText(employee.getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return mEmployee_list.size();
    }

    public Employee getItemAtPosition (int position){
        return mEmployee_list.get(position);
    }
}
