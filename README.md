# Android Project with Clean Architecture and Multimodule

This Android project is structured with clean architecture and consists of two main branches:

- **master**: The version without multimodule.
- **multi-module-version**: The version with multimodule setup.

## Project Architecture

The project follows the **Clean Architecture** principles, organized into three main layers:

### 1. Domain Layer
- **Domain** is the core of the app and contains the business logic.
- It is independent of other layers.
- **No dependencies** from other layers.
  
### 2. Data Layer
- **Data** is responsible for managing the data sources (remote, local, etc.).
- It depends on the **domain** layer but not on the **presentation** layer.

### 3. Presentation Layer
- **Presentation** handles the user interface and interacts with the domain via the UI.
- It uses **Jetpack Compose** for UI components.
- It depends on the **domain** layer but not on the **data** layer.

### Dependency Injection (DI) Bridge Module
- A **DI bridge module** is used to manage dependency injection between modules.
- This avoids exposing the full data and domain modules to the app module.
- **Koin** is used for Dependency Injection.

### Architecture Overview

```mermaid
graph TD;
    App[App Module] --> D[DI Bridge Module];
    D --> A[Presentation Module];
    D --> C[Data Module];
    A[Presentation Module] --> B[Domain Module];
    C[Data Module] --> B[Domain Module];
