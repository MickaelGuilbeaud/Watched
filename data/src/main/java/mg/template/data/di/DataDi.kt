package mg.template.data.di

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import mg.template.data.BuildConfig
import mg.template.data.pokemon.PokemonService
import mg.template.data.pokemon.PokemonStore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

val dataDiModule = module {

    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single<OkHttpClient> {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("OkHttp").log(Log.INFO, message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY

            if (!BuildConfig.DEBUG) {
                redactHeader("Authorization")
                redactHeader("Cookie")
            }
        }

        val userAgentInterceptor: (Interceptor.Chain) -> Response = { chain ->
            val request = chain.request().newBuilder()
                .header("app", "Template")
                .header("app-version", "1.0.0")
                .build()
            chain.proceed(request)
        }

        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(userAgentInterceptor)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<String>(named("baseUrl")))
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single<PokemonService> {
        val retrofit: Retrofit = get()
        retrofit.create(PokemonService::class.java)
    }

    single<PokemonStore> { PokemonStore(get()) }
}