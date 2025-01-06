package com.frdx.tcgstats.joueur.userside.apater.controller.joueur

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.frdx.tcgstats.joueur.domain.usecase.joueur.CreerJoueur
import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.mapper.JoueurMapper.toJoueur
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(JoueurController::class, excludeAutoConfiguration = [SecurityAutoConfiguration::class])
@ExtendWith(MockitoExtension::class)
class JoueurControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var creerJoueur : CreerJoueur

    @Test
    fun `Lorsque j'essaye de créer un joueur je ne rencontre aucune erreur`() {
        // GIVEN
        val utilisateurACreer = CreerJoueurRestRessource("addresseMail@mail.com", "#123456789", "test")
        // WHEN
        `when`(creerJoueur(utilisateurACreer.toJoueur())).thenReturn(utilisateurACreer.toJoueur())
        // THEN
        mockMvc.post("/tcgstat/joueur/creer") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(utilisateurACreer)
        }.andExpect {
            status { is2xxSuccessful() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }

    }
}