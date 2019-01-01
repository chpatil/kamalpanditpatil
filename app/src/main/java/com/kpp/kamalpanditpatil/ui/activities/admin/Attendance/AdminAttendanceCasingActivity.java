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
import android.widget.Toast;

import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.ui.activities.utilities.AttendanceDatewiseDetailActivity;
import com.kpp.kamalpanditpatil.ui.activities.utilities.AttendanceDialog;
import com.kpp.kamalpanditpatil.ui.activities.utilities.AttendanceListActivity;
import com.kpp.kamalpanditpatil.ui.activities.utilities.AttendanceOvertimeDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminAttendanceCasingActivity extends AppCompatActivity {
    static final int Dialog_id1 = 0;
    static final int Dialog_id2 = 1;
    static final int Dialog_id3 = 2;
    Button dateButton, datewiseButton, personwise, daterange1, daterange2, overtimewise;
    String Day, Date, today, DatabaseDate, DatabaseDaterange1, DatabaseDaterange2, startdate, enddate;
    ProgressDialog pDialog;
    int year_x, month_x, day_x;
    int day, month, dyear;
    AttendanceDialog attendanceDialog;
    String department = "Casing";
    private ListView lv;
    java.util.Date today1;
    java.util.Date date1;
    java.util.Date date2;
    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
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

    private DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {
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
            daterange1.setText(Day + "-" + (selectedMonth) + "-" + selectedYear);
            DatabaseDaterange1 = selectedYear + "-" + (selectedMonth) + "-" + Day;
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener3 = new DatePickerDialog.OnDateSetListener() {
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
            daterange2.setText(Day + "-" + (selectedMonth) + "-" + selectedYear);
            DatabaseDaterange2 = selectedYear + "-" + (selectedMonth) + "-" + Day;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance_casing);
        Toolbar toolbar = findViewById(R.id.adminattendancecasingworkerToolbar);
        toolbar.setTitle("CASING ATTENDANCE DATEWISE");
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
        today = year_x + "-" + month_x + "-" + Day;
        DatabaseDate = year_x + "-" + month_x + "-" + Day;
        daterange1 = findViewById(R.id.adminAttendanceCasingdaterangeButton1);
        daterange2 = findViewById(R.id.adminAttendanceCasingdaterangeButton2);
        dateButton = findViewById(R.id.adminAttendanceCasingdateButton);
        personwise = findViewById(R.id.adminAttendanceCasingpersonwisewiseButton);
        overtimewise = findViewById(R.id.adminAttendanceCasingOvertimewisewiseButton);
        dateButton.setText(Date);
        datewiseButton = findViewById(R.id.adminAttendanceCasingdatewiseButton);
        datewiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    today1 = sdf.parse(today);
                    date1 = sdf.parse(DatabaseDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date1.before(today1) || date1.equals(today1)) {
                    Intent intent = new Intent(AdminAttendanceCasingActivity.this, AttendanceDatewiseDetailActivity.class);
                    intent.putExtra("department", department);
                    intent.putExtra("date", DatabaseDate);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AdminAttendanceCasingActivity.this, "Please set after date properly", Toast.LENGTH_SHORT).show();

                }
            }
        });
        personwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (DatabaseDaterange1 == null || DatabaseDaterange2 == null) {
                    Toast.makeText(AdminAttendanceCasingActivity.this, "Please set date properly", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        today1 = sdf.parse(today);
                        date1 = sdf.parse(DatabaseDaterange1);
                        date2 = sdf.parse(DatabaseDaterange2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (date1.before(date2) && date1.before(today1) && (date2.equals(today1) || date2.before(today1))) {
                        Intent intent = new Intent(AdminAttendanceCasingActivity.this, AttendanceListActivity.class);
                        intent.putExtra("department", department);
                        intent.putExtra("from", DatabaseDaterange1);
                        intent.putExtra("to", DatabaseDaterange2);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(AdminAttendanceCasingActivity.this, "Please set after date properly", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        overtimewise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (DatabaseDaterange1 == null || DatabaseDaterange2 == null) {
                    Toast.makeText(AdminAttendanceCasingActivity.this, "Please set date properly", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        today1 = sdf.parse(today);
                        date1 = sdf.parse(DatabaseDaterange1);
                        date2 = sdf.parse(DatabaseDaterange2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (date1.before(date2) && date1.before(today1) && (date2.equals(today1) || date2.before(today1))) {
                        Intent intent = new Intent(AdminAttendanceCasingActivity.this, AttendanceOvertimeDetails.class);
                        intent.putExtra("department", department);
                        intent.putExtra("startdate", DatabaseDaterange1);
                        intent.putExtra("enddate", DatabaseDaterange2);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(AdminAttendanceCasingActivity.this, "Please set after date properly", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Dialog_id1);
            }
        });
        daterange1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Dialog_id2);
            }
        });
        daterange2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Dialog_id3);
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
        switch (id) {
            case 0:
                return new DatePickerDialog(this, datePickerListener1, dyear, month, day);
            case 1:
                return new DatePickerDialog(this, datePickerListener2, dyear, month, day);
            case 2:
                return new DatePickerDialog(this, datePickerListener3, dyear, month, day);
            default:
                return new DatePickerDialog(this, datePickerListener1, dyear, month, day);

        }
    }
}
