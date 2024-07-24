package com.example.testapps.pojoclass

data class ApiErrorResponse(
    var errMsg: String? = ""
)

data class ApiResponse<T>(
    var isRedirect: Boolean = false,
    var data: T? = null,
    var error: Boolean = false,
    var msg: String? = null
)