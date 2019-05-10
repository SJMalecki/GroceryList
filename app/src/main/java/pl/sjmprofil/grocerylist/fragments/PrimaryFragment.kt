package pl.sjmprofil.grocerylist.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_primary.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.grocerylist.R
import pl.sjmprofil.grocerylist.adapters.LocalFragmentRecyclerViewAdapter
import pl.sjmprofil.grocerylist.adapters.PrimaryFragmentRecyclerViewAdapter
import pl.sjmprofil.grocerylist.database.LocalItem
import pl.sjmprofil.grocerylist.database.Repository
import pl.sjmprofil.grocerylist.model.Item
import pl.sjmprofil.grocerylist.model.JsonObject
import pl.sjmprofil.grocerylist.network.ApiRepository

class PrimaryFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val apiRepository: ApiRepository by instance()

    private val localAdapter: LocalFragmentRecyclerViewAdapter by instance()
    private val localItemRepository: Repository by instance()

    private val dialogFragmentAddItem = AddItemDialogFragment()
    private var onSwipeJob: Job? = null
    private var onDeleteItemJob: Job? = null
    private var onAddItemJob: Job? = null

    private var primaryAdapter: PrimaryFragmentRecyclerViewAdapter = PrimaryFragmentRecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_primary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupPrimaryAdapterOnSwipe()

        if (checkForInternetConnection()){
            swipe_refresh_layout_primary_fragment.isRefreshing = true
            synchronizeItemList()
        }

        primaryAdapter.onDeleteImageUsed = {item: Item, id: Int -> deleteItemFromItemList(item, id)}
        primaryAdapter.onMoveImageUsed = {item: Item, id: Int -> movedItemFromItemList(item, id)}
        setupEditBioButton()
    }

    private fun setupPrimaryAdapterOnSwipe() {
        swipe_refresh_layout_primary_fragment.setOnRefreshListener {
            if (checkForInternetConnection()) {
                    synchronizeItemList()
            } else {
                swipe_refresh_layout_primary_fragment.isRefreshing = false
            }
        }
    }

    private fun setupEditBioButton() {
        button_add_primary_fragment.setOnClickListener {
            dialogFragmentAddItem.show(fragmentManager, "DialogFragmentAddBio")
            dialogFragmentAddItem.onAddButtonClick = { header, description -> addItemToItemList(header, description, "0") }
        }
    }

    private fun setupAdapter() {
        recycler_view_primary_fragment.adapter = primaryAdapter
    }

    private fun synchronizeItemList()  {
        onSwipeJob = GlobalScope.launch(Dispatchers.Main) {
            val itemObject: JsonObject = apiRepository.getRegisterData()
            val itemList: MutableList<Item> = itemObject.response as MutableList<Item>
            primaryAdapter.swapFragmentItemList(itemList)
            swipe_refresh_layout_primary_fragment.isRefreshing = false
        }
    }

    private fun deleteItemFromItemList(item: Item, id: Int) {
        if(checkForInternetConnection()){
            onDeleteItemJob = GlobalScope.launch(Dispatchers.Main) {
                apiRepository.postForDeleteItem(id)
                primaryAdapter.deletePrimaryFragmentItem(item)
            }
        }
    }

    private fun movedItemFromItemList(item: Item, id: Int) {
        val s1 = item.name
        val s2 = item.userName
        val s3 = item.userPassword
        onAddItemJob = GlobalScope.launch(Dispatchers.Main) {
            val localItem = LocalItem(null, s1, s2, s3)
            localItemRepository.addLocalJoke(localItem)
            val lastAddedItem = localItemRepository.getAllLocalJokes().last()
            localAdapter.addLocalFragmentItem(lastAddedItem)
        }
        if(checkForInternetConnection()){
            onDeleteItemJob = GlobalScope.launch(Dispatchers.Main) {
                apiRepository.postForDeleteItem(id)
                primaryAdapter.deletePrimaryFragmentItem(item)
            }
        }
    }

    private fun addItemToItemList(s1: String?, s2: String?, s3: String?) {
       if(checkForInternetConnection()){
           onAddItemJob = GlobalScope.launch(Dispatchers.Main) {
               apiRepository.postRegisterData(s1 ?:"",s2 ?:"", s3 ?:"")
               val itemObject: JsonObject = apiRepository.getRegisterData()
               val itemList: MutableList<Item> = itemObject.response as MutableList<Item>
               var itemuu = itemList[itemList.size-1]
               primaryAdapter.addPrimaryFragmentItem(itemuu)
           }
       }
    }

    // Network connection
    private fun checkForInternetConnection(): Boolean {

        val connectivityManager =
            context!!.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isDeviceConnectedToInternet = networkInfo != null && networkInfo.isConnected
        return if (isDeviceConnectedToInternet) {
            true
        } else {
            createConnectionAlertDialog()
            false
        }
    }

    private fun createConnectionAlertDialog() {
        val alertDialog: AlertDialog = AlertDialog.Builder(context!!).create()
        alertDialog.setTitle(getString(R.string.network_error))
        alertDialog.setMessage(getString(R.string.check_internet_connection))
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            getString(R.string.ok)
        ) { dialog: DialogInterface?, which: Int ->
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }

        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            getString(R.string.cancel)
        ) { dialog: DialogInterface?, which: Int ->
            Toast.makeText(context!!, getString(R.string.connection_condition), Toast.LENGTH_LONG).show()
        }
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        onSwipeJob?.cancel()
        onDeleteItemJob?.cancel()
        onAddItemJob?.cancel()
    }
}
