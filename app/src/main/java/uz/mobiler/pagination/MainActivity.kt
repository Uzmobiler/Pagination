package uz.mobiler.pagination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.mobiler.pagination.adapters.PaginationAdapter
import uz.mobiler.pagination.databinding.ActivityMainBinding
import uz.mobiler.pagination.models.UserData
import uz.mobiler.pagination.retrofit.ApiClient
import uz.mobiler.pagination.utils.PaginationScrollListener

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var paginationAdapter: PaginationAdapter

    lateinit var linearLayoutManager: LinearLayoutManager

    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1
    private var TOTAL_PAGES = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        linearLayoutManager = LinearLayoutManager(this)

        paginationAdapter = PaginationAdapter()
        binding.rv.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                loadNextPage()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
        binding.rv.layoutManager = linearLayoutManager
        binding.rv.adapter = paginationAdapter

        loadFirstPage()
    }

    fun loadFirstPage() {
        ApiClient.apiService.getUsers(currentPage).enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    binding.progressbar.visibility = View.GONE
                    paginationAdapter.addAll(response.body()?.data ?: emptyList())

                    if (currentPage <= TOTAL_PAGES) {
                        paginationAdapter.editLoading()
                    } else {
                        isLastPage = true
                    }
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {

            }
        })
    }

    fun loadNextPage() {
        ApiClient.apiService.getUsers(currentPage).enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    paginationAdapter.removeLoading()
                    isLoading = false

                    paginationAdapter.addAll(response.body()?.data ?: emptyList())

                    if (currentPage <= TOTAL_PAGES) {
                        paginationAdapter.editLoading()
                    } else {
                        isLastPage = true
                    }
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {

            }
        })
    }
}