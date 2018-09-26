package com.farmdrop.customer.managers

import com.farmdrop.customer.data.model.user.UserData
import uk.co.farmdrop.library.utils.helpers.SharedPreferencesHelper
import uk.co.farmdrop.library.utils.managers.UserManager

class UserManager(sharedPreferencesHelper: SharedPreferencesHelper) : UserManager(sharedPreferencesHelper) {

    companion object {
        internal const val KEY_USER_EMAIL = "KEY_USER_EMAIL"
        internal const val KEY_USER_SESSION_TOKEN = "KEY_USER_SESSION_TOKEN"
        internal const val KEY_USER_POSTCODE = "KEY_USER_POSTCODE"
    }

    fun createUserSession(userData: UserData) {
        super.startSessionForUser(userData.id)
        saveUserParam(KEY_USER_EMAIL, userData.email)
        saveUserParam(KEY_USER_POSTCODE, userData.zipCode)
        saveUserParam(KEY_USER_SESSION_TOKEN, userData.authenticationToken)
    }

    override fun isUserLoggedIn() = super.isUserLoggedIn() || getUserParam(KEY_USER_LOGGED_IN, USER_DEFAULT_ID) != USER_DEFAULT_ID

    override fun getCurrentUserId() = getUserParam(KEY_USER_LOGGED_IN, USER_DEFAULT_ID)

    /**
     * Removes the session data
     */
    override fun closeUserSession() {
        super.closeUserSession()
        clearUserParams(KEY_USER_EMAIL, KEY_USER_POSTCODE, KEY_USER_SESSION_TOKEN)
    }

    fun getUserSessionToken(): String? = getUserParam(KEY_USER_SESSION_TOKEN, null)

    fun getUserEmail(): String? = getUserParam(KEY_USER_EMAIL, null)

    fun getUserPostcode(): String? = getUserParam(KEY_USER_POSTCODE, null)

    fun setPostcode(postcode: String) = saveUserParam(KEY_USER_POSTCODE, postcode)

    fun isPostcodeSet() = containsUserParam(KEY_USER_POSTCODE)
}
