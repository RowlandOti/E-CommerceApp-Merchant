package com.rowland.delivery.data.source.category

import com.rowland.delivery.data.contracts.category.ICategoryDataStore
import com.rowland.delivery.data.contracts.category.ICategoryRemoteSource
import com.rowland.delivery.data.model.category.CategoryPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class CategoryRemoteDataStore @Inject constructor(private val dataSource: ICategoryRemoteSource) : ICategoryDataStore {

    override fun saveToCache(categorys: List<CategoryPojo>): Completable {
        throw UnsupportedOperationException()
    }

    override fun clearFromCache(): Completable {
        throw UnsupportedOperationException()
    }

    override fun isCached(): Single<Boolean> {
        throw java.lang.UnsupportedOperationException()
    }

    override fun createCategory(category: CategoryPojo, userUid: String): Single<CategoryPojo> {
        return dataSource.createCategory(category, userUid)
    }

    override fun updateCategory(categoryUpdateFields: HashMap<String, Any>, categoryUid: String): Completable {
        return dataSource.updateCategory(categoryUpdateFields, categoryUid)
    }

    override fun deleteCategory(categoryUid: String, userUid: String): Completable {
        return dataSource.deleteCategory(categoryUid, userUid)
    }

    override fun loadCategories(userUid: String): Flowable<List<CategoryPojo>> {
        return dataSource.loadCategories(userUid)
    }
}
