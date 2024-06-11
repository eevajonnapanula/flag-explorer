package com.eevajonna.flagexplorer.ui

import androidx.lifecycle.ViewModel
import com.eevajonna.FlagQuery
import com.eevajonna.flagexplorer.data.apolloClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FlagsUiState(
    val loading: Boolean = true,
    val flags: List<FlagQuery.AllFlag> = emptyList(),
    val currentFlag: FlagQuery.AllFlag? = null,
)

class FlagsViewModel : ViewModel() {
    private var _state = MutableStateFlow(FlagsUiState())
    val state = _state.asStateFlow()

    suspend fun getFlags() {
        val flagsQuery = apolloClient.query(FlagQuery()).execute()
        _state.update { currentState ->
            currentState.copy(
                loading = false,
                flags = flagsQuery.data?.allFlags ?: emptyList(),
            )
        }
    }

    fun setFlag(flag: FlagQuery.AllFlag) {
        _state.update {
            it.copy(
                currentFlag = flag,
            )
        }
    }
}
