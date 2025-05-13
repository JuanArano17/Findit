package com.group.findit.ui.game.model

import com.group.findit.ui.domain.model.Photo
import retrofit2.Response
import java.io.IOException

/**
 * Extension function to map [PhotoDto] to [Photo].
 * @return A [Photo] object.
 */
fun PhotoDto.toDomain() = Photo(
    name = name,
    image = picture.large
)

/**
 * Extension function to map a [Response] of [PhotoDto] to a [Result] of [Photo].
 * @return A [Result] containing a [Photo] if successful, or an [IOException] if not.
 */
fun Response<PhotoDto>.toDomain() =
    if (isSuccessful) Result.success((body() as PhotoDto).toDomain())
    else Result.failure(IOException())