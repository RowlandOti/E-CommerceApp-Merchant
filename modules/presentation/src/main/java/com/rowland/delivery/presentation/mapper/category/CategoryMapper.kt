package com.rowland.delivery.presentation.mapper.category


import com.rowland.delivery.domain.models.category.CategoryEntity
import com.rowland.delivery.presentation.mapper.IMapper
import com.rowland.delivery.presentation.model.category.CategoryModel
import javax.inject.Inject

open class CategoryMapper @Inject constructor() : IMapper<CategoryModel, CategoryEntity> {


    override fun mapToView(type: CategoryEntity): CategoryModel {
        val category = CategoryModel()
        category.name = type.name
        return  category
    }
}

