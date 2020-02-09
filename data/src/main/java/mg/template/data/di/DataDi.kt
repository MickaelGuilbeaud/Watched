package mg.template.data.di

import android.util.Log
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import mg.template.data.BuildConfig
import mg.template.data.TemplateDatabase
import mg.template.data.anime.AnimeRepository
import mg.template.data.anime.db.AnimeDao
import mg.template.data.anime.network.AnimeService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

val dataDiModule = module {

    // region Stores

    single<AnimeRepository> { AnimeRepository(get(), get()) }

    // endregion

    // region Network

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

    single<AnimeService> {
        val retrofit: Retrofit = get()
        retrofit.create(AnimeService::class.java)
    }

    // endregion

    // region Database

    single<TemplateDatabase> {
        Room.databaseBuilder(androidContext(), TemplateDatabase::class.java, "template_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<AnimeDao> {
        val db: TemplateDatabase = get()
        db.animeDao()
    }

    // endregion
}