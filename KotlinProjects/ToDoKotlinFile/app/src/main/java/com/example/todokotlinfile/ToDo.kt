package com.example.todokotlinfile

import java.io.Serializable
import java.util.*

class ToDo(name: String?, description: String?) : Serializable {
    var id: Int
    var name: String?
    var description: String?
    var date: Date?



    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val toDo = o as ToDo
        if (id != toDo.id) return false
        if (if (name != null) name != toDo.name else toDo.name != null) return false
        if (if (description != null) description != toDo.description else toDo.description != null) return false
        return if (date != null) date == toDo.date else toDo.date == null
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + if (name != null) name.hashCode() else 0
        result = 31 * result + if (description != null) description.hashCode() else 0
        result = 31 * result + if (date != null) date.hashCode() else 0
        return result
    }

    init {
        id = UUID.randomUUID().hashCode()
        this.name = name
        this.description = description
        date = Date()
    }
}