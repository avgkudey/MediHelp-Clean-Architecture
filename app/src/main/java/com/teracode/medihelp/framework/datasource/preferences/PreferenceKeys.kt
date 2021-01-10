package com.teracode.medihelp.framework.datasource.preferences

class PreferenceKeys {

    companion object{

        // Shared Preference Files:
        const val DRUG_PREFERENCES: String = "com.teracode.medihelp.drugs"

        // Shared Preference Keys
        val DRUG_FILTER: String = "${DRUG_PREFERENCES}.DRUG_FILTER"
        val DRUG_ORDER: String = "${DRUG_PREFERENCES}.DRUG_ORDER"

    }
}