package com.davidbejarcaceres.kotlinmongo

import com.fasterxml.jackson.databind.ObjectMapper
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
    @PostMapping(value = ["/addPlayer"], produces = ["application/json"])
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


    @ApiOperation(value = "Add new Player or array of players", response = Players::class)
    @PostMapping(value = [""], produces = ["application/json"])
    fun createNewPLayerOrMultiplePlayers(@RequestBody newPlayer: Any): ResponseEntity<Any> {
        val mapper = ObjectMapper()
        try {
            //Try to convert single object (LinkedHashMap) to Json String (String) and back to Players
            val jsonInString: String = mapper.writeValueAsString(newPlayer)
            val playerSerielized: Players = mapper.readValue(jsonInString, Players::class.java)

            if (!playerSerielized._id.isNullOrEmpty() && getPlayersById(playerSerielized._id!!) != null) {
                return ResponseEntity.status(400).body("ERROR: Player with same ID already found")
            }

            if (getPlayersByDNI(playerSerielized.dni) != null) {
                return ResponseEntity.status(400).body("ERROR: Player with same DNI found")
            }
            repositoryPlayers.save(playerSerielized.apply { _id = ObjectId.get().toHexString() })
            return ResponseEntity.status(201).body(playerSerielized)
        } catch (error: Throwable) {
            print(error)
        }

        try {//Try to convert multiple objects (Array<LinkedHashMap>) to Json String (String) and back to Array<Players>
            val jsonInString: String = mapper.writeValueAsString(newPlayer)
            val listOfPlayers: Array<Players> = mapper.readValue(jsonInString, Array<Players>::class.java);

            if (listOfPlayers is Array<Players> && !listOfPlayers.isEmpty()) {
                listOfPlayers.forEach { it ->
                    if (it is Players && it != null) {
                        if (!it._id.isNullOrEmpty() && getPlayersById(it._id!!) != null) {
                            return ResponseEntity.status(400).body("ERROR: Player with same ID ${it._id} already found")
                        }

                        if (getPlayersByDNI(it.dni) != null) {
                            return ResponseEntity.status(400).body("ERROR: Player with same DNI ${it.dni} found")
                        }
                    }
                }
                repositoryPlayers.saveAll(listOfPlayers.toMutableList()) // Important, convert to mutable list before saving to Mongo
                return ResponseEntity.status(201).body(listOfPlayers)
            }
        } catch (e: Exception) {
            return ResponseEntity.status(400).body("Error Parsing Array of Players")
        }

        return ResponseEntity.status(400).body("Not Saved")
    }

    @ApiOperation(value = "Update player info", response = Players::class)
    @PutMapping(value = ["/{id}"], produces = ["application/json"])
    @PatchMapping
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