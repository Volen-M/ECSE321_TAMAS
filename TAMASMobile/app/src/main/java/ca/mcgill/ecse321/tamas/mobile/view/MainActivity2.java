package ca.mcgill.ecse321.tamas.mobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;

import tamas.ecse321.mcgill.ca.view.R;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private static final String EXTRA_MESSAGE = "tamas.ecse321.mcgill.ca.ca.mcgill.ecse321.tamas.mobile.view.MESSAGE";
    CheckBox monday, tuesday, wednesday, thursday, friday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monday = (CheckBox) findViewById(R.id.androidChkBox_monday);
        tuesday = (CheckBox) findViewById(R.id.androidChkBox_tuesday);
        wednesday =(CheckBox) findViewById(R.id.androidChkBox_wednesday);;
        thursday =(CheckBox) findViewById(R.id.androidChkBox_thursday);;
        friday =(CheckBox) findViewById(R.id.androidChkBox_friday); ;
    }

    public void viewPosting(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        //Spinner s = (Spinner) findViewById(R.id.posting_spinner);
        //String posting = s.getSelectedItem().toString();
        //intent.putExtra(EXTRA_MESSAGE, posting);
        startActivity(intent);
    }

    public void viewProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void showTimePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getTimeFromLabel(tf.getText());
        args.putInt("id", v.getId());

        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private Time bundleToTime (Bundle bundle){
        int time_hour = bundle.getInt("hour");
        int time_min = bundle.getInt("minute");
        return new Time(time_hour,time_min,0);
    }

    private Bundle getTimeFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split(":");
        int hour = 12;
        int minute = 0;

        if (comps.length == 2) {
            hour = Integer.parseInt(comps[0]);
            minute = Integer.parseInt(comps[1]);
        }

        rtn.putInt("hour", hour);
        rtn.putInt("minute", minute);

        return rtn;
    }

    public void setTime(int id, int h, int m) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d:%02d", h, m));
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private Date bundleToDate (Bundle bundle){
        int date_day= bundle.getInt("day");
        int date_month=bundle.getInt("month");
        int date_year=bundle.getInt("year");
        return new Date(date_year,date_month,date_day);

    }

    private Bundle getDateFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);

        return rtn;
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.androidChkBox_monday:
                if (monday.isChecked())
                    Toast.makeText(getApplicationContext(), "Monday", Toast.LENGTH_LONG).show();
                break;
            case R.id.androidChkBox_tuesday:
                if (tuesday.isChecked())
                    Toast.makeText(getApplicationContext(), "Tuesday", Toast.LENGTH_LONG).show();
                break;
            case R.id.androidChkBox_wednesday:
                if (wednesday.isChecked())
                    Toast.makeText(getApplicationContext(), "Wednesday", Toast.LENGTH_LONG).show();
                break;
            case R.id.androidChkBox_thursday:
                if (thursday.isChecked())
                    Toast.makeText(getApplicationContext(), "Thursday", Toast.LENGTH_LONG).show();
                break;
            case R.id.androidChkBox_friday:
                if (friday.isChecked())
                    Toast.makeText(getApplicationContext(), "Friday", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
