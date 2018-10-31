package com.rowland.delivery.domain.usecases.product

import com.rowland.delivery.domain.contracts.IProductRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor

import com.rowland.delivery.domain.models.product.ProductEntity
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class LoadProductsUseCaseTest {

    companion object {
        private val FAKEUSERID = "fakeUserId"
        private val FAKECATEGORY = "fakeategory"
        private val FAKEPRODUCTFIRESTOREUID = "fakefirestoreuiid"
    }

    lateinit var mockProductEntityList: ArrayList<ProductEntity>

    @Mock
    private lateinit var productRepository: IProductRepository

    @Mock
    private lateinit var threadExecutor: IThreadExecutor

    @Mock
    private lateinit var postExecutionThread: IPostExecutionThread

    @InjectMocks
    private lateinit var loadProductsUseCase: LoadProductsUseCase

    @Before
    fun setUp() {
        mockProductEntityList = ArrayList<ProductEntity>()
        mockProductEntityList.add(ProductEntity())
        mockProductEntityList.add(ProductEntity())
        mockProductEntityList.add(ProductEntity())
    }

    @Test
    fun loadProducts() {
        Mockito.doReturn(Flowable.fromArray(mockProductEntityList)).`when`(productRepository).loadProducts(FAKEUSERID, FAKECATEGORY)


        val testSubscriber = loadProductsUseCase.buildUseCaseObservable(LoadProductsUseCase.Params.forProducts(FAKEUSERID, FAKECATEGORY)).test()
        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)

        Assert.assertTrue(testSubscriber.values().get(0).size.equals(mockProductEntityList.size))
        Assert.assertTrue(testSubscriber.values().get(0).containsAll(mockProductEntityList))

        val mockProductList2 = ArrayList<ProductEntity>()
        val prod1 = ProductEntity()
        prod1.firestoreUid = FAKEPRODUCTFIRESTOREUID
        mockProductList2.add(prod1)
        Assert.assertTrue(testSubscriber.values().get(0).containsAll(mockProductList2).equals(false))

        Mockito.verify(productRepository).loadProducts(FAKEUSERID, FAKECATEGORY)
    }
}