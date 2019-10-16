package com.superdar.historical

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_person_viewer.*


class PersonViewer : AppCompatActivity() {
    private var ID = "0"
    private val db = FirebaseFirestore.getInstance()
    private val query = db.collection("Person")
    private var q = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_viewer)
        ID = intent.extras!!.getString("ID").toString()
        q = intent.extras!!.getString("query").toString()
        MobileAds.initialize(this, "ca-app-pub-7749815556108724~1871512962")
        val adReq = AdRequest.Builder().build()
        adView3.loadAd(adReq)
        avl.setIndicator("BallScaleIndicator")
        cons_viewer.visibility = View.GONE
        avl.show()
        query.document(q).collection("Personality").whereEqualTo("ID", ID)
            .get()
            .addOnSuccessListener {
                try {
                    for (document in it) {
                        name_viewer.text = document["Name"].toString()
                        desc_viwer.text = document["Description"].toString()
                        death_viwer.text = document["Deathday"].toString()
                        porn_viwer.text = document["Birthday"].toString()
                        natio_viewer.text = document["Nationality"].toString()
                        Glide.with(baseContext)
                            .load(document["Picture"].toString())
                            .placeholder(R.drawable.user_defult_pic)
                            .into(img_viwer)
                    }
                    avl.hide()
                    cons_viewer.visibility = View.VISIBLE
                } catch (e: Exception) {
                    Log.d("FieldLoad", "Error ${e.message}")
                }

            }
            .addOnFailureListener {
                Log.d("Error", it.message.toString())
            }
    }
}
