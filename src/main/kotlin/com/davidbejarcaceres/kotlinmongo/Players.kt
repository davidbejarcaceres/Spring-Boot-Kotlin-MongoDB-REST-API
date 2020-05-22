package com.davidbejarcaceres.kotlinmongo

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "players")
class Players {
        @Id
        lateinit var _id: ObjectId
        lateinit var name: String
        lateinit var lastname: String
        lateinit var age: String
        lateinit var dni: String

        // Constructors Getters and Setters omitted
        constructor() {}
        constructor(_id: ObjectId, name: String, lastname: String, age: String, dni: String) {
                this._id = _id
                this.name = name
                this.lastname = lastname
                this.age = age
                this.dni = dni
        }

        // Mandatory to convert
        fun get_id(): String {
                return _id.toHexString()
        }

        fun setId(id: ObjectId) {
                this._id = id
        }

        override fun toString(): String {
                return "{" +
                        " _id='" + get_id() + "'" +
                        ", name='" + name + "'" +
                        ", lastname='" + lastname + "'" +
                        ", age='" + age + "'" +
                        ", dni='" + dni + "'" +
                        "}"
        }
}
