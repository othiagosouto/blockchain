# BlockChain App
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d7f6d867299744d69c6181a4e5789f25)](https://app.codacy.com/gh/othiagosouto/blockchain?utm_source=github.com&utm_medium=referral&utm_content=othiagosouto/blockchain&utm_campaign=Badge_Grade_Settings)

<img src=".github/images/app_logo.svg"
 alt="BlockChainApp logo" align="right" width="100" height="100" />

An Android app that uses [Blockchain Charts & Statistics API](https://www.blockchain.com/api/charts_api) to experiment android libraries and different architectures.

## Architecture
This app is being developed using MVVM with clean architecture concepts.

## Tech stack
- Koin for dependency injection
- [Java Platform Plugin](https://docs.gradle.org/current/userguide/java_platform_plugin.html) to manage dependencies between modules.
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) as chart library
- Github actions for CI
- Jetpack libraries(ViewModel, Activity)
- Coroutines + retrofit for http requests
- Kotlinx.Serialization for JSON handling
- Detekt & formatting(ktlint) for static analysis

### Testing stack
- Activity Scenario for unit and instrumented testing
- robolectric for unit testing
- Espresso assertions for UI testing
- [Screenshot Tests for Android](https://github.com/facebook/screenshot-tests-for-android) for chart testing
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) for API and integration testing

### CI
The CI machine run the following steps:
1. detekt & formatting (ktlint)
2. unit tests
3. instrumented testing
4. snapshot tests

## Quick Start

### How to run?
This project is being built using Android studio 4.1.3, so, I do recommend install the latest stable version.

### Running from CLI
`./gradlew test` will run all unit testing
`./gradlew detekt` will run detekt
`./gradlew connectedVariantNameAndroidTest` will run instrumented tests

## Snapshot testing
To run snapshot testing you need:
1. install `python 2.7`
2. `python-pillow`
3. `ANDROID_HOME` mapping your sdk path
4. Run snapshot testing using Pixel 3 device API 27

`./gradlew verifyDebugAndroidTestScreenshotTest` to run snapshot testing

### Thanks
App icon Blockchain by [Andrejs Kirma](https://thenounproject.com/andrejs/) from [the Noun Project](https://thenounproject.com/search/?q=blockchain&i=3863938)
Error Icon  made by [Smashicons](https://www.flaticon.com/authors/smashicons) available at [Flaticon](https://www.flaticon.com/)
