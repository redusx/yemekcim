package com.example.yemekcim.uix.viewModel

import androidx.lifecycle.ViewModel
import com.example.yemekcim.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfilePageViewModel@Inject constructor(private val repo:UserRepository):ViewModel() {
}