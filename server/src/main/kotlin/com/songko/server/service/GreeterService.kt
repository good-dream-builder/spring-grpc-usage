package com.songko.server.service

import com.songko.grpc.GreeterGrpcKt
import com.songko.grpc.HelloReply
import com.songko.grpc.HelloRequest
import net.devh.boot.grpc.server.service.GrpcService
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Unary example
 */
@GrpcService
class GreeterService : GreeterGrpcKt.GreeterCoroutineImplBase(
    coroutineContext = EmptyCoroutineContext
) {
    override suspend fun sayHello(request: HelloRequest): HelloReply =
        HelloReply.newBuilder().apply {
            message = "Hello, ${request.name}"
        }.build()
}