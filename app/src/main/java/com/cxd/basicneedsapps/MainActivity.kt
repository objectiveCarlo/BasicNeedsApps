package com.cxd.basicneedsapps

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cxd.basicneedsapps.business.flashlight.FlashLightUtil
import com.cxd.basicneedsapps.business.flashlight.FlashLightViewModel
import com.cxd.basicneedsapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val flashLightViewModel: FlashLightViewModel by viewModels()

        binding.topSegmentView.flashLightBtn.setOnCheckedChangeListener{ _, isChecked ->
           flashLightViewModel.statusChange(isChecked)
        }
        flashLightViewModel.status.observe(this, { status ->
            if (status) {
               if(FlashLightUtil.hasCameraFlash(this)) {
                   if(FlashLightUtil.isPermissionGranted(this)) {
                       turnOnTorch()
                   } else {
                       FlashLightUtil.requestPermission(this)
                   }
               }
            } else {
                turnOffTorch()
            }
            binding.quoteTextView.text = if(status) {
                "On"
            } else {
                "Off"
            }
        })
        turnOffTorch()
    }

    private fun turnOnTorch() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            FlashLightUtil.turnOnTorch(this)
        }
    }

    private fun turnOffTorch() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            FlashLightUtil.turnOffTorch(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(FlashLightUtil.onRequestPermissionsResultGranted(requestCode, grantResults)) {
            turnOnTorch()
        }
    }
}