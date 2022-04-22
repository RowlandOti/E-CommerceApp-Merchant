package com.rowland.delivery.sharedcore.features

object Features {
    enum class Flag(internal val isEnabledByDefault: Boolean) {

        /**
         * Enables one type sign for google accounts
         */
        GOOGLE_ONE_TAP_SIGN_IN(true),
    }

    /**
     * Collection of Flags with their respective default values.
     */

    @JvmStatic
    val FeatureFlagsOverrideCollection = mutableMapOf<Flag, Boolean>()

    /**
     * Returns the value of the specified Flag.
     *
     * @param feature Flag to query the value for.
     * @return Default Flag value.
     */
    @JvmStatic
    fun isFeatureEnabled(feature: Flag): Boolean {
        return FeatureFlagsOverrideCollection[feature] ?: feature.isEnabledByDefault
    }

    /**
     * Returns string presenting the values of all the feature flags in log-friendly format.
     *
     * @return String that's being logged.
     */
    @JvmStatic
    fun getFeatureFlagsValueLogString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("All feature flags:\n")

        val allFlags = Flag.values()
        for (flag in allFlags) {
            stringBuilder.append("Flag: $flag, enabled: ${isFeatureEnabled(flag)}\n")
        }

        return stringBuilder.toString()
    }
}