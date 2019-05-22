package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.Device
import com.kleknersrevice.templates.Repository.DeviceRepository
import com.kleknersrevice.templates.Service.DeviceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class DeviceServiceImpl(private val deviceRepository: DeviceRepository) : DeviceService {

    override fun findDevice(device: Device): Optional<Device> {
        return deviceRepository.findDeviceByNameAndFocusAndMpAndResolutionAndStabilization(
            device.name,
            device.focus,
            device.mp,
            device.resolution,
            device.stabilization
        )
    }

    override fun addDevice(device: Device) {
        deviceRepository.saveAndFlush(device)
    }

    override fun deleteDevice(id: Long) {
        deviceRepository.findById(id).let {
            if (it.isPresent) {
                deviceRepository.deleteById(id)
            }
        }
    }

    override fun updateDevice(device: Device) {
        deviceRepository.saveAndFlush(device)
    }

    override fun allDevice(): List<Device> {
        return deviceRepository.findAll()
    }

    override fun getDeviceById(id: Long): Optional<Device> {
        return deviceRepository.findById(id)
    }

    /*override fun getAllByOs(os: OS): List<Device> {
        return deviceRepository.getAllDeviceByOs(os)
    }*/
}
