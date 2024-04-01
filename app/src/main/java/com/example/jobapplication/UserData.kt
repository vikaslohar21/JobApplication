package com.example.jobapplication

class UserData (
    val profileImageUri: String,
    val post: String,
    val username: String
) {
    fun copy(
        profileImageUri: String = this.profileImageUri,
        post: String = this.post,
        username: String = this.username
    ): UserData {
        return UserData(profileImageUri, post, username)
    }
}