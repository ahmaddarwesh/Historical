package com.superdar.historical

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    var MyList = ArrayList<Type>()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        try {
            FirebaseApp.initializeApp(this)
            MobileAds.initialize(this, "ca-app-pub-7749815556108724~1871512962")
            val adReq = AdRequest.Builder().build()
            adView.loadAd(adReq)
        } catch (e: Exception) {
            Log.d("FIEELDS0","Error ${e.message}")
        }


        SignUp()
        initData()
        configView()

    }

    private fun SignUp() {
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("FieldIn", "signInAnonymously:success")
                    val user = auth.currentUser

                } else {
                    Log.w("FieldIn", "signInAnonymously:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun configView() {
        recycllerTypes.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycllerTypes.adapter = adapter(this, MyList)
    }

    private fun initData() {
        MyList.add(Type(1, "شخصيات فنية", R.drawable.a7))
        MyList.add(Type(2, "شخصيات سياسية", R.drawable.a6))
        MyList.add(Type(3, "شخصيات دينية", R.drawable.a9))
        MyList.add(Type(4, "شخصيات علمية", R.drawable.a5))
        MyList.add(Type(5, "شخصيات منوعة", R.drawable.a8))
        MyList.add(Type(6, "حول التطبيق!", R.drawable.a10))
    }

    class adapter(var conx: Context, var list: ArrayList<Type>) :
        RecyclerView.Adapter<adapter.MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val myView = LayoutInflater.from(conx).inflate(R.layout.cardtyp, parent, false)
            return MyHolder(myView)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            holder.name.text = list[position].name
            Glide.with(conx)
                .load(list[position].pic)
                .placeholder(R.drawable.a10)
                .into(holder.image)

            holder.cardView.setOnClickListener {
                conx.startActivity(
                    Intent(conx, PersonByType::class.java).putExtra(
                        "type",
                        list[position].id
                    )
                )
            }
            holder.name.setOnClickListener {
                if (list[position].id == 6) {
                    conx.startActivity(Intent(conx, AboutActivity::class.java))

                } else {
                    conx.startActivity(
                        Intent(conx, PersonByType::class.java).putExtra(
                            "type",
                            list[position].id
                        )
                    )
                }
            }
        }

        class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
            var name = view.findViewById<TextView>(R.id.txt_typ)
            var image = view.findViewById<ImageView>(R.id.img_typ)
            var cardView = view.findViewById<CardView>(R.id.card_typ)
        }

    }

}
