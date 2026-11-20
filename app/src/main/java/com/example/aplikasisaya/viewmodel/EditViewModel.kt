package com.example.aplikasisaya.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasisaya.repositori.RepositoriSiswa
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoriSiswa: RepositoriSiswa
):
    ViewModel() {

        var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

        private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetailSiswa.itermIdArg])
            init{
                viewModelScope.launch {
                    uiStateSiswa = repositoriSiswa.getSiswaStream(idSiswa)
                        .filterNotNull()
                        .first()
                        .toUiStateSiswa(true)
                }
            }



}
