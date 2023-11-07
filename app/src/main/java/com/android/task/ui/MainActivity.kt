package com.android.task.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.android.task.R
import com.android.task.data.repo.DefaultLabelRepository
import com.android.task.data.source.network.LabelNetworkDataSource
import com.android.task.databinding.ActivityMainBinding
import com.android.task.ui.adapter.ImagePagerAdapter
import com.android.task.ui.adapter.RecyclerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityVM
    private lateinit var binding: ActivityMainBinding
    private val labelsAdapter = RecyclerAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = DefaultLabelRepository(LabelNetworkDataSource())
        val viewModelFactory = MyViewModelFactory((repo))
        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityVM::class.java]

        setupViewPager()
        setupLabelRecycler()



        lifecycleScope.launch {
            viewModel.items.collect {
                labelsAdapter.submitList(it.toMutableList())
            }
        }


        binding.searchView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // EditText is focused, hide the search hint layout
                binding.searchHintLayout.visibility = View.GONE
            } else {
                // EditText lost focus, show the search hint layout
                binding.searchHintLayout.visibility = View.VISIBLE
            }
        }

        binding.searchView.setOnEditorActionListener { _, actionId, _ ->
            val isImeActionSearch =
                actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
            if (isImeActionSearch && binding.searchView.text.isNotEmpty()) {
                // Handle the search action here
                viewModel.search(binding.searchView.text.toString())
                clearSearchView()
                true  // Return true to indicate that the event was handled
            } else {
                false  // Return false to indicate that the event was not handled
            }
        }


    }

    private fun setupLabelRecycler() {
        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        val divider =
            ResourcesCompat.getDrawable(resources, R.drawable.recycler_divider_layer, null)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = labelsAdapter
            divider?.let {
                decoration.setDrawable(it)
                addItemDecoration(decoration)
            }
        }
    }

    private fun setupViewPager() {
        val imageAdapter = ImagePagerAdapter(viewModel.getHeaders())
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(20))
        binding.imageViewPager.apply {
            adapter = imageAdapter
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 2
            setPageTransformer(compositePageTransformer)
        }
        TabLayoutMediator(binding.indicator, binding.imageViewPager) { _, _ -> }.attach()
    }

    private fun clearSearchView() {
        binding.searchView.text.clear()
        binding.searchView.clearFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
    }
}