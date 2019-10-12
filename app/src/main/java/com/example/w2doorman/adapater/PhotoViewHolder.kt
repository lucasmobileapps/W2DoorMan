package com.example.w2doorman.adapater

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.w2doorman.R
import kotlinx.android.synthetic.main.person_item_view_layout.view.*

class PhotoViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(
        R.layout.person_item_view_layout, parent, false)) {

    fun bind(photoUrl: String?) {
        val personName : TextView? = itemView.findViewById(R.id.name_textview)
        val personRelation : TextView? = itemView.findViewById(R.id.relation_textview)
        val url = if (photoUrl != null) "$photoUrl?w=360" else null //1
        Glide.with(itemView)  //2
            .load(url) //3
            .centerCrop() //4
            .placeholder(R.drawable.ic_person_black_24dp) //5
            .error(R.drawable.ic_person_black_24dp) //6
            .fallback(R.drawable.ic_person_black_24dp) //7
            .into(itemView.name_imageview) //8
    }
}