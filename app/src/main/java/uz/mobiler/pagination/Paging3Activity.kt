package uz.mobiler.pagination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import uz.mobiler.pagination.adapters.Paging3Adapter
import uz.mobiler.pagination.databinding.ActivityPaging3Binding
import uz.mobiler.pagination.viewmodels.UserViewModel

class Paging3Activity : AppCompatActivity() {
    lateinit var binding: ActivityPaging3Binding

    lateinit var userViewModel: UserViewModel

    lateinit var paging3Adapter: Paging3Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaging3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        paging3Adapter = Paging3Adapter()
        binding.rv.adapter = paging3Adapter

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.liveData.observe(this, Observer {
            GlobalScope.launch(Dispatchers.Main) {
                paging3Adapter.submitData(it)
            }
        })
    }
}