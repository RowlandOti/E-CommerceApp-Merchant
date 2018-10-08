package com.rowland.delivery.services.session

import android.content.Context
import android.content.SharedPreferences
import com.rowland.delivery.services.sharedpreferences.SharedPreferencesManager
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class SessionManagerTest {


    private val VALUE = UUID.randomUUID().toString()

    @Mock
    lateinit var context: Context
    @Mock
    lateinit var sharedPref: SharedPreferences
    @Mock
    lateinit var editor: SharedPreferences.Editor


    //@Mock
    lateinit var sharedPrefManager: SharedPreferencesManager

    //@InjectMocks
    lateinit var sessionManager: SessionManager

    @Before
    fun setUp() {
        Mockito.`when`(sharedPref.edit()).thenReturn(editor)

        sharedPrefManager = SharedPreferencesManager(sharedPref)
        sessionManager = SessionManager(sharedPrefManager)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }


    @Test
    fun setLogin() {

    }

    @Test
    fun isLoggedIn() {
        Mockito.`when`(editor.putString(Mockito.anyString(), anyString())).thenReturn(editor)
        Mockito.`when`(editor.putBoolean(Mockito.anyString(), anyBoolean())).thenReturn(editor)
        Mockito.`when`(editor.putLong(Mockito.anyString(), anyLong())).thenReturn(editor)
        sessionManager.setLogin(VALUE)

//        Assert.assertFalse(sessionManager.shouldLogout())
        //       Assert.assertTrue(sessionManager.isLoggedIn())
    }

    @Test
    fun getAuthToken() {
    }

    @Test
    fun logout() {
    }

    @Test
    fun shouldLogout() {
    }
}