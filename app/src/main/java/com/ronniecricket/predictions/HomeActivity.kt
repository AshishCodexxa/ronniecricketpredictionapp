package com.ronniecricket.predictions


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.net.URLEncoder


class HomeActivity : AppCompatActivity() {

    private lateinit var card1: CardView
    private lateinit var telegramCard: CardView
    private lateinit var card3: CardView
    private lateinit var card4: CardView
    private lateinit var whatsAppCard: CardView
    private var userId: String? = null
    private var number: String? = null
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        card1 = findViewById(R.id.prulecard1)
        whatsAppCard = findViewById(R.id.card5)
        telegramCard = findViewById(R.id.ctipscard2)
        card3 = findViewById(R.id.contactcard3)
        card4 = findViewById(R.id.aboutcard4)


        userId = FirebaseAuth.getInstance().currentUser?.uid
        Log.e("Test--->", userId.toString());

        card1.setOnClickListener {

            openWhatsApp(number.toString())
        }
        telegramCard.setOnClickListener {

            openWhatsApp(number.toString())
        }
        card3.setOnClickListener {

            openWhatsApp(number.toString())
        }
        card4.setOnClickListener {
            openWhatsApp(number.toString())
        }
        whatsAppCard.setOnClickListener {

            Log.e("Hiiiii", "Hiiiii")


            openWhatsApp(number.toString())

        }


    }

    override fun onStart() {
        super.onStart()
        if (userId == null) {

            db.collection("user").get().addOnSuccessListener {

                val fnum = it.documents.get(0).get("number").toString()

                number = fnum.toString()


                Log.e("fnummm", fnum)
            }
        }
    }


    private fun openWhatsApp(number: String) {
        try {
            val i = Intent(Intent.ACTION_VIEW)
            val url =
                "https://api.whatsapp.com/send?phone=+91$number&text=" + URLEncoder.encode(
                    "Hi Team, I am looking for Cricket Prediction Tips",
                    "UTF-8"
                )
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i)
            } else {
                Toast.makeText(
                    this@HomeActivity,
                    "Could not start whatsapp",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Log.e("ERROR WHATSAPP", e.toString())
            Toast.makeText(
                this@HomeActivity,
                "${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}
