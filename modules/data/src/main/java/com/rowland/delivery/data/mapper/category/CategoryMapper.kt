package com.rowland.delivery.data.mapper.category

import com.rowland.delivery.data.mapper.Mapper
import com.rowland.delivery.data.model.category.CategoryPojo
import com.rowland.delivery.domain.models.category.CategoryEntity
import javax.inject.Inject

open class CategoryMapper @Inject constructor() : Mapper<CategoryPojo, CategoryEntity> {

    override fun mapFromData(type: CategoryPojo): CategoryEntity {
        val category = CategoryEntity()
        category.name = type.name
        return category
    }

    override fun mapToData(type: CategoryEntity): CategoryPojo {
        val category = CategoryPojo()
        category.name = type.name
        return  category
    }
}

