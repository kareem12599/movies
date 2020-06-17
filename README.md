# Movies Browser 

- A master details sample app display a large list of movies back to 2009 until 2018 catgorized by year. Details screen displays movie details beside a grid list of picture of the movies loaded from flickr picture search API (https://www.flickr.com/services/api/explore/flickr.photos.search) 

- With a search view you can search for any movies in the list. 
- The app follows the architecture recommended in the ["Guide to app architecture"](https://developer.android.com/jetpack/docs/guide) ., with benefits of Paging 3.0 library with room 
-  Using `Room` DataBase as a single source of truth to `MoviePagingSource` which loads data asynchronously
- `MovieRepository` exposes `Flow` that emits  Flow<PagingData> based on a configuration and a function that defines how to instantiate the PagingSource.

- The `MainFragment` observes the changed PagingData and uses the `MovieAdapter` to update the UI 

- `lifecycleScope` responsible for canceling the request when the activity is recreated and `MainFragment` hold a reference to a new `Job` that will be cancelled every time we search for a new query.

# Libraries

- [Android Architecture Component](https://developer.android.com/jetpack/docs/getting-started) 
- [Kotin Coroutine] (https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html)
- [Dagger 2] (https://dagger.dev/dev-guide/)
- [Retrofit] (https://square.github.io/retrofit/)
- [Glide] (https://github.com/bumptech/glide)
