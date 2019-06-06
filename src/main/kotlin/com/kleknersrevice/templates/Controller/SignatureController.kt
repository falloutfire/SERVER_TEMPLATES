package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Controller.ResponseValues.Companion.CREATED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.DELETED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.EXIST
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.NOT_FOUND
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_ADMIN
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_USER
import com.kleknersrevice.templates.Entity.Signature
import com.kleknersrevice.templates.Entity.SignatureDetails
import com.kleknersrevice.templates.Service.AuthenticationFacadeService
import com.kleknersrevice.templates.Service.SignatureService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/signature")
class SignatureController(
    private val signatureService: SignatureService,
    private val authenticationFacadeService: AuthenticationFacadeService
) {

    private val log = LoggerFactory.getLogger(SignatureController::class.java)

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("")
    fun checkSignature(@RequestBody listSignature: List<SignatureDetails>): ApiResponse {
        log.info(
            String.format(
                "received request to find signature %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )

        return signatureService.findSignatureBySignatureDetails(listSignature).run {
            if (isPresent) {
                ApiResponse(HttpStatus.OK, this)
            } else {
                ApiResponse(HttpStatus.NOT_FOUND, this)
            }
        }
    }

    @Secured(ROLE_ADMIN)
    @PostMapping("")
    fun saveSignature(@RequestBody signature: Signature): ApiResponse {
        log.info(
            String.format(
                "received request to post signature %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return signatureService.findSignatureBySignatureDetails(signature.listDetails).run {
            if (!isPresent) {
                signatureService.saveSignature(signature)
                ApiResponse(HttpStatus.CREATED, "SIGNATURE $CREATED")
            } else {
                ApiResponse(HttpStatus.OK, "SIGNATURE $EXIST")
            }
        }
    }

    @Secured(ROLE_ADMIN)
    @DeleteMapping("/{id}")
    fun deleteSignature(@PathVariable(value = "id") osId: Long): ApiResponse {
        log.info(
            String.format(
                "received request to delete signature %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return signatureService.findSignatureById(osId).run {
            if (isPresent) {
                signatureService.deleteSignatureById(osId)
                ApiResponse(HttpStatus.OK, "OS $DELETED")
            } else ApiResponse(HttpStatus.NOT_FOUND, "OS $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN)
    @GetMapping("/all")
    fun getAllSignature(): ApiResponse {
        return signatureService.findAllSignatures().run {
            ApiResponse(HttpStatus.OK, this)
        }
    }
}