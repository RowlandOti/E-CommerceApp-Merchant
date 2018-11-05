package com.rowland.delivery.cache.source.category

import com.rowland.delivery.data.contracts.category.ICategoryCacheSource
import com.rowland.delivery.data.model.category.CategoryPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableEmpty
import java.util.*
import javax.inject.Inject

class CategoryCacheSource @Inject constructor() : ICategoryCacheSource {

    override fun createCategory(Categories: CategoryPojo): Single<CategoryPojo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateCategory(categoryUpdateFields: HashMap<String, Any>, categoryUid: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCategory(categoryUid: String, userUid: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadCategories(userUid: String): Flowable<List<CategoryPojo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveToCache(categories: List<CategoryPojo>): Completable {
        return CompletableEmpty.complete()
    }

    override fun clearFromCache(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCached(): Single<Boolean> {
        return Single.just(false)
    }

    override fun setLastCacheTime(lastCache: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}