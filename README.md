# Watched - Your anime:tv: and manga:book: tracking app

Watched is a work-in-progress anime and manga tracking Android app using the [myanimelist](https://myanimelist.net/) API.

<img src="docs\images\watched_main_screens.png" alt="Anime list and anime detail screens" style="zoom:20%;" />

It aims to be a fully functional application, but also a showcase of modern Android development and a playground to try new patterns and libraries.

### The stack

- Full Kotlin!
- Single Activity
- Day/Night theme
- Multi-modules
- Common libraries: Architecture components ViewModel and LiveData, Retrofit/OkHttp, RxJava2, Glide.

### TODOs

As an ever evolving project, here's some of the planned improvements:

- Add possibility to update an anime rating and to add an anime to our watchlist
- Implement a Settings screen, allowing the user to disconnect, customize the app theme and choose the anime title language
- Updated to the Paging 3.X version (from 2.X)
- Migrate to coroutines (from RxJava 2)
- Migrate to kotlinx.serialization (from Moshi)
- Offline mode
- Properly manage authentication tokens
- More tests
- Configure a nice CI