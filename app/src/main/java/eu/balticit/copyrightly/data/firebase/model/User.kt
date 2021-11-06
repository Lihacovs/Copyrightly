package eu.balticit.copyrightly.data.firebase.model

/**
 * POJO for User
 */
data class User(
    val userId : String,
    val userEmail : String?,
    val userPassword : String?,
    val userName : String?,
    val userPhotoUrl : String?,
    val userGender : String?,
    val userBirthDate : String?,
    val userCreationDate : String?,
    val userPremium : Boolean?,
    val userAdmin : Boolean?
)