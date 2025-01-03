package com.frdx.tcgstats.joueur.userside.apater.controller.joueur

import com.frdx.tcgstats.joueur.userside.apater.controller.joueur.documentation.JoueurControllerDocumentation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/joueur")
class JoueurController : JoueurControllerDocumentation {

    @PostMapping
    override fun creer() {
        TODO("Not yet implemented")
    }
    @GetMapping
    fun get() : ResponseEntity<String?> {
        return ResponseEntity.ok().body("Hello Joueur")
    }
}