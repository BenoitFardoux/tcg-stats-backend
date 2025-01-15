package com.frdx.tcgstats.joueur.serverside.dto

import com.frdx.tcgstats.jeux.serverside.dto.JeuxDocument
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.apache.commons.lang3.builder.ToStringExclude
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
@Entity
@Table(name = "joueurs")
data class JoueurDocument(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    val verifie: Boolean,

    val motDePasse: String,

    @Column(unique = true)
    val courriel: String,

    @Column(unique = true)
    val pseudo: String,

    val photoProfil: String,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "joueurs_jeux",
        joinColumns = [JoinColumn(name = "joueur_id")],
        inverseJoinColumns = [JoinColumn(name = "jeu_id")]
    )
    @ToStringExclude
    val jeux: MutableList<JeuxDocument> = mutableListOf(),

    @ManyToMany(fetch = FetchType.EAGER)  // Une relation avec les rôles
    @JoinTable(
        name = "joueurs_roles",
        joinColumns = [JoinColumn(name = "joueur_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableList<RoleDocument> = mutableListOf()  // Définir un modèle de rôle
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        // Retourne dynamiquement les rôles associés à l'utilisateur
        return roles.map { SimpleGrantedAuthority("ROLE_${it.nom}") }.toMutableList()
    }

    override fun getPassword(): String = motDePasse

    override fun getUsername(): String = courriel

    override fun isAccountNonExpired(): Boolean = true  // Définissez ces méthodes selon votre logique
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = verifie
}
