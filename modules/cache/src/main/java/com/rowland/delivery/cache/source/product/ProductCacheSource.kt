package com.rowland.delivery.cache.source.product

import com.rowland.delivery.data.contracts.product.IProductCacheSource
import com.rowland.delivery.data.model.product.ProductPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class ProductCacheSource @Inject constructor() : IProductCacheSource {
    override fun createProduct(productPojo: ProductPojo, productCategory: String, userUid: String): Single<ProductPojo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateProduct(productUpdateFields: HashMap<String, Any>, productUid: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteProduct(productUid: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadProducts(userUid: String, productCategory: String): Flowable<List<ProductPojo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveToCache(products: List<ProductPojo>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearFromCache(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCached(): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLastCacheTime(lastCache: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}