package com.learn.artbook.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.learn.artbook.R
import com.learn.artbook.api.RetrofitAPI
import com.learn.artbook.repo.ArtRepository
import com.learn.artbook.repo.ArtRepositoryInterface
import com.learn.artbook.roomdb.Art
import com.learn.artbook.roomdb.ArtDao
import com.learn.artbook.roomdb.ArtDatabase
import com.learn.artbook.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import dagger.hilt.internal.ComponentEntryPoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context : Context) = Room.databaseBuilder(
        context,ArtDatabase::class.java,"ArtBookDB"
        ).build()




    @Singleton
    @Provides
    fun injectDao(database : ArtDatabase) = database.artDao()


    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(RetrofitAPI::class.java)

    }

    @Singleton
    @Provides
    fun injectRepo(dao : ArtDao, api: RetrofitAPI) = ArtRepository(dao,api) as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injectGlidee(@ApplicationContext context: Context) = Glide.with(context)
        .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground))
}