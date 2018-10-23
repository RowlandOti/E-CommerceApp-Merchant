package com.rowland.delivery.features.dash.data.model.category

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.rowland.delivery.features.dash.domain.contracts.ICategoryRepository
import com.rowland.delivery.features.dash.domain.models.category.CategoryEntity
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
constructor(private val mFirebaseFirestone: FirebaseFirestore) : ICategoryRepository {

    override fun getCategories(userUid: String): Flowable<List<CategoryEntity>> {
        val categoryCollectionRef = mFirebaseFirestone.collection("categories")
        val query = categoryCollectionRef.whereEqualTo(String.format("merchants.%s", userUid), true)
        return RxFirestore.observeQueryRef(query, DocumentWithIdSnapshotMapper.listOf(CategoryEntity::class.java) as io.reactivex.functions.Function<QuerySnapshot, List<CategoryEntity>>)
    }

    override fun createCategory(categoryEntity: CategoryEntity, userUid: String): Single<CategoryEntity> {
        val documentReference = mFirebaseFirestone.collection("categories").document(categoryEntity.name!!)
        documentReference.set(categoryEntity, SetOptions.merge()).addOnSuccessListener { _ ->
            val members = HashMap<String, Any>()
            members[String.format("merchants.%s", userUid)] = true
            documentReference.update(members)
        }
        return RxFirestore.getDocument(documentReference, CategoryEntity::class.java).toSingle()
    }
}
