package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.Device
import com.kleknersrevice.templates.Entity.OS
import java.util.*

interface DeviceService {
    fun addDevice(device: Device)
    fun deleteDevice(id: Long)
    fun updateDevice(device: Device)
    fun allDevice(): List<Device>
    fun getDeviceById(id: Long): Optional<Device>
    fun getAllByOs(os: OS): List<Device>
    fun findDevice(device: Device): Optional<Device>
}