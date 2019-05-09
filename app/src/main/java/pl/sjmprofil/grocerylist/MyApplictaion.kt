package pl.sjmprofil.grocerylist

import android.app.Application
import android.arch.persistence.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import pl.sjmprofil.grocerylist.adapters.LocalFragmentRecyclerViewAdapter
import pl.sjmprofil.grocerylist.database.Database
import pl.sjmprofil.grocerylist.database.Repository
import pl.sjmprofil.grocerylist.network.ApiRepository
import pl.sjmprofil.grocerylist.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MyApplictaion: Application(), KodeinAware {

    override val kodein by Kodein.lazy {

        bind<OkHttpClient>() with singleton {
            OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
        }

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .client(instance())
                .baseUrl(applicationContext.getString(R.string.api_endpoint_address))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }

        bind<ApiService>() with singleton {
            val retrofit: Retrofit = instance()
            retrofit.create(ApiService::class.java)
        }

        bind<ApiRepository>() with singleton {
            ApiRepository(applicationContext, instance())
        }

        bind<Database>() with singleton {
            Room.databaseBuilder(applicationContext, Database::class.java, "localGroceryListItem.db")
                .fallbackToDestructiveMigration().build()
        }

        bind<Repository>() with singleton {
            Repository(instance())
        }

        bind<LocalFragmentRecyclerViewAdapter>() with singleton {
            LocalFragmentRecyclerViewAdapter()
        }
    }
}