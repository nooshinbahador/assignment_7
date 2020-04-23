package com.example.assignment4

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_time_activity.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import java.util.*


class time_activity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_activity)


        b_stop.setOnClickListener {
            val alfa = "Stop Time"
            editText.setText(alfa)
        }

        b_start.setOnClickListener {
            val alfa = "Start Time"
            editText.setText(alfa)
        }



        b_eating.setOnClickListener {
            val beta = "Eating"
            editText2.setText(beta)
        }

        b_walking.setOnClickListener {
            val beta = "Walking"
            editText2.setText(beta)
        }

        b_running.setOnClickListener {
            val beta = "Running"
            editText2.setText(beta)
        }


        button.setOnClickListener {

            //val calendar = SimpleDateFormat("d MMM yyyy HH:mm:ss", Locale.getDefault()).format(Date())
            //val calendar = LocalDateTime.now()
            val calendar = Calendar.getInstance()
            val ss = calendar.timeInMillis


            if (editText.text.toString() !="") {


                val reminder = Reminder(

                    uid = null,
                    time = ss,
                    location = editText2.text.toString(),
                    message= editText.text.toString()
                )

                doAsync {
                    val db = Room.databaseBuilder(applicationContext,AppDatabase::class.java, "reminders").build()
                    db.reminderDao().insert(reminder)

                    setAlarm(reminder.time!!, reminder.message)

                    finish()

                }
            } else{
                toast("wrong data")
            }



        }


    }




    private fun setAlarm(time: Long, Message: String?){
        val intent = Intent(this, ReminderReceiver::class.java)
        intent.putExtra("message",Message)
        val pendingIntent = PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_ONE_SHOT)
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC,time,pendingIntent)
        }
        runOnUiThread{toast("reminder is created")}
    }
}

