package com.klar.shared.di

import com.klar.shared.data.InMemoryLessonRepository
import com.klar.shared.data.Lesson
import com.klar.shared.data.LessonRepository
import com.klar.shared.viewmodel.LessonViewModel
import com.klar.shared.viewmodel.PathViewModel
import org.koin.dsl.module

val appModule = module {
    // Repository
    single<LessonRepository> { InMemoryLessonRepository() }

    // ViewModels
    factory { PathViewModel(get()) }
    factory { (lesson: Lesson) -> LessonViewModel(lesson) }
}
