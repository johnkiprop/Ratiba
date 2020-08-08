package com.example.soko.products


import android.app.SearchManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media.session.MediaButtonReceiver.handleIntent
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soko.R
import com.example.soko.base.BaseFragment
import com.example.soko.databinding.ProductScreenBinding
import com.example.soko.model.ProductItems
import com.example.soko.proddata.MySuggestionProvider
import com.example.soko.proddata.SearchAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@ExperimentalCoroutinesApi
 class ProductsFragment: BaseFragment<ProductScreenBinding>(), ProductAdapter.RepoClickedListener {
    /**
     * If using AndroidX fragment-ktx dependency, you may use this to simplify getting ViewModel
     */
    private val viewModel: ProductsViewModel by viewModels()

    private var mSuggestionAdapter: SearchAdapter? = null
    private var searchJob: Job? = null
    private var searchItem:MenuItem? =null
  private  val productAdapter = ProductAdapter(this,ProductAdapter.UserComparator)
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null
  private var searchQuery:String? =null
    fun newInstance(): Fragment? {
        val bundle = Bundle()
        bundle.putString("instance_id", UUID.randomUUID().toString())
        val fragment: Fragment = ProductsFragment()
        fragment.arguments = bundle
        return fragment
    }


    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ProductScreenBinding =
        ProductScreenBinding.inflate(inflater, container, false)


    override fun onViewBound(view:View) {
        initAdapter()
        designSpecs()


    }
    private fun designSpecs(){
//        // Set up the toolbar.
      (activity as AppCompatActivity).setSupportActionBar(binding?.toolbar)
        // Set up the RecyclerView
       binding?.repoList?.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) 2 else 1
            }
        }
        binding?.repoList?.layoutManager = gridLayoutManager
        binding?.repoList?.adapter= productAdapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small)
        binding?.repoList?.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
    }

    private fun loadRepos(query:String){
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getResult(query).collect{
                productAdapter.submitData(it)
            }
        }

    }


     private fun initRefresh(){

             lifecycleScope.launch {
                 @OptIn(ExperimentalPagingApi::class)
                 productAdapter.dataRefreshFlow.collect {
                     binding?.repoList?.scrollToPosition(0)
                 }

         }
     }
    private fun initAdapter(){
        binding?.retryButton?.setOnClickListener { productAdapter.retry() }
        binding?.repoList?.adapter = productAdapter.withLoadStateHeaderAndFooter(
            header = ProductLoadStateAdapter { productAdapter.retry() },
            footer = ProductLoadStateAdapter{ productAdapter.retry() })
        productAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh !is LoadState.NotLoading) {
                // We're refreshing: either loading or we had an error
                // So we can hide the list
                binding?.repoList?.visibility = View.GONE
                binding?.progressBar?.visibility = toVisibility(loadState.refresh is LoadState.Loading)
                binding?.retryButton?.visibility = toVisibility(loadState.refresh is LoadState.Error)
            } else {
                // We're not actively refreshing
                // So we should show the list
                binding?.repoList?.visibility = View.VISIBLE
                binding?.progressBar?.visibility = View.GONE
                binding?.retryButton?.visibility = View.GONE

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> {
                        loadState.append as LoadState.Error
                    }
                    loadState.prepend is LoadState.Error -> {
                        loadState.prepend as LoadState.Error
                    }
                    else -> {
                        null
                    }
                }
                errorState?.let {
                    Toast.makeText(
                        context,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        searchQuery = savedInstanceState?.getString(SEARCH_QUERY) ?: DEFAULT_QUERY
        loadRepos(searchQuery!!)

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        searchQuery = searchView?.query.toString()
        outState.putString(SEARCH_QUERY, searchQuery)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
         searchItem = menu.findItem(R.id.search)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        mSuggestionAdapter = SearchAdapter(requireActivity(), null, 0)
        if (searchItem != null) {
            searchView = searchItem!!.actionView as SearchView
        }
        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            searchView!!.isQueryRefinementEnabled = true
            searchView!!.suggestionsAdapter = mSuggestionAdapter

            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    Log.i("onQueryTextChange", newText)
                    searchQuery = newText
                    // Update Cursor With Each Query Text Change
                    val cursor = getRecentSuggestions(newText)
                    if (cursor != null) {
                        mSuggestionAdapter?.swapCursor(cursor)
                    }
                         if(newText.isEmpty()){
                             loadRepos(newText)
                             initRefresh()
                             searchView!!.clearFocus()
                         }

                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {

                    searchQuery = query
                    val suggestions = SearchRecentSuggestions(
                        requireActivity(),
                        MySuggestionProvider.AUTHORITY,
                        MySuggestionProvider.MODE
                    )
                    suggestions.saveRecentQuery(query, null)
                    Log.i("onQueryTextSubmit", query)
                        query.trim().let {
                         if (it.isNotEmpty()) {
//                             binding?.repoList?.scrollToPosition(0)
                             loadRepos(it)
                             initRefresh()
                         }
                     }
                    searchView!!.clearFocus()
                    return true
                }
            }
            searchView?.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return false
                }

                override fun onSuggestionClick(position: Int): Boolean {
                    // On Clicking Suggestion Load It To Submit Query Listener

                    searchView?.setQuery(mSuggestionAdapter?.getSuggestionText(position), true)

                    return true
                }
            })
            searchView!!.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search ->
                // Not implemented here
                return false
            else -> {
            }
        }
        searchView!!.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    // Function To Retrieve Suggestion From Content Resolver
    fun getRecentSuggestions(query: String): Cursor? {
        val uriBuilder = Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(MySuggestionProvider.AUTHORITY)

        uriBuilder.appendPath(SearchManager.SUGGEST_URI_PATH_QUERY)

        val selection = " ?"
        val selArgs = arrayOf(query)

        val uri = uriBuilder.build()
        return requireActivity().contentResolver?.query(uri, null, selection, selArgs, null)
    }

    companion object {
        private const val SEARCH_QUERY: String = "query"
        private const val DEFAULT_QUERY: String = ""
    }

    override fun onRepoClicked(productItems: ProductItems?) {
        TODO("Not yet implemented")
    }
}
















