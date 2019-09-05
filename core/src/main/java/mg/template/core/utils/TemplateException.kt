package mg.template.core.utils

open class TemplateException : Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(
        message,
        cause,
        enableSuppression,
        writableStackTrace
    )
}

class RouterException : TemplateException {
    constructor(routerClass: Class<*>) : super("Parent component doesn't implement ${routerClass.name}")
    constructor(message: String) : super(message)
}