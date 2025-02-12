package com.droidcode.apps.cryptostatstracker.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcode.apps.cryptostatstracker.domain.models.Coin
import com.droidcode.apps.cryptostatstracker.domain.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {
    private val coinState = MutableLiveData<List<Coin>>()
    val coins: LiveData<List<Coin>> get() = coinState
    //var coins = mutableStateOf(emptyList<Coin>())

    init {
        viewModelScope.launch {
            coinState.value = repository.get10Coins()
        }
    }
}
