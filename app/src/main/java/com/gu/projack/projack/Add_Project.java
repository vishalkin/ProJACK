package com.gu.projack.projack;

import android.app.DatePickerDialog;
import android.app.Dialog;
import java.util.Calendar;


import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Project extends AppCompatActivity{

    private Button btnAddProjects;
    private EditText editName, editDesc;
    private Spinner editType;

    Calendar calendar;
    private String date1="",date2="";
    private int year, month, day;
    private Button startDate, endDate;
    private TextView tvStartDate, tvEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_project);

        startDate = (Button) findViewById(R.id.startDate);
        endDate = (Button) findViewById(R.id.endDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        final SqlDbHandler db = new SqlDbHandler(this);

        btnAddProjects = (Button) findViewById(R.id.btnSubmit);
        editName = (EditText) findViewById(R.id.input_name);
        editDesc = (EditText) findViewById(R.id.input_desc);
        editType = (Spinner) findViewById(R.id.input_type);

        tvStartDate=(TextView) findViewById(R.id.tvStartDate);
        tvEndDate=(TextView) findViewById(R.id.tvEndDate);

        btnAddProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(TextUtils.isEmpty(editName.getText().toString())) && !(TextUtils.isEmpty(editDesc.getText().toString())) && !(tvStartDate.getText().equals("")) && !(tvEndDate.getText().equals(""))) {

                    db.addProject(new SqlProjects(editName.getText().toString(), editDesc.getText().toString(), editType.getSelectedItem().toString(), tvStartDate.getText().toString(), tvEndDate.getText().toString()));

                    editName.setText("");
                    editDesc.setText("");
                    tvStartDate.setText("");
                    tvEndDate.setText("");

                    Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                else {

                    if(TextUtils.isEmpty(editName.getText().toString())){
                        editName.setError("Enter Project Name");
                    }


                    if(TextUtils.isEmpty(editDesc.getText().toString())){
                        editDesc.setError("Enter Description");
                    }


                    if(tvStartDate.getText().equals(""))
                    {
                        tvStartDate.setError("Select Start Date");
                    }


                    if(tvEndDate.getText().equals(""))
                    {
                        tvEndDate.setError("Select End Date");
                    }
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        if (view == startDate){
            showDialog(998);
        }

        else if (view == endDate){
            showDialog(999);
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 998) {
            return new DatePickerDialog(this,
                    fromDateListener, year, month, day);
        }

        else if (id==999){
            return new DatePickerDialog(this,
                    toDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener fromDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day

                    date1=arg1+"/"+ (arg2+1) +"/"+arg3;

                    tvStartDate=(TextView) findViewById(R.id.tvStartDate);
                    tvStartDate.setText(date1);

                  //  showDate(arg1, arg2+1, arg3);

                }
            };

    private DatePickerDialog.OnDateSetListener toDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                   // showDate(arg1, arg2+1, arg3);
                    date2=arg1+"/"+ (arg2+1) +"/"+arg3;

                    tvEndDate=(TextView) findViewById(R.id.tvEndDate);
                    tvEndDate.setText(date2);
                }
            };


    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(Add_Project.this, MainActivity.class));
        return;
    }

  }