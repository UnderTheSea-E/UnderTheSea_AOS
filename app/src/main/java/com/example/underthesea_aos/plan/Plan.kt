package com.example.underthesea_aos.plan

import com.example.underthesea_aos.user.Friend
import java.util.*

class Plan {
    var planId: Long? = null
        get() { return field }
        set(value) { field = value }
    var friend: Friend? = null
        get() { return field }
        set(value) { field = value }
    var content: String? = null
        get() { return field }
        set(value) { field = value }
    var date: String? = null
        get() { return field }
        set(value) { field = value }
    var status: String? = null
        get() { return field }
        set(value) { field = value }
    var created_at: Date? = null
        get() { return field }
        set(value) { field = value }
    var updated_at: Date? = null
        get() { return field }
        set(value) { field = value }
}