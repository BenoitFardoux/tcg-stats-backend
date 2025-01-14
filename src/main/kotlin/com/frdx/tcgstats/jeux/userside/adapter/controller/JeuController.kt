package com.frdx.tcgstats.jeux.userside.adapter.controller

import com.frdx.tcgstats.jeux.domain.model.Jeu
import com.frdx.tcgstats.jeux.domain.usecase.jeux.CreerJeu
import com.frdx.tcgstats.jeux.domain.usecase.jeux.RecupererJeu
import com.frdx.tcgstats.jeux.domain.usecase.jeux.RecupererJeux
import com.frdx.tcgstats.jeux.domain.usecase.jeux.SupprimerJeu
import com.frdx.tcgstats.jeux.userside.adapter.controller.documentation.JeuControllerDocumentation
import com.frdx.tcgstats.jeux.userside.dto.CreerJeuDto
import com.frdx.tcgstats.jeux.userside.mapper.JeuMapper.toJeu
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("tcgstat/jeux")
class JeuController(
    private val creerJeu: CreerJeu,
    private val recupererJeuxUsecase: RecupererJeux,
    private val recupererJeu: RecupererJeu,
    private val supprimerJeu: SupprimerJeu
) : JeuControllerDocumentation {

    @PostMapping()
    override fun creer(@RequestBody creerJeuDto: CreerJeuDto): ResponseEntity<Jeu> {
        val jeuCree = creerJeu(creerJeuDto.toJeu())
        return ResponseEntity.status(HttpStatus.CREATED).body(jeuCree)
    }

    @GetMapping
    override fun recupererJeux(): ResponseEntity<List<Jeu>> {
        val listeJeux = recupererJeuxUsecase()
        return ResponseEntity.ok(listeJeux)
    }

    @GetMapping("/{id}")
    override fun recupererUnJeu(@PathVariable id: String): ResponseEntity<Jeu> {
        val jeu = recupererJeu(id)
        return ResponseEntity.ok(jeu)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun supprimerUnJeu(@PathVariable id: String) {
        supprimerJeu(id)
    }
}