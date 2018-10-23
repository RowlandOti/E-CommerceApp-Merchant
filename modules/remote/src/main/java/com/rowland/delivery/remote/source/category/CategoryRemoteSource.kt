package com.rowland.delivery.remote.source.category

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.rowland.delivery.data.contracts.category.ICategoryRemoteSource
import com.rowland.delivery.data.model.category.CategoryPojo
import com.rowland.delivery.remote.firebasesnapshots.FirestoreDocumentWithIdSnapshotMapper
import com.rowland.delivery.remote.mapper.category.CategoryMapper
import com.rowland.delivery.remote.model.category.CategoryPayload
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class CategoryRemoteSource @Inject constructor(private val mFirebaseFirestone: FirebaseFirestore, private val mapper: CategoryMapper) : ICategoryRemoteSource {
    override fun createCategory(categoryPojo: CategoryPojo, userUid: String): Single<CategoryPojo> {
        val documentReference = mFirebaseFirestone.collection("categories").document(categoryPojo.name!!)
        documentReference.set(mapper.mapToRemote(categoryPojo), SetOptions.merge()).addOnSuccessListener { _ ->
            val members = HashMap<String, Any>()
            members[String.format("merchants.%s", userUid)] = true
            documentReference.update(members)
        }
        return RxFirestore.getDocument(documentReference, CategoryPayload::class.java).toSingle()
                .map { mapper.mapFromRemote(it) }
    }

    override fun updateCategory(categoryUpdateFields: HashMap<String, Any>, categoryUid: String): Completable {
        val orderDocumentRef = mFirebaseFirestone.collection("categories").document(categoryUid)
        return RxFirestore.updateDocument(orderDocumentRef, categoryUpdateFields)
    }

    override fun deleteCategory(categoryUid: String, userUid: String): Completable {
        throw UnsupportedOperationException()
    }

    override fun loadCategories(userUid: String): Flowable<List<CategoryPojo>> {
        val categoryCollectionRef = mFirebaseFirestone.collection("categories")
        val query = categoryCollectionRef.whereEqualTo(String.format("merchants.%s", userUid), true)
        return RxFirestore.observeQueryRef(query, FirestoreDocumentWithIdSnapshotMapper.listOf(CategoryPayload::class.java) as io.reactivex.functions.Function<QuerySnapshot, List<CategoryPayload>>)
                .map { it }
                .map {
                    val categoryPojos = mutableListOf<CategoryPojo>()
                    it.map { categoryPojos.add(mapper.mapFromRemote(it)) }
                    categoryPojos
                }

    }
}