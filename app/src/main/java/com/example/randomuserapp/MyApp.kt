package com.example.randomuserapp

import android.app.Application
import com.example.randomuserapp.data.local.AppDatabase

class MyApp : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
}