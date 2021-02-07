package com.cxd.basicneedsapps

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cxd.basicneedsapps.business.flashtlight.FlashLightViewModel
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
        flashLightViewModel.status.observe(this, Observer<Boolean>{ status ->
            binding.quoteTextView.text = if(status) {
                "On"
            } else {
                "Off"
            }
        })
    }
}