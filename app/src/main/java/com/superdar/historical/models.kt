package com.superdar.historical

import androidx.annotation.Keep


data class Type(var id: Int, var name: String, var pic: Int)


@Keep
data class PersonModel(
        var ID: String,
        var Name: String,
        var Picture: String
) {
    constructor() : this("", "", "")
}


