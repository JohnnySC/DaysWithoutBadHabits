package com.github.johnnysc.dayswithoutbadhabits

import com.github.johnnysc.dayswithoutbadhabits.core.Communication

/**
 * @author Asatryan on 15.12.2022
 */
interface MainCommunication {

    interface Put : Communication.Put<UiState>

    interface Observe : Communication.Observe<UiState>

    interface Mutable : Put, Observe

    class Base : Communication.Abstract<UiState>(), Mutable
}