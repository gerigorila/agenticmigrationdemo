package com.gerigorila.agenticmigrationdemo.presentation.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gerigorila.agenticmigrationdemo.R
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch
import java.util.Locale

class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by lazy {
        ViewModelProvider(this)[ProductDetailViewModel::class.java]
    }

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

        val productId = requireArguments().getInt(ARG_PRODUCT_ID)
        viewModel.loadProduct(productId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.product.collect { product ->
                    if (product != null) {
                        toolbar.title = product.name
                        view.findViewById<TextView>(R.id.txt_name).text = product.name
                        view.findViewById<TextView>(R.id.txt_price).text =
                            String.format(Locale.US, "$%.2f", product.price)
                        view.findViewById<TextView>(R.id.txt_description).text =
                            product.description
                    }
                }
            }
        }
    }

    companion object {
        private const val ARG_PRODUCT_ID = "product_id"

        fun newInstance(productId: Int) = ProductDetailFragment().apply {
            arguments = Bundle().apply { putInt(ARG_PRODUCT_ID, productId) }
        }
    }
}
