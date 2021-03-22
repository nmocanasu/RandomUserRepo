package com.example.myapplication.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.MainFragmentBinding
import com.example.myapplication.ui.main.data.repo.RepositoryImpl
import com.example.myapplication.ui.main.data.repo.local.LocalRepository
import com.example.myapplication.ui.main.data.repo.local.api.AppDatabase
import com.example.myapplication.ui.main.data.repo.remote.RemoteRepository
import com.example.myapplication.ui.main.data.repo.remote.api.RetrofitBuilder
import com.example.myapplication.ui.main.data.repo.remote.model.ResultsData
import com.example.myapplication.ui.main.view.list.OnLoadMoreListener
import com.example.myapplication.ui.main.view.list.RVScrolListenr
import com.example.myapplication.ui.main.view.list.UserAdapter

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var scrollListener: RVScrolListenr
    private lateinit var adapter: UserAdapter

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.load()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repositoryImpl = RepositoryImpl.getInstance(
            RemoteRepository(RetrofitBuilder.apiService),
            LocalRepository(AppDatabase.getDatabase(requireContext()).userDao())
        )
        val vmFactory = MainViewModelFactory(repositoryImpl)

        viewModel = ViewModelProvider(this, vmFactory).get(MainViewModel::class.java)

        adapter = UserAdapter {
            // reveal details
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToUserDetailsFragment(it.uuid))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

        viewModel.observableList.observe(viewLifecycleOwner) {
            adapter.updateDataSource(it)
            scrollListener.setLoaded()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(), DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.adapter = adapter

        scrollListener = RVScrolListenr(layoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.load()
            }
        })

        binding.recyclerView.addOnScrollListener(scrollListener)
    }
}