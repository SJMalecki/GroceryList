package pl.sjmprofil.grocerylist.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.grocerylist.R
import pl.sjmprofil.grocerylist.network.ApiRepository

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by org.kodein.di.android.kodein(this)
    private val apiRepository: ApiRepository by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        GlobalScope.launch(Dispatchers.Main) {
//            val user = apiRepository.getRegisterData()
//            println(user.response!![0])
//            tvMain.text = user.response!![0].name
//            indexer = user.response!![0].id
//
//            val apii = apiRepository.postRegisterData("A1", "A2", "A3")
//            println(apii)
//
//
//            println(apiRepository.postForDeleteItem(49))
//        }
    }
}
