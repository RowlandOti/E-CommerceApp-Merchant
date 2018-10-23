package com.rowland.delivery.data.source.product

import com.rowland.delivery.data.contracts.product.IProductCacheSource
import com.rowland.delivery.data.contracts.product.IProductDataStore
import com.rowland.delivery.data.model.product.ProductPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class ProductCacheDataStore @Inject constructor(private val dataSource: IProductCacheSource) : IProductDataStore {

    override fun clearFromCache(): Completable {
        return dataSource.clearFromCache()
    }

    override fun saveToCache(products: List<ProductPojo>): Completable {
        return dataSource.saveToCache(products)
    }

    override fun createProduct(productPojo: ProductPojo, productCategory: String, userUid: String): Single<ProductPojo> {
       return dataSource.createProduct(productPojo, productCategory, userUid)
    }

    override fun updateProduct(productUpdateFields: HashMap<String, Any>, productUid: String): Completable {
        return dataSource.updateProduct(productUpdateFields, productUid)
    }

    override fun deleteProduct(productUid: String): Completable {
        return dataSource.deleteProduct(productUid)
    }

    override fun loadProducts(userUid: String, productCategory: String): Flowable<List<ProductPojo>> {
        return dataSource.loadProducts(userUid, productCategory)
    }

    override fun isCached(): Single<Boolean> {
        return dataSource.isCached()
    }
}
