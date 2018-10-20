package com.rowland.delivery.features.dash.data.model.product

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.rowland.delivery.features.dash.domain.models.product.Product
import com.rowland.delivery.features.dash.presentation.tools.snapshots.DocumentWithIdSnapshotMapper
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject


/**
 * Created by Rowland on 5/13/2018.
 */

class ProductRepository @Inject
constructor(private val mFirebaseFirestone: FirebaseFirestore) {

    fun getProducts(userUid: String, productCategory: String): Flowable<List<Product>> {
        // Get all products published by given merchant
        val categoryCollectionRef = mFirebaseFirestone.collection("products")
        val query = categoryCollectionRef
                .whereEqualTo(String.format("merchants.%s", userUid), true)
                .whereEqualTo(String.format("categories.%s", productCategory), true)
        return RxFirestore.observeQueryRef(query, DocumentWithIdSnapshotMapper.listOf(Product::class.java) as io.reactivex.functions.Function<QuerySnapshot, List<Product>>)

    }

    fun createProduct(product: Product, productCategory: String, userUid: String): Single<Product> {
        val documentReference = mFirebaseFirestone.collection("products").document()
        documentReference.set(product, SetOptions.merge()).addOnSuccessListener { aVoid ->
            val members = HashMap<String, Any>()
            members[String.format("merchants.%s", userUid)] = true
            members[String.format("categories.%s", productCategory)] = true
            documentReference.update(members)
        }
        return RxFirestore.getDocument(documentReference, Product::class.java).toSingle()
    }

    fun updateProduct(productUpdateFields: HashMap<String, Any>, firestoreUid: String): Single<Product> {
        val documentReference = mFirebaseFirestone.collection("products").document(firestoreUid!!)
        val imageUrls = productUpdateFields.remove("imageUrls")
        if (imageUrls != null) {
            return RxFirestore.updateDocument(documentReference, productUpdateFields).doOnComplete {
                for(i in imageUrls as ArrayList<*>) {
                    documentReference.update("imageUrls", FieldValue.arrayUnion(i))
                }
            }.toSingle { Product() }
        }
        return RxFirestore.updateDocument(documentReference, productUpdateFields).toSingle { Product() }
    }

    fun deleteProduct(productUid: String): Completable {
        val documentReference = mFirebaseFirestone.collection("products").document(productUid)
        return RxFirestore.deleteDocument(documentReference)
    }
}
