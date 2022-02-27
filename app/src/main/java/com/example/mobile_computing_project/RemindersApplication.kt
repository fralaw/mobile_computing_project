package com.example.mobile_computing_project

import android.app.Application
import com.example.mobile_computing_project.util.Graph

class RemindersApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        Graph.provide(this)

    }
}