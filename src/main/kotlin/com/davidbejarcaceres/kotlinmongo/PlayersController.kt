package com.davidbejarcaceres.kotlinmongo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"]) //CORS security, Allows connecting to the API from external paths.
@RestController
@RequestMapping("/players")
class PlayersController () {

    @Autowired
    private lateinit var repositoryPlayers: PlayersRepository

    @GetMapping(produces = ["application/json"])
    fun getPlayers(): List<Players> = repositoryPlayers.findAll()

    @GetMapping("/{id}", produces = ["application/json"])
    fun getPlayersById(@PathVariable("id") id : String): ResponseEntity<Players> = if (repositoryPlayers.findBy_id(id) != null)
        ResponseEntity.status(200).body(repositoryPlayers.findBy_id(id)) else ResponseEntity.status(404).body(null)

    @GetMapping("/dni/{dni}", produces = ["application/json"])
    fun getPlayersByDNI(@PathVariable("dni") dni : String): ResponseEntity<Players> = if (repositoryPlayers.findByDni(dni) != null)
        ResponseEntity.status(200).body(repositoryPlayers.findByDni(dni)) else ResponseEntity.status(404).body(null)

    @GetMapping("/name/{name}", produces = ["application/json"])
    fun getPlayersByName(@PathVariable("name") name : String): ResponseEntity<List<Players>> = if (repositoryPlayers.findByName(name) != null)
        ResponseEntity.status(200).body(repositoryPlayers.findByName(name)) else ResponseEntity.status(404).body(null)

    @GetMapping("/lastname/{lastname}", produces = ["application/json"])
    fun getPlayersBylastname(@PathVariable("lastname") lastname : String): ResponseEntity<List<Players>> = if (repositoryPlayers.findByLastname(lastname) != null)
        ResponseEntity.status(200).body(repositoryPlayers.findByLastname(lastname)) else ResponseEntity.status(404).body(null)

    @GetMapping("/helloworld/{name}")
    fun helloWorldUser(@PathVariable("name") name: String?): String = "Hellow World $name";
}