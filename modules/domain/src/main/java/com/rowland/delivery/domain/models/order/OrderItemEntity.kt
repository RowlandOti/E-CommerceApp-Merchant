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

package com.rowland.delivery.domain.models.order

/**
 * Created by Rowland on 5/8/2018.
 */

class OrderItemEntity {

    var itemCode: String? = null
    var title: String? = null
    var imageUrl: String? = null
    var itemQuantity: Int? = null
    var price: Int? = null
    var itemTotal: Int? = null
    var foreign: Boolean? = null
}
