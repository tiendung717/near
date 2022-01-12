package org.app.dzung.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.app.dzung.common.StateResult
import org.app.dzung.common.getState
import org.app.dzung.data.Repository
import org.app.dzung.data.model.nft.Nft
import javax.inject.Inject

@HiltViewModel
class AssetViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var selectedAsset = mutableStateOf<Nft?>(null)

    fun getPopularAssets(state: MutableState<StateResult<List<Nft>>>) = getState(state) {
        repository.getPopularAssets()
    }

    fun getAllAssets(state: MutableState<StateResult<List<Nft>>>) = getState(state) {
        repository.getAllAssets()
    }

    fun setCurrentAsset(nft: Nft) {
        selectedAsset.value = nft
    }
}