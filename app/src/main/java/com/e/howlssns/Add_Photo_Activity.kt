package com.e.howlssns

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add__photo_.*
import java.text.SimpleDateFormat
import java.util.*

class Add_Photo_Activity : AppCompatActivity() {
    val PICK_IMAGE_FROM_ALBUM = 0
    var storage :FirebaseStorage? = null
    var photoURI :Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__photo_)

        storage = FirebaseStorage.getInstance()

        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type ="image/*"
        startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)
        addPhoto_Image.setOnClickListener {
            var photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type ="image/*"
            startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)
        }
        addphoto_btn_upload.setOnClickListener {
            contentUpload()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==PICK_IMAGE_FROM_ALBUM){
            addPhoto_Image.setImageURI(data?.data)
            if(resultCode==Activity.RESULT_OK){

                photoURI = data?.data
                addPhoto_Image.setImageURI(data?.data)

            }else{
                finish()
            }
        }
    }
    fun contentUpload(){
        val timeStamp = SimpleDateFormat("yyyymmdd_HHMMSS").format(Date())
        val ImageFileName = "JPEG_"+timeStamp+"_png"
        val storageRef = storage?.reference?.child("images")?.child(ImageFileName)

        storageRef?.putFile(photoURI!!)?.addOnSuccessListener {
            Toast.makeText(this,getString(R.string.upload_success),Toast.LENGTH_LONG).show()



        }

    }
}
