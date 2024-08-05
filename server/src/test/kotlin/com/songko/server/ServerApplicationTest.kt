package com.songko.server

import com.songko.grpc.GreeterGrpcKt
import com.songko.grpc.HelloRequest
import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@SpringBootTest(
    classes = [ServerApplication::class],
)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles
class ServerApplicationTest {
    private val channel: Channel = ManagedChannelBuilder
        .forAddress("localhost", 8090)
        .usePlaintext()
        .build()

    @Test
    fun greeter_service_test() {
        val stub = GreeterGrpcKt.GreeterCoroutineStub(channel)
        val req = HelloRequest.newBuilder().setName("고윤정").build()

        runBlocking {
            val reply = stub.sayHello(req)
            println(reply.message)
        }
    }
}