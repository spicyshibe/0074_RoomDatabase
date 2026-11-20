package com.example.aplikasisaya.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasisaya.repositori.RepositoriSiswa
import com.example.aplikasisaya.uicontroller.DestinasiDetailSiswa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoriSiswa: RepositoriSiswa): ViewModel() {

    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetailSiswa.itemIdArg])

    val uiDetailedState: StateFlow<DetailsSiswaUiState> =
        repositoriSiswa.getSiswaStream(idSiswa)
            .filterNotNull()
            .map{
                DetailsSiswaUiState(detailsSiswa = it.toDetailsSiswa())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DetailsSiswaUiState()
            )

    suspend fun deleteSiswa() {
        repositoriSiswa.deleteSiswa(uiDetailedState.value.detailsSiswa.toSiswa())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

// UI STATE FOR ITEMDETAILSSCREEN

data class DetailsSiswaUiState(
    val detailsSiswa: DetailsSiswa = DetailsSiswa()
)
