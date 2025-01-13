package com.frdx.tcgstats.joueur.userside.apater.controller.joueur

import com.frdx.tcgstats.joueur.domain.exception.CourrielInvalideException
import com.frdx.tcgstats.joueur.serverside.exception.IdentifiantJoueurInvalide
import com.frdx.tcgstats.joueur.serverside.exception.JoueurDejaExistantException
import com.frdx.tcgstats.joueur.serverside.exception.JoueurInnexistantException
import com.frdx.tcgstats.joueur.userside.exception.MotDePasseInvalideException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice(assignableTypes = [JoueurController::class])
class JoueurControllerAdvice {

    @ExceptionHandler(CourrielInvalideException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun error400(exception: CourrielInvalideException) : ProblemDetail {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.message)
    }

    @ExceptionHandler(MotDePasseInvalideException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun error400(exception: MotDePasseInvalideException) : ProblemDetail {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.message)
    }

    @ExceptionHandler(JoueurDejaExistantException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun error409(exception: JoueurDejaExistantException) : ProblemDetail {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.message)
    }
    @ExceptionHandler(JoueurInnexistantException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun error404(exception: JoueurInnexistantException) : ProblemDetail {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.message)
    }
    @ExceptionHandler(IdentifiantJoueurInvalide::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun error400(exception: IdentifiantJoueurInvalide) : ProblemDetail {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.message)
    }
}