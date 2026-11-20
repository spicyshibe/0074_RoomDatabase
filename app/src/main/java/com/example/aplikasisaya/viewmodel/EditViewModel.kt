package com.example.aplikasisaya.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasisaya.repositori.RepositoriSiswa
import com.example.aplikasisaya.uicontroller.DestinasiEditSiswa
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoriSiswa: RepositoriSiswa
): ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiEditSiswa.itemIdArg])

    init {
        viewModelScope.launch {
            uiStateSiswa = repositoriSiswa.getSiswaStream(idSiswa)
                .filterNotNull()
                .first()
                .toUiStateSiswa(true)
        }
    }

    private fun validasiInput(uiState: DetailsSiswa = uiStateSiswa.detailsSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateUiState(detailsSiswa: DetailsSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailsSiswa = detailsSiswa, isEntryValid = validasiInput(detailsSiswa))
    }

    suspend fun updateSiswa() {
        if (validasiInput(uiStateSiswa.detailsSiswa)) {
            repositoriSiswa.updateSiswa(uiStateSiswa.detailsSiswa.toSiswa())
        }
    }
}
