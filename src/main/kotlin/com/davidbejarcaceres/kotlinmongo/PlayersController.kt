package com.davidbejarcaceres.kotlinmongo

import io.swagger.annotations.ApiOperation
import org.bson.types.ObjectId
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

    @ApiOperation(value = "Find Player by Id", response = Players::class)
    @GetMapping("/{id}", produces = ["application/json"])
    fun getPlayersById(@PathVariable("id") id : String): Players? = repositoryPlayers.findBy_id(id)

    @GetMapping("/dni/{dni}", produces = ["application/json"])
    fun getPlayersByDNI(@PathVariable("dni") dni : String): Players? = repositoryPlayers.findByDni(dni)

    @GetMapping("/name/{name}", produces = ["application/json"])
    fun getPlayersByName(@PathVariable("name") name : String): List<Players>? = repositoryPlayers.findByName(name)

    @GetMapping("/lastname/{lastname}", produces = ["application/json"])
    fun getPlayersBylastname(@PathVariable("lastname") lastname : String): List<Players>? = repositoryPlayers.findByLastname(lastname)

    @GetMapping("/helloworld/{name}")
    fun helloWorldUser(@PathVariable("name") name: String?): String = "Hellow World $name";


    @ApiOperation(value = "Add new Player", response = Players::class)
    @PostMapping(value = [""], produces = ["application/json"])
    fun createNewPLayer(@RequestBody newPlayer: Players): ResponseEntity<Any> {
        if (!newPlayer._id.isNullOrEmpty() && getPlayersById(newPlayer._id!!) != null) {
            return ResponseEntity.status(400).body("ERROR: Player with same ID already found")
        }

        if (getPlayersByDNI(newPlayer.dni) != null) {
            return ResponseEntity.status(400).body("ERROR: Player with same DNI found")
        }
        repositoryPlayers.save(newPlayer.apply { _id = ObjectId.get().toHexString() })
        return ResponseEntity.status(201).body(newPlayer)
    }

    @ApiOperation(value = "Update player info", response = Players::class)
    @PutMapping(value = ["/{id}"], produces = ["application/json"])
    fun updatePlayerInfo(@PathVariable("id") id: String, @RequestBody player: Players): ResponseEntity<Any> {
        if (!id.isNullOrEmpty()) {
            val originalPlayer: Players? = getPlayersById(id)
            if (originalPlayer != null) {
                originalPlayer.apply {
                    name = if (player.name != null && player.name != originalPlayer.name && player.name.length > 2 ) player.name else originalPlayer.name
                    lastname = if (player.lastname != null && player.lastname != originalPlayer.lastname && player.lastname.length > 2 ) player.lastname else originalPlayer.lastname
                    age = if (player.age != null && player.age != originalPlayer.age ) player.age else originalPlayer.age
                    dni = if (player.dni != null && player.dni != originalPlayer.dni && player.dni.length > 6 ) player.dni else originalPlayer.dni
                }
                repositoryPlayers.save(originalPlayer)
                return ResponseEntity.ok().body(null)
            } else {
                return ResponseEntity.status(404).body(null)
            }
        }
        return ResponseEntity.status(404).body(null)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deletePlayer(@PathVariable id: String) {
        val originalPlayer: Players? = getPlayersById(id)
        if (originalPlayer != null) {
            repositoryPlayers.delete(repositoryPlayers.findBy_id(id)!!)
        } else {
            ResponseEntity.ok()
        }
    }
}