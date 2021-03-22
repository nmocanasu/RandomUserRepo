package com.example.myapplication.ui.main.view

import androidx.lifecycle.*
import com.example.myapplication.ui.main.data.repo.Repository
import com.example.myapplication.ui.main.view.model.ListUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private var _observableList = MutableLiveData<List<ListUser>>()
    val observableList: LiveData<List<ListUser>> get() = _observableList

    private var currentPage = 1
    private val itemsPerPage = 10

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = repository.fetchItems(currentPage, itemsPerPage)

            viewModelScope.launch(Dispatchers.Main) {
                _observableList.value = items
                items.takeIf { it.isNotEmpty() }.let { currentPage++ }
            }
        }
    }
}

class MainViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(Repository::class.java)
            .newInstance(repository)
    }
}