package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.Device
import com.kleknersrevice.templates.Entity.OS
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceRepository : JpaRepository<Device, Long> {

    //@Query("select d from Device d where d.os = :os")
    fun getAllDeviceByOs(/*@Param("os")*/ os: OS): List<Device>
}