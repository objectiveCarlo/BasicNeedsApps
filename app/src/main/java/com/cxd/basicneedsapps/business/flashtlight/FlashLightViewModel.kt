package com.cxd.basicneedsapps.business.flashtlight

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlashLightViewModel: ViewModel() {
    val status = MutableLiveData<Boolean>()

    fun statusChange(stats: Boolean) {
        status.value = stats
    }
}