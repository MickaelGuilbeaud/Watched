package mg.template.data.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import mg.template.data.BuildConfig
import mg.template.data.UltimateListPreferences
import mg.template.data.anime.AnimeRepository
import mg.template.data.anime.network.AnimeService
import mg.template.data.authentication.AuthenticationManager
import mg.template.data.authentication.AuthenticationService
import mg.template.data.user.UserRepository
import mg.template.data.user.network.UserService
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

    // region Repositories

    factory<SharedPreferences> { androidContext().getSharedPreferences("ultimate_list", Context.MODE_PRIVATE) }

    factory<UltimateListPreferences> { UltimateListPreferences(get(), get()) }

    single<AnimeRepository> { AnimeRepository(get(), get()) }

    single<AuthenticationManager> { AuthenticationManager(get(), get(), get()) }

    single<UserRepository> { UserRepository(get(), get(), get()) }

    // endregion

    // region Network

    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single<OkHttpClient>(named("default")) {
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

        val malClientIdInterceptor: (Interceptor.Chain) -> Response = { chain ->
            val request = chain.request().newBuilder()
                .header("X-MAL-Client-ID", "6114d00ca681b7701d1e15fe11a4987e")
                .build()
            chain.proceed(request)
        }

        OkHttpClient.Builder()
            .addInterceptor(malClientIdInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    single<OkHttpClient>(named("authenticated")) {
        val defaultOkHttpClient: OkHttpClient = get(named("default"))
        val authenticationManager: AuthenticationManager = get()
        defaultOkHttpClient.newBuilder()
            .addNetworkInterceptor { chain ->
                // TODO: Handle missing access token
                val request = chain.request().newBuilder()
                    .header("authorization", "Bearer " + authenticationManager.accessToken!!)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single<Retrofit>(named("default")) {
        Retrofit.Builder()
            .baseUrl(get<String>(named("baseUrl")))
            .client(get(named("default")))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single<Retrofit>(named("authenticated")) {
        val defaultRetrofit: Retrofit = get(named("default"))
        defaultRetrofit.newBuilder()
            .client(get(named("authenticated")))
            .build()
    }

    single<AuthenticationService> {
        val retrofit: Retrofit = get(named("default"))
        retrofit.create(AuthenticationService::class.java)
    }

    single<AnimeService> {
        val retrofit: Retrofit = get(named("authenticated"))
        retrofit.create(AnimeService::class.java)
    }

    single<UserService> {
        val retrofit: Retrofit = get(named("authenticated"))
        retrofit.create(UserService::class.java)
    }

    // endregion

    // region Database

    /*
    single<TemplateDatabase> {
        Room.databaseBuilder(androidContext(), TemplateDatabase::class.java, "template_database")
            .fallbackToDestructiveMigration()
            .build()
    }
     */

    // endregion
}