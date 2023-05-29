package com.blinkslabs.blinkist.android.challenge.domain.interactors

import com.blinkslabs.blinkist.android.challenge.domain.repositories.BooksRepository
import com.blinkslabs.blinkist.android.challenge.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchNetworkBookListUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
) {
    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    booksRepository.updateBooksList(),
                ),
            )
        } catch (exception: Exception) {
            emit(
                Resource.Error(
                    message = exception.message ?: "An unexpected error occurred!",
                ),
            )
        }
    }
}
