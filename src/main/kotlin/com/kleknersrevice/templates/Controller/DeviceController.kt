package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.Device
import com.kleknersrevice.templates.Entity.OS
import com.kleknersrevice.templates.Service.DeviceService
import com.kleknersrevice.templates.Service.OsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/device/")
class DeviceController(private val deviceService: DeviceService, private val osService: OsService) {

    @PostMapping("")
    fun addDevice(@RequestBody device: Device): ResponseEntity<HttpStatus> {
        deviceService.addDevice(device)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping("{id}")
    fun deleteDevice(@PathVariable(value = "id") id: Long): ResponseEntity<HttpStatus> {
        return deviceService.getDeviceById(id).let {
            if (it.isPresent) {
                osService.deleteOs(id)
                ResponseEntity(HttpStatus.OK)
            } else ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("")
    fun updateDevice(@RequestBody device: Device): ResponseEntity<HttpStatus> {
        deviceService.updateDevice(device)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("")
    fun allDevice(): ResponseEntity<*> {
        return deviceService.allDevice().let {
            if (it.isEmpty()) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{id}")
    fun getDeviceById(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        return deviceService.getDeviceById(id).let {
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("find_by_os")
    fun getAllByOs(@RequestBody os: OS): ResponseEntity<List<Device>> {
        return osService.getOsById(os.id).let {
            if (it.isPresent) ResponseEntity(
                deviceService.getAllByOs(os),
                HttpStatus.OK
            ) else ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }
}