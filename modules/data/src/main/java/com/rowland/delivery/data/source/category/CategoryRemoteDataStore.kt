package com.rowland.delivery.data.source.category

import com.rowland.delivery.data.contracts.category.ICategoryDataStore
import com.rowland.delivery.data.contracts.category.ICategoryRemoteSource
import com.rowland.delivery.data.model.category.CategoryPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class CategoryRemoteDataStore @Inject constructor(private val cache: ICategoryRemoteSource) : ICategoryDataStore {

    override fun saveToCache(categorys: List<CategoryPojo>): Completable {
        throw UnsupportedOperationException()
    }

    override fun clearFromCache(): Completable {
        throw UnsupportedOperationException()
    }

    override fun isCached(): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createCategory(category: CategoryPojo): Single<CategoryPojo> {
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
}
