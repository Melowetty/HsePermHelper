package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.model.Group

interface GroupService {
    fun save(group: Group): Group
}