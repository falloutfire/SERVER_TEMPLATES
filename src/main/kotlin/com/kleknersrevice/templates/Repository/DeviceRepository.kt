package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.Device
import com.kleknersrevice.templates.Entity.OS
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface DeviceRepository : JpaRepository<Device, Long> {

    fun getAllDeviceByOs(os: OS): List<Device>
    @Query(
        "select d from Device d where d.name = :name and d.os = :os and d.camDiafragma = :camDiafragma and " +
                "d.mp = :mp and d.focus = :focus and d.stabilization = :stabilization"
    )
    fun findDevice(
        @Param("name") name: String, @Param("os") os: OS, @Param("camDiafragma") camDiafragma: Double,
        @Param("mp") mp: Long, @Param("focus") focus: Double, @Param("stabilization") stabilization: Boolean
    ): Optional<Device>
}