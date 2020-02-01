package com.umbo.domain

import com.umbo.data.NetworkService
import com.umbo.data.Outcome
import com.umbo.data.Photo

class PhotosRepositoryImpl(
    private val networkService: NetworkService,
    private val storage: PhotosStorage
): PhotosRepository {

    override suspend fun invalidateCachedData() {
        storage.clearData()
    }

    override suspend fun photos():  Outcome<List<Photo>> {
        val result = storage.photos
        val cachedPhotos = (result as? Outcome.Success<List<Photo>>)?.value

        return if (cachedPhotos.isNullOrEmpty()){
            networkService.photos()
        } else {
            result
        }
    }
}