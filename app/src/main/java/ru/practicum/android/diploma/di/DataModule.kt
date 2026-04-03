package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.db.data.AppDatabase
import ru.practicum.android.diploma.feature.filter.data.FilterRegionNetworkClient
import ru.practicum.android.diploma.feature.filter.data.StorageClient
import ru.practicum.android.diploma.feature.filter.data.network.RetrofitFilterRegionNetworkClient
import ru.practicum.android.diploma.feature.filter.data.storage.SharedPrefsStorageClient
import ru.practicum.android.diploma.feature.search.data.NetworkClient
import ru.practicum.android.diploma.feature.search.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.feature.search.data.network.VacancyApiService
import ru.practicum.android.diploma.feature.vacancy.data.ExternalNavigator
import ru.practicum.android.diploma.feature.vacancy.data.navigator.ExternalNavigatorImpl
import ru.practicum.android.diploma.util.AuthInterceptor
import ru.practicum.android.diploma.util.ConnectionChecker
import ru.practicum.android.diploma.util.ResourceProvider
import ru.practicum.android.diploma.util.ResourceProviderImpl

const val VACANCY_BASE_URL = "https://android-diploma.education-services.ru/"
val api =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwcmFjdGljdW0ucnUiLCJhdWQiOiJwcmFjdGljdW0ucnUiLCJ1c2VybmFtZSI6InBsdXNpa25vaXN5QGdtYWlsLmNvbSJ9.2n5ISGkvja0Xb8Q_FJ--ukXyJ0v_tZolj87leTRr0bg"
val dataModule = module {

    single<VacancyApiService> {
        Retrofit.Builder()
            .baseUrl(VACANCY_BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor(api)).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VacancyApiService::class.java)
    }

    single {
        androidContext().getSharedPreferences("filter_prefs", Context.MODE_PRIVATE)
    }

    single { Gson() }

    single<StorageClient<String>> {
        SharedPrefsStorageClient(get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), get())
    }

    single<FilterRegionNetworkClient> {
        RetrofitFilterRegionNetworkClient(get(), get())
    }

    single<ConnectionChecker> {
        ConnectionChecker(androidContext())
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db").build()
    }

    single {
        get<AppDatabase>().getVacancyDao()
    }

    single<ResourceProvider> {
        ResourceProviderImpl(androidContext())
    }
}
