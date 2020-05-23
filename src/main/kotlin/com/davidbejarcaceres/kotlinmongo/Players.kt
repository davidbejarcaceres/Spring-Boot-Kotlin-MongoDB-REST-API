package com.davidbejarcaceres.kotlinmongo

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "players")
data class Players (
        @Id @JsonProperty("_id")  var _id:String? = null,
        @JsonProperty("name") var name: String,
        @JsonProperty("lastname") var lastname: String,
        @JsonProperty("age") var age: String,
        @JsonProperty("dni") var dni: String

)
