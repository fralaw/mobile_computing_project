package com.example.mobile_computing_project

import android.app.Application
import android.os.Bundle

class RemindersApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        Graph.provide(this)

    }
}