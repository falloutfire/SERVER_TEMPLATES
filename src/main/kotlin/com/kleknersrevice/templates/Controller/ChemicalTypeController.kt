package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Service.ChemicalTypeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chemical_types/")
class ChemicalTypeController(private val chemicalTypeService: ChemicalTypeService) {

    @PostMapping("")
    fun addChemicalType(@RequestBody chemicalType: ChemicalType): ResponseEntity<HttpStatus> {
        chemicalTypeService.addChemicalType(chemicalType)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("")
    fun allChemicalTypes(): ResponseEntity</*List<ChemicalType>*/*> {
        return chemicalTypeService.allChemicalType().let {
            if (!it.isEmpty()) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{id}")
    fun getChemicalTypeById(@PathVariable(value = "id") chemicalTypeId: Long): ResponseEntity<*> {
        return chemicalTypeService.getChemicalTypeById(chemicalTypeId).let {
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("{id}")
    fun deleteChemicalTypeById(@PathVariable(value = "id") chemicalTypeId: Long): ResponseEntity<HttpStatus> {
        return chemicalTypeService.getChemicalTypeById(chemicalTypeId).let {
            if (it.isPresent) {
                chemicalTypeService.deleteChemicalType(chemicalTypeId)
                ResponseEntity(HttpStatus.OK)
            } else ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("")
    fun updateChemicalType(@RequestBody chemicalType: ChemicalType): ResponseEntity<HttpStatus> {
        chemicalTypeService.updateChemicalType(chemicalType)
        return ResponseEntity(HttpStatus.OK)
    }
}