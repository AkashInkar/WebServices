package com.riztech.webserviceseptember.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.riztech.webserviceseptember.R;
import com.riztech.webserviceseptember.model.BaseResponse;
import com.riztech.webserviceseptember.model.Employee;
import com.riztech.webserviceseptember.service.ApiClient;
import com.riztech.webserviceseptember.service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    EditText edtName, edtAddress, edtPhone, edtSalary, edtDesignation;
    Button btnupdate;
    Employee employee;
    ProgressBar progress;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update);
        employee = getIntent().getParcelableExtra(GetAllEmployeeActivity.DATA);
        id = employee.getId();
        edtAddress = findViewById(R.id.edtAddress);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhoneNumber);
        edtSalary = findViewById(R.id.edtSalary);
        edtDesignation = findViewById(R.id.edtDesignation);


        edtAddress.setText(employee.getAddress());
        edtName.setText(employee.getName());
        edtPhone.setText(employee.getPhoneNumber());
        edtSalary.setText(String.valueOf(employee.getSalary()));
        edtDesignation.setText(employee.getDesignation());


    }

    public void updateEmployee(View view) {
        String name = edtName.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phoneNumber = edtPhone.getText().toString().trim();
        String salString = edtSalary.getText().toString().trim();
        String designation = edtDesignation.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(phoneNumber)
                || TextUtils.isEmpty(salString) || TextUtils.isEmpty(designation)) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long salary = Long.parseLong(salString);

        Employee employee = new Employee(name, address, phoneNumber, salary, designation);
        employee.setId(id);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<BaseResponse> call = apiInterface.updateEmployee(id, employee);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                progress.setVisibility(View.GONE);
                setResult(RESULT_OK);
                finish();

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
