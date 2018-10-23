package com.rowland.delivery.remote.firebasesnapshots

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.rowland.delivery.remote.model.FirestoreModel
import io.reactivex.functions.Function
import java.util.*

/**
 * Created by Rowland on 5/12/2018.
 */

abstract class FirestoreDocumentWithIdSnapshotMapper<T, U> private constructor() : Function<T, U> {

    class TypedMapQuerySnapshotMapper<U : FirestoreModel> internal constructor(private val clazz: Class<U>) : FirestoreDocumentWithIdSnapshotMapper<QuerySnapshot, LinkedHashMap<String, U>>() {

        override fun apply(querySnapshot: QuerySnapshot): LinkedHashMap<String, U> {
            val items = LinkedHashMap<String, U>()
            val var3 = querySnapshot.iterator()

            while (var3.hasNext()) {
                val documentSnapshot = var3.next() as DocumentSnapshot
                items.put(documentSnapshot.id, documentSnapshot.toObject(this.clazz)!!.withFirestoreId<U>(documentSnapshot.id))
            }

            return items
        }
    }

    class TypedListQuerySnapshotMapper<U : FirestoreModel> @JvmOverloads internal constructor(private val clazz: Class<U>, private val mapper: Function<DocumentSnapshot, U>? = null) : FirestoreDocumentWithIdSnapshotMapper<QuerySnapshot, List<U>>() {

        @Throws(Exception::class)
        override fun apply(querySnapshot: QuerySnapshot): List<U> {
            val items = ArrayList<U>()
            val var3 = querySnapshot.iterator()

            while (var3.hasNext()) {
                val documentSnapshot = var3.next() as DocumentSnapshot
                items.add(if (this.mapper != null) this.mapper.apply(documentSnapshot) else documentSnapshot.toObject(this.clazz)!!.withFirestoreId(documentSnapshot.id))
            }
            return items
        }
    }

    class TypedDocumentSnapshotMapper<U : FirestoreModel> internal constructor(private val clazz: Class<U>) : FirestoreDocumentWithIdSnapshotMapper<DocumentSnapshot, U>() {

        override fun apply(documentSnapshot: DocumentSnapshot): U {
            return documentSnapshot.toObject(this.clazz)!!.withFirestoreId(documentSnapshot.id)
        }
    }

    companion object {

        fun <U : FirestoreModel> of(clazz: Class<U>): FirestoreDocumentWithIdSnapshotMapper<DocumentSnapshot, U> {
            return FirestoreDocumentWithIdSnapshotMapper.TypedDocumentSnapshotMapper(clazz)
        }

        fun <U : FirestoreModel> listOf(clazz: Class<U>): FirestoreDocumentWithIdSnapshotMapper<QuerySnapshot, List<U>> {
            return FirestoreDocumentWithIdSnapshotMapper.TypedListQuerySnapshotMapper(clazz)
        }

        fun <U : FirestoreModel> listOf(clazz: Class<U>, mapper: Function<DocumentSnapshot, U>): FirestoreDocumentWithIdSnapshotMapper<QuerySnapshot, List<U>> {
            return FirestoreDocumentWithIdSnapshotMapper.TypedListQuerySnapshotMapper(clazz, mapper)
        }

        fun <U : FirestoreModel> mapOf(clazz: Class<U>): FirestoreDocumentWithIdSnapshotMapper.TypedMapQuerySnapshotMapper<U> {
            return FirestoreDocumentWithIdSnapshotMapper.TypedMapQuerySnapshotMapper(clazz)
        }

        private fun <U : FirestoreModel> getDataSnapshotTypedValue(documentSnapshot: DocumentSnapshot, clazz: Class<U>): U {
            return documentSnapshot.toObject(clazz)!!.withFirestoreId(documentSnapshot.id)
        }
    }
}


