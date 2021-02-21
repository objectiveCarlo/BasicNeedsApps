package com.cxd.basicneedsapps.business.qr

import android.content.Context
import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.common.util.concurrent.ListenableFuture


class QRViewModel: ViewModel() {
    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null

    fun startCamera(previewView: PreviewView, context: Context) {
        cameraProviderFuture = ProcessCameraProvider.getInstance(context);
        cameraProviderFuture!!.addListener({
            try {
                val cameraProvider =
                    cameraProviderFuture!!.get()
                    bindCameraPreview(cameraProvider, previewView, context)
            } catch (e: Exception) {

            }
        }, ContextCompat.getMainExecutor(context))
    }
    private fun bindCameraPreview(cameraProvider: ProcessCameraProvider, previewView: PreviewView, context: Context) {
        previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE

        val preview = Preview.Builder()
            .build()

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()


        preview.setSurfaceProvider(previewView.surfaceProvider)

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(context),
            QRCodeImageAnalyzer(object : QRCodeFoundListener {
                override fun onQRCodeFound(qrCode: String?) {
                    qrCode?.let { Log.d("QR", it) }
                }

                override fun qrCodeNotFound() {
                    Log.d("QR", "not found")
                }
            })
        )

        val camera = cameraProvider.bindToLifecycle(
            (context as LifecycleOwner)!!,
            cameraSelector,
            imageAnalysis,
            preview
        )

    }
}