package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.Device
import com.kleknersrevice.templates.Entity.OS
import com.kleknersrevice.templates.Service.DeviceService
import com.kleknersrevice.templates.Service.OsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/device/")
class DeviceController(private val deviceService: DeviceService, private val osService: OsService) {

    @PostMapping("")
    fun addDevice(@RequestBody device: Device): ResponseEntity<*> {
        osService.getOs(device.os).let {
            if (it.isPresent) {
                deviceService.findDevice(device).let { findDevice ->
                    return if (!findDevice.isPresent) {
                        deviceService.addDevice(device)
                        ResponseEntity(messageJson(201, "Device $device created"), HttpStatus.CREATED)
                    } else {
                        ResponseEntity(messageJson(200, "Device $device already exist"), HttpStatus.OK)
                    }
                }
            } else {
                return ResponseEntity(messageJson(404, "OS $it not found"), HttpStatus.NOT_FOUND)
            }
        }
    }

    @DeleteMapping("{id}")
    fun deleteDevice(@PathVariable(value = "id") id: Long): ResponseEntity<HttpStatus> {
        return deviceService.getDeviceById(id).let {
            if (it.isPresent) {
                deviceService.deleteDevice(id)
                ResponseEntity(HttpStatus.OK)
            } else ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("")
    fun updateDevice(@RequestBody device: Device): ResponseEntity<*> {
        return deviceService.getDeviceById(device.id)
            .let {
                if (it.isPresent) {
                    deviceService.updateDevice(device)
                    ResponseEntity(messageJson(200, "Device $it updated"), HttpStatus.OK)
                } else {
                    ResponseEntity(messageJson(404, "Device $it not found"), HttpStatus.NOT_FOUND)
                }
            }
    }

    @GetMapping("")
    fun allDevice(): ResponseEntity<*> {
        return deviceService.allDevice().let {
            if (!it.isEmpty()) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{id}")
    fun getDeviceById(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        return deviceService.getDeviceById(id).let {
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("find_by_os")
    fun getAllByOs(@RequestBody os: OS): ResponseEntity<*> {
        return osService.getOsById(os.id).let {
            if (it.isPresent) ResponseEntity(
                deviceService.getAllByOs(os),
                HttpStatus.OK
            ) else ResponseEntity(
                messageJson(
                    404,
                    "OS $it not found"
                ), HttpStatus.NOT_FOUND
            )
        }
    }
}