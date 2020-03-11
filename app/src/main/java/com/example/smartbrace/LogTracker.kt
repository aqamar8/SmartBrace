package com.example.smartbrace

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import android.util.Log.d
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_log_tracker.view.*

class LogTracker : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var logList : MutableList<Log>
    lateinit var listview: ListView

    var text : String = "" //testing data retrieval in logcat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //overide fun onActivityResult(requestCode: Int,resultCode: Int, data: Intent ){}
        //var bundle :Bundle ?=intent.extras
        //var message = bundle!!.getString("layout")

        //if(message == "yes"){
            setContentView(R.layout.activity_log_tracker)
        //}else{
        //    setContentView(R.layout.activity_main)
        //}

        logList = mutableListOf()
        listview = findViewById(R.id.listView)
        ref = FirebaseDatabase.getInstance().getReference("LogData")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                if (p0.exists()){
                    logList.clear()

                    for (l in p0.children ){
                        val log = l.getValue(Log::class.java)
                        logList.add(log!!)
                        d("loglisted", log.toString())
                    }

                    //----------//testing data retrieval in logcat
                    for (i in p0.children){
                        text = i.getValue().toString()
                        d("logged", text)
                    }
                    //----------//
                    //setting adapter to generate and layout on logstrip.xml
                    //then setting listview adapter to this adapter
                    val adapter = LogstripAdapter(this@LogTracker, R.layout.logstrip, logList)
                    listview.adapter = adapter
                }
            }

        })


    }
}