package com.rowland.delivery.remote.mapper.category

import com.rowland.delivery.data.model.category.CategoryPojo
import com.rowland.delivery.remote.mapper.IMapper
import com.rowland.delivery.remote.model.category.CategoryPayload
import javax.inject.Inject

/**
 * Map a [CategoryPayload] to and from a [CategoryPojo] instance when data is moving between
 * this later and the Data layer
 */
class CategoryMapper @Inject constructor() : IMapper<CategoryPayload, CategoryPojo> {
    override fun mapToRemote(type: CategoryPojo): CategoryPayload {
        val category = CategoryPayload()
        category.name = type.name
        category.merchants = type.merchants
        return category
    }

    /**
     * Map an instance of a [CategoryPayload] to a [CategoryPojo] model
     */
    override fun mapFromRemote(type: CategoryPayload): CategoryPojo {
        val category = CategoryPojo()
        category.name = type.name
        category.merchants = type.merchants
        return category
    }
}