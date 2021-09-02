package uz.mobiler.pagination.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.mobiler.pagination.databinding.ItemDataBinding
import uz.mobiler.pagination.models.Data

class Paging3Adapter : PagingDataAdapter<Data, Paging3Adapter.Vh>(MyDiffUtill()) {

    inner class Vh(var itemDataBinding: ItemDataBinding) :
        RecyclerView.ViewHolder(itemDataBinding.root) {

        fun onBind(data: Data?) {
            itemDataBinding.apply {
                Picasso.get().load(data?.avatar).into(avatar)
                tv1.text = "${data?.first_name} ${data?.last_name}"
                tv2.text = data?.email
            }
        }
    }

    class MyDiffUtill : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}