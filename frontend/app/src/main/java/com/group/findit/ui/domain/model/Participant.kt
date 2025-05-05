package com.group.findit.ui.domain.model

/**
 * Domain model representing a participant in the game.
 * @property name The name of the participant.
 * @property nat The nationality of the participant.
 * @property age The age of the participant.
 */
data class Participant(val name: String, val nat: String, val age: Int)
