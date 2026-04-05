# CLAUDE.md

## Project

AgenticMigrationDemo — Android app for demonstrating sequential migration: Java/MVP/XML → Kotlin → MVVM → Jetpack Compose.

Package: `com.gerigorila.agenticmigrationdemo`
Min SDK 24, Target SDK 36, single-activity with fragment navigation.

## Source Structure

```
app/src/main/java/com/gerigorila/agenticmigrationdemo/
├── domain/model/Product.java
├── data/repository/ProductRepository.java
├── presentation/
│   ├── home/           HomeContract, HomePresenter, HomeFragment
│   ├── productlist/    ProductListContract, ProductListPresenter, ProductListFragment, ProductAdapter
│   └── productdetail/  ProductDetailContract, ProductDetailPresenter, ProductDetailFragment
└── MainActivity.java
```

## Build

```bash
./gradlew assembleDebug
```

## Migration Order

Each step is a separate branch. Migrate only one concern per step:

1. `kotlin-migration` from `legacy` — Java→Kotlin. Keep MVP, keep XML.
2. `mvvm-migration` from `kotlin-migration` — MVP→MVVM. Keep XML.
3. `compose-migration` from `mvvm-migration` — XML→Compose.

See `.github/kotlinmigration/SKILL.md`, `.github/mvvmmigration/SKILL.md`, `.github/composemigration/SKILL.md` for per-step instructions.

## Rules

- Do not add DI frameworks
- Do not add network/database layers
- Do not add comments explaining obvious code
- Do not change concerns outside the current migration step
- Each commit must leave the app compilable and runnable
- Match existing code patterns
- 4-space indent, no wildcard imports
