package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.OS
import java.util.*

interface OsService {
    fun addOs(os: OS)
    fun deleteOs(id: Long)
    fun updateOs(os: OS)
    fun allOs(): List<OS>
    fun getOsById(id: Long): Optional<OS>
    fun getOs(os: OS): Optional<OS>
}