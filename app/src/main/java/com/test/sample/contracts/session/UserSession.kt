package com.farmdrop.customer.contracts.session

import com.farmdrop.customer.data.model.user.UserData

interface UserSession {
    interface Login {
        fun createUserSession(userData: UserData)
        fun isUserLoggedIn(): Boolean
    }

    interface Logout {
        fun finishUserSession()
    }

    interface Reinitialise {
        fun reinitialiseStripeUserSession()
    }
}
