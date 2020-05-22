package com.davidbejarcaceres.kotlinmongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "players")
data class Players (@Id val _id: String , val name: String , val lastname: String , val age: String , val dni: String)
