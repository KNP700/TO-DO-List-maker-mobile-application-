plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // id("com.example.todolist") version "7.3.0" apply false
//    alias(libs.plugins.gms.google.services)apply false
    id("com.google.gms.google-services") version "4.4.4" apply false
}