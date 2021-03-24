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

package com.rowland.delivery.merchant.robots.auth

import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.robots.BaseScreenRobot

/**
 * Created by rowlandoti on 31/01/2021
 */

class GoogleLoginRobot : BaseScreenRobot<GoogleLoginRobot>() {

    fun clickGoogleLogin() = performClickOnView(R.id.btn_google_login)
}