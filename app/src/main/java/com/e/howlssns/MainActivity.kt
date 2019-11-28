package com.e.howlssns

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){

            R.id.action_search -> {
                var gridFragment = GridFragment()
                println("1")
                supportFragmentManager.beginTransaction().replace(R.id.main_content,gridFragment).commit()
                return true
            }
            R.id.action_home -> {

                var dviewFragment = DetailviewFragment()
                println("2")
                supportFragmentManager.beginTransaction().replace(R.id.main_content,dviewFragment).commit()

                return true
            }
            R.id.action_add_photo -> {
                if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    startActivity(Intent(this,Add_Photo_Activity::class.java))
                }
                return true
            }
            R.id.action_favorite_alarm -> {
                var alertFragment = AlertFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,alertFragment).commit()
                return true
            }
            R.id.action_account -> {
                var userFragment = UserFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,userFragment).commit()
                return true
            }

        }
        return false

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        bottom_navigation.setOnNavigationItemSelectedListener(this)

    }


}
