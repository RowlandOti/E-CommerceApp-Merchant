package com.rowland.delivery.data.repository.product

import com.rowland.delivery.data.mapper.product.ProductMapper
import com.rowland.delivery.data.model.product.ProductPojo
import com.rowland.delivery.data.source.product.ProductDataStoreFactory
import com.rowland.delivery.domain.contracts.IProductRepository
import com.rowland.delivery.domain.models.product.ProductEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject


/**
 * Created by Rowland on 5/13/2018.
 */
class ProductRepository @Inject constructor(private val dataStoreFactory: ProductDataStoreFactory,
                                            private val mapper: ProductMapper) : IProductRepository {
    override fun clearFromCache(): Completable {
        return dataStoreFactory.retrieveCacheDataStore().clearFromCache()
    }

    override fun saveToCache(products: List<ProductEntity>): Completable {
        val productPojos = mutableListOf<ProductPojo>()
        products.map { productPojos.add(mapper.mapToData(it)) }
        return dataStoreFactory.retrieveCacheDataStore().saveToCache(productPojos)
    }

    override fun loadProducts(userUid: String, productCategory: String): Flowable<List<ProductEntity>> {
        // Get all products published by given merchant
        return dataStoreFactory.retrieveCacheDataStore().isCached()
                .flatMapPublisher {
                    dataStoreFactory.retrieveDataStore(it).loadProducts(userUid, productCategory)
                }
                .flatMap {
                    Flowable.just(it.map { mapper.mapFromData(it) })
                }
                .flatMap {
                    saveToCache(it).toSingle { it }.toFlowable()
                }
    }

    override fun createProduct(productEntity: ProductEntity, productCategory: String, userUid: String): Single<ProductEntity> {
        return dataStoreFactory.retrieveDataStore(false).createProduct(mapper.mapToData(productEntity), productCategory, userUid)
                .map { mapper.mapFromData(it) }
    }

    override fun updateProduct(productUpdateFields: HashMap<String, Any>, productUid: String): Completable {
        return dataStoreFactory.retrieveDataStore(false).updateProduct(productUpdateFields, productUid)
    }

    override fun deleteProduct(productUid: String): Completable {
        return dataStoreFactory.retrieveDataStore(false).deleteProduct(productUid)
    }
}
