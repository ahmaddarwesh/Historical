package com.superdar.historical

import com.google.firebase.firestore.IgnoreExtraProperties


data class Type(var id: Int, var name: String, var pic: Int)

@IgnoreExtraProperties
data class PersonModel(
        var ID: String,
        var Name: String,
        var Picture: String
) {
    constructor() : this("", "", "")
}


