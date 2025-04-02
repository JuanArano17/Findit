package com.group.findit.ui.startgame.model

import com.group.findit.ui.domain.model.Participant
import retrofit2.Response
import java.io.IOException

fun RemoteParticipantDto.toDomain() = Participant(
    name = "${name.first} ${name.last}",
    nat = nat,
    age = dob.age
)
fun Response<RemoteParticipantDto>.toDomain() =
    if (isSuccessful) Result.success((body() as RemoteParticipantDto).toDomain())
    else Result.failure(IOException())