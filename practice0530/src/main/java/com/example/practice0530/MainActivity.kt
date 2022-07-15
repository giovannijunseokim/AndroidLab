package com.example.practice0530

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
        {
            Log.d("gio","ok")
        }
        else{Log.d("gio","not okx")}
    }
}