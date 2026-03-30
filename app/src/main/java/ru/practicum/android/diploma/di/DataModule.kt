package ru.practicum.android.diploma.di

import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.feature.search.data.NetworkClient
import ru.practicum.android.diploma.feature.search.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.feature.search.data.network.VacancyApiService
import ru.practicum.android.diploma.feature.vacancy.data.ExternalNavigator
import ru.practicum.android.diploma.feature.vacancy.data.navigator.ExternalNavigatorImpl
import ru.practicum.android.diploma.util.AuthInterceptor
import ru.practicum.android.diploma.util.ConnectionChecker

const val VACANCY_BASE_URL = "https://android-diploma.education-services.ru/"

const val apiAcces = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwcmFjdGljdW0ucnUiLCJhdWQiOiJwcmFjdGljdW0ucnUiLCJ1c2VybmFtZSI6InBsdXNpa25vaXN5QGdtYWlsLmNvbSJ9.2n5ISGkvja0Xb8Q_FJ--ukXyJ0v_tZolj87leTRr0bg"

val dataModule = module {

    single<VacancyApiService> {
        Retrofit.Builder()
            .baseUrl(VACANCY_BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor(apiAcces)).build())
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
}
