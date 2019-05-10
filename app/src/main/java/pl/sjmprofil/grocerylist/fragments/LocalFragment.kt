package pl.sjmprofil.grocerylist.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_local.*
import kotlinx.android.synthetic.main.fragment_primary.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.grocerylist.R
import pl.sjmprofil.grocerylist.adapters.LocalFragmentRecyclerViewAdapter
import pl.sjmprofil.grocerylist.database.LocalItem
import pl.sjmprofil.grocerylist.database.Repository
import pl.sjmprofil.grocerylist.model.Item
import pl.sjmprofil.grocerylist.model.JsonObject

class LocalFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val localAdapter: LocalFragmentRecyclerViewAdapter by instance()
    private val localItemRepository: Repository by instance()

    private val dialogFragmentAddItem = AddItemDialogFragment()
    private var onDeleteItemJob: Job? = null
    private var onAddItemJob: Job? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

//        GlobalScope.launch(Dispatchers.Main) {
//            localItemRepository.addLocalJoke(LocalItem(null, "seb", "wee", "0"))
//            localItemRepository.addLocalJoke(LocalItem(null, "seb1", "wee2", "01"))
//            localItemRepository.addLocalJoke(LocalItem(null, "seb2", "wee3", "023"))
//        }

        synchronizeItemList()
        setupEditBioButton()
        localAdapter.onDeleteImageUsed = { localItem, id -> deleteItemFromList(localItem, id)}
    }

    private fun setupEditBioButton() {
        button_add_local_fragment.setOnClickListener {
            dialogFragmentAddItem.show(fragmentManager, "DialogFragmentAddBio")
            dialogFragmentAddItem.onAddButtonClick = { header, description -> addItemToItemList(header, description, "0") }
        }
    }

    private fun setupAdapter() {
        recycler_view_local_fragment.adapter = localAdapter
    }

    private fun deleteItemFromList(localItem: LocalItem, id: Long?){
        onDeleteItemJob = GlobalScope.launch(Dispatchers.Main) {
            localItemRepository.deleteLocalJoke(localItem)
            localAdapter.deleteLocalFragmentItem(id)
        }
    }

    private fun addItemToItemList(s1: String?, s2: String?, s3: String?) {
            onAddItemJob = GlobalScope.launch(Dispatchers.Main) {
                val localItem = LocalItem(null, s1, s2, s3)
                localItemRepository.addLocalJoke(localItem)
                val lastAddedItem = localItemRepository.getAllLocalJokes().last()
                localAdapter.addLocalFragmentItem(lastAddedItem)
            }
    }

    private fun synchronizeItemList()  {
        GlobalScope.launch(Dispatchers.Main) {
            val itemList = localItemRepository.getAllLocalJokes()
            localAdapter.swapFragmentItemList(itemList)
//            swipe_refresh_layout_primary_fragment.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onDeleteItemJob?.cancel()
        onAddItemJob?.cancel()
    }
}
