/*
 * Copyright 2021 Otieno Rowland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
