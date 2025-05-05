package com.group.findit.ui.startgame.model

import com.group.findit.ui.domain.model.Participant
import retrofit2.Response
import java.io.IOException

/**
 * Extension function to map [RemoteParticipantDto] to [Participant].
 * @return A [Participant] object.
 */
fun RemoteParticipantDto.toDomain() = Participant(
    name = "${name.first} ${name.last}",
    nat = nat,
    age = dob.age
)

/**
 * Extension function to map a [Response] of [RemoteParticipantDto] to a [Result] of [Participant].
 * @return A [Result] containing a [Participant] if successful, or an [IOException] if not.
 */
fun Response<RemoteParticipantDto>.toDomain() =
    if (isSuccessful) Result.success((body() as RemoteParticipantDto).toDomain())
    else Result.failure(IOException())