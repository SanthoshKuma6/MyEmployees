package com.management.myemployees.activity.apiinterface


sealed class ApiResult<T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val showLoader: Boolean? = null
) {
    class Loading<T>(showLoader: Boolean) : ApiResult<T>(showLoader = showLoader)
    class Success<T>(data: T? = null) : ApiResult<T>(data = data)
    class Error<T>(errorMessage: String) : ApiResult<T>(errorMessage = errorMessage)
}