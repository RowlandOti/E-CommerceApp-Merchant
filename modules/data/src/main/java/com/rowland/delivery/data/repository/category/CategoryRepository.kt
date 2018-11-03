package com.rowland.delivery.data.repository.category

import com.rowland.delivery.data.mapper.category.CategoryMapper
import com.rowland.delivery.data.model.category.CategoryPojo
import com.rowland.delivery.data.source.category.CategoryDataStoreFactory
import com.rowland.delivery.domain.contracts.ICategoryRepository
import com.rowland.delivery.domain.models.category.CategoryEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject


/**
 * Created by Rowland on 5/5/2018.
 */

class CategoryRepository @Inject constructor(private val dataStoreFactory: CategoryDataStoreFactory,
                                             private val mapper: CategoryMapper) : ICategoryRepository {
    override fun clearFromCache(): Completable {
        return dataStoreFactory.retrieveCacheDataStore().clearFromCache()
    }

    override fun saveToCache(categories: List<CategoryEntity>): Completable {
        val categoriesPojos = mutableListOf<CategoryPojo>()
        categories.map { categoriesPojos.add(mapper.mapToData(it)) }
        return dataStoreFactory.retrieveCacheDataStore().saveToCache(categoriesPojos)
    }

    override fun loadCategories(userUid: String): Flowable<List<CategoryEntity>> {
        // Get all products published by given merchant
        return dataStoreFactory.retrieveCacheDataStore().isCached()
                .flatMapPublisher {
                    dataStoreFactory.retrieveDataStore(it).loadCategories(userUid)
                }
                .flatMap {
                    Flowable.just(it.map { mapper.mapFromData(it) })
                }
                .flatMap {
                    saveToCache(it).toSingle { it }.toFlowable()
                }
    }

    override fun createCategory(categoryEntity: CategoryEntity, userUid: String): Single<CategoryEntity> {
        return dataStoreFactory.retrieveDataStore(false).createCategory(mapper.mapToData(categoryEntity), userUid)
                .map { mapper.mapFromData(it) }
    }

    override fun updateCategory(categoryUpdateFields: HashMap<String, Any>, categoryUid: String): Completable {
        return dataStoreFactory.retrieveDataStore(false).updateCategory(categoryUpdateFields, categoryUid)
    }

    override fun deleteCategory(categoryUid: String, userUid: String): Completable {
        return dataStoreFactory.retrieveDataStore(false).deleteCategory(categoryUid, userUid)
    }


}
