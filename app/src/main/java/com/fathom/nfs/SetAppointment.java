package com.fathom.nfs;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetAppointment extends Fragment {


    private LinearLayout setAppointmentContent;
    private CalendarView mCalendarView;
    private Calendar calendar;

    public SetAppointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setAppointmentContent = view.findViewById(R.id.setAppointmentContent);
        calendar = Calendar.getInstance();
        mCalendarView = view.findViewById(R.id.calendarView);
        Date date = new Date();

        try {
            mCalendarView.setDate(calendar.getTime());
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        showAppointmentsForTheDay(calendar.getTime());

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                showAppointmentsForTheDay(eventDay.getCalendar().getTime());

            }
        });





        ViewCompat.setLayoutDirection(setAppointmentContent, ViewCompat.LAYOUT_DIRECTION_LTR);
    }


    private void showAppointmentsForTheDay(Date date) {

        String dayOfWeek = "null";

        switch (date.getDay()) {
            case 0:
                dayOfWeek = "Sunday";
                break;
            case 1:
                dayOfWeek = "Monday";
                break;
            case 2:
                dayOfWeek = "Tuesday";
                break;
            case 3:
                dayOfWeek = "Wednesday";
                break;
            case 4:
                dayOfWeek = "Thursday";
                break;
            case 5:
                dayOfWeek = "Friday";
                break;
            case 6:
                dayOfWeek = "Saturday";


        }
        Toast.makeText(getContext(),"The day is " + dayOfWeek   , Toast.LENGTH_SHORT).show();

        ;

    }
}
