package com.example.photodemo.di.module

import com.example.photodemo.BuildConfig
import com.example.photodemo.data.repository.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        MainRepository(BuildConfig.UNSPLASH_API_KEYS,get())
    }
}