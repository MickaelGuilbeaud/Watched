# Watched - Your anime:tv: and manga:book: tracking app

Watched is a work-in-progress anime and manga tracking Android app using the [myanimelist](https://myanimelist.net/) API.

<img src="docs\images\watched_main_screens.png" alt="Anime list and anime detail screens" style="zoom:20%;" />

It aims to be a fully functional application, but also a showcase of modern Android development and a playground to try new patterns and libraries.

### The stack

- **Kotlin** everywhere! Code, Coroutines and Gradle files (conversion in progress)
- **UI**: Material, Day/Night theme
- **Architecture**: Single Activity, Multi-modules
- **Libraries**: Architecture components ViewModel and LiveData, Retrofit/OkHttp, Moshi, Glide

### Feature TODOs

- Implement a Manga feature with the same capabilities than the Anime feature
- Implement a Settings screen, allowing the user to disconnect, customize the app theme and choose the anime title language
- Anime list: Show rating and watch status, add an empty state
- Log in: Use Autofill so password managers work, and send to the myanimelist website to create an account (or do it in the app?)

### Tech TODOs

- Properly manage authentication tokens
- Improve architecture
- Update to Paging 3.X version (from 2.X)
- Replace LiveData with a Flow equivalent
- Offline mode
- Consider kotlinx.serialization for JSON parsing (actually using Moshi)
- More tests
- Improve the CI