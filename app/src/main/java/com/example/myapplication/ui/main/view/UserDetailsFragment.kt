package com.example.myapplication.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.UserDetailsFragmentBinding
import com.example.myapplication.ui.main.data.repo.RepositoryImpl
import com.example.myapplication.ui.main.data.repo.local.LocalRepository
import com.example.myapplication.ui.main.data.repo.local.api.AppDatabase
import com.example.myapplication.ui.main.data.repo.remote.RemoteRepository
import com.example.myapplication.ui.main.data.repo.remote.api.RetrofitBuilder

class UserDetailsFragment : Fragment(R.layout.user_details_fragment) {

    companion object {
        fun newInstance() = UserDetailsFragment()
    }

    private lateinit var viewModel: UserDetailsViewModel

    private var _binding: UserDetailsFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repositoryImpl = RepositoryImpl.getInstance(
            RemoteRepository(RetrofitBuilder.apiService),
            LocalRepository(AppDatabase.getDatabase(requireContext()).userDao())
        )
        val vmFactory = MainViewModelFactory(repositoryImpl)

        viewModel = ViewModelProvider(this, vmFactory).get(UserDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UserDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: UserDetailsFragmentArgs by navArgs()

        viewModel.loadDetails(args.userUuid)

        viewModel.observableDetails.observe(viewLifecycleOwner) {
            binding.address.text = "Address: ${it.address}"
            binding.age.text = "Age: ${it.age}"
            binding.name.text = "Name: ${it.name}"
            binding.phone.text = "Phone: ${it.phone}"
            binding.gender.text = "Email: ${it.email}"
            Glide.with(requireContext()).load(it.picture).into(binding.avatar)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}