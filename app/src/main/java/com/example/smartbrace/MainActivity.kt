package com.example.smartbrace

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {

    //Database Variables - LogData
    lateinit var refMain : DatabaseReference
    lateinit var formList : MutableList<Form>

    //Initial Settings Reference
    lateinit var reffSettings : DatabaseReference
    var first = true

    //Notification Variables
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    val channelID = "com.example.smartbrace"
    val description = "Non-Ergonomic Form Detected!"

    //App Vibration
    var vibrate = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //upon first boot, set initial settings
        reffSettings = FirebaseDatabase.getInstance().getReference("VibrateMotor")
        if(first) {
            reffSettings.child("Vibrate").setValue("ON")
            reffSettings.child("Pattern").setValue("A")
            first = false
        }

        refMain = FirebaseDatabase.getInstance().getReference("LogData")
        formList = mutableListOf()


        refMain.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(f0: DataSnapshot) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                //Loading state: Loading progress bar / text
                cForm.text = "Loading"
                loadingBar.isVisible = true
                accurateBar.isVisible = false
                inaccurateBar.isVisible = false

                //Fetching Data
                if (f0.exists()) {
                    formList.clear()

                    for (f in f0.children) {
                        val form = f.getValue(Form::class.java)
                        formList.add(form!!)
                        //formList.add(form!!)
                        d("Formlisted", form.toString())
                    }

                    val current = formList.last()

                    //5 second timer for Inaccurate form
                    if(current.form == "Accurate"){
                        progressBarUpdate(current.form)
                    }
                    else if(current.form =="Inaccurate"){

                        //TIMER - CURRENTLY SET TO 0
                        Timer("Load Wait", false).schedule(0){
                            if(current.form == "Inaccurate")
                                progressBarUpdate(current.form)
                        }
                    }
                }
            }
        })

        //Log Report
        logButton.setOnClickListener {
            d("main", "Log Tracker button was pressed!")
            startActivity(Intent(this, LogTracker::class.java))
        }

        //Sensor Reports
        srButton.setOnClickListener {
            d("main", "Sensor Report button was pressed!")
            startActivity(Intent(this, SensorReports::class.java))
        }

        //Discover Page
        discButton.setOnClickListener {
            d("main", "Discover button pressed")
            startActivity(Intent(this, Discover::class.java))
        }
        fab.setOnClickListener{
            val intent2 = Intent(this@MainActivity, SBWebView::class.java)

            intent2.putExtra("link",
                "https://aqamar.s3.ca-central-1.amazonaws.com/index.html")
            d("main", "Website link button was pressed!")
            startActivity(intent2)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        //return super.onCreateOptionsMenu(menu)
        return true
    }

    //Progress Bar Function
    fun progressBarUpdate(current: String){
        runOnUiThread {
            //Accurate state: Green progress bar
            if (current == "Accurate") {
                cForm.text = current + " Form"

                accurateBar.isVisible = true
                loadingBar.isVisible = false
                inaccurateBar.isVisible = false
            }
            //Inaccurate state: Red progress bar
            else if (current == "Inaccurate") {
                inaccurateBar.isVisible = true
                loadingBar.isVisible = false
                accurateBar.isVisible = false

                cForm.text = current + " Form"

                notificationBuilder()
                notificationManager.notify(123, builder.build())

                /*Timer("SettingUp", false).schedule(5000) {
                    if(current=="Inaccurate"){}
                }*/
            }
        }
    }

    private fun notificationBuilder(){

        var title = "Posture Notifier"
        var message = "Non-Ergonomic Posture Detected"

        //Notification
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        val intentN = Intent(this,LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intentN,
            PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelID, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.sblogo_small)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.sblogo_large))
                .setContentIntent(pendingIntent)

        }
    }
    //
    //fun vibrateSettings(value:Boolean){ }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent1 = Intent(this@MainActivity, SBWebView::class.java)
        val id = item.itemId

        when(id){

            //WebSite option
            R.id.wSite -> {intent1.putExtra("link",
                "https://aqamar.s3.ca-central-1.amazonaws.com/index.html")
                d("main", "Website link button was pressed!")
                startActivity(intent1)}

            R.id.action_settings -> {
                d("main", "Settings menu button was pressed!")
                startActivity(Intent(this, Settings::class.java ))}
        }
        return true
    }
}


