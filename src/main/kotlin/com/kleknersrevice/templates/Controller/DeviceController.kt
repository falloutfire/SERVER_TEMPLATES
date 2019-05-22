package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Controller.ResponseValues.Companion.CREATED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.DELETED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.EXIST
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.NOT_FOUND
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_ADMIN
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_USER
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.SUCCESS
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.UPDATED
import com.kleknersrevice.templates.Entity.Device
import com.kleknersrevice.templates.Service.AuthenticationFacadeService
import com.kleknersrevice.templates.Service.DeviceService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/device")
class DeviceController(
    private val deviceService: DeviceService,
    private val authenticationFacadeService: AuthenticationFacadeService
    /*private val osService: OsService*/
) {

    private val log = LoggerFactory.getLogger(DeviceController::class.java)

    @Secured(ROLE_ADMIN)
    @PostMapping("")
    fun addDevice(@RequestBody device: Device): ApiResponse {
        log.info(
            String.format(
                "received request to add device %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        /*return osService.getOs(device.os).run {
            if (isPresent) {
                deviceService.findDevice(device).let { findDevice ->
                    return if (!findDevice.isPresent) {
                        deviceService.addDevice(device)
                        ApiResponse(HttpStatus.CREATED, "Device $CREATED")
                    } else {
                        ApiResponse(HttpStatus.OK, "Device $EXIST")
                    }
                }
            } else {
                return@run ApiResponse(HttpStatus.NOT_FOUND, "OS $NOT_FOUND")
            }
        }*/
        return deviceService.findDevice(device).run {
            if (!isPresent) {
                deviceService.addDevice(device)
                /*return@run*/ ApiResponse(HttpStatus.CREATED, "Device $CREATED")
            } else {
                /*return@run*/ ApiResponse(HttpStatus.OK, "Device $EXIST")
            }
        }
    }

    @Secured(ROLE_ADMIN)
    @DeleteMapping("/{id}")
    fun deleteDevice(@PathVariable(value = "id") id: Long): ApiResponse {
        log.info(
            String.format(
                "received request to delete device %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return deviceService.getDeviceById(id).run {
            if (isPresent) {
                deviceService.deleteDevice(id)
                ApiResponse(HttpStatus.OK, "Device $DELETED")
            } else ApiResponse(HttpStatus.NOT_FOUND, "Device $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN)
    @PutMapping("")
    fun updateDevice(@RequestBody device: Device): ApiResponse {
        log.info(
            String.format(
                "received request to update device %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return deviceService.getDeviceById(device.id)
            .run {
                if (isPresent) {
                    deviceService.updateDevice(device)
                    ApiResponse(HttpStatus.OK, "Device $UPDATED")
                } else {
                    ApiResponse(HttpStatus.NOT_FOUND, "Device $NOT_FOUND")
                }
            }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("")
    fun allDevice(): ApiResponse {
        log.info(
            String.format(
                "received request to list device %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return deviceService.allDevice().run {
            ApiResponse(HttpStatus.OK, SUCCESS, this)
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/{id}")
    fun getDeviceById(@PathVariable(value = "id") id: Long): ApiResponse {
        log.info(
            String.format(
                "received request to add device %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return deviceService.getDeviceById(id).run {
            if (isPresent) ApiResponse(HttpStatus.OK, SUCCESS, this) else ApiResponse(
                HttpStatus.NOT_FOUND,
                "Device $NOT_FOUND"
            )
        }
    }

    /*@Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/find_by_os")
    fun getAllByOs(@RequestBody os: OS): ApiResponse {
        log.info(
            String.format(
                "received request to get device by id %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return osService.getOsById(os.id).run {
            if (isPresent)
                ApiResponse(HttpStatus.OK, SUCCESS, this)
            else ApiResponse(HttpStatus.NOT_FOUND, "Devices $NOT_FOUND")
        }
    }*/
}
