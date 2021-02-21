package com.cxd.basicneedsapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.cxd.basicneedsapps.business.camera.CameraUtil
import com.cxd.basicneedsapps.business.flashlight.FlashLightViewModel
import com.cxd.basicneedsapps.business.qr.QRViewModel
import com.cxd.basicneedsapps.databinding.ActivityMainBinding
import com.cxd.basicneedsapps.databinding.ActivityQrScannerBinding

class QRScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrScannerBinding

    private val qrViewModel: QRViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrScannerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}