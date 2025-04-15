package com.group.findit.ui.game.model

import com.group.findit.ui.domain.model.Participant
import retrofit2.Response
import java.io.IOException

fun PhotoDto.toDomain() = Photo(
    name = name,
    image = picture.large
)
fun Response<PhotoDto>.toDomain() =
    if (isSuccessful) Result.success((body() as PhotoDto).toDomain())
    else Result.failure(IOException())