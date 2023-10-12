package com.eiselle.kotlinreactive.exception.handler

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class GlobalErrorAttributes : DefaultErrorAttributes() {
    override fun getErrorAttributes(
            request: ServerRequest,
            options: ErrorAttributeOptions
    ): Map<String, Any> {
        val map = super.getErrorAttributes(request, options)
        map["message"] = getError(request).message
        map.remove("error")
        map.remove("requestId")
        map.remove("status")
        return map
    }
}