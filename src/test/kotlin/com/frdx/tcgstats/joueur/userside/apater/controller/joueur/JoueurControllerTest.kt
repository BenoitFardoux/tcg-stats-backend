package com.frdx.tcgstats.joueur.userside.apater.controller.joueur

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.frdx.tcgstats.joueur.domain.exception.CourrielInvalideException
import com.frdx.tcgstats.joueur.domain.usecase.joueur.CreerJoueur
import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.mapper.JoueurMapper.toJoueur
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


@WebMvcTest(JoueurController::class, excludeAutoConfiguration = [SecurityAutoConfiguration::class])
class JoueurControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var creerJoueur: CreerJoueur

    @MockitoBean
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @Test
    fun `Lorsque j'essaye de créer un joueur je ne rencontre aucune erreur`() {
        // GIVEN
        val password = "#password"
        val utilisateurACreer = CreerJoueurRestRessource("addresseMail@mail.com", password, "test")
        val joueur = utilisateurACreer.toJoueur()

        // WHEN
        `when`(bCryptPasswordEncoder.encode(password)).thenReturn("#password")
        `when`(creerJoueur(joueur)).thenReturn(joueur)

        // THEN
        mockMvc.post("/tcgstat/joueur/creer") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(utilisateurACreer)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }}

        @Test
        fun `Lorsque j'essaye de créer un joueur avec une adresse mail invalide, j'ai une erreur 400`() {
            // GIVEN
            val password = "#password"
            val utilisateurACreer = CreerJoueurRestRessource("addresseMail", password, "test")
            val joueur = utilisateurACreer.toJoueur()

            // WHEN
            `when`(bCryptPasswordEncoder.encode(password)).thenReturn("#password")
            `when`(creerJoueur(joueur)).thenThrow(CourrielInvalideException::class.java)

            // THEN
            mockMvc.post("/tcgstat/joueur/creer") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
                content = jacksonObjectMapper().writeValueAsString(utilisateurACreer)
            }.andExpect {
                status { isBadRequest() }
                content { contentType(MediaType.APPLICATION_PROBLEM_JSON) }
            }
        }
    }