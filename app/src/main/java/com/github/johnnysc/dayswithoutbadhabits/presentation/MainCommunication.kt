package com.github.johnnysc.dayswithoutbadhabits.presentation

import com.github.johnnysc.dayswithoutbadhabits.core.Communication
import com.github.johnnysc.dayswithoutbadhabits.core.SingleLiveEvent

/**
 * @author Asatryan on 17.12.2022
 */
interface MainCommunication {

    interface Put : Communication.Put<MainUiState>

    interface Observe : Communication.Observe<MainUiState>

    interface Mutable : Put, Observe

    class Base : Communication.Abstract<MainUiState>(SingleLiveEvent()), Mutable
}