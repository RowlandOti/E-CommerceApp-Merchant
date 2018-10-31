package com.rowland.delivery.remote.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 *
 * @param <M> the remote model input type
 * @param <E> the entity model output type
 */
interface IMapper<M, E> {
    fun mapFromRemote(type: M): E
    fun mapToRemote(type: E): M
}
