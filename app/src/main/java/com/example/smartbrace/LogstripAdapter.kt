package com.example.smartbrace

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import android.util.Log.d

class LogstripAdapter(val mCtx : Context, val layoutId: Int, val logList: List<Log>)
    :ArrayAdapter<Log>(mCtx, layoutId, logList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //checking if class called
        d("adapter", "class was called")


        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(layoutId,null)

        val dateTime = view.findViewById<TextView>(R.id.timeText)
        val form = view.findViewById<TextView>(R.id.formText)
        val angle = view.findViewById<TextView>(R.id.angleText)

        //Reversing list so last in first out
        var logListR = logList.reversed()
        val log = logListR[position]
        //------------------------------    Testing last index

        val tempB = logListR.last()
        d("indexcc", tempB.form)


        //------------------------------
        //testing if log carries string
        d("adapterCheck", log.toString())

        dateTime.text = log.date
        form.text = log.form
        angle.text = log.angle + "°"

        //Checking for empty value
        if(log.date.toString() == ""){
            d("adapterTextbox", log.angle)
        }

        //testing data retrieval
        d("adapterstr", log.date.toString()+"includes")
        d("adapterstr", log.form.toString()+"includes")
        d("adapterstr", log.angle.toString()+"includes")

        return view
        //return super.getView(position, convertView, parent)
    }
}