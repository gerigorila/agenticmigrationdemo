# AgenticMigrationDemo

Android app demonstrating a sequential migration from **Java/MVP/XML** to **Kotlin/MVVM/Jetpack Compose**, with the entire migration process assisted by AI tools.

## What This Project Demonstrates

A simple 3-screen product catalog app (Home → Product List → Product Detail) that exists across 5 branches, each representing a stage in a real migration pipeline:

| Branch | Language | Architecture | UI |
|--------|----------|-------------|-----|
| `legacy` | Java | MVP | XML + Fragments |
| `kotlin-migration` | Kotlin | MVP | XML + Fragments |
| `mvvm-migration` | Kotlin | MVVM | XML + Fragments |
| `compose-migration` | Kotlin | MVVM | Jetpack Compose |
| `master` | Kotlin | MVVM | Jetpack Compose |

Each branch builds on the previous one. Every commit compiles and runs.

## Project Structure

```
app/src/main/java/com/gerigorila/agenticmigrationdemo/
├── domain/model/          Product data class
├── data/repository/       ProductRepository (hardcoded data)
├── presentation/
│   ├── home/              Home screen
│   ├── productlist/       Product list with ViewModel
│   ├── productdetail/     Product detail with ViewModel
│   ├── navigation/        Compose NavHost
│   └── theme/             Material3 theme
└── MainActivity.kt
```

## AI-Assisted Development Process

### Tools Used

- **Claude Code** (Claude Opus) — performed all migrations, generated instruction files
- **Custom instruction files** — `.github/copilot-instructions.md`, `CLAUDE.md`, and per-step `SKILL.md` files for consistent, repeatable migrations

### Instruction Files

| File | Purpose |
|------|---------|
| `.github/copilot-instructions.md` | Project context, code style, migration roadmap for GitHub Copilot |
| `CLAUDE.md` | Project context and rules for Claude Code |
| `.github/kotlinmigration/SKILL.md` | Java→Kotlin conversion patterns, migration order, gotchas |
| `.github/mvvmmigration/SKILL.md` | MVP→MVVM conversion patterns, ViewModel examples |
| `.github/composemigration/SKILL.md` | XML→Compose patterns, LazyColumn, NavHost setup |

Each SKILL.md contains:
- **Scope boundaries** — what to change, what NOT to change
- **Migration checklist** — file-by-file order
- **Before/after code patterns** — concrete examples
- **Dependency changes** — what to add to build.gradle
- **Gotchas** — common pitfalls

### How AI Accelerated the Work

**Pattern translation** — AI was most effective at repetitive structural transformations:
- Java POJO → Kotlin data class
- MVP Contract + Presenter → ViewModel + StateFlow
- XML layout + Fragment → @Composable function
- RecyclerView + Adapter → LazyColumn
- Fragment navigation → Compose NavHost

**Custom instructions improved consistency** — Without SKILL.md files, AI produces varied output. With explicit scope, patterns, and constraints, every migration step followed the same conventions.

**Where human review mattered:**
- Build configuration (AGP 9.x bundles Kotlin natively — no separate plugin needed, which AI initially got wrong)
- Compose compiler plugin setup
- Lifecycle-aware collection (`collectAsStateWithLifecycle` vs `collectAsState`)
- Navigation architecture (callback lambdas vs passing NavController)

### Key Findings

1. **Instruction files are the highest-leverage investment.** A well-written SKILL.md with before/after examples produces dramatically better AI output than ad-hoc prompting.
2. **Scope constraints prevent AI from overreaching.** "Do NOT change XML layouts" during Kotlin migration keeps each step focused.
3. **Migration order matters.** Bottom-up (model → repository → contracts → presenters → fragments) avoids compilation errors between steps.
4. **AI struggles with build tooling.** Plugin versions, Gradle catalog syntax, and AGP version-specific behavior required manual intervention.

## Build

```bash
./gradlew assembleDebug
```

Min SDK 24, Target SDK 36, AGP 9.1.0, Gradle 9.3.1.

## Branches Walkthrough

### `legacy` — Starting point
Java, MVP pattern, XML layouts. Each screen has a Contract interface (defining View and Presenter methods), a Presenter class, and a Fragment.

### `kotlin-migration` — Java → Kotlin
Converted all `.java` files to `.kt` using Kotlin idioms: data classes, null safety, lambdas, scope functions. MVP architecture and XML layouts unchanged.

### `mvvm-migration` — MVP → MVVM
Replaced Contract+Presenter pairs with ViewModels exposing StateFlow. Fragments observe state via `repeatOnLifecycle`. Home screen simplified (no ViewModel needed — stateless).

### `compose-migration` / `master` — XML → Compose
Replaced all XML layouts and Fragments with @Composable functions. RecyclerView+Adapter replaced with LazyColumn. Fragment navigation replaced with Compose NavHost. MainActivity now extends ComponentActivity with `setContent`.
