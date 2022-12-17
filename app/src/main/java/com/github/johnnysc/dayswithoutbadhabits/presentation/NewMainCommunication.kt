package com.github.johnnysc.dayswithoutbadhabits.presentation

import com.github.johnnysc.dayswithoutbadhabits.core.Communication
import com.github.johnnysc.dayswithoutbadhabits.core.SingleLiveEvent

/**
 * @author Asatryan on 17.12.2022
 */
interface NewMainCommunication {

    interface Put : Communication.Put<NewUiState>

    interface Observe : Communication.Observe<NewUiState>

    interface Mutable : Put, Observe

    class Base : Communication.Abstract<NewUiState>(SingleLiveEvent()), Mutable
}