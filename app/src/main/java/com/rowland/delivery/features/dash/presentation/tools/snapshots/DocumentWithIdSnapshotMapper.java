package com.rowland.delivery.features.dash.presentation.tools.snapshots;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rowland.delivery.features.dash.domain.models.FirestoreModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * Created by Rowland on 5/12/2018.
 */

public abstract class DocumentWithIdSnapshotMapper<T, U> implements Function<T, U> {

    private DocumentWithIdSnapshotMapper() {
    }

    public static <U extends FirestoreModel> DocumentWithIdSnapshotMapper<DocumentSnapshot, U> of(Class<U> clazz) {
        return new DocumentWithIdSnapshotMapper.TypedDocumentSnapshotMapper(clazz);
    }

    public static <U extends FirestoreModel> DocumentWithIdSnapshotMapper<QuerySnapshot, List<U>> listOf(Class<U> clazz) {
        return new DocumentWithIdSnapshotMapper.TypedListQuerySnapshotMapper(clazz);
    }

    public static <U extends FirestoreModel> DocumentWithIdSnapshotMapper<QuerySnapshot, List<U>> listOf(Class<U> clazz, Function<DocumentSnapshot, U> mapper) {
        return new DocumentWithIdSnapshotMapper.TypedListQuerySnapshotMapper(clazz, mapper);
    }

    public static <U extends FirestoreModel> DocumentWithIdSnapshotMapper.TypedMapQuerySnapshotMapper<U> mapOf(Class<U> clazz) {
        return new DocumentWithIdSnapshotMapper.TypedMapQuerySnapshotMapper(clazz);
    }

    private static <U extends FirestoreModel> U getDataSnapshotTypedValue(DocumentSnapshot documentSnapshot, Class<U> clazz) {
        return documentSnapshot.toObject(clazz).withFirestoreId(documentSnapshot.getId());
    }

    private static class TypedMapQuerySnapshotMapper<U extends FirestoreModel> extends DocumentWithIdSnapshotMapper<QuerySnapshot, LinkedHashMap<String, U>> {

        private final Class<U> clazz;

        TypedMapQuerySnapshotMapper(Class<U> clazz) {
            this.clazz = clazz;
        }

        public LinkedHashMap<String, U> apply(QuerySnapshot querySnapshot) {
            LinkedHashMap<String, U> items = new LinkedHashMap();
            Iterator var3 = querySnapshot.iterator();

            while (var3.hasNext()) {
                DocumentSnapshot documentSnapshot = (DocumentSnapshot) var3.next();
                items.put(documentSnapshot.getId(), documentSnapshot.toObject(this.clazz).withFirestoreId(documentSnapshot.getId()));
            }

            return items;
        }
    }

    private static class TypedListQuerySnapshotMapper<U extends FirestoreModel> extends DocumentWithIdSnapshotMapper<QuerySnapshot, List<U>> {

        private final Class<U> clazz;
        private final Function<DocumentSnapshot, U> mapper;

        TypedListQuerySnapshotMapper(Class<U> clazz) {
            this(clazz, (Function) null);
        }

        TypedListQuerySnapshotMapper(Class<U> clazz, Function<DocumentSnapshot, U> mapper) {
            this.clazz = clazz;
            this.mapper = mapper;
        }

        public List<U> apply(QuerySnapshot querySnapshot) throws Exception {
            List<U> items = new ArrayList();
            Iterator var3 = querySnapshot.iterator();

            while (var3.hasNext()) {
                DocumentSnapshot documentSnapshot = (DocumentSnapshot) var3.next();
                items.add(this.mapper != null ? this.mapper.apply(documentSnapshot) : documentSnapshot.toObject(this.clazz).withFirestoreId(documentSnapshot.getId()));
            }
            return items;
        }
    }

    private static class TypedDocumentSnapshotMapper<U extends FirestoreModel> extends DocumentWithIdSnapshotMapper<DocumentSnapshot, U> {

        private final Class<U> clazz;

        private TypedDocumentSnapshotMapper(Class<U> clazz) {
            this.clazz = clazz;
        }

        @Override
        public U apply(DocumentSnapshot documentSnapshot) throws Exception {
            return documentSnapshot.toObject(this.clazz).withFirestoreId(documentSnapshot.getId());
        }
    }
}


