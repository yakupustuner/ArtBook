package com.learn.artbook.repo

import androidx.lifecycle.LiveData
import com.learn.artbook.model.ImageResponse
import com.learn.artbook.roomdb.Art
import com.learn.artbook.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton


interface ArtRepositoryInterface {

    suspend fun insertArt(art : Art )

    suspend fun deleteArt(art : Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>
}