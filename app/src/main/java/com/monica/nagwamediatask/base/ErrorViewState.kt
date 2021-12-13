package com.monica.nagwamediatask.base

import androidx.annotation.StringRes

sealed class ErrorViewState : BaseViewState() {
    data class Error(val message: String?, @StringRes val localString: Int? = null) : BaseViewState()
}