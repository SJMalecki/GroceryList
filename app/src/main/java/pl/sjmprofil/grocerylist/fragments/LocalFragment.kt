package pl.sjmprofil.grocerylist.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_local.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.sjmprofil.grocerylist.R
import pl.sjmprofil.grocerylist.adapters.LocalFragmentRecylerViewAdapter
import pl.sjmprofil.grocerylist.adapters.PrimaryFragmentRecyclerViewAdapter
import pl.sjmprofil.grocerylist.model.Item

class LocalFragment : Fragment() {

    private var localAdapter: LocalFragmentRecylerViewAdapter = LocalFragmentRecylerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        synchronizeItemList()
    }

    private fun setupAdapter() {
        recycler_view_local_fragment.adapter = localAdapter
    }

    private fun synchronizeItemList()  {
        GlobalScope.launch(Dispatchers.Main) {
            //val itemObject: JsonObject = apiRepository.getRegisterData()
            //val itemList: MutableList<Item> = itemObject.response as MutableList<Item>
            val itemList: MutableList<Item> = mutableListOf(Item(0,"Same", "TheSAme","weee"))
            localAdapter.swapFragmentItemList(itemList)
//            swipe_refresh_layout_primary_fragment.isRefreshing = false
        }
    }
}
