package com.rowland.delivery.features.dash.domain.usecases.product

import com.rowland.delivery.features.dash.domain.contracts.IProductRepository
import com.rowland.delivery.features.dash.domain.executor.IPostExecutionThread
import com.rowland.delivery.features.dash.domain.executor.IThreadExecutor
import com.rowland.delivery.features.dash.domain.models.product.ProductEntity
import io.reactivex.Flowable
import org.buffer.android.boilerplate.domain.interactor.FlowableUseCase
import javax.inject.Inject

/**
 * Created by Rowland on 5/13/2018.
 */

class LoadProductsUseCase @Inject
constructor(private val productRepository: IProductRepository, threadExecutor: IThreadExecutor, postExecutionThread: IPostExecutionThread) : FlowableUseCase<List<ProductEntity>, LoadProductsUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Flowable<List<ProductEntity>> {
        return this.productRepository.loadProducts(params!!.userUID, params!!.productCategory)
    }

    /* fun loadProducts(userUID: String, productCategory: String): Flowable<List<ProductEntity>> {
         return this.productRepository.loadProducts(userUID, productCategory)
     }*/

    data class Params constructor(val userUID: String, val productCategory: String) {
        companion object {
            fun forProducts(userUID: String, productCategory: String): Params {
                return Params(userUID, productCategory)
            }
        }
    }
}
