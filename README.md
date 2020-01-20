# Template

### Overview

Template is a mix of 3 goals:

- A showcase of some best practices and solutions to common problems. I can use it as a reminder of how I solved some problems, but it can be used as a support material during exchanges to other developers.
- A playground for me to test new patterns and libraries. The Android ecosystem evolves quickly so it's important to stay up-to-date with the industry trends.
- A template to start a new project faster. Set up and configuration is rarely the most interesting part of a project, and it can takes quite some times.

### To Do

As an always in-progress project, here's a short list of topics I want to explore or add:

- Refactor the PokemonStore. Two improvements can be done: the in-memory cache is redundant with the database, and the pagination from the DB data is overkill and actually hurting the UX as even when all the data is available in DB, it still needs to be loaded per page
- Unit tests for the PokedexViewModel and the PokemonStore
- Integration tests for the Pokemon feature, trying MockWebServer in the process
- Convert all gradle files to Kotlin DSL -> On hold, after playing with the Kotlin DSL it doesn't feel like it's ready for production yet.
- Work on a CI, probably Bitrise or Github actions, that build and run tests of the project. It's also a nice opportunity to implement a visual QA
- Rename builds based on the build variant
- Use a custom font

## Template as a... template

### Architecture

#### Modules

#### Dependency injection

#### Single Activity

I think that the two most viable way to build an app is as a Single Activity or only Activities (without Fragments). An app that is half-way between these two philosophies has only downsides without any upside: navigation is harder, factorized behaviors in a BaseActivity or BaseFragment are duplicated, and for each new screen we should choose between making it with an Activity or a Fragment.

Creating an app using the Single Activity pattern is enjoyable and is the recommended way by Google, so it's the one I choose. It's worth mentioning that there is valid reasons to have more than one Activity in a Single Activity app, but it should be an exception and be done after thinking about the advantages and disadvantages.

#### MVVM as the UI design pattern

The MVVM design pattern fits nicely in a reactive architecture, i.e. an architecture that revolves around the [Observer pattern](https://en.wikipedia.org/wiki/Observer_pattern). When a piece of data is updated somewhere in the app, the UI will be notified of the changes automatically and be able to react. It helps keeping the UI up-to-date with the app state and simplify communication between the architecture layers. Luckily Google provides everything we need to implement the MVVM design pattern with the Architecture components [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) and [LiveData](https://developer.android.com/topic/libraries/architecture/livedata).

A ViewModel implementation I enjoy is one with 3 outgoings LiveData: one for *view states*, one for *navigation events* and one for *action events*. A **view state** is a data class containing all UI data of the screen and comes with the main benefit of preventing inconsistent UIs that can happens when the view observes many observables to build it's UI. A **navigation event** is an event representing the possible exiting navigations from this screen, most commonly implemented as a sealed class. An event wrapper (taken from [this android developers blog post](https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150)) is used to avoid consuming it multiple times when the view re-subscribe to the LiveData or if there is more than one observer. Finally an **action event** is very similar to a navigation event in that it's often a sealed class and it comes with the same event wrapper. It is used often for showing success or error messages.

### UI

#### Dark theme

Even if the dark theme concept is relatively new users expect all apps to have one. Luckily inheriting from a *Theme.MaterialComponents.DayNight* theme is a great starting point and is doing a lot of work for us.

#### Branded splash screen

Most applications have a branded splash screen displaying their brand logo. This feature is available in Template with the **Theme.Template.Splash** theme that is set to the Activity in the manifest, then the **Theme.Template** is applied in the Activity *onCreate* method once it's launched to avoid keeping the brand logo on screen. This [blog post](https://android.jlelse.eu/revisited-a-guide-on-splash-screen-in-android-in-2020-bbcd4bb1ce42) gives more details and alternatives to implement a nice splash screen.

#### Glide

### Gradle

#### Dependencies

Project and dependencies versions are factorized in a *Dependencies* file inside the *buildSrc* folder, as recommended in the [Gradle documentation](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources). It includes Android compile/min/target sdk versions, Java version and dependencies.
The main benefit of this approach is to centralize versions in a single place, avoiding having to edit every module *build.gradle* file each time a new version is available, and ensuring that all modules use the same versions.

#### Resources optimization

Removing of unused alternatives resources: Adding a **resConfigs** property in the app *build.gradle* file removes resources from unused locales, reducing the final apk size. See the [Android developer documentation](https://developer.android.com/studio/build/shrink-code#unused-alt-resources) for more details.

### Tests

### Quality

#### Codestyle

The project code style is following the official [Kotlin coding convention](https://kotlinlang.org/docs/reference/coding-conventions.html) and is enforced by [klint](https://github.com/pinterest/ktlint). klint adds the ./gradlew klintcheck and ./gradlew klintformat Gradle tasks for finding and fixing formatting errors, see [Goobar.io](https://goobar.io/2019/07/25/adding-ktlint-to-your-kotlin-project/) tutorial for more informations.

#### Crash reporting

Template uses Firebase Crashlytics library for crash reporting. It is disabled for dev builds through a flag in the dev AndroidManifest to avoid getting spammed by Firebase crash emails.

#### Memory leaks detection

[LeakCanary](https://square.github.io/leakcanary/) is an awesome library for detecting memory leaks, and adding the dependency is enough to start using it. It is enabled only for <u>debug</u> builds so memory leaks are detected sooner during development / QA phases and there is no risk that this dependency has a negative impact to the users with release builds.

### Libraries

Template uses some of the most widespread libraries: AAC ViewModel, LiveData, Retrofit, OkHttp, Room, Moshi, Glide, RxJava2, Firebase Crashlytics. As they are so commonly used a lot of documentation is available and there is a high chance that a new developer joining the project will be able to quickly add value.

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

#### Logging

Logging is done using Timber. It is initialized to only print logs in debug builds, preventing logs in release versions.
TemplateExceptions, an Exception subclass, are logged in Crashlytics as non-fatal crashes so we still have crash insight.
Finally logs are forwarded to Crashlytics for non-dev builds in order to get more informations about crashes.

## How to start a new project using Template

### Packages

The base package used by Template is mg.template, it can and should be replaced by your app package.

### Renaming

Basically everything called Template should be renamed with the real project name. It includes the TemplateApplication, TemplateException[TODO]

### Firebase

In order to use Firebase in Template, a google-services.json file should be put in the app/ folder and projects should be created for your.package, your.package.preprod and your.package.dev. If you are not planning to use Firebase simply remove the dependencies and their use in code.