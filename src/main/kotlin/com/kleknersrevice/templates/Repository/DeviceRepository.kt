package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.Device
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DeviceRepository : JpaRepository<Device, Long> {

    fun findDeviceByNameAndFocusAndMpAndResolutionAndStabilization(
        name: String,
        focus: Double,
        mp: Long,
        resolution: Double,
        stabilization: Boolean
    ): Optional<Device>
}
