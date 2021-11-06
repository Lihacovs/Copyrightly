package eu.balticit.copyrightly.data.firebase.model

import eu.balticit.copyrightly.utils.AppUtils

/**
 * POJO for User
 */
data class User(
    val userId : String = "",
    val userEmail : String? = null,
    val userPassword : String? = null,
    val userName : String? = null,
    val userPhotoUrl : String? = null,
    val userGender : String? = null,
    val userBirthDate : String? = null,
    val userCreationDate : String? = AppUtils.getCurrentDate(),
    val userPremium : Boolean? = false,
    val userAdmin : Boolean? = false
)