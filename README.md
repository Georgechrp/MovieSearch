🎬 MovieSearch App

Android Developer Assignment – Demo app using The Movie Database (TMDB) API
A modern Android app built for the technical assessment, following clean architecture with Repository pattern, Hilt DI, and 100% Jetpack Compose UI.

📌 Features

- Search movies by keyword using TMDB public API

- Discover movies by genre (e.g. Action, Comedy, Sci-Fi)

- List view with title, poster and short description

- Details screen with poster, rating, vote count, and full description

- Navigation using Jetpack Navigation Compose

- Clean architecture with Repository & ViewModel layers

- Dependency Injection with Hilt

- Pagination support (loads more movies when scrolling)

- Compatible with Android SDK 21+

🚫 Not Implemented
- Unit tests / UI tests 
- Offline caching / Room database – networking only, no persistence

⚙️ Tech Stack

- Kotlin

- Jetpack Compose – UI

- Hilt (Dagger) – Dependency Injection

- Retrofit + Moshi – Network calls & JSON parsing

- OkHttp Interceptors – Authentication + logging

- Coil – Image loading

- Coroutines + ViewModel – Asynchronous data fetching & state mgmt.
```text
📂 Project Structure
com.christopoulos.moviesearch
 ├── data
 │   ├── remote        # DTOs, API definition (Retrofit), toDomain mappers
 │   └── repository    # Repository interface + implementation
 │
 ├── di                # Hilt Modules (Network, Repository)
 │
 ├── domain
 │   └── model         # Core domain models (Movie, MovieGenre)
 │
 ├── presentation
 │   ├── navigation    # App destinations & navigation graphs
 │   ├── ui
 │   │   ├── main      # Main Activity
 │   │   ├── movies_list      # Search list screen + ViewModel
 │   │   ├── movie_details    # Movie details screen + ViewModel
 │   │   └── splash    # Splash Screen
 │
 └── MovieSearchApp.kt  # Hilt Android Application
```

🚀 Setup Instructions
Clone the project
git clone https://github.com/Georgechrp/MovieSearch

- Open in Android Studio 2025.1.2
- Build & Run

- Target SDK: 35

- Min SDK: 24

- Works on emulator & physical devices.

API Key

The TMDB API key is already included for testing:
2810b46c0fe82e2e7eb43466581d495f

You can replace it in NetworkModule.kt if needed.

👉 Check the presentation (includes screenshots, .apk file to download, library & plugin versions)  [here](https://georgioschristopoulos.info/moviesearchPresentation/index.html)


