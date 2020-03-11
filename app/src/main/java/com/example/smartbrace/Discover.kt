package com.example.smartbrace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_discover.*
import android.util.Log.d

class Discover : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)

        val intent = Intent(this@Discover, LinkWebView::class.java)
        val intent1 = Intent(this@Discover, SBWebView::class.java)

        val links: Array<String> = arrayOf(
            "https://orthoinfo.aaos.org/en/diseases--conditions/carpal-tunnel-syndrome/",
            "https://www.healthxchange.sg/bones-joints/shoulder-elbow-hands/prevent-ca" +
                    "rpal-tunnel-syndrome-better-posture",
            "https://www.ahchealthenews.com/2016/01/22/3-signs-its-time-to-see-a-" +
                    "doctor-for-carpal-tunnel-syndrome/",
            "https://www.zocdoc.com/procedure/carpal-tunnel-syndrome-1090",
            "https://www.athletico.com/2017/04/14/sleep-positioning-carpal-tunnel-syndrome/",
            "https://www.healthline.com/health/carpal-tunnel-wrist-exercises")


        //On click Events
        //Link0
        cardView0.setOnClickListener(){
            d("Discover", "Attempt to access link 0")
            intent1.putExtra("link", links[0].toString())
            startActivity(intent1)
        }

        //Link1
        cardView1.setOnClickListener(){
            d("Discover", "Attempt to access link 1")
            intent.putExtra("link", links[1].toString())
            startActivity(intent)
        }

        //Link2
        cardView2.setOnClickListener(){
            d("Discover", "Attempt to access link 2")
            intent.putExtra("link", links[2].toString())
            startActivity(intent)
        }

        //Link 3
        cardView3.setOnClickListener(){
            d("Discover", "Attempt to access link 3")
            intent.putExtra("link", links[3].toString())
            startActivity(intent)
        }

        //Link 4
        cardView4.setOnClickListener(){
            d("Discover", "Attempt to access link 4")
            intent.putExtra("link", links[4].toString())
            startActivity(intent)
        }

        //Link 5
        cardView5.setOnClickListener(){
            d("Discover", "Attempt to access link 5")
            intent.putExtra("link", links[5].toString())
            startActivity(intent)
        }

    }
}