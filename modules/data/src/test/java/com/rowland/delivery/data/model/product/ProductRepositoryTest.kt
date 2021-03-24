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

package com.rowland.delivery.data.model.product

import com.rowland.delivery.data.repository.product.ProductRepository
import com.rowland.delivery.domain.models.product.ProductEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.*
import org.junit.runner.*
import org.mockito.*
import org.mockito.junit.*
import java.util.ArrayList
import java.util.HashMap

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryTest {

    companion object {

        private const val FAKEUSERID = "fakeUserId"
        private val FAKECATEGORY = "fakeategory"
        private const val FAKEPRODUCTNAME = "fakeproductname"
        private const val FAKEPRODUCTFIRESTOREUID = "fakefirestoreuiid"
    }

    @Mock
    private lateinit var productRepository: ProductRepository

    lateinit var mockProductEntityList: ArrayList<ProductEntity>

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

        val testSubscriber = productRepository.loadProducts(FAKEUSERID, FAKECATEGORY).test()
        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)

        Assert.assertTrue(testSubscriber.values().get(0).size.equals(mockProductEntityList.size))
        Assert.assertTrue(testSubscriber.values().get(0).containsAll(mockProductEntityList))

        Mockito.verify(productRepository).loadProducts(FAKEUSERID, FAKECATEGORY)
    }

    @Test
    fun createProduct() {
        val mockProduct = ProductEntity()
        mockProduct.firestoreUid = FAKEPRODUCTFIRESTOREUID
        Mockito.doReturn(Single.just(mockProduct)).`when`(productRepository)
            .createProduct(mockProduct, FAKEUSERID, FAKECATEGORY)

        val testSubscriber = productRepository.createProduct(mockProduct, FAKEUSERID, FAKECATEGORY).test()
        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()

        Assert.assertTrue(testSubscriber.values().get(0).firestoreUid.equals(FAKEPRODUCTFIRESTOREUID))

        Mockito.verify(productRepository).createProduct(mockProduct, FAKEUSERID, FAKECATEGORY)
    }

    @Test
    fun updateProduct() {
        val mockProduct = ProductEntity()
        mockProduct.firestoreUid = FAKEPRODUCTFIRESTOREUID
        mockProduct.name = FAKEPRODUCTNAME

        val mockProductUpdateFields = HashMap<String, Any>()
        mockProductUpdateFields.put("name", FAKEPRODUCTNAME)

        Mockito.doReturn(Completable.complete()).`when`(productRepository)
            .updateProduct(mockProductUpdateFields, FAKEPRODUCTFIRESTOREUID)

        val testSubscriber = productRepository.updateProduct(mockProductUpdateFields, FAKEPRODUCTFIRESTOREUID).test()
        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()

        Mockito.verify(productRepository).updateProduct(mockProductUpdateFields, FAKEPRODUCTFIRESTOREUID)
    }

    @Test
    fun deleteProduct() {
        val mockProduct = ProductEntity()
        mockProduct.firestoreUid = FAKEPRODUCTFIRESTOREUID

        Mockito.doReturn(Completable.complete()).`when`(productRepository).deleteProduct(FAKEPRODUCTFIRESTOREUID)

        val testSubscriber = productRepository.deleteProduct(FAKEPRODUCTFIRESTOREUID).test()
        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()

        Mockito.verify(productRepository).deleteProduct(FAKEPRODUCTFIRESTOREUID)
    }
}
