package com.rowland.delivery.domain.usecases.product

import com.rowland.delivery.domain.contracts.IProductRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor
import com.rowland.delivery.domain.usecases.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class DeleteProductUseCase @Inject
constructor(
    private val productRepository: IProductRepository,
    threadExecutor: IThreadExecutor,
    postExecutionThread: IPostExecutionThread
) : CompletableUseCase<DeleteProductUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params): Completable {
        return this.productRepository.deleteProduct(params.productUID)
    }

    fun deleteProduct(params: Params): Completable {
        return this.productRepository.deleteProduct(params.productUID)
    }

    data class Params constructor(val productUID: String) {
        companion object {
            fun forProduct(productUID: String): Params {
                return Params(productUID)
            }
        }
    }
}