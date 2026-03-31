package ru.practicum.android.diploma.di

import androidx.room.Room
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.db.data.AppDatabase
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

val dataModule = module {

    single<VacancyApiService> {
        Retrofit.Builder()
            .baseUrl(VACANCY_BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor(BuildConfig.API_ACCESS_TOKEN)).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VacancyApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), get())
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
