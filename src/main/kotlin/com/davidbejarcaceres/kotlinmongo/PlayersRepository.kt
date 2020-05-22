package com.davidbejarcaceres.kotlinmongo

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "players", path = "players")
interface PlayersRepository : MongoRepository<Players, String> {
    fun findBy_id(_id: ObjectId): Players
    fun findByName(name: String): List<Players>
    fun findByDni(dni: String): List<Players>
    fun findByLastname(lastname: String): List<Players>
}