package com.riztech.webserviceseptember.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.riztech.webserviceseptember.Adapter.EmployeeClickListener;
import com.riztech.webserviceseptember.Adapter.Employee_List_Adapter;
import com.riztech.webserviceseptember.R;
import com.riztech.webserviceseptember.model.BaseResponse;
import com.riztech.webserviceseptember.model.Employee;
import com.riztech.webserviceseptember.service.ApiClient;
import com.riztech.webserviceseptember.service.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllEmployeeActivity extends AppCompatActivity implements EmployeeClickListener{
    RecyclerView rvEmployee;
    ProgressBar progress;
    Employee employee;
    List<Employee>employees;
    ApiInterface apiInterface;
    Employee_List_Adapter employee_list_adapter;

    public static final String DATA ="data";
    public static final int REQUEST_UPDATE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_employee);
        rvEmployee = findViewById(R.id.rvEmployee);
        progress =findViewById(R.id.progress);
        rvEmployee.setLayoutManager( new LinearLayoutManager(this));


         apiInterface = ApiClient.getClient().create(ApiInterface.class);
       fetchAllEmployee();

    }

    private void fetchAllEmployee() {
        Call<List<Employee>> callEmployee= apiInterface.getAllEmployee();
        progress.setVisibility(View.VISIBLE);

        callEmployee.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                progress.setVisibility(View.GONE);
                employees=response.body();
                employee_list_adapter = new Employee_List_Adapter(employees,GetAllEmployeeActivity.this);
                rvEmployee.setAdapter(employee_list_adapter);
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                progress.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        final Employee employee = employee_list_adapter.getItemAtPosition(position);

        int employeeId = employee.getId();
        Call<BaseResponse> callDelete = apiInterface.deleteEmployee(employeeId);

        progress.setVisibility(View.VISIBLE);



        callDelete.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                progress.setVisibility(View.GONE);
                BaseResponse baseResponse =response.body();
                employees.remove(employee);
                employee_list_adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                progress.setVisibility( View.GONE);

            }
        });



    }

    @Override
    public void onItemClick(int position) {
        Employee employee = employee_list_adapter.getItemAtPosition(position);
        Intent intent = new Intent(this,UpdateActivity.class);
        intent.putExtra(DATA,employee);
        startActivityForResult(intent,REQUEST_UPDATE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE && requestCode== RESULT_OK){
            fetchAllEmployee();

        }
    }


}
