package com.frdx.tcgstats.jeux.userside.adapter.controller

import com.frdx.tcgstats.jeux.serverside.exception.JeuDejaExistantException
import com.frdx.tcgstats.jeux.serverside.exception.JeuNonExistantException
import com.frdx.tcgstats.jeux.serverside.exception.JeuUtiliseException
import com.frdx.tcgstats.jeux.serverside.exception.UUIDJeuInvalideException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice(assignableTypes = [JeuController::class])
class JeuControllerAdvice {
    @ExceptionHandler(JeuDejaExistantException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun error409(exception: JeuDejaExistantException): ProblemDetail {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.message)
    }

    @ExceptionHandler(JeuNonExistantException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun error404(exception : JeuNonExistantException): ProblemDetail {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.message)
    }

    @ExceptionHandler(UUIDJeuInvalideException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun error400(exception : UUIDJeuInvalideException): ProblemDetail {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.message)
    }

    @ExceptionHandler(JeuUtiliseException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun error400(exception : JeuUtiliseException): ProblemDetail {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.message)
    }
}