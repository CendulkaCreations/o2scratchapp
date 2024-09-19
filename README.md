# Introduction
This repository contains the implementation of a Scratch Card App. The project is structured following a clean architecture pattern and is built using Jetpack Compose for the UI and Kotlin for the business logic.

# Project Structure
The project is organized into several layers, each responsible for a different concern:

## Data Layer
Handles data fetching from both local and remote sources.

### Local:
* **CardDataStore** - Manages local data storage using Jetpack DataStore.
### Remote:
* **ScratchCardApi** - Interface for API calls to activate the scratch card.
* **CardResponseDto** - DTO for converting API responses to domain models.
* **ScratchCardRepositoryImpl** - Implements the data fetching logic and integrates both local and remote sources.

## Domain Layer
Contains business logic and the core models of the app. For simplicity, no use-cases have been used.

* **ScratchCardModel** - Defines the structure of a scratch card in the domain layer. For simplicity, it's shared also in the presentation layer
* Repository Interface **ScratchCardRepository** - Abstracts data operations, implemented by the ScratchCardRepositoryImpl.

## Presentation Layer
Handles UI components and state management. Navigation is configured by routes in **ScreenRoute**.

### Home Screen
* **HomeScreen** - The main screen displaying user interactions.
* **HomeViewModel** - Manages state and logic for the home screen

### Scratch Screen
* **ScratchCardScreen** - Screen where the scratch card's code is presented
* **ScratchCardState** - Defines the state for the scratch process such as loading state, generated a code
* **ScratchCardViewModel** - Handles the state and logic for the scratch card screen

### Activation
* **ActivationScreen** - Screen for the scratch actication
* **ActivationState** - Defines the state for the activation process such as loading state, error or success.
* **ActivationViewModel** - Manages the logic for activation, including the state and events.

# Technologies Used
* **Kotlin**: Primary language for logic and UI.
* **Jetpack Compose**: Declarative UI framework for Android.
* **Hilt**: Dependency injection framework.
* **Jetpack DataStore**: Local storage solution.
* **Retrofit**: HTTP client for API calls.
