# SKILL: XML to Jetpack Compose Migration

## Scope

Replace XML layouts and Fragment-based navigation with Jetpack Compose UI and Compose Navigation in the `compose-migration` branch.
Code is already Kotlin + MVVM. Do NOT change: ViewModels, Repository, domain model.

## New Dependencies

Add to `gradle/libs.versions.toml`:

```toml
[versions]
composeBom = "2025.06.00"
navigationCompose = "2.9.0"
activityCompose = "1.13.0"

[libraries]
compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
compose-ui = { group = "androidx.compose.ui", name = "ui" }
compose-material3 = { group = "androidx.compose.material3", name = "material3" }
compose-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
compose-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
lifecycle-runtime-compose = { group = "androidx.lifecycle", name = "lifecycle-runtime-compose", version.ref = "lifecycle" }
```

Add to `app/build.gradle.kts`:

```kotlin
android {
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.navigation.compose)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.runtime.compose)
    debugImplementation(libs.compose.ui.tooling)
}
```

Also add compose compiler plugin to `libs.versions.toml` plugins and `build.gradle.kts`:

```toml
[plugins]
compose-compiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
```

```kotlin
// app/build.gradle.kts
plugins {
    alias(libs.plugins.compose.compiler)
}
```

## Migration Steps

### 1. Set up Compose in MainActivity

```kotlin
// BEFORE
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }
}

// AFTER
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgenticMigrationDemoTheme {
                AppNavigation()
            }
        }
    }
}
```

### 2. Create navigation graph

```kotlin
// presentation/navigation/AppNavigation.kt
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("products") { ProductListScreen(navController) }
        composable("product/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            ProductDetailScreen(navController, productId)
        }
    }
}
```

### 3. Convert each screen

#### HomeScreen

```kotlin
// BEFORE: HomeFragment + fragment_home.xml
// AFTER:
@Composable
fun HomeScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // gradient background
        Box(modifier = Modifier.fillMaxSize().background(
            Brush.linearGradient(listOf(Color(0xFF3949AB), Color(0xFF283593), Color(0xFF1A237E)))
        ))
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("AgenticMigrationDemo", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("Product Catalog Demo", color = Color.White, fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
            Button(onClick = { navController.navigate("products") }, modifier = Modifier.padding(top = 48.dp)) {
                Text("Start")
            }
        }
    }
}
```

#### ProductListScreen — RecyclerView → LazyColumn

```kotlin
// BEFORE: RecyclerView + ProductAdapter + item_product.xml
// AFTER:
@Composable
fun ProductListScreen(navController: NavController) {
    val viewModel: ProductListViewModel = viewModel()
    val products by viewModel.products.collectAsStateWithLifecycle()

    Column {
        TopAppBar(title = { Text("Products") })
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            items(products) { product ->
                ProductCard(product) { navController.navigate("product/${product.id}") }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable(onClick = onClick)) {
        Row(modifier = Modifier.padding(12.dp)) {
            Box(modifier = Modifier.size(64.dp).background(Color(0xFFE0E0E0)))
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(product.name, fontWeight = FontWeight.Bold)
                Text(String.format(Locale.US, "$%.2f", product.price), color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
```

#### ProductDetailScreen

```kotlin
@Composable
fun ProductDetailScreen(navController: NavController, productId: Int) {
    val viewModel: ProductDetailViewModel = viewModel()
    LaunchedEffect(productId) { viewModel.loadProduct(productId) }
    val product by viewModel.product.collectAsStateWithLifecycle()

    Column {
        TopAppBar(
            title = { Text(product?.name ?: "") },
            navigationIcon = { IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }}
        )
        product?.let { p ->
            Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp).background(Color(0xFFE0E0E0)))
                Text(p.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
                Text(String.format(Locale.US, "$%.2f", p.price), color = MaterialTheme.colorScheme.primary, fontSize = 20.sp, modifier = Modifier.padding(top = 8.dp))
                Text(p.description, fontSize = 16.sp, modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}
```

### 4. Create Theme

```kotlin
// presentation/theme/Theme.kt
@Composable
fun AgenticMigrationDemoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = dynamicColorScheme() ?: lightColorScheme(),
        content = content
    )
}
```

Or keep it simple with default `MaterialTheme`.

## Files to Delete

- All Fragment classes: `HomeFragment.kt`, `ProductListFragment.kt`, `ProductDetailFragment.kt`
- `ProductAdapter.kt` — replaced by LazyColumn
- All XML layouts: `fragment_home.xml`, `fragment_product_list.xml`, `fragment_product_detail.xml`, `item_product.xml`
- `activity_main.xml` — no longer needed (Compose uses `setContent`)

## Files to Create

- `presentation/navigation/AppNavigation.kt`
- `presentation/home/HomeScreen.kt`
- `presentation/productlist/ProductListScreen.kt`
- `presentation/productdetail/ProductDetailScreen.kt`
- `presentation/theme/Theme.kt` (optional)

## Files to Modify

- `MainActivity.kt` — switch from `AppCompatActivity` + `setContentView` to `ComponentActivity` + `setContent`
- `app/build.gradle.kts` — add Compose dependencies and `buildFeatures { compose = true }`

## Gotchas

- Use `collectAsStateWithLifecycle()` (not `collectAsState()`) — it's lifecycle-aware
- `rememberNavController()` must be created in the composable that hosts `NavHost`
- `viewModel()` requires `implementation("androidx.lifecycle:lifecycle-viewmodel-compose")` — already included via BOM
- Remove `appcompat` dependency if no longer using AppCompat
- The `home_background.xml` drawable can be deleted — replace with Compose `Brush.linearGradient`
- Remove `fragment` dependency from build.gradle — no longer needed
