package com.e.howlssns

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
        auth?.createUserWithEmailAndPassword(email_EditText.text.toString(),password_EditText.text.toString())?.addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                Toast.makeText(this,"아이디 생성 성공",Toast.LENGTH_LONG).show()
            }
            else if(task.exception?.message.isNullOrEmpty()){
                Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
            } else{
                signinLogin()
            }
        }

    }
    fun signinLogin() {
        auth?.signInWithEmailAndPassword(
            email_EditText.text.toString(),
            password_EditText.text.toString()
        )?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //Toast.makeText(this,"로그인이 성공했습니다.",Toast.LENGTH_LONG).show()
                moveMainPage(auth?.currentUser)
            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }


        }
    }
    fun moveMainPage(user : FirebaseUser?){
        if(user != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }

}
