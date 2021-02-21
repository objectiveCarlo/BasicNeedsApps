package com.cxd.basicneedsapps.business.flashlight

import android.app.Activity
import android.content.pm.PackageManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cxd.basicneedsapps.MainActivity
import com.cxd.basicneedsapps.business.camera.CameraUtil

class FlashLightViewModel: ViewModel() {
    val status = MutableLiveData<Boolean>()

    fun statusChange(stats: Boolean) {
        status.value = stats
    }

    fun turnOnTorch(activity: Activity) {
        if(FlashLightUtil.hasCameraFlash(activity)) {
            if(CameraUtil.isPermissionGranted(activity)) {
                FlashLightUtil.turnOnTorch(activity)
            } else {
                CameraUtil.requestPermission(activity)
            }
        }
    }

    fun turnOffTorch(activity: Activity) {
        if(FlashLightUtil.hasCameraFlash(activity)) {
            if(CameraUtil.isPermissionGranted(activity)) {
                FlashLightUtil.turnOffTorch(activity)
            } else {
                CameraUtil.requestPermission(activity)
            }
        }
    }

    fun onCreateAction(activity: Activity) {
        if(FlashLightUtil.hasCameraFlash(activity)) {
            if(CameraUtil.isPermissionGranted(activity)) {
                turnOffTorch(activity)
            }
        }
    }

    fun doLastAction(activity: Activity) {
        if (status.value == true) {
            turnOnTorch(activity)
        } else {
            turnOffTorch(activity)
        }
    }

}