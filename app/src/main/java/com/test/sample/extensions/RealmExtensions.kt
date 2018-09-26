package com.farmdrop.customer.extensions

import io.realm.Realm
import io.realm.RealmObject
import timber.log.Timber

fun <T : RealmObject> Realm.deleteAllCascade(classInst: Class<T>): Boolean {
    return try {
        executeTransaction { transactionRealmInst ->
            val results = transactionRealmInst.where(classInst).findAll()
            results.forEach {
                if (it is RealmObjectDeleteCascade) {
                    it.deleteCascadeFromRealm()
                } else {
                    it.deleteFromRealm()
                }
            }
        }
        true
    } catch (e: Exception) {
        Timber.e(e)
        false
    }
}
