package com.rowland.delivery.features.dash.data.model.category

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.rowland.delivery.features.dash.domain.models.category.Category
import com.rowland.delivery.features.dash.presentation.tools.snapshots.DocumentWithIdSnapshotMapper
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Created by Rowland on 5/5/2018.
 */

class CategoryRepository @Inject
constructor(private val mFirebaseFirestone: FirebaseFirestore) {

    fun getCategories(userUid: String): Flowable<List<Category>> {
        val categoryCollectionRef = mFirebaseFirestone.collection("categories")
        val query = categoryCollectionRef.whereEqualTo(String.format("merchants.%s", userUid), true)
        return RxFirestore.observeQueryRef(query, DocumentWithIdSnapshotMapper.listOf(Category::class.java) as io.reactivex.functions.Function<QuerySnapshot, List<Category>>)
    }

    fun createCategory(category: Category, userUid: String): Single<Category> {
        val documentReference = mFirebaseFirestone.collection("categories").document(category.name)
        documentReference.set(category, SetOptions.merge()).addOnSuccessListener { aVoid ->
            val members = HashMap<String, Any>()
            members[String.format("merchants.%s", userUid)] = true
            documentReference.update(members)
        }
        return RxFirestore.getDocument(documentReference, Category::class.java).toSingle()
    }
}
