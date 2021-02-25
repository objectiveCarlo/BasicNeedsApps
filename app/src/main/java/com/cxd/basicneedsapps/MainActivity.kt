package com.cxd.basicneedsapps

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cxd.basicneedsapps.business.camera.CameraUtil
import com.cxd.basicneedsapps.business.flashlight.FlashLightViewModel
import com.cxd.basicneedsapps.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val flashLightViewModel: FlashLightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        torchInitActions()

        binding.topSegmentView.qrBtn.setOnClickListener{
            val myIntent = Intent(this, QRScannerActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun torchInitActions() {
        binding.topSegmentView.flashLightBtn.setOnCheckedChangeListener{ _, isChecked ->
            flashLightViewModel.statusChange(isChecked)
        }
        flashLightViewModel.status.observe(this, { status ->
            if (status) {
                flashLightViewModel.turnOnTorch(this)
            } else {
                flashLightViewModel.turnOffTorch(this)
            }
        })
        flashLightViewModel.onCreateAction(this)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(CameraUtil.onRequestPermissionsResultGranted(requestCode, grantResults)) {
            flashLightViewModel.doLastAction(this)
        }
    }
}