package com.cxd.basicneedsapps.business.flashlight

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object FlashLightUtil {
    private const val CAMERA_REQUEST = 50
    fun isPermissionGranted(activity: Activity): Boolean {
        return (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)

    }

    fun hasCameraFlash(activity: Activity): Boolean {
        return activity.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun turnOnTorch(activity: Activity) {
        val cameraManager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, true)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun turnOffTorch(activity: Activity) {
        val cameraManager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, false)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}