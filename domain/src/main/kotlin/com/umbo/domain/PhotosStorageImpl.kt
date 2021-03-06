package com.umbo.domain

import com.umbo.data.Outcome
import com.umbo.data.Photo
import com.umbo.data.PhotosStorage

class PhotosStorageImpl: PhotosStorage {

    private var storedPhotos: MutableList<Photo> = mutableListOf()

    override fun replacePhotos(photos: List<Photo>) {
        storedPhotos.clear()
        storedPhotos.addAll(photos)
    }

    override val photos: Outcome<List<Photo>>
        get() = Outcome.Success(storedPhotos) // if from database could return an error

    override fun clearData() {
        storedPhotos.clear()
    }
}