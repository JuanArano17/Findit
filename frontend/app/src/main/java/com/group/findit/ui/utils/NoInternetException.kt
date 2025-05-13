package com.group.findit.ui.utils

/**
 * Custom exception for handling cases where there is no internet connection.
 * @param message The error message to be displayed.
 */
class NoInternetException(message: String) : Exception(message)