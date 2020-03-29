package com.fathom.nfs;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetAppointment extends Fragment {


    private LinearLayout setAppointmentContent;
    private CalendarView mCalendarView;
    private Calendar calendar;
    private Spinner startTime;
    private Spinner startAmPm;
    private Spinner endTime;
    private Spinner endAmPm;
    private Button bookAppointment;
    private ImageView backButton;

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
        startTime = view.findViewById(R.id.startTime);
        startAmPm = view.findViewById(R.id.startAMPM);
        endTime = view.findViewById(R.id.endTime);
        endAmPm = view.findViewById(R.id.endAMPM);
        bookAppointment = view.findViewById(R.id.appointmentBooking);
        backButton = view.findViewById(R.id.backButtontoDoctorDetailed);

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, getResources().getStringArray(R.array.timings));
        ArrayAdapter<String> AmPmAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, getResources().getStringArray(R.array.AMPM));
        timeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        AmPmAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        startTime.setAdapter(timeAdapter);
        endTime.setAdapter(timeAdapter);

        startAmPm.setAdapter(AmPmAdapter);
        endAmPm.setAdapter(AmPmAdapter);


        Date date = new Date();

        try {
            mCalendarView.setDate(calendar.getTime());
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        showAppointmentsForTheDay(calendar.getTime());

        Toast.makeText(getContext(),"Calender is "+ calendar.getTime()   , Toast.LENGTH_SHORT).show();


        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                showAppointmentsForTheDay(eventDay.getCalendar().getTime());

            }
        });




        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigateUp();
            }
        });

        ViewCompat.setLayoutDirection(setAppointmentContent, ViewCompat.LAYOUT_DIRECTION_LTR);


        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Appointment is " +startTime.getSelectedItem().toString()+ " "+ startAmPm.getSelectedItem().toString() +
                        " to "+ endTime.getSelectedItem().toString()+ " "+ endAmPm.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
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


    private void creatEventOnCalender() {

//        Event event = new Event()
//                .setSummary("Google I/O 2015")
//                .setLocation("800 Howard St., San Francisco, CA 94103")
//                .setDescription("A chance to hear more about Google's developer products.");
//
//        DateTime startDateTime = new DateTime("2015-05-28T09:00:00-07:00");
//        EventDateTime start = new EventDateTime()
//                .setDateTime(startDateTime)
//                .setTimeZone("America/Los_Angeles");
//        event.setStart(start);
//
//        DateTime endDateTime = new DateTime("2015-05-28T17:00:00-07:00");
//        EventDateTime end = new EventDateTime()
//                .setDateTime(endDateTime)
//                .setTimeZone("America/Los_Angeles");
//        event.setEnd(end);
//
//        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
//        event.setRecurrence(Arrays.asList(recurrence));
//
//        EventAttendee[] attendees = new EventAttendee[] {
//                new EventAttendee().setEmail("lpage@example.com"),
//                new EventAttendee().setEmail("sbrin@example.com"),
//        };
//        event.setAttendees(Arrays.asList(attendees));
//
//        EventReminder[] reminderOverrides = new EventReminder[] {
//                new EventReminder().setMethod("email").setMinutes(24 * 60),
//                new EventReminder().setMethod("popup").setMinutes(10),
//        };
//        Event.Reminders reminders = new Event.Reminders()
//                .setUseDefault(false)
//                .setOverrides(Arrays.asList(reminderOverrides));
//        event.setReminders(reminders);
//
//        String calendarId = "primary";
//        event = service.events().insert(calendarId, event).execute();
//        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }
}
