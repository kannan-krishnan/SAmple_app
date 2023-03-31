package com.example.sample_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sample_app.MainActivity
import com.example.sample_app.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

    private val TAG=HomeFragment::class.java.simpleName

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textHome
    homeViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
    return root
  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        checkLogin()

        attendance()
        notificationListenerService()
        binding.textHome.setOnClickListener {
            createNewUser()
        }
        userDetails()
    }


    private fun checkLogin() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finishAffinity()
        }
    }



    private fun attendance() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finishAffinity()
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
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().   finishAffinity()
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


    private fun createNewUser(){
        auth.createUserWithEmailAndPassword("tamillddd1@gmail.com","123@Kannan")
            .addOnCompleteListener {
                if (it.isSuccessful){
                   userDetails()
                    addUser()
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun userDetails() {
        val newUser=auth.currentUser

        Log.d(TAG, "createNewUser: "+newUser?.uid)
        Log.d(TAG, "createNewUser: "+newUser?.email)
        Log.d(TAG, "createNewUser: "+newUser?.tenantId)
        Log.d(TAG, "createNewUser: "+newUser?.displayName)
        Log.d(TAG, "createNewUser: "+newUser?.phoneNumber)
        Log.d(TAG, "createNewUser: "+newUser?.providerId)
    }

    private fun addUser(){
        val newUser=auth.currentUser
        val user: MutableMap<String, Any> = HashMap()
        user["name"] = "kannan"
        user["email"] = "tamil"
        user["phone"] = "80888888"

        db.collection("Users").document(auth.currentUser?.uid.toString()).set(
            AddStudentPojo("kannan","address","30/04/1995","30-41-20","","A","Ramakrishnan","rejeswari")
        ).addOnSuccessListener {
            Toast.makeText(requireContext(), "Register success", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                it.printStackTrace()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}