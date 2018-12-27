package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Service.ChemicalTypeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/chemical_types/")
class ChemicalTypeController(private val chemicalTypeService: ChemicalTypeService) {

    @PostMapping("")
    fun addChemicalType(@RequestBody chemicalType: ChemicalType): ResponseEntity<*> {
        return chemicalTypeService.findChemicalType(chemicalType).let {
            if (!it.isPresent) {
                chemicalTypeService.addChemicalType(chemicalType)
                ResponseEntity(messageJson(201, "ChemicalType $chemicalType created"), HttpStatus.CREATED)
            } else {
                ResponseEntity(messageJson(200, "ChemicalType $chemicalType already exist"), HttpStatus.OK)
            }
        }
    }

    @GetMapping("")
    fun allChemicalTypes(): ResponseEntity<*> {
        return chemicalTypeService.allChemicalType().let {
            if (!it.isEmpty()) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(
                messageJson(404),
                HttpStatus.NOT_FOUND
            )
        }
    }

    @GetMapping("{id}")
    fun getChemicalTypeById(@PathVariable(value = "id") chemicalTypeId: Long): ResponseEntity<*> {
        return chemicalTypeService.getChemicalTypeById(chemicalTypeId).let {
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(
                messageJson(404, "ChemicalType $chemicalTypeId not found"),
                HttpStatus.NOT_FOUND
            )
        }
    }

    @DeleteMapping("{id}")
    fun deleteChemicalTypeById(@PathVariable(value = "id") chemicalTypeId: Long): ResponseEntity<*> {
        return chemicalTypeService.getChemicalTypeById(chemicalTypeId).let {
            if (it.isPresent) {
                chemicalTypeService.deleteChemicalType(chemicalTypeId)
                ResponseEntity(messageJson(201, "ChemicalType $chemicalTypeId deleted"), HttpStatus.OK)
            } else ResponseEntity(messageJson(404, "ChemicalType $chemicalTypeId not found"), HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("")
    fun updateChemicalType(@RequestBody chemicalType: ChemicalType): ResponseEntity<*> {
        return chemicalTypeService.getChemicalTypeById(chemicalType.id)
            .let {
                if (it.isPresent) {
                    chemicalTypeService.updateChemicalType(chemicalType)
                    ResponseEntity(messageJson(201, "ChemicalType $chemicalType updated"), HttpStatus.OK)
                } else {
                    ResponseEntity(messageJson(404, "ChemicalType $chemicalType not found"), HttpStatus.NOT_FOUND)
                }
            }
    }
}