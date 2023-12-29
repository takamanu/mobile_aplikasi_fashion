package com.example.aifash.ui.admin

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aifash.databinding.FragmentFashionSearchBinding
import com.example.aifash.trade.ProductViewModel
import com.example.aifash.ui.users.FashionAdapter


class FashionSearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentFashionSearchBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var adapter: FashionAdapter
    private lateinit var recyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFashionSearchBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FashionAdapter(emptyList()) // Initially empty
        recyclerView.adapter = adapter

        val searchView = binding.searchView
        searchView.setIconifiedByDefault(false)
        searchView.clearFocus()

        performSearch("")

        // Set up the query listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    performSearch(query)
                    Log.d(TAG, "Ini search query: $query")
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle query text change if needed
                return true
            }
        })



        return binding.root

    }


    private fun performSearch(query: String) {
        productViewModel.searchProductsFashion(query)

        productViewModel.productsFashion.observe(viewLifecycleOwner) {productsFashion ->
            if (productsFashion != null) {
                adapter = productsFashion?.let { FashionAdapter(it) }!!
                recyclerView.adapter = adapter
            }
        }
//        productViewModel.searchResultEmpty.observe(this) {searchResultEmpty ->
//            if (searchResultEmpty) {
//                val message = "Product not found"
//                val rootView = findViewById<View>(android.R.id.content) // Replace with the actual root view of your layout
//                val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
//                val snackbarView = snackbar.view
//                val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
//                textView.setTypeface(textView.typeface, Typeface.BOLD)
//                snackbar.setBackgroundTint(resources.getColor(com.example.aifash.R.color.red))
//                snackbar.show()
//            }
//        }
    }

    companion object {
        private const val TAG = "FashionSearchFragment"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FashionSearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FashionSearchFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}