# AgenticMigrationDemo — Copilot Instructions

## Project

Android app demonstrating sequential migration: Java/MVP/XML → Kotlin → MVVM → Jetpack Compose.
3 screens: Home (splash with Start button), Product List (RecyclerView), Product Detail.

- Package: `com.gerigorila.agenticmigrationdemo`
- Min SDK: 24, Target SDK: 36
- Single-activity, fragment-based navigation

## Architecture

Clean Architecture with 3 layers:

```
domain/model/          — Product POJO (id, name, price, description, imageUrl)
data/repository/       — ProductRepository (hardcoded data, no network/DB)
presentation/
  home/                — HomeContract + HomePresenter + HomeFragment
  productlist/         — ProductListContract + ProductListPresenter + ProductListFragment + ProductAdapter
  productdetail/       — ProductDetailContract + ProductDetailPresenter + ProductDetailFragment
```

## Current Stack (legacy branch)

- Language: Java
- UI: XML layouts with Material3 components
- Pattern: MVP (Contract interface defines View + Presenter, Fragment implements View)
- Navigation: Fragment transactions via `getSupportFragmentManager()`
- No dependency injection, no network, no database

## Code Style

- 4-space indentation
- No wildcard imports
- Fields before constructors before methods
- `final` on fields that don't change
- Method-level Javadoc only when behavior is non-obvious

## Migration Strategy

Migrate one concern at a time, in order:

1. **Java → Kotlin** (`kotlin-migration` branch from `legacy`)
   - Convert all `.java` files to `.kt`
   - Use Kotlin idioms (data class, null safety, lambdas, scope functions)
   - Do NOT change MVP architecture or XML layouts

2. **MVP → MVVM** (`mvvm-migration` branch from `kotlin-migration`)
   - Replace Contract+Presenter with ViewModel+StateFlow
   - Fragments observe ViewModel state
   - Do NOT change XML layouts

3. **XML → Compose** (`compose-migration` branch from `mvvm-migration`)
   - Replace XML layouts with @Composable functions
   - Replace Fragment navigation with Compose Navigation (NavHost)
   - Replace RecyclerView with LazyColumn
   - Remove Fragment classes, use composable screens directly

## Rules for AI-Generated Code

- Match existing patterns in the codebase
- Do not add dependencies unless the migration step requires it
- Do not introduce DI frameworks (Hilt/Dagger) — keep it simple
- Do not add comments explaining obvious code
- Do not add logging or analytics
- Preserve the existing package structure
- Each migration step should produce a compilable, runnable app
