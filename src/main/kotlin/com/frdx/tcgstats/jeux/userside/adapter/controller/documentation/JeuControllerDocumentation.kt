package com.frdx.tcgstats.jeux.userside.adapter.controller.documentation

import com.frdx.tcgstats.jeux.domain.model.Jeu
import com.frdx.tcgstats.jeux.userside.dto.CreerJeuDto
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

interface JeuControllerDocumentation {
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "201",
            description = "Le jeu a bien été créé",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE)]
        ),
            ApiResponse(
                responseCode = "400",
                description = "le nom est invalide",
                content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
            ),
            ApiResponse(
                responseCode = "409",
                description = "Un jeu avec le nom saisi existe déjà",
                content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
            )]
    )
    fun creer(creerJeuDto: CreerJeuDto): ResponseEntity<Jeu>

    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "la liste des jeux",
            content = [io.swagger.v3.oas.annotations.media.Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)]

        )]
    )
    fun recupererJeux(): ResponseEntity<List<Jeu>>


    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "Le jeu demandé",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE)]
        ),
        ApiResponse(
            responseCode = "400",
            description = "l'id saisi est invalide",
            content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Le jeu demandé est introuvable",

            content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
        )]
    )
    fun recupererUnJeu(id : String): ResponseEntity<Jeu>

    @ApiResponses(
        value = [ApiResponse(
            responseCode = "204",
            description = "Le jeu a été supprimé",
        ),
        ApiResponse(
            responseCode = "400",
            description = "l'id saisi est invalide",
            content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Le jeu demandé est introuvable",

            content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
        )]
    )
    fun supprimerUnJeu(id : String)
}