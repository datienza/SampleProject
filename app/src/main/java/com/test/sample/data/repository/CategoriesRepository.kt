package com.farmdrop.customer.data.repository

import com.farmdrop.customer.data.model.CategoryData
import com.farmdrop.customer.data.remote.CategoriesRemoteDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults
import uk.co.farmdrop.library.data.common.connection.ConnectionChecker

class CategoriesRepository(
    private val localDataSource: CategoriesLocalDataSource,
    private val remoteDataSource: CategoriesRemoteDataSource,
    private val realm: Realm,
    private val connectionChecker: ConnectionChecker,
    private val scheduler: Scheduler
) {

    fun getCategories(forceReload: Boolean): Single<RealmResults<CategoryData>>? {
        return if ((forceReload || shouldFetch()) && connectionChecker.isOnline) {
            // TODO check when to clear DB.
            // val success = mLocalDataSource.clearAllData(mRealm)
            // Timber.d("categories data cleared - %s ", success)
            remoteGetAndSaveCategories()
        } else {
            Single.just(localDataSource.getAllEntriesPlain(realm))
        }
    }

    private fun remoteGetAndSaveCategories() =
        remoteDataSource.get()
            .doOnSuccess { TaxonomyListResponseWrapper ->
                localDataSource.saveData(TaxonomyListResponseWrapper.response)
            }
            .observeOn(scheduler)
            .map { localDataSource.getAllEntriesPlain(realm) }

    private fun shouldFetch() = true // TODO define how we fetch data / how data expires.

    fun getCategory(categoryId: Int): Flowable<CategoryData>? =
        localDataSource.getSingleEntry(realm, categoryId)
}
