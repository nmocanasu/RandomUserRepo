package com.example.myapplication.ui.main.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.ui.main.view.model.ListUser


class UserAdapter(private val clickListener: ((ListUser) -> Unit) = { _ -> }) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object
    {
        private const val VIEW_TYPE_DATA = 0
        private const val VIEW_TYPE_PROGRESS = 1
    }

    private var dataSet: MutableList<ListUser> = mutableListOf()

     internal fun updateDataSource(list: List<ListUser>){
         dataSet.takeUnless { it.isEmpty() }.let { it?.dropLast(1) }

         dataSet.addAll(list)
         notifyDataSetChanged()
     }

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            private val textView: TextView = view.findViewById(R.id.name)
            private val imageView: ImageView = view.findViewById(R.id.thumbNail)

            fun bind(item: ListUser, clickListener: ((ListUser) -> Unit)){

                textView.text = item.name
                // Aici de lene am trisat un pic, cu inca o tabela/Entity in room si retrofit, se putea lejer lucra fara Glide ;).
                Glide.with(view.context).load(item.thumb).into(imageView)

                view.setOnClickListener{ clickListener(item) }
            }
        }

    private class LoadingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(){

        }
    }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder  = when(viewType) {
            // Create a new view, which defines the UI of the list item
            VIEW_TYPE_DATA -> {

                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.users_list_item, viewGroup, false)

                ViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.list_item_loading, viewGroup, false)

                LoadingViewHolder(view)
            }
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

            when(viewHolder){
                is ViewHolder -> {
                    viewHolder.bind(dataSet[position], clickListener)
                }
                is LoadingViewHolder -> {
                    viewHolder.bind()
                }
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataSet.size

    override fun getItemViewType(position: Int): Int = when (position) {
            dataSet.count() - 1 -> VIEW_TYPE_PROGRESS
            else -> VIEW_TYPE_DATA
        }
}