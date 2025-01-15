package com.frdx.tcgstats.joueur.userside.apater.controller.joueur.documentation

import com.frdx.tcgstats.joueur.userside.dto.AssocierJeuDto
import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.dto.JoueurRestRessource
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable

interface JoueurControllerDocumentation {


    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "La liste des joueurs existants")
        ]
    )
    @Operation(
        summary = "Recuperer les utilisateurs",
        security = [SecurityRequirement(name = "")]
    )
    fun recuperer(): ResponseEntity<List<JoueurRestRessource>>

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "L'utilisateur demandé"
            ),
            ApiResponse(
                responseCode = "404",
                description = "L'utilisateur n'existe pas",
                content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
            )
        ]
    )
    fun recupererParId(@PathVariable id: String): ResponseEntity<JoueurRestRessource>


    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "L'utilisateur a été supprimé"
            ),
            ApiResponse(
                responseCode = "404",
                description = "L'utilisateur n'existe pas",
                content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
            )
        ]
    )
    fun supprimerUnJoueur(@PathVariable id: String)


    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "L'utilisateur a bien été modifié"
            ),
        ApiResponse(
            responseCode = "404",
            description = "L'utilisateur ou le jeu n'existe pas",
            ),
        ApiResponse(
            responseCode = "400",
            description = "Un ou plusieurs UUID est mal formatté",
        ),
        ApiResponse(
            responseCode = "304",
            description = "Le joueur joue déjà à ce jeu"
        )
        ]
    )
    fun associerUnJeu(id : String, idJeu : AssocierJeuDto): ResponseEntity<JoueurRestRessource>

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "L'utilisateur a bien été modifié"
            ),
        ApiResponse(
            responseCode = "404",
            description = "L'utilisateur ou le jeu n'existe pas",
            ),
        ApiResponse(
            responseCode = "400",
            description = "Un ou plusieurs UUID est mal formatté",
        ),
        ApiResponse(
            responseCode = "304",
            description = "Le joueur ne possede pas ce jeu"
        )
        ]
    )
    fun dissocierUnJeu(id : String, idJeu : String): ResponseEntity<JoueurRestRessource>
}