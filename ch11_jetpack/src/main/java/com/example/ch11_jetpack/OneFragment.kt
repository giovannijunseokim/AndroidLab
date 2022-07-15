package com.example.ch11_jetpack

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ch11_jetpack.databinding.FragmentOneBinding
import com.example.ch11_jetpack.databinding.ItemRecyclerviewBinding

class MyViewHolder(val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root)
//뷰 홀더 : item_recyclerview 를 가져와서 binding 한 후, 그것을 뷰 홀더로 지정하는 클래스 마이뷰홀더 클래스를 만듬
//여기서 item_recyclerview 는 리사이클 뷰를 받은 뷰 홀더는, 프로필 하나의 구성 xml이다.
class MyAdapter(val datas: MutableList<String>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//뷰 어댑터는 만들어진 뷰홀더에, 각각의 파일을 넣는 역할을 한다. 밑의 세 함수는 부모클래스의 추상 메서드이다.
    override fun getItemCount(): Int {
        return datas.size
    }
//출력할 뷰 홀더의 개수. datas.size는 리스트의 개수를 의미한다. 0 을 리턴하면 아무것도 출력되지 않는다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),
        parent, false))
//위에서 만든 뷰 홀더를 준비
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.itemData.text = datas[position]
    }
}
//뷰 홀더에 정보(text)값을 대입하여, 출력할 준비를 함.

class MyDecoration(val context:Context): RecyclerView.ItemDecoration(){
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state) // 항목 배치 후 꾸밈. 배치 전 꾸밈은 onDraw

        val width = parent.width
        val height = parent.height
        val dr:Drawable? = ResourcesCompat.getDrawable(context.getResources(),
            R.drawable.kbo, null)
        val drWidth =dr?.intrinsicWidth
        val drHeight =dr?.intrinsicHeight

        val left = width/2 - drWidth?.div(2) as Int
        val top = height/2 - drHeight?.div(2) as Int

        c.drawBitmap(
            BitmapFactory.decodeResource(context.getResources(), R.drawable.kbo),
            left.toFloat(),
            top.toFloat(),
            null
        )
    }

    override fun getItemOffsets( // 각 항목을 꾸미는 함수
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view)+1

        if (index%3==0)
            outRect.set(10,10,10,60)
        else
            outRect.set(10, 10, 10, 0)
        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        ViewCompat.setElevation(view, 20.0f )
    }
}

class OneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val binding = FragmentOneBinding.inflate(inflater, container, false)
        val datas = mutableListOf<String>()
        for(i in 1..9){ datas.add("Item$i") }
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))
        return binding.root
    }

}