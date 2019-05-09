package pl.sjmprofil.grocerylist.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_row_primary_fragment.view.*
import pl.sjmprofil.grocerylist.R
import pl.sjmprofil.grocerylist.model.Item

class LocalFragmentRecylerViewAdapter: RecyclerView.Adapter<LocalFragmentViewHolder>() {

    private val localFragmentItemList: MutableList<Item> = mutableListOf(Item(1,"A","B","C"))
    var onDeleteImageUsed: ((Item, Int) -> Unit?)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): LocalFragmentViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemRow = layoutInflater.inflate(R.layout.item_row_primary_fragment, viewGroup, false)
        return LocalFragmentViewHolder(itemRow)
    }

    override fun getItemCount(): Int {
        return localFragmentItemList.size
    }

    override fun onBindViewHolder(viewHolder: LocalFragmentViewHolder, position: Int) {
        viewHolder.view.tv_item_name_item_row_primary_fragment.text = localFragmentItemList[position].name
        viewHolder.view.tv_item_id_item_row_primary_fragment.text = localFragmentItemList[position].userName.toString()
        viewHolder.view.iv_delete_picture_item_row_primary_fragment.isClickable = true
        viewHolder.view.setBackgroundColor(ContextCompat.getColor(viewHolder.view.context, R.color.colorCreamTransparent))

        viewHolder.view.iv_delete_picture_item_row_primary_fragment.setOnClickListener{
            onDeleteImageUsed?.invoke(localFragmentItemList[viewHolder.adapterPosition], localFragmentItemList[viewHolder.adapterPosition].id ?:0)
            viewHolder.view.iv_delete_picture_item_row_primary_fragment.isClickable = false
            viewHolder.view.setBackgroundColor(ContextCompat.getColor(viewHolder.view.context, R.color.colorPrimaryTransparent))
        }
    }

    fun swapFragmentItemList(itemList: MutableList<Item>) {
        localFragmentItemList.clear()
        localFragmentItemList.addAll(itemList)
        localFragmentItemList.sortBy { it.name }
        notifyDataSetChanged()
    }
}

class LocalFragmentViewHolder(val view: View): RecyclerView.ViewHolder(view){

}