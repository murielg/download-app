package com.gonzoapps.downloadapp.ui.loadingbutton


sealed class ButtonState {
    object Clicked : ButtonState()
    object Loading : ButtonState()
    object Completed : ButtonState()
}