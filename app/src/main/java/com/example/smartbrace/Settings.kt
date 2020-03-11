package com.example.smartbrace

import android.app.Dialog
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseError
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_settings.*


internal lateinit var rDialog: Dialog
internal lateinit var conButton: Button

class Settings : AppCompatActivity() {

    private lateinit var reffLD : DatabaseReference
    private lateinit var reffGyro : DatabaseReference
    private lateinit var reffEmg : DatabaseReference
    private lateinit var reffVM : DatabaseReference

    private lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //FireBase references
        // NOTE the to delete deletes the entire database, Arduino will recreate if not found
        reffLD = FirebaseDatabase.getInstance().getReference("LogData")
        reffGyro = FirebaseDatabase.getInstance().getReference("GyroData")
        reffVM = FirebaseDatabase.getInstance().getReference("VibrateMotor")
        reffEmg = FirebaseDatabase.getInstance().getReference("emg")

        mDatabase = FirebaseDatabase.getInstance().getReference("VibrateMotor").
            child("Vibrate")
        radioButton1.isClickable = true
        radioButton2.isClickable = true

        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val current = dataSnapshot.value as String?
                // do your stuff here with value
                if(current=="ON"){
                    switch1.isChecked = true
                    //radioButton1.isChecked = true
                    //radioButton2.isChecked = false

                }
                if(current =="OFF"){
                    switch1.isChecked = false
                    radioButton1.isChecked = false
                    radioButton2.isChecked = false
                    radioButton1.isClickable = false
                    radioButton2.isClickable = false

                }
            }
        })

        //Vibrataion Motor Switch
        switch1.setOnCheckedChangeListener {_, onSwitch ->
            if(onSwitch) {
                Toast.makeText(this, "Vibrate ON", Toast.LENGTH_LONG).show()

                //radioButton1.isChecked = true
                radioButton2.isChecked = false
                radioButton1.isClickable = true
                radioButton2.isClickable = true
                reffVM.child("Vibrate").setValue("ON")
            }
            else{
                radioButton1.isChecked = false
                radioButton2.isChecked = false
                radioButton1.isClickable = false
                radioButton2.isClickable = false
                reffVM.child("Vibrate").setValue("OFF")
            }
        }

        radioButton1.setOnCheckedChangeListener{_, onSelect ->
            if(onSelect){
                Toast.makeText(this, "Pattern One Selected", Toast.LENGTH_LONG).show()
                radioButton2.isChecked = false
                reffVM.child("Pattern").setValue("A")
            }
        }
        //the underscore was compoundButton at first
        radioButton2.setOnCheckedChangeListener{_, onSelect ->
            if(onSelect){
                Toast.makeText(this, "Pattern One Selected", Toast.LENGTH_LONG).show()
                radioButton1.isChecked = false
                reffVM.child("Pattern").setValue("B")
            }
        }

        //Phone Vibrate Setting
        /*switch2.setOnCheckedChangeListener {_, onSwitch ->
            if(onSwitch){
                //MainActivity.vibrateSettings
            }
            else{
            }
        }*/


        //Reset Data Button
        resetButton.setOnClickListener(){
            showDialog()
        }
    }

    private fun showDialog(){
        rDialog = Dialog(this)
        rDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        rDialog.setContentView(R.layout.activity_reset_popup)
        rDialog.setTitle("Reset Data")

        conButton = rDialog.findViewById(R.id.rcButton) as Button

        conButton.setOnClickListener{
            delData()
            Toast.makeText(applicationContext, "Data has been reset", Toast.LENGTH_LONG).show()
            rDialog.cancel()
        }
        rDialog.show()
    }

    private fun delData() {
        reffLD.removeValue()
        reffGyro.removeValue()
        reffEmg.removeValue()
    }


}