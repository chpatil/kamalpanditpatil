package com.kpp.kamalpanditpatil.ui.activities.utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.kpp.kamalpanditpatil.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DaterangeDialog extends Dialog {
    List<Date> datelist;
    String startDate, endDate;
    Context context;

    public DaterangeDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Date start = new Date("2018-11-01");
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView datePicker = findViewById(R.id.calendar);
        datePicker.init(start, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);
        datelist = datePicker.getSelectedDates();

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(datelist.get(0));
                startDate = calSelected.get(Calendar.YEAR)
                        + "-" + (calSelected.get(Calendar.MONTH) + 1)
                        + "-" + calSelected.get(Calendar.DAY_OF_MONTH);
                calSelected.setTime(datelist.get(datelist.size() - 1));
                endDate = calSelected.get(Calendar.YEAR)
                        + "-" + (calSelected.get(Calendar.MONTH) + 1)
                        + "-" + calSelected.get(Calendar.DAY_OF_MONTH);


            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        SharedPreferences settings = context.getSharedPreferences("STARTANDENDDATE",
                MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("startdate", startDate);
        prefEditor.putString("enddate", endDate);
        prefEditor.commit();
        dismiss();
    }
}
