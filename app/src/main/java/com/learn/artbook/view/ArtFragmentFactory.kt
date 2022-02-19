package com.learn.artbook.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.learn.artbook.adapter.ArtAdapter
import com.learn.artbook.adapter.ImageAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(

    private val artAdapter : ArtAdapter,
    private val glide : RequestManager,
    private val imageAdapter : ImageAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className) {
            ArtFragment::class.java.name -> ArtFragment(artAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }
}