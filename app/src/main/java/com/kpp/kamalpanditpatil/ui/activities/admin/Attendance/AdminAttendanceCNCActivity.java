package com.kpp.kamalpanditpatil.ui.activities.admin.Attendance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.ui.activities.utilities.AttendanceDetailActivity;

import java.util.Calendar;

public class AdminAttendanceCNCActivity extends AppCompatActivity {
    static final int Dialog_id = 0;
    Button dateButton, datewiseButton;
    String Day, Date, DatabaseDate;
    ProgressDialog pDialog;
    int year_x, month_x, day_x;
    int day, month, dyear;
    String department = "CNC";
    private ListView lv;
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            selectedMonth = selectedMonth + 1;
            if (selectedDay < 10) {
                Day = "0" + selectedDay;

            } else {
                Day = String.valueOf(selectedDay);
            }
            month_x = selectedMonth;
            year_x = selectedYear;
            dateButton.setText(Day + "-" + (selectedMonth) + "-" + selectedYear);
            DatabaseDate = selectedYear + "-" + (selectedMonth) + "-" + Day;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance_cnc);
        Toolbar toolbar = findViewById(R.id.adminattendanceCNCworkerToolbar);
        toolbar.setTitle("CNC ATTENDANCE DATEWISE");
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        month_x = month_x + 1;
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        if (day_x < 10) {
            Day = "0" + day_x;
        } else {
            Day = String.valueOf(day_x);
        }
        Date = Day + "-" + month_x + "-" + year_x;
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);
        DatabaseDate = year_x + "-" + month_x + "-" + Day;
        dateButton = findViewById(R.id.adminAttendanceCNCdateButton);
        dateButton.setText(Date);
        datewiseButton = findViewById(R.id.adminAttendanceCNCdatewiseButton);
        datewiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAttendanceCNCActivity.this, AttendanceDetailActivity.class);
                intent.putExtra("department", department);
                intent.putExtra("date", DatabaseDate);
                startActivity(intent);
                finish();
            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Dialog_id);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AdminAttendanceBaseActivity.class));
        finish();
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, dyear, month, day);
    }
}
