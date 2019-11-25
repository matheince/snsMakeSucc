package com.e.howlssns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var auth: FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        email_login_button.setOnClickListener {
            createAndLoginEmail()
        }

    }
    fun createAndLoginEmail(){
        auth?.createUserWithEmailAndPassword("mathienceinst@gmail.com","123456")?.addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                Toast.makeText(this,"아이디 생성 성공",Toast.LENGTH_LONG).show()
            }
        }

    }
}
