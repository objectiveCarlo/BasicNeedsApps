package com.cxd.basicneedsapps

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cxd.basicneedsapps.business.flashlight.FlashLightViewModel
import com.cxd.basicneedsapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val flashLightViewModel: FlashLightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        torchInitActions()
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
            binding.quoteTextView.text = if(status) {
                "On"
            } else {
                "Off"
            }
        })
        flashLightViewModel.onCreateAction(this)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(flashLightViewModel.onRequestPermissionsResultGranted(requestCode, grantResults)) {
            flashLightViewModel.doLastAction(this)
        }
    }
}