package com.e.howlssns

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Add_Photo_Activity : AppCompatActivity() {
    val PICK_IMAGE_FROM_ALBUM = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__photo_)
        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type ="image/+"
        startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)

    }
}
