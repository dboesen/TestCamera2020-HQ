package com.example.testcamera2020_hq

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

private const val REQUEST_CODE = 1
private lateinit var myPhoto: File
private var myPhotoName = "photo"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClicker.setOnClickListener{
            val intentPics = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            myPhoto = getPhotoFromCamera(myPhotoName)
            // due to security with talking with other programs - this is the most secure way
            val newProvider = FileProvider.getUriForFile(this,"com.example.testcamera2020_hq.fileprovider", myPhoto)
            intentPics.putExtra(MediaStore.EXTRA_OUTPUT, newProvider)


            if (intentPics.resolveActivity(this.packageManager) != null) {
                startActivityForResult(intentPics, REQUEST_CODE)
            } else {
                Toast.makeText(this, "No Camera", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPhotoFromCamera(fileName: String): File {
        val localDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName,".jpg", localDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val HQImg = BitmapFactory.decodeFile(myPhoto.absolutePath)
            imageView.setImageBitmap(HQImg)  // Bigger image
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}