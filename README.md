# Overview
This repository contains a source code for sample client Android application. The main focus is to showcase the architecture approach that author commonly uses during Android Software Development.
App is based on [Poke API](https://pokeapi.co/) and communicates via REST with PokeAPI service;

Please refer to [Architecture Overview](#architecture-overview) section for more architecture details.

Please note that this is not and official sample app.

**Features**

- Paginated list of Pokemons;
- Details screen with image and species;
- Portrait/landscape support;
- Data is persistent during configuration changes;
- Total Pokemons count;
- Loading states as the user scrolls up/down to load more items;
- Network connectivity validation;
- Network connectivity text on both (list and details);
- Dynamic background for image and species at Details screen;
- Long press on image action: shake animation and haptics feedback (vibration);
- Dark Mode Support.

Please check `screenshots` folder for better understanding how these features look like.

### Requirements
In order to import, sync and build Android application you must meet the following requirements:

**Your PC's local settings:**
1. At least Java 11 OpenJDK installed and configured;
2. Latest [Android Studio](https://developer.android.com/studio) installed;
3. At least Android SDK of API level 32 is downloaded;
4. Android Emulator is created or physical device is connected.

### Project importing

To import the project follow instructions below:

1. Clone this repository;
2. Close active project in Android Studio if it is open;
3. Select `File` -> `Open` -> Navigate to `poke-android-client/app` and select `build.gradle`;
4. Wait until Android Studio synchronizes the project, Gradle Plugin, and all dependencies successfully. It will take some time.
5. Build the project.

If all the steps pass successfully, you can proceed to [How to run the application](#how-to-run-the-application) section. Otherwise, check [troubleshooting](#troubleshooting) section.

### Architecture Overview

Project is implemented on top of Clean Architecture and MVVM pattern. Project structure is organized by layers (android libraries):
- app
- core:
  - data
  - system
  - ui
- domain (business layer)
- features:
  - pokemons
  - pokemondetails

The multi-module structure provides a clear separation of concerns as well as decouples UI from business and data layers. Each feature includes only the components it needs by adding required dependencies.

**Unit tests**

Data layer contains Unit Tests which cover Retrofit's API Service with requests.
Navigate to core/data/src/test to view and run tests on your machine.

### Technologies/libraries

- Kotlin
- Kotlin Coroutines/Flow
- Hilt for dependency injection
- Paging 3 library
- Retrofit/OkHttp, Gson
- AndroidX tools
- Ktx extensions
- Glide for image processing
- MockWebServer for testing API calls

### Troubleshooting

- If you import the project in other way than using `app/build.gradle` file, it will fail to finish successfully. You'll need to clone this repository again;
- In case your Java SDK does not meet the requirements above, Gradle Plugin will fail to import all the dependencies, especially those that use newer versions of Gradle plugins.

### How to run the application

The project is already configured to run the app in release mode. 

1. Navigate to `Build Variants` and select `release` configuration for `app` module. It will be applied automatically to all other modules.
2. Select `Run` and wait until build finishes and .apk file is installed.
3. Play with the app.

### Additional info

This repository contains `release.keystore` file and `signingConfigs` with release keys for testing purposes.
Please note that uploading app's keys and keystore file into the repository is absolutely not recommended for typical Android apps and must be avoided.