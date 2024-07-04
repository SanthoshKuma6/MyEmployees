package com.management.myemployees.activity.apiinterface

class Response<out T : Any?> (
    val status: String?= null,
    val message: String?= null,
    val data: T
)