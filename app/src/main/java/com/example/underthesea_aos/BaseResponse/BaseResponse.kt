package com.example.underthesea_aos.BaseResponse

class BaseResponse<T> {
    var isSuccess: Boolean? = null
        get() { return field }
        set(value) { field = value }
    var code: Int? = null
        get() { return field }
        set(value) { field = value }
    var message: String? = null
        get() { return field }
        set(value) { field = value }
    var result: T? = null
        get() { return field }
        set(value) { field = value }
}