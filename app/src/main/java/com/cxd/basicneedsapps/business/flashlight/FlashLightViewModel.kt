package com.cxd.basicneedsapps.business.flashlight

import android.app.Activity
import android.content.pm.PackageManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cxd.basicneedsapps.MainActivity

class FlashLightViewModel: ViewModel() {
    val status = MutableLiveData<Boolean>()

    fun statusChange(stats: Boolean) {
        status.value = stats
    }

    fun turnOnTorch(activity: Activity) {
        if(FlashLightUtil.hasCameraFlash(activity)) {
            if(FlashLightUtil.isPermissionGranted(activity)) {
                FlashLightUtil.turnOnTorch(activity)
            } else {
                FlashLightUtil.requestPermission(activity)
            }
        }
    }

    fun turnOffTorch(activity: Activity) {
        if(FlashLightUtil.hasCameraFlash(activity)) {
            if(FlashLightUtil.isPermissionGranted(activity)) {
                FlashLightUtil.turnOffTorch(activity)
            } else {
                FlashLightUtil.requestPermission(activity)
            }
        }
    }

    fun onRequestPermissionsResultGranted(
            requestCode: Int,
            grantResults: IntArray
    ): Boolean {
        return FlashLightUtil.onRequestPermissionsResultGranted(requestCode, grantResults)
    }

    fun onCreateAction(activity: Activity) {
        turnOnTorch(activity)
    }

}