package com.example.todolist

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class AddTask : AppCompatActivity() {
    lateinit var date:TextView
    lateinit var time:TextView
    val selectedDateTime = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        val title=findViewById<EditText>(R.id.title)
        val description=findViewById<EditText>(R.id.description)
        val t=title.text
        val desc=description.text
        val add=findViewById<Button>(R.id.add)
         date=findViewById<TextView>(R.id.date)
         time=findViewById<TextView>(R.id.time)
        date.setOnClickListener {
            showDatePicker()
        }
        time.setOnClickListener {
            showTimePicker()
        }

        val taskViewModel=TaskViewModel(application)
        add.setOnClickListener {


            CoroutineScope(Dispatchers.IO).launch {
                taskViewModel.insertTask(Task(0,t.toString(),desc.toString(),date.text.toString(),time.text.toString()))
            }
            scheduleNotification()
            startActivity(Intent(this,MainActivity::class.java))

        }


    }
//    private fun showDatePicker() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val datePickerDialog = DatePickerDialog(
//            this,
//            { _, yearSelected, monthOfYear, dayOfMonth ->
//                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$yearSelected"
//                date.text = selectedDate
//            },
//            year,
//            month,
//            day
//        )
//        datePickerDialog.show()
//        selectedDateTime.set(Calendar.YEAR, year)
//        selectedDateTime.set(Calendar.MONTH, month)
//        selectedDateTime.set(Calendar.DAY_OF_MONTH, day)
//    }
//
//    private fun showTimePicker() {
//        val calendar = Calendar.getInstance()
//        val hour = calendar.get(Calendar.HOUR_OF_DAY)
//        val minute = calendar.get(Calendar.MINUTE)
//
//        val timePickerDialog = TimePickerDialog(
//            this,
//            { _, hourOfDay, minuteOfDay ->
//                val selectedTime = "$hourOfDay:$minuteOfDay"
//                time.text = selectedTime
//            },
//            hour,
//            minute,
//            true // true for 24-hour format, false for AM/PM
//        )
//        timePickerDialog.show()
//
//    }
private fun showDatePicker() {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        this,
        { _, yearSelected, monthOfYear, dayOfMonth ->
            selectedDateTime.set(Calendar.YEAR, yearSelected)
            selectedDateTime.set(Calendar.MONTH, monthOfYear)
            selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$yearSelected"
            date.text = selectedDate
        },
        year,
        month,
        day
    )
    datePickerDialog.show()
}

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minuteOfDay ->
                selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedDateTime.set(Calendar.MINUTE, minuteOfDay)
                selectedDateTime.set(Calendar.SECOND, 0)

                val selectedTime = "$hourOfDay:$minuteOfDay"
                time.text = selectedTime
            },
            hour,
            minute,
            true // true for 24-hour format, false for AM/PM
        )
        timePickerDialog.show()
    }

    private fun scheduleNotification() {
        val calendar = Calendar.getInstance()
        val selectedTimeInMillis = selectedDateTime.timeInMillis
        val timeDifference = selectedTimeInMillis - calendar.timeInMillis
        calendar.add(Calendar.SECOND, 5) // Schedule after 5 seconds (for testing)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as? AlarmManager

//        val intent = Intent(this, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
//
//        if (alarmManager != null) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, selectedTimeInMillis, pendingIntent)
//        }
//        val timeDifference = selectedDateTime.timeInMillis - System.currentTimeMillis()

//        if (timeDifference > 0) { // Ensure the selected time is in the future
//            val intent = Intent(this, AlarmReceiver::class.java)
//            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
//                0)
//
//            // Schedule the notification at the selected time
//            alarmManager?.setExact(AlarmManager.RTC_WAKEUP, selectedDateTime.timeInMillis, pendingIntent)
//        } if (timeDifference > 0) {
//    val intent = Intent(this, AlarmReceiver::class.java)
//    val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//
//    alarmManager?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, selectedDateTime.timeInMillis, pendingIntent)
//}
        if (timeDifference > 0) {
            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE)
            alarmManager?.setAlarmClock(AlarmManager.AlarmClockInfo(selectedDateTime.timeInMillis, pendingIntent),
                pendingIntent)

            //alarmManager?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, selectedDateTime.timeInMillis, pendingIntent)
        }
        else {
            // Handle the case where the selected time is in the past
            Toast.makeText(this, "Selected time is in the past", Toast.LENGTH_SHORT).show()
        }
    }


}