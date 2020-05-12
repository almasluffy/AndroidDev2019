package com.example.todokotlinroom

import java.io.Serializable
import java.util.*

class ToDo(
    var id: Int, //    public void setDate(Date date) {
    var name: String, var description: String
) :
    Serializable {
    val date: Date

    //        this.date = date;
//    }
    init {
        date = Date()
    }
}