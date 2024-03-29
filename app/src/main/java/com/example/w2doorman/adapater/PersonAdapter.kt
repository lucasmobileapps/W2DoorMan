package com.example.w2doorman.adapater

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.w2doorman.R
import com.example.w2doorman.model.Constants
import com.example.w2doorman.model.Persons
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.person_item_view_layout.*
import kotlinx.android.synthetic.main.person_item_view_layout.view.*

class PersonAdapter(private val personadapterDelegate: PersonAdapterDelegate)
    : androidx.recyclerview.widget.ListAdapter<Persons, PersonAdapter.PersonViewHolder>(PersonDiffUtil()){

    interface PersonAdapterDelegate

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonViewHolder {
        val view = LayoutInflater. from(parent.context).inflate(R.layout.person_item_view_layout, parent, false)

    return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.apply {
            personName?.text = getItem(position).person
            personRelation?.text = getItem(position).relation
            viewGroup?.context?.let {
                Glide.with(it) //1
                    .load(getItem(position).url)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .error(R.drawable.ic_person_black_24dp)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(CircleCrop())
                    .into(personImage)
            }
        }

    }

    class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val personName :TextView? = view.findViewById(R.id.name_textview)
        val personRelation :TextView? = view.findViewById(R.id.relation_textview)
        val viewGroup: ConstraintLayout? = view.findViewById(R.id.person_item_view_layout)
        val personImage : ImageView = view.findViewById(R.id.name_imageview)
    }
}

class PersonDiffUtil: DiffUtil.ItemCallback<Persons>(){
    override fun areItemsTheSame(oldItem: Persons, newItem: Persons): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Persons, newItem: Persons): Boolean {
        return oldItem.person == newItem.person && oldItem.relation == newItem.relation
    }
}