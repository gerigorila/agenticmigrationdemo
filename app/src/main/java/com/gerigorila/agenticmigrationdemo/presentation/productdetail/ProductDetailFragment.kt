package com.gerigorila.agenticmigrationdemo.presentation.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gerigorila.agenticmigrationdemo.R
import com.gerigorila.agenticmigrationdemo.data.repository.ProductRepository
import com.gerigorila.agenticmigrationdemo.domain.model.Product
import com.google.android.material.appbar.MaterialToolbar
import java.util.Locale

class ProductDetailFragment : Fragment(), ProductDetailContract.View {

    private lateinit var presenter: ProductDetailPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_product_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(com.google.android.material.R.drawable.ic_arrow_back_black_24)
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        presenter = ProductDetailPresenter(this, ProductRepository())

        val productId = requireArguments().getInt(ARG_PRODUCT_ID)
        presenter.loadProduct(productId)
    }

    override fun showProduct(product: Product) {
        val view = view ?: return

        view.findViewById<MaterialToolbar>(R.id.toolbar).title = product.name
        view.findViewById<TextView>(R.id.txt_name).text = product.name
        view.findViewById<TextView>(R.id.txt_price).text =
            String.format(Locale.US, "$%.2f", product.price)
        view.findViewById<TextView>(R.id.txt_description).text = product.description
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ARG_PRODUCT_ID = "product_id"

        fun newInstance(productId: Int) = ProductDetailFragment().apply {
            arguments = Bundle().apply { putInt(ARG_PRODUCT_ID, productId) }
        }
    }
}
