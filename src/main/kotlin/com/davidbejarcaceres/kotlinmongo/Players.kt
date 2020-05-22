package com.davidbejarcaceres.kotlinmongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "players")
data class Players (@Id @Indexed(unique=true)  var _id:String? = null, var name: String, var lastname: String, var age: String, @Indexed(unique=true) var dni: String)
