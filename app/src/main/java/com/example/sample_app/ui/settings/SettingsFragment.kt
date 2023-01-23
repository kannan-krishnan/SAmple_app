package com.example.sample_app.ui.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sample_app.MainActivity
import com.example.sample_app.databinding.FragmentSlideshowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.core.utilities.Utilities
import com.google.firebase.ktx.Firebase


class SettingsFragment : Fragment() {

private var _binding: FragmentSlideshowBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!
  private lateinit var auth: FirebaseAuth
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val slideshowViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

    _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
    val root: View = binding.root

    slideshowViewModel.text.observe(viewLifecycleOwner) {

    }
    return root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    auth = Firebase.auth
    binding.busNotification.setOnCheckedChangeListener { compoundButton, b ->
      if (!b) {
        Log.i("Yeah", "Is Not Selected busNotification")
//
      } else {
        Log.i("Yeah", "Is   Selected busNotification")

      }
    }

    binding.messageNotification.setOnCheckedChangeListener { compoundButton, b ->
      if (!b) {
        Log.i("Yeah", "Is Not Selected messageNotification")
//
      } else {
        Log.i("Yeah", "Is   Selected messageNotification")

      }
    }

    binding.schoolNotification.setOnCheckedChangeListener { compoundButton, b ->
      if (!b) {
        Log.i("Yeah", "Is Not Selected school_notification")
//
      } else {
        Log.i("Yeah", "Is   Selected school_notification")

      }
    }

    binding.logout.setOnClickListener {

      auth.signOut()
      checkLogin()
    }

  }
  private fun checkLogin() {
    val currentUser = auth.currentUser
    if (currentUser == null) {
      startActivity(Intent(requireContext(), MainActivity::class.java))
      requireActivity().finishAffinity()
    }
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}