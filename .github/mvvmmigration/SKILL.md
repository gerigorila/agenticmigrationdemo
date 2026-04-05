# SKILL: MVP to MVVM Migration

## Scope

Replace MVP pattern (Contract + Presenter) with MVVM (ViewModel + StateFlow) in the `mvvm-migration` branch.
Code is already Kotlin. Do NOT change: XML layouts, navigation (Fragments + transactions), package structure.

## New Dependencies

Add to `gradle/libs.versions.toml`:

```toml
[versions]
lifecycle = "2.9.1"
coroutines = "1.10.2"

[libraries]
lifecycle-viewmodel = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-ktx", version.ref = "lifecycle" }
lifecycle-runtime = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycle" }
coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines" }
```

Add to `app/build.gradle.kts` dependencies:

```kotlin
implementation(libs.lifecycle.viewmodel)
implementation(libs.lifecycle.runtime)
implementation(libs.coroutines.android)
```

## Migration Per Screen

For each screen (home, productlist, productdetail):

1. Create `XxxViewModel.kt` — extends `ViewModel()`, exposes `StateFlow<UiState>`
2. Delete `XxxContract.java` (or `.kt`) — no longer needed
3. Delete `XxxPresenter.kt` — replaced by ViewModel
4. Update `XxxFragment.kt` — get ViewModel via `ViewModelProvider`, observe StateFlow in `lifecycleScope`

## Conversion Patterns

### HomeScreen (stateless)

No ViewModel needed — Home has no data to load. Remove Contract and Presenter. The Fragment handles the button click directly.

```kotlin
// BEFORE (MVP)
class HomeFragment : Fragment(), HomeContract.View {
    private lateinit var presenter: HomePresenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = HomePresenter(this)
        btnStart.setOnClickListener { presenter.onStartClicked() }
    }
    override fun navigateToProductList() { /* fragment transaction */ }
}

// AFTER (MVVM — simplified, no ViewModel needed)
class HomeFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnStart.setOnClickListener { navigateToProductList() }
    }
    private fun navigateToProductList() { /* fragment transaction */ }
}
```

### ProductList — Presenter → ViewModel

```kotlin
// BEFORE
class ProductListPresenter(
    private val view: ProductListContract.View,
    private val repository: ProductRepository
) : ProductListContract.Presenter {
    override fun loadProducts() {
        view.showProducts(repository.getProducts())
    }
    override fun onProductClicked(productId: Int) {
        view.navigateToProductDetail(productId)
    }
}

// AFTER
class ProductListViewModel : ViewModel() {
    private val repository = ProductRepository()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init { loadProducts() }

    private fun loadProducts() {
        _products.value = repository.getProducts()
    }
}
```

### Fragment observing ViewModel

```kotlin
// BEFORE
presenter = ProductListPresenter(this, ProductRepository())
presenter.loadProducts()

// AFTER
private val viewModel: ProductListViewModel by lazy {
    ViewModelProvider(this)[ProductListViewModel::class.java]
}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    // ...setup RecyclerView...
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.products.collect { products ->
                adapter.setProducts(products)
            }
        }
    }
}
```

### ProductDetail — Presenter → ViewModel

```kotlin
// AFTER
class ProductDetailViewModel : ViewModel() {
    private val repository = ProductRepository()
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product.asStateFlow()

    fun loadProduct(productId: Int) {
        _product.value = repository.getProductById(productId)
    }
}
```

## Files to Delete

Per screen, delete:
- `XxxContract.kt` (view/presenter interface)
- `XxxPresenter.kt`

## Files to Create

- `presentation/productlist/ProductListViewModel.kt`
- `presentation/productdetail/ProductDetailViewModel.kt`

## Files to Modify

- `presentation/home/HomeFragment.kt` — remove presenter, handle click directly
- `presentation/productlist/ProductListFragment.kt` — use ViewModel
- `presentation/productdetail/ProductDetailFragment.kt` — use ViewModel

## Gotchas

- ViewModel has no reference to View/Fragment — it only exposes state
- Navigation stays in Fragment (ViewModel does not navigate)
- Use `viewLifecycleOwner.lifecycleScope` (not `lifecycleScope`) for UI collection
- Use `repeatOnLifecycle(Lifecycle.State.STARTED)` to avoid collecting when backgrounded
- ProductRepository instantiation moves into ViewModel
- No ViewModelFactory needed — ViewModels have no constructor parameters
