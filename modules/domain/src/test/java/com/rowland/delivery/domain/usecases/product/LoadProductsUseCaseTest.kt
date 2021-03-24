/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rowland.delivery.domain.usecases.product

import com.rowland.delivery.domain.contracts.IProductRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor
import com.rowland.delivery.domain.models.product.ProductEntity
import io.reactivex.Flowable
import org.junit.*
import org.junit.runner.*
import org.mockito.*
import org.mockito.junit.*
import java.util.ArrayList

@RunWith(MockitoJUnitRunner::class)
class LoadProductsUseCaseTest {

    companion object {

        private const val FAKEUSERID = "fakeUserId"
        private val FAKECATEGORY = "fakeategory"
        private const val FAKEPRODUCTFIRESTOREUID = "fakefirestoreuiid"
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
        Mockito.doReturn(Flowable.fromArray(mockProductEntityList)).`when`(productRepository)
            .loadProducts(FAKEUSERID, FAKECATEGORY)

        val testSubscriber = loadProductsUseCase.buildUseCaseObservable(
            LoadProductsUseCase.Params.forProducts(
                FAKEUSERID,
                FAKECATEGORY
            )
        ).test()
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
