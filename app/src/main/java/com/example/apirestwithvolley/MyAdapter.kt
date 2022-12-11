package com.example.apirestwithvolley

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyAdapter (private val listData: MutableList<photo>, private val listener: OnItemClickListener):
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var ps : Int = 0
     var selectedItemPosition: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ligne, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo: photo = listData[position]
        holder.bind(photo)

        holder.itemView.setOnClickListener {
            selectedItemPosition = position
            notifyDataSetChanged()
        }
        if(selectedItemPosition == position)
            holder.title.setBackgroundColor(Color.parseColor("#DC746C"))
        else
            holder.title.setBackgroundColor(Color.WHITE)

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView),
        View.OnClickListener {

        private var albumId: TextView = itemView.findViewById(R.id.albumId)
        private var id: TextView = itemView.findViewById(R.id.id)
         var title: TextView = itemView.findViewById(R.id.title)
        private var url: TextView = itemView.findViewById(R.id.url)
        private var thumbnailUrl: TextView = itemView.findViewById(R.id.thumbnailUrl)
        private var delete: Button = itemView.findViewById(R.id.delete)
        private var update: Button = itemView.findViewById(R.id.update)
        var cardView: CardView = itemView.findViewById(R.id.card)


        init {
            ItemView.setOnClickListener(this)
        }

        fun bind(photo: photo) {
            albumId.text = "albumId : " + photo.albumId.toString()
            id.text = "id : " + photo.id.toString()
            title.text = "title : " + photo.title
            url.text = "url : " + photo.url
            thumbnailUrl.text = "thumbnailUrl : " + photo.thumbnailUrl
            delete.setOnClickListener { v ->
                var cc = photo.id
                var url = "https://jsonplaceholder.typicode.com/photos/$cc"
                val queue = Volley.newRequestQueue(v.context)
                val request =object : StringRequest(Request.Method.DELETE, url,
                Response.Listener { response ->
                    Toast.makeText(v.context, "photo supprimer", Toast.LENGTH_SHORT).show();
                    listData.removeAt(adapterPosition)
                    notifyDataSetChanged()
                },
                    Response.ErrorListener { error ->
                        Toast.makeText(v.context, "Erroooooor", Toast.LENGTH_SHORT).show();
                    }){ }
                queue.add(request)
            }
            update.setOnClickListener{ v ->
                val intent = Intent(v.context, updatePhoto::class.java)
                intent.putExtra("albumId", photo.albumId)
                intent.putExtra("id", photo.id )
                intent.putExtra("title", photo.title )
                intent.putExtra("url", photo.url )
                intent.putExtra("thumbnailUrl", photo.thumbnailUrl )
                v.context.startActivity(intent)

            }

            }

        override fun onClick(p0: View?) {
            ps = adapterPosition
            if (ps != RecyclerView.NO_POSITION) {
                listener.OnItemClick(ps)
            }
        }

    }

    interface OnItemClickListener {
        fun OnItemClick(position: Int)
    }
}
