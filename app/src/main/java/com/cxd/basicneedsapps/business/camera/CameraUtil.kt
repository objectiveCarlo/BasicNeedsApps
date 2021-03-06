package com.cxd.basicneedsapps.business.camera

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cxd.basicneedsapps.business.flashlight.FlashLightUtil

object CameraUtil {
    private const val CAMERA_REQUEST = 50
    fun isPermissionGranted(activity: Activity): Boolean {
        return (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
    }

    fun requestPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST
        )
    }

    fun onRequestPermissionsResultGranted(
        requestCode: Int,
        grantResults: IntArray
    ): Boolean {
        when (requestCode) {
            CAMERA_REQUEST -> return grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        return false
    }
}