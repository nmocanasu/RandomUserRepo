package com.example.myapplication.ui.main.view

import androidx.lifecycle.*
import com.example.myapplication.ui.main.data.repo.Repository
import com.example.myapplication.ui.main.view.model.DetailedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val repository: Repository) : ViewModel() {
    private var _observableDetails = MutableLiveData<DetailedUser>()
    val observableDetails: LiveData<DetailedUser> get() = _observableDetails

    fun loadDetails(uuid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val details = repository.fetchDetails(uuid)

            viewModelScope.launch(Dispatchers.Main) {
                _observableDetails.value = details
            }
        }
    }
}

class UserDetailsViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(Repository::class.java)
            .newInstance(repository)
    }
}