package com.frdx.tcgstats.joueur.userside.apater.controller.joueur

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbJoueurRepository
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueurDocument
import com.frdx.tcgstats.joueur.userside.apater.controller.joueur.joueurFixture.courrielInvalide
import com.frdx.tcgstats.joueur.userside.apater.controller.joueur.joueurFixture.creerJoueurDTO
import com.frdx.tcgstats.joueur.userside.apater.controller.joueur.joueurFixture.motDePasseInvalide
import com.frdx.tcgstats.joueur.userside.apater.controller.joueur.joueurFixture.motDePasseValide
import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.mapper.JoueurMapper.toJoueur
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
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
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension::class)
@TestPropertySource(locations = ["classpath:application-test.properties"])
class JoueurControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mariaDbJoueurRepository: MariaDbJoueurRepository

    @MockitoBean
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @BeforeEach
    fun setUp() {
        mariaDbJoueurRepository.deleteAll()
    }


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
        val utilisateurACreer = creerJoueurDTO.copy(courriel = courrielInvalide)
        val password = utilisateurACreer.motDePasse

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
        val utilisateurACreer = creerJoueurDTO.copy(motDePasse = motDePasseInvalide)

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

    @Test
    fun `Lorsque j'essaye de récuperer un utilisateur qui n'est pas en base de données je reçois une 404 not found`() {
        // GIVEN
        val uuidInnexistant = UUID.randomUUID()
        // WHEN
        //THEN
        mockMvc.get("/tcgstat/joueur/${uuidInnexistant}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
            content { contentType(MediaType.APPLICATION_PROBLEM_JSON) }
        }
    }

    @Test
    fun `Lorsque j'essaye de récuperer un utilisateur qui est  en base de données je reçois une 200`() {
        // GIVEN
        val user = mariaDbJoueurRepository.saveAndFlush(creerJoueurDTO.toJoueur().toJoueurDocument())

        // WHEN
        //THEN
        mockMvc.get("/tcgstat/joueur/${user.id}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    fun `Lorsque j'esaye de récuperer la liste des utilisateurs, celle ci contient les utilisateurs de la base de données`() {
        // GIVEN
        mariaDbJoueurRepository.saveAndFlush(creerJoueurDTO.toJoueur().toJoueurDocument())
        // WHEN

        //THEN
        mockMvc.get("/tcgstat/joueur") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    fun `Lorsque j'essaye de supprimer un utilisateur et qu'il existe, celui ci est correctement supprimé`() {
        // GIVEN
        val joueur = mariaDbJoueurRepository.saveAndFlush(creerJoueurDTO.toJoueur().toJoueurDocument())
        // WHEN
        // THEN
        mockMvc.delete("/tcgstat/joueur/${joueur.id}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }

            .andExpect {
                status { isOk() }
            }
        assertThat(mariaDbJoueurRepository.existsById(joueur.id!!)).isFalse()
    }

    @Test
    fun `Lorsque j'essaye de supprimer un utilisateur et qu'il n'existe pas, il y a une erreur 404`() {
        // GIVEN
        val idJoueur = UUID.randomUUID()
        // WHEN
        // THEN
        mockMvc.delete("/tcgstat/joueur/${idJoueur}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }

            .andExpect {
                status { isNotFound() }
                content { contentType(MediaType.APPLICATION_PROBLEM_JSON) }
            }
    }
}
