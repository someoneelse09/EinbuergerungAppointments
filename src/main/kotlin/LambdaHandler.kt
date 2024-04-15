package org.example

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class LambdaHandler : RequestHandler<Map<String, String>, String> {
    private val logger: Logger = LogManager.getLogger(LambdaHandler::class.java)

    override fun handleRequest(msg: Map<String, String>, context: Context): String {
        logger.info("Executing lambda")
        main()
        return "AWS Lambda"
    }
}