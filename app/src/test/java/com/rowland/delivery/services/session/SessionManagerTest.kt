package com.rowland.delivery.services.session

import com.rowland.delivery.services.sharedpreferences.SharedPreferencesManager
import org.junit.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit


class SessionManagerTest {

    @Rule
    @JvmField
    var mockitoRule = MockitoJUnit.rule()

    /*@Mock
    lateinit var sharedPref: SharedPreferences*/

    @Mock
    lateinit var sharedPrefManager: SharedPreferencesManager

    @InjectMocks
    lateinit var sessionManager: SessionManager

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
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
        sessionManager.setLogin("ffhfgGFTftydtydtyTGFYUFT")
        sessionManager.isLoggedIn

        Assert.assertTrue(sessionManager.isLoggedIn)
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