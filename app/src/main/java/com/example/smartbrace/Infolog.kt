package com.example.smartbrace


import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_infolog.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Infolog : AppCompatActivity(){

    private lateinit var database: DatabaseReference

    val listSnapshot = ArrayList<DataSnapshot>()
    val listValue = ArrayList<Data>()
    val listTime = ArrayList<Long>()
    val listSnapshotLabel = ArrayList<Entry>()
    private lateinit var lineChart: LineChart

    private var progressBar: ProgressBar? = null

    var graphType = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infolog)

        progressBar = findViewById<ProgressBar>(R.id.progressBar) as ProgressBar
        lineChart = findViewById(R.id.linechart)
        gyroValues()

        pastHour.setTextColor(resources.getColor(R.color.colorPrimaryDark))

        pastHour.setOnClickListener {
            graphType = 0;

            pastHour.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            past6Hour.setTextColor(resources.getColor(R.color.colorBlack))
            past24Hour.setTextColor(resources.getColor(R.color.colorBlack))
//            pastWeek.setTextColor(resources.getColor(R.color.colorBlack))

            setupLineChartData()
        }

        past6Hour.setOnClickListener {
            graphType = 1;

            pastHour.setTextColor(resources.getColor(R.color.colorBlack))
            past6Hour.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            past24Hour.setTextColor(resources.getColor(R.color.colorBlack))
//            pastWeek.setTextColor(resources.getColor(R.color.colorBlack))

            setupLineChartData()
        }

        past24Hour.setOnClickListener {
            graphType = 2;

            pastHour.setTextColor(resources.getColor(R.color.colorBlack))
            past6Hour.setTextColor(resources.getColor(R.color.colorBlack))
            past24Hour.setTextColor(resources.getColor(R.color.colorPrimaryDark))
//            pastWeek.setTextColor(resources.getColor(R.color.colorBlack))

            setupLineChartData()
        }

//        pastWeek.setOnClickListener {
//            graphType = 3;
//
//            pastHour.setTextColor(resources.getColor(R.color.colorBlack))
//            past6Hour.setTextColor(resources.getColor(R.color.colorBlack))
//            past24Hour.setTextColor(resources.getColor(R.color.colorBlack))
//            pastWeek.setTextColor(resources.getColor(R.color.colorPrimaryDark))
//
//            setupLineChartData()
//        }
    }


    private fun gyroValues() {
        listSnapshot.clear()
        listValue.clear()
        listTime.clear()

        database = FirebaseDatabase.getInstance().reference
        val gyroQuery = database.child("LogData")
        gyroQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("main", "main "+dataSnapshot.value)
                for (postSnapshot in dataSnapshot.children) {
                    listSnapshot.add(postSnapshot)
                }

                for (itemSnapShot in listSnapshot) {
                    var readKeyGet = itemSnapShot.child("date").getValue().toString();
                    var readKey = readKeyGet.split(":")[0].toFloat()*60.0f*60.0f+readKeyGet.split(":")[1].toFloat()*60.0f+readKeyGet.split(":")[2].toFloat();
                    var readVal =  itemSnapShot.child("angle").getValue().toString().toFloat()*1.0f;

                    listValue.add(Data(valueTime = readKey.toLong(), valueData = readVal, valueType = itemSnapShot.child("form").getValue().toString()))
                    listTime.add(readKey.toLong())
                }

                setupLineChartData()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                progressBar?.visibility = View.GONE
            }
        })
    }

    private fun setupLineChartData() {
        val yVals = ArrayList<Entry>()
        val yVals1 = ArrayList<Entry>()

        progressBar?.visibility = View.GONE


        val formatter = SimpleDateFormat("HH:mm:ss")
        val dateTime = formatter.format(Calendar.getInstance().time)

        var dateTimeNowSecond = dateTime.split(":")[0].toInt()*60*60+dateTime.split(":")[1].toInt()*60+dateTime.split(":")[2].toInt();

        val xAxisValues = ArrayList<String>()

        var endVal = 0
        var stepVal = 15
        if(graphType == 0) {
            endVal = dateTimeNowSecond - 60 * 60
            stepVal = 15
        } else if(graphType == 1) {
            endVal = dateTimeNowSecond - 6 * 60 * 60
            stepVal = 15
        } else if(graphType == 2) {
            endVal = dateTimeNowSecond - 24 * 60 * 60
            stepVal = 15
        }

        if(endVal < 0)
            endVal = 0

        var i = 0
        var redDrawable = resources.getDrawable(R.drawable.red)
        var blueDrawable = resources.getDrawable(R.drawable.blue)
        for (k in dateTimeNowSecond downTo endVal step 1) {
            var readVal = 0.toFloat()
            var type = ""
            if(listTime.indexOf(k.toLong()) != -1) {
                readVal = listValue.get(listTime.indexOf(k.toLong())).valueData
                type  =  listValue.get(listTime.indexOf(k.toLong())).valueType
                if(type.equals("Accurate")) {
                    if(readVal > 25.0) {
                        yVals.add(Entry(i * 1.0f, readVal, redDrawable, "" + (i)));
                    } else
                        yVals.add(Entry(i * 1.0f, readVal, blueDrawable,"" + (i)));
                } else {
                    if(readVal > 25.0) {
                        yVals1.add(Entry(i * 1.0f, readVal, redDrawable, "" + (i)));
                    } else
                        yVals1.add(Entry(i * 1.0f, readVal, blueDrawable,"" + (i)));
                }
            } else {
                yVals.add(Entry(i * 1.0f, readVal, "" + (i)));
                yVals1.add(Entry(i * 1.0f, readVal, "" + (i)));
            }

            if(i%15 == 0) {
                when {
                    i <= 60 -> {
                        xAxisValues.add(""+i)
                    }
                    i in 61..3600 -> {
                        xAxisValues.add(""+i/60+":"+i%60)
                    }
                    else -> {
                        xAxisValues.add(""+i/3600+":"+i%3600/60+":"+i%60)
                    }
                }
            }  else {
                xAxisValues.add("")
            }
            i++
        }

        val set1: LineDataSet
        set1 = LineDataSet(yVals, "DataSet 1")

        // set1.fillAlpha = 110
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(5f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.color = Color.BLUE
        set1.setCircleColor(Color.BLUE)
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 0f
        set1.setDrawFilled(true)
        set1.fillAlpha = 110
        set1.setFillColor(Color.BLUE);

        set1.fillFormatter =
            IFillFormatter { dataSet, dataProvider -> // change the return value here to better understand the effect
                // return 0;
                lineChart.getAxisLeft().getAxisMinimum()
            }

        val set11: LineDataSet
        set11 = LineDataSet(yVals1, "DataSet 11")

        // set1.fillAlpha = 110
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(5f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set11.color = Color.RED
        set11.setCircleColor(Color.RED)
        set11.lineWidth = 1f
        set11.circleRadius = 3f
        set11.setDrawCircleHole(false)
        set11.valueTextSize = 0f
        set11.setDrawFilled(true)
        set11.fillAlpha = 110
        set11.setFillColor(Color.RED);

        set11.fillFormatter =
            IFillFormatter { dataSet, dataProvider -> // change the return value here to better understand the effect
                // return 0;
                lineChart.getAxisLeft().getAxisMinimum()
            }

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        dataSets.add(set11)
        val data = LineData(dataSets)

        // set data
        lineChart.setData(data)
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.fitScreen()
        lineChart.setPinchZoom(true)
        lineChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
        lineChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
        lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
        //lineChart.setDrawGridBackground()

        val rightAxis = lineChart.axisRight
        rightAxis.isEnabled = false

        lineChart.xAxis.labelCount = yVals.size
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.invalidate()
        lineChart.animateXY(300, 300)

        val xAxis: XAxis = lineChart.getXAxis()
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)
        xAxis.isEnabled = true
        xAxis.setDrawGridLines(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        lineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(xAxisValues))

    }



}