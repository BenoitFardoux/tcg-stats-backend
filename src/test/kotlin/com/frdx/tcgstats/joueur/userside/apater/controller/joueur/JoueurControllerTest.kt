package com.frdx.tcgstats.joueur.userside.apater.controller.joueur

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.frdx.tcgstats.joueur.userside.apater.controller.joueur.joueurFixture.motDePasseInvalide
import com.frdx.tcgstats.joueur.userside.apater.controller.joueur.joueurFixture.motDePasseValide
import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension::class)
@TestPropertySource(locations = ["classpath:application-test.properties"])
class JoueurControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


    @MockitoBean
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder



    @Test
    fun `Lorsque j'essaye de créer un joueur je ne rencontre aucune erreur`() {
        // GIVEN
        val password = motDePasseValide
        val utilisateurACreer = CreerJoueurRestRessource("addresseMail@mail.com", password, "test")

        // WHEN
        `when`(bCryptPasswordEncoder.encode(password)).thenReturn("#password")

        // THEN
        mockMvc.post("/tcgstat/joueur/creer") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(utilisateurACreer)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    fun `Lorsque j'essaye de créer un joueur avec une adresse mail invalide, j'ai une erreur 400`() {
        // GIVEN
        val password = motDePasseValide
        val utilisateurACreer = CreerJoueurRestRessource("addresseMail", password, "test")

        // WHEN
        `when`(bCryptPasswordEncoder.encode(password)).thenReturn("#password")

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

    @Test
    fun `Lorsque j'essaye de créer un utilisateur avec un mot de passe invalide , j'ai une erreur 400 `() {
        // GIVEN
        val utilisateurACreer = CreerJoueurRestRessource("addresseMail@mail.com", motDePasseInvalide, "test")

        // WHEN
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
