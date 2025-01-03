package com.frdx.tcgstats.joueur.userside.apater.controller.joueur.documentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement

interface JoueurControllerDocumentation {
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Le joueur à bien été créé en base de données"
            )
        ]
    )
    @Operation(
        summary = "Créer un utilisateur",
        security = [SecurityRequirement(name ="")]
    )
    fun creer()
}