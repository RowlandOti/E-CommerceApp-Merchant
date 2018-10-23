package com.rowland.delivery.features.dash.presentation.tools.snapshots

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.rowland.delivery.features.dash.domain.models.FirestoreModel

import java.util.ArrayList
import java.util.LinkedHashMap

import io.reactivex.functions.Function

/**
 * Created by Rowland on 5/12/2018.
 */

abstract class DocumentWithIdSnapshotMapper<T, U> private constructor() : Function<T, U> {

    class TypedMapQuerySnapshotMapper<U : FirestoreModel> internal constructor(private val clazz: Class<U>) : DocumentWithIdSnapshotMapper<QuerySnapshot, LinkedHashMap<String, U>>() {

        override fun apply(querySnapshot: QuerySnapshot): LinkedHashMap<String, U> {
            val items = LinkedHashMap()
            val var3 = querySnapshot.iterator()

            while (var3.hasNext()) {
                val documentSnapshot = var3.next() as DocumentSnapshot
                items.put(documentSnapshot.id, documentSnapshot.toObject(this.clazz)!!.withFirestoreId<U>(documentSnapshot.id))
            }

            return items
        }
    }

    class TypedListQuerySnapshotMapper<U : FirestoreModel> @JvmOverloads internal constructor(private val clazz: Class<U>, private val mapper: Function<DocumentSnapshot, U>? = null) : DocumentWithIdSnapshotMapper<QuerySnapshot, List<U>>() {

        @Throws(Exception::class)
        override fun apply(querySnapshot: QuerySnapshot): List<U> {
            val items = ArrayList()
            val var3 = querySnapshot.iterator()

            while (var3.hasNext()) {
                val documentSnapshot = var3.next() as DocumentSnapshot
                items.add(if (this.mapper != null) this.mapper.apply(documentSnapshot) else documentSnapshot.toObject(this.clazz)!!.withFirestoreId(documentSnapshot.id))
            }
            return items
        }
    }

    class TypedDocumentSnapshotMapper<U : FirestoreModel> internal constructor(private val clazz: Class<U>) : DocumentWithIdSnapshotMapper<DocumentSnapshot, U>() {

        override fun apply(documentSnapshot: DocumentSnapshot): U {
            return documentSnapshot.toObject(this.clazz)!!.withFirestoreId(documentSnapshot.id)
        }
    }

    companion object {

        fun <U : FirestoreModel> of(clazz: Class<U>): DocumentWithIdSnapshotMapper<DocumentSnapshot, U> {
            return DocumentWithIdSnapshotMapper.TypedDocumentSnapshotMapper(clazz)
        }

        fun <U : FirestoreModel> listOf(clazz: Class<U>): DocumentWithIdSnapshotMapper<QuerySnapshot, List<U>> {
            return DocumentWithIdSnapshotMapper.TypedListQuerySnapshotMapper(clazz)
        }

        fun <U : FirestoreModel> listOf(clazz: Class<U>, mapper: Function<DocumentSnapshot, U>): DocumentWithIdSnapshotMapper<QuerySnapshot, List<U>> {
            return DocumentWithIdSnapshotMapper.TypedListQuerySnapshotMapper(clazz, mapper)
        }

        fun <U : FirestoreModel> mapOf(clazz: Class<U>): DocumentWithIdSnapshotMapper.TypedMapQuerySnapshotMapper<U> {
            return DocumentWithIdSnapshotMapper.TypedMapQuerySnapshotMapper(clazz)
        }

        private fun <U : FirestoreModel> getDataSnapshotTypedValue(documentSnapshot: DocumentSnapshot, clazz: Class<U>): U {
            return documentSnapshot.toObject(clazz)!!.withFirestoreId(documentSnapshot.id)
        }
    }
}


