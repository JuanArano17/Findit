package com.group.findit

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Instrumented test class for the FindIt app.
 * Executes tests on an Android device.
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    /**
     * Verifies that the app context is correctly set up.
     */
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.group.findit", appContext.packageName)
    }
}