package mg.watched.data.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import mg.watched.data.BuildConfig
import mg.watched.data.UltimateListPreferences
import mg.watched.data.anime.AnimeRepository
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.AnimeMoshiAdapters
import mg.watched.data.authentication.AuthenticationManager
import mg.watched.data.authentication.AuthenticationService
import mg.watched.data.user.UserRepository
import mg.watched.data.user.network.UserService
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

    factory<SharedPreferences> { androidContext().getSharedPreferences("watched", Context.MODE_PRIVATE) }

    factory<UltimateListPreferences> { UltimateListPreferences(get(), get()) }

    single<AnimeRepository> { AnimeRepository(get()) }

    single<AuthenticationManager> { AuthenticationManager(get(), get()) }

    single<UserRepository> { UserRepository(get(), get()) }

    // endregion

    // region Network

    single<Moshi> {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .add(AnimeMoshiAdapters())
            .build()
    }

    single<OkHttpClient>(named("default")) {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").log(Log.INFO, message)
        }.apply {
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
        val authorizationInterceptor: Interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().newBuilder()
                    .header("authorization", "Bearer " + authenticationManager.accessToken!!)
                    .build()
                return chain.proceed(request)
            }
        }

        defaultOkHttpClient.newBuilder()
            .apply {
                // Add authorization interceptor before the logging interceptor
                interceptors().add(interceptors().size - 2, authorizationInterceptor)
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
