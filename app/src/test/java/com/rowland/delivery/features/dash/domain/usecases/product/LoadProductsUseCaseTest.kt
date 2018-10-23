package com.rowland.delivery.features.dash.domain.usecases.product

import com.rowland.delivery.features.dash.domain.contracts.IProductRepository
import com.rowland.delivery.features.dash.domain.models.product.Product
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

    lateinit var mockProductList: ArrayList<Product>

    @Mock
    private lateinit var productRepository: IProductRepository

    @InjectMocks
    private lateinit var loadProductsUseCase: LoadProductsUseCase

    @Before
    fun setUp() {
        mockProductList = ArrayList<Product>()
        mockProductList.add(Product())
        mockProductList.add(Product())
        mockProductList.add(Product())

        Mockito.doReturn(Flowable.fromArray(mockProductList)).`when`(productRepository).loadProducts(FAKEUSERID, FAKECATEGORY)
    }

    @Test
    fun loadProducts() {

        val testSubscriber = loadProductsUseCase.loadProducts(FAKEUSERID, FAKECATEGORY).test()
        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)

        Assert.assertTrue(testSubscriber.values().get(0).size.equals(mockProductList.size))
        Assert.assertTrue(testSubscriber.values().get(0).containsAll(mockProductList))

        val mockProductList2 = ArrayList<Product>()
        val prod1 = Product()
        prod1.firestoreUid = FAKEPRODUCTFIRESTOREUID
        mockProductList2.add(prod1)
        Assert.assertTrue(testSubscriber.values().get(0).containsAll(mockProductList2).equals(false))
    }
}