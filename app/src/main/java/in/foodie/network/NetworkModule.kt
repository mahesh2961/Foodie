package `in`.foodie.network


import `in`.foodie.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
    val API_HOST = "https://food2fork.com/api/"
    fun buildNetworkApi(): ApiHandler {
        val clientBuilder = OkHttpClient.Builder()
//        clientBuilder.networkInterceptors().add(authorizeInterceptor)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.interceptors().add(logging)
        }

        return Retrofit.Builder().client(clientBuilder.build())
                .baseUrl(API_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiHandler::class.java)

    }
}
