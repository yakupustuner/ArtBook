package com.learn.artbook.repo

import androidx.lifecycle.LiveData
import com.learn.artbook.api.RetrofitAPI
import com.learn.artbook.model.ImageResponse
import com.learn.artbook.roomdb.Art
import com.learn.artbook.roomdb.ArtDao
import com.learn.artbook.util.Resource
import com.learn.artbook.util.Util.API_KEY
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import kotlin.Exception

@ActivityScoped
class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val  retrofitApi: RetrofitAPI): ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }


    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.imageSearch(imageString, apiKey = API_KEY)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }

        } catch (e: Exception) {
            Resource.error("Data ! ",null)
        }
    }
}