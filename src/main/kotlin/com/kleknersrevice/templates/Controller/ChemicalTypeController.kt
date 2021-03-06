package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Controller.ResponseValues.Companion.CREATED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.DELETED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.EXIST
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.NOT_FOUND
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_ADMIN
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_USER
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.UPDATED
import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Service.AuthenticationFacadeService
import com.kleknersrevice.templates.Service.ChemicalTypeService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/chemical_types")
class ChemicalTypeController(
    private val chemicalTypeService: ChemicalTypeService,
    private val authenticationFacadeService: AuthenticationFacadeService
) {

    private val log = LoggerFactory.getLogger(ChemicalTypeController::class.java)

    @Secured(ROLE_ADMIN)
    @PostMapping("")
    fun addChemicalType(@RequestBody chemicalType: ChemicalType): ApiResponse {
        log.info(
            String.format(
                "received request to save ChemicalType %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return chemicalTypeService.findChemicalType(chemicalType).run {
            if (!isPresent) {
                chemicalTypeService.addChemicalType(chemicalType)
                ApiResponse(HttpStatus.CREATED, "ChemicalType ${CREATED}")
            } else {
                ApiResponse(HttpStatus.OK, "ChemicalType ${EXIST}")
            }
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("")
    fun allChemicalTypes(): ApiResponse {
        log.info(
            String.format(
                "received request to list ChemicalType %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return chemicalTypeService.allChemicalType().run {
            ApiResponse(HttpStatus.OK, this)
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/{id}")
    fun getChemicalTypeById(@PathVariable(value = "id") chemicalTypeId: Long): ApiResponse {
        log.info(
            String.format(
                "received request to get ChemicalType by id %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return chemicalTypeService.getChemicalTypeById(chemicalTypeId).run {
            if (isPresent) ApiResponse(HttpStatus.OK, this) else
                ApiResponse(HttpStatus.NOT_FOUND, "Chemical Type $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN)
    @DeleteMapping("/{id}")
    fun deleteChemicalTypeById(@PathVariable(value = "id") chemicalTypeId: Long): ApiResponse {
        log.info(
            String.format(
                "received request to delete ChemicalType %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return chemicalTypeService.getChemicalTypeById(chemicalTypeId).run {
            if (isPresent) {
                chemicalTypeService.deleteChemicalType(chemicalTypeId)
                ApiResponse(HttpStatus.OK, "Chemical Type $DELETED")
            } else ApiResponse(HttpStatus.NOT_FOUND, "Chemical Type $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN)
    @PutMapping("")
    fun updateChemicalType(@RequestBody chemicalType: ChemicalType): ApiResponse {
        log.info(
            String.format(
                "received request to update ChemicalType %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return chemicalTypeService.getChemicalTypeById(chemicalType.id)
            .run {
                if (isPresent) {
                    chemicalTypeService.updateChemicalType(chemicalType)
                    ApiResponse(HttpStatus.OK, "OS $UPDATED")
                } else ApiResponse(HttpStatus.NOT_FOUND, "Chemical Type $NOT_FOUND")
            }
    }
}
