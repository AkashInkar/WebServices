package com.riztech.webserviceseptember.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.riztech.webserviceseptember.R;

/**
 * Created by felix-its on 08-09-2018.
 */

public class EmployeeView_Holder extends RecyclerView.ViewHolder{
    TextView txtName,txtAddress,txtPhone;
    Button btndelete;
    EmployeeClickListener mListener;

    public EmployeeView_Holder(View itemView, EmployeeClickListener listener) {
        super(itemView);
        mListener = listener;
        txtName = itemView.findViewById(R.id.txtName);
        txtAddress = itemView.findViewById(R.id.txtAddress);
        txtPhone = itemView.findViewById(R.id.txtPhone);
        btndelete = itemView.findViewById(R.id.btndelete);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                mListener.onDeleteClick(position);
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                mListener.onItemClick(position);
            }
        });
    }
}
