package pl.sjmprofil.grocerylist.adapters

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_row_primary_fragment.view.*
import pl.sjmprofil.grocerylist.R
import pl.sjmprofil.grocerylist.model.Item

class PrimaryFragmentRecyclerViewAdapter: RecyclerView.Adapter<PrimaryFragmentViewHolder>() {

    private val primaryItemList = mutableListOf<Item>()

    var onDeleteImageUsed: ((Item, Int) -> Unit?)? = null
    var onMoveImageUsed: ((Item, Int) -> Unit?)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): PrimaryFragmentViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemRow = layoutInflater.inflate(R.layout.item_row_primary_fragment, viewGroup, false)
        return PrimaryFragmentViewHolder(itemRow)
    }

    override fun getItemCount(): Int {
        return primaryItemList.size
    }

    override fun onBindViewHolder(viewHolder: PrimaryFragmentViewHolder, position: Int) {
        viewHolder.view.tv_item_name_item_row_primary_fragment.text = primaryItemList[position].name
        viewHolder.view.tv_item_id_item_row_primary_fragment.text = primaryItemList[position].userName.toString()
        viewHolder.view.iv_delete_picture_item_row_primary_fragment.isClickable = true
        viewHolder.view.iv_swipe_picture_item_row_primary_fragment.isClickable = true
        //viewHolder.view.setBackgroundColor(ContextCompat.getColor(viewHolder.view.context, R.color.colorCreamTransparent))

        viewHolder.view.iv_delete_picture_item_row_primary_fragment.setOnClickListener{
            onDeleteImageUsed?.invoke(primaryItemList[viewHolder.adapterPosition], primaryItemList[viewHolder.adapterPosition].id ?:0)
            viewHolder.view.iv_delete_picture_item_row_primary_fragment.isClickable = false
            viewHolder.view.setBackgroundColor(ContextCompat.getColor(viewHolder.view.context, R.color.colorPrimaryTransparent))
        }

        viewHolder.view.iv_swipe_picture_item_row_primary_fragment.setOnClickListener{
            onMoveImageUsed?.invoke(primaryItemList[viewHolder.adapterPosition], primaryItemList[viewHolder.adapterPosition].id ?:0)
            viewHolder.view.iv_swipe_picture_item_row_primary_fragment.isClickable = false
            viewHolder.view.setBackgroundColor(ContextCompat.getColor(viewHolder.view.context, R.color.colorPrimaryTransparent))
        }
    }

    fun swapFragmentItemList(itemList: MutableList<Item>) {
        primaryItemList.clear()
        primaryItemList.addAll(itemList)
        primaryItemList.sortBy { it.name }
        notifyDataSetChanged()
    }

    fun deletePrimaryFragmentItem(item: Item){
        val index = primaryItemList.indexOf(item)
        primaryItemList.remove(item)
        notifyItemRemoved(index)
    }

    fun addPrimaryFragmentItem(item: Item) {
        primaryItemList.add(item)
        primaryItemList.sortBy { it.name }
        notifyItemInserted(primaryItemList.indexOf(item))
    }
}

class PrimaryFragmentViewHolder(var view: View): RecyclerView.ViewHolder(view) {

}