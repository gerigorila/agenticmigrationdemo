# SKILL: Java to Kotlin Migration

## Scope

Convert all `.java` files to `.kt` in the `kotlin-migration` branch.
Do NOT change: MVP architecture, XML layouts, navigation logic, package structure.

## Migration Order

Migrate bottom-up (dependencies first):

1. `domain/model/Product.java`
2. `data/repository/ProductRepository.java`
3. `presentation/home/HomeContract.java`
4. `presentation/home/HomePresenter.java`
5. `presentation/home/HomeFragment.java`
6. `presentation/productlist/ProductListContract.java`
7. `presentation/productlist/ProductListPresenter.java`
8. `presentation/productlist/ProductAdapter.java`
9. `presentation/productlist/ProductListFragment.java`
10. `presentation/productdetail/ProductDetailContract.java`
11. `presentation/productdetail/ProductDetailPresenter.java`
12. `presentation/productdetail/ProductDetailFragment.java`
13. `MainActivity.java`

## Conversion Patterns

### POJO → data class

```java
// BEFORE
public class Product {
    private final int id;
    private final String name;
    public Product(int id, String name) { ... }
    public int getId() { return id; }
    public String getName() { return name; }
}
```

```kotlin
// AFTER
data class Product(
    val id: Int,
    val name: String
)
```

### Contract interface

```java
// BEFORE
public interface HomeContract {
    interface View { void navigateToProductList(); }
    interface Presenter { void onStartClicked(); }
}
```

```kotlin
// AFTER
interface HomeContract {
    interface View { fun navigateToProductList() }
    interface Presenter { fun onStartClicked() }
}
```

### Presenter class

```java
// BEFORE
public class HomePresenter implements HomeContract.Presenter {
    private final HomeContract.View view;
    public HomePresenter(HomeContract.View view) { this.view = view; }
    @Override public void onStartClicked() { view.navigateToProductList(); }
}
```

```kotlin
// AFTER
class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    override fun onStartClicked() { view.navigateToProductList() }
}
```

### Fragment

```java
// BEFORE
btnStart.setOnClickListener(v -> presenter.onStartClicked());
```

```kotlin
// AFTER
btnStart.setOnClickListener { presenter.onStartClicked() }
```

### RecyclerView Adapter — anonymous click listener → lambda

```java
// BEFORE
holder.itemView.setOnClickListener(v -> {
    if (listener != null) { listener.onProductClick(product.getId()); }
});
```

```kotlin
// AFTER
holder.itemView.setOnClickListener {
    listener?.onProductClick(product.id)
}
```

### Fragment newInstance pattern

```java
// BEFORE
public static ProductDetailFragment newInstance(int productId) {
    ProductDetailFragment fragment = new ProductDetailFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_PRODUCT_ID, productId);
    fragment.setArguments(args);
    return fragment;
}
```

```kotlin
// AFTER
companion object {
    private const val ARG_PRODUCT_ID = "product_id"
    fun newInstance(productId: Int) = ProductDetailFragment().apply {
        arguments = Bundle().apply { putInt(ARG_PRODUCT_ID, productId) }
    }
}
```

### String.format → string template

```java
String.format(Locale.US, "$%.2f", product.getPrice())
```

```kotlin
String.format(Locale.US, "$%.2f", product.price)
```

## Build Config Change

AGP 9.x bundles the Kotlin compiler — no separate Kotlin plugin is needed.
The `compileOptions` block already sets Java 11 compatibility for Kotlin too.
No changes required to `build.gradle.kts` or `libs.versions.toml`.

## Gotchas

- Do NOT just remove semicolons — use proper Kotlin idioms
- `ArrayList` → `mutableListOf()`, `Arrays.asList()` → `listOf()`
- Use `requireArguments()` instead of `getArguments()!!`
- Use `?.` and `?:` for null safety instead of explicit null checks
- `OnProductClickListener` functional interface → use `((Int) -> Unit)?` or keep interface with `fun interface`
- Delete the `.java` file after creating the `.kt` file — do not leave both
