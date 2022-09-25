package com.example.androidstudy_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidstudy_receiver.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //add......................
        registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))!!.apply {
            when(getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                BatteryManager.BATTERY_PLUGGED_USB -> {
                    binding.chargingResultView.text = "USB Plugged"
                binding.chargingImageView.setImageBitmap(
                    BitmapFactory.decodeResource(resources, R.drawable.usb))}
                BatteryManager.BATTERY_PLUGGED_AC -> {
                    binding.chargingResultView.text = "AC Plugged"
                    binding.chargingImageView.setImageBitmap(
                        BitmapFactory.decodeResource(resources, R.drawable.ac))}
                else -> {
                    binding.chargingResultView.text = "NotPlugged"
                }
            }
            val level = getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPct = level/scale.toFloat()*100
            binding.percentResultView.text = "$batteryPct %"
        }

        binding.button.setOnClickListener{
            val intent = Intent(this, MyReceiver::class.java)
            sendBroadcast(intent)
        }

        val bootingIntent = Intent(this, BootReceiver::class.java)
        sendBroadcast(bootingIntent)

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent:Intent?){
                when(intent?.action) {
                    Intent.ACTION_SCREEN_ON -> Log.d("Gio", "screen on")
                    Intent.ACTION_SCREEN_OFF -> Log.d("Gio", "screen off")
                }
            }
        }

        val filter = IntentFilter(Intent.ACTION_SCREEN_ON).apply{
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(receiver, filter)
    }
}