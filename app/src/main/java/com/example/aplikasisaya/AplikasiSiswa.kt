package com.example.aplikasisaya

import android.app.Application
import com.example.aplikasisaya.repositori.ContainerApp
import com.example.aplikasisaya.repositori.ContainerDataApp

class AplikasiSiswa : Application() {
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}