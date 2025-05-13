package com.group.findit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the FindIt app.
 * Annotated with [HiltAndroidApp] to enable dependency injection with Hilt.
 */
@HiltAndroidApp
class FindItApplication : Application()