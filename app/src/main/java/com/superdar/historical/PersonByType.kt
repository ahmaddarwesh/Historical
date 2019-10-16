package com.superdar.historical

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_person_by_type.*


class PersonByType : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var PPersonality = db.collection("Person").document("PP").collection("Personality")
    private var RPersonality = db.collection("Person").document("RP").collection("Personality")
    private var SPersonality = db.collection("Person").document("SP").collection("Personality")
    private var APersonality = db.collection("Person").document("AP").collection("Personality")
    private var DPersonality = db.collection("Person").document("DP").collection("Personality")
    private var qts = ""
    var addapter: adapter? = null
    private var type: Int = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_by_type)
        FirebaseApp.initializeApp(this)
        type = intent.extras!!.getInt("type")
        MobileAds.initialize(this, "ca-app-pub-7749815556108724~1871512962")
        val adReq = AdRequest.Builder().build()
        adView2.loadAd(adReq)
        av2.show()
        getAll()
    }

    private fun getAll() {
        recy_person.layoutManager = GridLayoutManager(this, 2)
        var query: Query? = null
        when (type) {
            1 -> {
                query = APersonality.orderBy("Name", Query.Direction.ASCENDING)
                qts = "AP"
            }
            2 -> {
                query = PPersonality.orderBy("Name", Query.Direction.ASCENDING)
                qts = "PP"
            }
            3 -> {
                query = RPersonality.orderBy("Name", Query.Direction.ASCENDING)
                qts = "RP"
            }
            4 -> {
                query = SPersonality.orderBy("Name", Query.Direction.ASCENDING)
                qts = "SP"
            }
            5 -> {
                query = DPersonality.orderBy("name", Query.Direction.ASCENDING)
                qts = "DP"
            }
        }

        val options = FirestoreRecyclerOptions.Builder<PersonModel>()
            .setQuery(query!!, PersonModel::class.java)
            .build()

        addapter = adapter(this, options, qts)
        recy_person.adapter = addapter
        av2.hide()
        addapter!!.startListening()
        addapter!!.notifyDataSetChanged()

    }

    class adapter(
        var conx: Context,
        options: FirestoreRecyclerOptions<PersonModel>,
        var qts: String
    ) :
        FirestoreRecyclerAdapter<PersonModel, adapter.MyHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val myView = LayoutInflater.from(conx).inflate(R.layout.card_person, parent, false)
            return MyHolder(myView)
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int, person: PersonModel) {
            holder.name.text = person.Name
            holder.id.text = person.ID
            Glide.with(conx)
                .load(person.Picture)
                .placeholder(R.drawable.user_defult_pic)
                .into(holder.pic)
            holder.card.setOnClickListener {
                conx.startActivity(
                    Intent(conx, PersonViewer::class.java).putExtra("ID", person.ID)
                        .putExtra("query", qts)
                )
            }
        }
        class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
            var name = view.findViewById<TextView>(R.id.person_name)
            var id = view.findViewById<TextView>(R.id.person_id)
            var pic = view.findViewById<ImageView>(R.id.person_picture)
            var card = view.findViewById<androidx.cardview.widget.CardView>(R.id.card_per)
        }
    }

    override fun onStart() {
        super.onStart()
        addapter!!.startListening()
    }


    override fun onStop() {
        super.onStop()
        addapter!!.stopListening()

    }


    override fun onResume() {
        super.onResume()
        getAll()
    }


}
