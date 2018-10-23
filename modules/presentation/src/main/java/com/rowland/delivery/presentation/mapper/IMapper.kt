package com.rowland.delivery.presentation.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer layers
 *
 * @param <V> the view model input type
 * @param <D> the domain model output type
 */
interface IMapper<out V, in D> {

    fun mapToView(type: D): V

}