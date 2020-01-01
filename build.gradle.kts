// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.github.ben-manes.versions") version "0.27.0" // Dependencies version checker
}

buildscript {
    apply(from = ("dependencies.gradle"))

    repositories {
        google()
        jcenter()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://maven.fabric.io/public") }
    }
    dependencies {
        // NOTE: Do not place your application dependencies here; they belong in the individual module build.gradle
        // files
        classpath("com.android.tools.build:gradle:3.5.3")
        classpath(kotlin("gradle-plugin", version = Versions.kotlin))
        classpath("com.google.gms:google-services:4.3.3")
        classpath("io.fabric.tools:gradle:1.31.2") // Crashlytics plugin
        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.1.1") // klint
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}