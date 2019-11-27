package com.e.howlssns

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginActivity : AppCompatActivity() {

    var auth: FirebaseAuth?=null
    var googleSignInClient : GoogleSignInClient?=null
    var GOOGLE_LOGIN_CODE = 9001
    var callbackManager :CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        email_login_button.setOnClickListener {
            createAndLoginEmail()
        }
        google_sign_In_button.setOnClickListener {
            googleLogin()
        }
        facebook_login_button.setOnClickListener {
            facebookLogin()
        }

    var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        printHashKey(this)
        callbackManager = CallbackManager.Factory.create() // 초기화


    }
    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.getPackageManager()
                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("Howls", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("Howls", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("Howls", "printHashKey()", e)
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

    override fun onResume() {
        super.onResume()
        moveMainPage(auth?.currentUser)
    }

    fun moveMainPage(user : FirebaseUser?){
        if(user != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
    fun googleLogin(){
        var singIntent = googleSignInClient?.signInIntent
        startActivityForResult(singIntent,GOOGLE_LOGIN_CODE)
    }
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount){
        var credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth?.signInWithCredential(credential)
    }

    fun facebookLogin(){
        LoginManager
            .getInstance()
            .logInWithPublishPermissions(this, Arrays.asList("public_profile","email"))
        LoginManager
            .getInstance()
            .registerCallback(callbackManager,object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    handleFacebookAccessToken(result?.accessToken)
                }

                override fun onCancel() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(error: FacebookException?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


            })
    }
fun handleFacebookAccessToken(token : AccessToken?){
    var credential = FacebookAuthProvider.getCredential(token?.token!!)
    auth?.signInWithCredential(credential)
}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode,resultCode,data)

        if(requestCode==GOOGLE_LOGIN_CODE) {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess){
                var account = result.signInAccount
                //firebaseAuthWithGoogle(account!! )
                firebaseAuthWithGoogle(account!!)

            }
        }
    }
}
