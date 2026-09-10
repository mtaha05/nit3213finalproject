# NIT3213 Final Project

Android application developed for the NIT3213 Mobile Application Development final assessment.

## Overview

The application demonstrates Android development concepts including API integration, fragment navigation, RecyclerView, dependency injection with Hilt, ViewModels, StateFlow, Retrofit networking, and unit testing.

The application uses a single-activity architecture with multiple fragments.

## Features

- User authentication through the NIT3213 API
- Login error handling and loading state
- Dashboard data retrieved using the login keypass
- RecyclerView displaying entity summaries
- Entity descriptions excluded from Dashboard items
- Clickable Dashboard items
- Details screen displaying all entity information
- Fragment-based navigation
- Dependency injection using Hilt
- Unit tests for LoginViewModel and DashboardViewModel

## Architecture

The application follows a single Activity with multiple Fragments:

MainActivity
- LoginFragment
- DashboardFragment
- DetailsFragment

The application also separates responsibilities into:

- API service
- Data models
- Repositories
- ViewModels
- UI state classes
- Hilt dependency injection modules

## Technologies Used

- Kotlin
- Android XML Views
- Android Fragments
- Navigation Component
- RecyclerView
- View Binding
- ViewModel
- StateFlow
- Kotlin Coroutines
- Retrofit
- Moshi
- OkHttp
- Hilt
- JUnit
- MockK
- kotlinx-coroutines-test

## API

Base URL:

https://nit3213apinew.onrender.com/

Authentication endpoint:

POST /footscray/auth

Dashboard endpoint:

GET /dashboard/{keypass}

The keypass returned after successful authentication is used to retrieve the Dashboard data.

## Build and Run Instructions

1. Clone the repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync and download the required dependencies.
4. Ensure JDK 17 is configured for Gradle.
5. Start an Android emulator or connect an Android device.
6. Run the `app` configuration.
7. Enter valid NIT3213 login credentials on the Login screen.

Minimum supported Android SDK: API 24.

## Unit Testing

Local unit tests are located under:

app/src/test/java/

The project contains tests for:

- LoginViewModel
  - Empty credential validation
  - Successful authentication
  - Failed authentication

- DashboardViewModel
  - Successful Dashboard response
  - Failed Dashboard request

Tests use JUnit, MockK, and kotlinx-coroutines-test.

To run the unit tests in Android Studio, right-click the test class or test directory and select Run.

## Project Structure

app/src/main/java/com/vu/nit3213finalproject/

- data/api
- data/model
- data/repository
- di
- ui/login
- ui/dashboard
- ui/details

## Version Control

Git was used throughout development with separate commits for major project milestones and features.