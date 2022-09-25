package com.example.androidstudy20220821

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidstudy20220821.databinding.ActivityMainBinding

const val tag = "gioKim"
lateinit var binding: ActivityMainBinding
var datas: MutableList<String>? = null
lateinit var adapter: MyAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cameraRequestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            val bitmap = it?.data?.extras?.get("data") as Bitmap
        }

        val addRequestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            it.data!!.getStringExtra("result")?.let {
                datas?.add(it)
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
        }

        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        } ?: let {
            mutableListOf<String>()
        }


        binding.recyclerView.adapter = MyAdapter(datas)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.addTodoFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            addRequestLauncher.launch(intent)
        }

        binding.cameraFab.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraRequestLauncher.launch(intent)
        }

        binding.webFab.setOnClickListener {

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))
    }
}
