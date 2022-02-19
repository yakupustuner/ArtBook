package com.learn.artbook.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.artbook.model.ImageResponse
import com.learn.artbook.repo.ArtRepository
import com.learn.artbook.repo.ArtRepositoryInterface
import com.learn.artbook.roomdb.Art
import com.learn.artbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val  repository : ArtRepositoryInterface,


) : ViewModel() {



    val artList = repository.getArt()


    private val images = MutableLiveData<Resource<ImageResponse>>()

    val imageList : LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()

    val selectedImageUrl : LiveData<String>
    get() =  selectedImage

    private var insertArtMsq = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
    get() = insertArtMsq


    fun resetInsertArtMsg() {
        insertArtMsq = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }
    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name : String, artistName: String, year : String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsq.postValue(Resource.error("Enter!! name, artist,year",null))
            return
        }

        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsq.postValue(Resource.error("Enter Number! ",null))
            return
        }
        val art = Art(name,artistName,yearInt,selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsq.postValue(Resource.success(art))

    }

    fun searchForImage(searchString : String) {
        if (searchString.isEmpty()){
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }

    }

        }
