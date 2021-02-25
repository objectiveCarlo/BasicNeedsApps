package com.cxd.basicneedsapps

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cxd.basicneedsapps.business.camera.CameraUtil
import com.cxd.basicneedsapps.business.qr.QRViewModel
import com.cxd.basicneedsapps.databinding.ActivityQrScannerBinding


class QRScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrScannerBinding

    private val qrViewModel: QRViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrScannerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (CameraUtil.isPermissionGranted(this)) {
            qrViewModel.startCamera(binding.cameraPreview, this)
        } else {
            CameraUtil.requestPermission(this)
        }

        qrViewModel.qrCode.observe(this, { code ->
            binding.qrTextView.visibility = if (TextUtils.isEmpty(code)) {
                View.GONE
            } else {
                View.VISIBLE
            }

            binding.qrTextView.text = code
        })

        binding.qrTextView.setOnClickListener {
            val text = binding.qrTextView.text as String
            var handled = false
            if (Patterns.WEB_URL.matcher(text).matches()) {
                try {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
                    startActivity(browserIntent)
                    handled = true
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }

            if (!handled) {
                val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", text)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(CameraUtil.onRequestPermissionsResultGranted(requestCode, grantResults)) {
            qrViewModel.startCamera(binding.cameraPreview, this)
        }
    }

}