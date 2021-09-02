package uz.mobiler.pagination.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import uz.mobiler.pagination.retrofit.ApiClient

class UserViewModel : ViewModel() {

    val liveData = Pager(PagingConfig(pageSize = 2)) {
        UserDataSource(ApiClient.apiService)
    }.liveData
}