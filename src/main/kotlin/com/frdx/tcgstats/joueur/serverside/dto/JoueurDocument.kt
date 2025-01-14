package com.frdx.tcgstats.joueur.serverside.dto

import com.frdx.tcgstats.jeux.serverside.dto.JeuxDocument
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "joueurs") // Nom de la table dans la base de données
data class JoueurDocument (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Utilise un générateur UUID natif si disponible
    val id: UUID? = null,

    val verifie: Boolean,

    val motDePasse: String,

    @Column(unique = true) // Le courriel doit être unique
    val courriel: String,

    @Column(unique = true) // Le pseudo doit aussi être unique
    val pseudo: String,

    val photoProfil: String,



    @ManyToMany
    @JoinTable(
        name = "joueurs_jeux",
        joinColumns = [JoinColumn(name = "joueur_id")],
        inverseJoinColumns = [JoinColumn(name = "jeu_id")]
    )
    val jeux: MutableList<JeuxDocument> = mutableListOf()
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun getPassword(): String = motDePasse

    override fun getUsername(): String = pseudo
}
