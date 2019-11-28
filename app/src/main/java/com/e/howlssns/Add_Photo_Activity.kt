package com.e.howlssns

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.e.howlssns.model.ContentDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_add__photo_.*
import java.text.SimpleDateFormat
import java.util.*


private val UploadTask.TaskSnapshot.downloadUrl: Any
    get() {}

class Add_Photo_Activity : AppCompatActivity() {
    val PICK_IMAGE_FROM_ALBUM = 0
    var storage :FirebaseStorage? = null
    var photoURI :Uri? = null

    // 밑에줄
    var auth : FirebaseAuth? =null
    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__photo_)

        storage = FirebaseStorage.getInstance()
    // auth 사용
        auth = FirebaseAuth.getInstance()

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


        storageRef?.putFile(photoURI!!)?.addOnSuccessListener { taskSnapshot ->
            Toast.makeText(this,getString(R.string.upload_success),Toast.LENGTH_LONG).show()
            // 업로드된 이미지 주소 url
            var uri = taskSnapshot.downloadUrl
            var contentDTO = ContentDTO()
            //val db = FirebaseFirestore.getInstance()
            //이미지 주소
            contentDTO.imageUrl = uri!!.toString()
            // 유저의 UID
            contentDTO.uid = auth?.currentUser?.uid
            // 게시물 설명
            contentDTO.explain = addPhotoEdit_explain.text.toString()
            // 유저 ID
            contentDTO.userId = auth?.currentUser?.email
            // 게시물 업로드 시간
            contentDTO.timestamp = System.currentTimeMillis()
            // 저장시 firestore 설정 요망
            // firebase 창 띄워 Firestore 지정 --> 자동 저장됨
           // val db = FirebaseFirestore.getInstance()
           // 다음 문장을 쓰기위해 onCreate() 위에  변수 지정
           // var firestore : FirebaseFirestore? = null 지정
            firestore?.collection("images")?.document()?.set(contentDTO)


            setResult(Activity.RESULT_OK)
            finish()


        }



    }
}
