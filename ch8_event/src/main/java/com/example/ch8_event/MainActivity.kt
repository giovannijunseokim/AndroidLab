package com.example.ch8_event

import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ch8_event.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var initTime = 0L
    var pauseTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener{
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.start()
            binding.startBtn.isEnabled = false
            binding.stopBtn.isEnabled = true
            binding.resetBtn.isEnabled = true
            Toast.makeText(this, "START 버튼을 눌렀습니다!!",Toast.LENGTH_SHORT).show()
        }

        binding.stopBtn.setOnClickListener{
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.startBtn.isEnabled = true
            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = true
            Toast.makeText(this, "STOP 버튼을 눌렀습니다!!",Toast.LENGTH_SHORT).show()
        }

        binding.resetBtn.setOnClickListener{
            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.startBtn.isEnabled = true
            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = false
            Toast.makeText(this, "RESET 버튼을 눌렀습니다!!",Toast.LENGTH_SHORT).show()
        }
        binding.startBtn.setOnLongClickListener {
            Toast.makeText(this, "해당 기능은 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
            true
        }
        binding.stopBtn.setOnLongClickListener {
            Toast.makeText(this, "해당 기능은 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
            true
        }
        binding.resetBtn.setOnLongClickListener {
            Toast.makeText(this, "해당 기능은 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode===KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis() - initTime > 3000){
                Toast.makeText(this, "종료하려면 한 번 더 누르세요!!", Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
