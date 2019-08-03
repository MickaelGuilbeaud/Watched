# Template

This template is both a project best practice showcase and a base to kickstart a new project faster.

## Overview

### Libraries

### Gradle

An env product flavor is present on top of the default debug and release build types. Its primary use is to define the DOMAIN_URL variable for switching the back-end domain between prod, preprod and dev.

### Modules

#### Core module

The core module gathers base code and utility classes. It doesn't contain any business logic and basically it's code that can be copy-pasted in any project.

#### Data module

The data module only goal is to retrieve, gather and manipulate data that are hused by features. 

### Base classes

A BaseActivity and BaseFragment are implemented with the added benefit to automatically log the most interesting lifecycle events. Child activities and fragments just have to provide the logsTag variable.

### Setup

#### Crash reporting

#### Logging

Logging is done using Timber. It is initialized to only print logs in debug builds, preventing logs in release versions.

### Codestyle

The project code style is following the official [Kotlin coding convention](https://kotlinlang.org/docs/reference/coding-conventions.html) and is enforced by [klint](https://github.com/pinterest/ktlint). klint adds the ./gradlew klintcheck and ./gradlew klintformat Gradle tasks for finding and fixing formatting errors, see [Goobar.io](https://goobar.io/2019/07/25/adding-ktlint-to-your-kotlin-project/) tutorial for more informations.

## How to start a new project using Template

### Renaming

Basically everything called Template should be renamed with the real project name. It includes [TODO]