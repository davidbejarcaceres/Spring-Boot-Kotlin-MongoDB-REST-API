package com.davidbejarcaceres.kotlinmongo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"]) //CORS security, Allows connecting to the API from external paths.
@RestController
@RequestMapping("/players")
class PlayersController () {

    @Autowired
    private lateinit var repositoryPlayers: PlayersRepository

    @GetMapping(produces = ["application/json"]) fun getPlayers(): List<Players> = repositoryPlayers.findAll()

    @GetMapping("/helloworld/{name}") fun helloWorldUser(@PathVariable("name") name: String?): String = "Hellow World $name";
}