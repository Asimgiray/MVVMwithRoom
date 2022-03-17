package com.example.mvvmwithroomnoteapp.utils

import android.view.View
import androidx.core.view.isVisible
import org.junit.Assert.*
import org.junit.Test
import org.powermock.api.mockito.PowerMockito

class UtilityTest{

    @Test
    fun test_is_null_or_empty_with_empty() {
        val str = ""
        val result = Utility.isNullOrEmpty(str)
        assertTrue(result)
    }

    @Test
    fun test_is_null_or_empty_with_null() {
        val str: String? = null
        val result = Utility.isNullOrEmpty(str)
        assertTrue(result)
    }

    @Test
    fun test_bool_to_visibility_true() {
        val view: View = PowerMockito.mock(View::class.java)
        Utility.boolToVisibility(view , true)
        assertTrue(view.isVisible)
    }

    @Test
    fun test_bool_to_visibility_false() {
        val view: View = PowerMockito.mock(View::class.java)
        Utility.boolToVisibility(view , false)
        assertTrue(!view.isVisible)
    }
}