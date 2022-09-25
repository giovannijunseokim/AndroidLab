package com.example.androidstudy_outer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*

class MyMessengerService : Service() {
    lateinit var messenger : Messenger
    lateinit var replyMessenger : Messenger
    lateinit var player : MediaPlayer

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    inner class IncomingHandler(context: Context, private val applicationContext: Context = context.applicationContext)
        : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }
}