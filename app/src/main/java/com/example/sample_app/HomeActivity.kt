package com.example.sample_app

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sample_app.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var dialog1: Dialog

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val TAG = HomeActivity::class.java.simpleName

    override fun onStart() {
        super.onStart()
        checkLogin()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        binding.appBarHome.fab.setOnClickListener { view ->

            showAlertForFilter()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        getDetails()

//        attendance()
//        notificationListenerService()
    }

    /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
         // Inflate the menu; this adds items to the action bar if it is present.
         menuInflater.inflate(R.menu.home, menu)
         return true
     }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun showAlertForFilter() {

        if (!::dialog1.isInitialized) {
//            dialog1 = BottomSheetDialog(requireContext())
            dialog1 = Dialog(ContextThemeWrapper(this, R.style.Theme_SAmple_app))
        }

        dialog1.setContentView(R.layout.laugage)

        dialog1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog1.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );

        val list = dialog1.findViewById<LinearLayout>(R.id.list)

        for (i in 1..28) {
            val cb = CheckBox(applicationContext)
            cb.setText("I'm dynamic!");
            list.addView(cb);

        }




        if (dialog1.getWindow() != null) {
//            dialog1.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimation;
//            dialog.show();
        }

        dialog1.setCancelable(true)
        dialog1.show()


    }

    private fun checkLogin() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }

    private fun getDetails() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        } else {
            val uid = currentUser.uid
            db.collection("Users")
                .document( uid)
                .get()
//            Users/cjYxmkhOxpblVaInmBkHm07dTMT2
                .addOnSuccessListener {
                    Log.d(TAG, "getDetails:addOnSuccessListener --ID------------------------>${it.id}")
                    Log.d(TAG, "getDetails:addOnSuccessListener -------------------------->${it.data}")


                }

                .addOnFailureListener {
                    it.printStackTrace()
                }
            Log.e(TAG, "getDetails: uid----$uid")
        }

    }

    private fun attendance() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        } else {
            val uid = currentUser.uid
            db.collection("Users")
                .document( uid).collection("attendance")
                .get()
//            Users/cjYxmkhOxpblVaInmBkHm07dTMT2
                .addOnSuccessListener {
                    Log.d(TAG, "attendance:addOnSuccessListener --ID------------------------>${it.size()}")
                    if (!it.isEmpty) {
                        it.forEachIndexed { index, it ->
                            Log.d(
                                TAG,
                                "attendance:addOnSuccessListener -------------------------->${it.data}"
                            )

                        }

                    }
                }

                .addOnFailureListener {
                    it.printStackTrace()
                }
            Log.e(TAG, "getDetails: uid----$uid")
        }

    }

    private fun notificationListenerService() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        } else {
            val uid = currentUser.uid
            db.collection("notification")
//                .document( uid).collection("attendance")

//            Users/cjYxmkhOxpblVaInmBkHm07dTMT2
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null && !snapshot.isEmpty) {
                        Log.d(TAG, "Current data: ${snapshot.size()}")
                        snapshot.forEachIndexed { index, queryDocumentSnapshot ->

                            Log.d(TAG, "notificationListenerService: ${queryDocumentSnapshot.data} ")
                        }
                    } else {
                        Log.d(TAG, "Current data: null")
                    }



                }


            Log.e(TAG, "getDetails: uid----$uid")
        }

    }
}


