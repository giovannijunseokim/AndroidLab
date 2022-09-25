package com.example.ch13_activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch13_activity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //add................................

        val requestLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            it.data!!.getStringExtra("result")?.let {
                datas?.add(it)
                adapter.notifyDataSetChanged()
            }
        }

        val cameraRequestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == RESULT_OK) {
                val bitmap = it?.data?.extras?.get("data") as Bitmap
            }
        }

        binding.addTodoFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            requestLauncher.launch(intent)
        }

        binding.cameraFab.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraRequestLauncher.launch(intent)
        }
        binding.webFab.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            startActivity(intent)
        }

        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        } ?: let {
            mutableListOf<String>()
        }

        

        val layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.layoutManager=layoutManager
        adapter=MyAdapter(datas)
        binding.mainRecyclerView.adapter=adapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))
    }

}