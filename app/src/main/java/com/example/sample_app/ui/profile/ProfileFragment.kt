package com.example.sample_app.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sample_app.R
import com.example.sample_app.databinding.FragmentGalleryBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.log

class ProfileFragment : Fragment() {
  private val TAG= "ProfileFragment"

private var _binding: FragmentGalleryBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!
  private lateinit var dialog1: Dialog
  var listLan= arrayListOf<Lan>()
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val galleryViewModel =ViewModelProvider(this).get(ProfileViewModel::class.java)

    _binding = FragmentGalleryBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textGallery
    galleryViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
    textView.setOnClickListener {
      showAlertForFilter()
    }

    return root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    listLan=  getAllLan()

    Log.d(TAG, "onViewCreated: -------------------------->")




    Log.d(TAG, "onViewCreated: main thread conti.....")
  }


  data class Lan(
    val id:Int,
    val lan:String?=null,
    var isSlected:Boolean=false,
  )

  fun setSelctedLan(){
   val data= listLan.filterIndexed { index, lan -> lan.isSlected }
    Log.d("TAG", "setSelctedLan:data=${data} ")
    val lanTex=""

//    binding.textGallery.text=data.filter { it.lan !="" }
  }
  fun getAllLan(): ArrayList<Lan> {
    val list: ArrayList<Lan> = arrayListOf()
    list.add(Lan(1, "English", false))
    list.add(Lan(2, "Hindi", false))
    list.add(Lan(2, "Tamil", false))
    list.add(Lan(3, "Assamese", false))
    list.add(Lan(4, "Bengali", false))
    list.add(Lan(5, "Gujarati", false))
    list.add(Lan(6, "Kannada", false))
    list.add(Lan(7, "Kashmiri", false))
    list.add(Lan(8, "Konkani", false))
    list.add(Lan(9, "Malayalam", false))
    list.add(Lan(10, "Manipuri", false))
    list.add(Lan(11, "Marathi", false))
    list.add(Lan(12, "Nepali", false))
    list.add(Lan(13, "Oriya", false))
    list.add(Lan(14, "Punjabi", false))
    list.add(Lan(15, "Sanskrit", false))
    list.add(Lan(16, "Sindhi", false))
    list.add(Lan(17, "Telugu", false))
    list.add(Lan(18, "Urdu", false))
    list.add(Lan(19, "Bodo", false))
    list.add(Lan(20, "Santhali", false))
    list.add(Lan(21, "Maithili", false))
    list.add(Lan(22, "Dogri", false))
    return list
  }



  private fun showAlertForFilter() {

    if (!::dialog1.isInitialized) {
//            dialog1 = BottomSheetDialog(requireContext())
      dialog1 =  Dialog(ContextThemeWrapper(requireContext(), R.style.Theme_SAmple_app))
    }

    dialog1.setContentView(R.layout.laugage)

    dialog1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog1.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    val list = dialog1.findViewById<LinearLayout>(R.id.list)


    for( i in listLan){
      val cb = CheckBox(requireContext())
      cb.id=i.id
      cb.setText("${i.lan}");
      cb.isChecked=(i.isSlected)

      cb.setOnCheckedChangeListener { buttonView, isChecked ->
        Log.d("TAG", "showAlertForFilter:buttonView:${buttonView.text} ")
        Log.d("TAG", "showAlertForFilter:buttonView:${buttonView.id} ")
        Log.d("TAG", "showAlertForFilter:isChecked:${isChecked} ")
        listLan[buttonView.id].isSlected = isChecked
        setSelctedLan()
      }

      list.addView(cb);


    }




    if (dialog1.getWindow() != null) {
//            dialog1.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimation;
//            dialog.show();
    }

    dialog1.setCancelable(true)
    dialog1.show()


  }


  override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}