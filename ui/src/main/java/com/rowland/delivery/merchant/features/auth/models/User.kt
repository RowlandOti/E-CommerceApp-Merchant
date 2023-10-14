/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
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

package com.rowland.delivery.merchant.features.auth.models

/**
 * Created by Rowland on 5/1/2018.
 */

open class User {
    var userId: String? = null
    var username: String? = null
    var firstName: String? = null
    var middleName: String? = null
    var lastName: String? = null
    var email: String? = null
    var birthday: String? = null
    var gender: Int = 0
    var profileLink: String? = null
    var phoneNumber: String? = null
    var idToken: String? = null

    override fun toString(): String {
        return "User{" +
            "userId='" + userId + '\''.toString() +
            ", username='" + username + '\''.toString() +
            ", firstName='" + firstName + '\''.toString() +
            ", middleName='" + middleName + '\''.toString() +
            ", lastName='" + lastName + '\''.toString() +
            ", email='" + email + '\''.toString() +
            ", birthday='" + birthday + '\''.toString() +
            ", gender=" + gender +
            ", profileLink='" + profileLink + '\''.toString() +
            ", phoneNumber='" + phoneNumber + '\''.toString() +
            ", idToken='" + idToken + '\''.toString() +
            '}'.toString()
    }
}
