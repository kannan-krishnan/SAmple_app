package com.example.sample_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

lateinit var  emailET:TextInputEditText
lateinit var  passwordET:TextInputEditText
private lateinit var auth: FirebaseAuth
private lateinit var error: TextView
private lateinit var loginText: TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailET= findViewById(R.id.emailET)
        passwordET= findViewById(R.id.psw)
        error= findViewById(R.id.error)
        loginText= findViewById(R.id.loginText)

        auth = Firebase.auth

        findViewById<CardView>(R.id.login).setOnClickListener {
            
            val email= emailET.text.toString().trim()
            val password= passwordET.text.toString().trim()

            error.visibility=View.GONE
            error.text=""
            if (email !="" && password !=""){
                login(email,password)
            }else{
                Toast.makeText(this, "Please enter valid user credentials", Toast.LENGTH_SHORT).show()
            }
            
//            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
    public override fun onStart() {
        super.onStart()
        checkLogin()

    }

    private fun login(email: String, password: String) {
        loginText.text="Loading..."
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task->
                    if (task.isSuccessful){
                        checkLogin()
                    }else{
                        Toast.makeText(this, "Login failed...", Toast.LENGTH_SHORT).show()
                    }
                loginText.text="Login"

            }
            .addOnFailureListener { it->
                Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
                error.visibility=View.VISIBLE
                error.text="${it.message}"
                it.printStackTrace()
                loginText.text="Login"
            }
    }

    private fun checkLogin(){

        val currentUser = auth.currentUser
        Log.d("TAG", "checkLogin: currentUser-->${currentUser?.uid}")
        Log.d("TAG", "checkLogin: currentUser-->${currentUser?.email}")
        Log.d("TAG", "checkLogin: currentUser-->${currentUser?.phoneNumber}")
        Log.d("TAG", "checkLogin: currentUser-->${currentUser?.tenantId}")
        Log.d("TAG", "checkLogin: currentUser-->${currentUser?.isEmailVerified}")
        Log.d("TAG", "checkLogin: currentUser-->${currentUser?.displayName}")
        Log.d("TAG", "checkLogin: currentUser-->${currentUser?.photoUrl}")
        Log.d("TAG", "checkLogin: currentUser-->${currentUser?.providerId}")
        if(currentUser != null){
            startActivity(Intent(this,HomeActivity::class.java))
            finishAffinity()
        }
    }
}