package com.songko.server.service

import com.songko.grpc.ChatGrpcKt
import com.songko.grpc.ChatMessage
import com.songko.grpc.ChatResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.devh.boot.grpc.server.service.GrpcService

/**
 * Bidirectional streaming example
 */
@GrpcService
class ChatService : ChatGrpcKt.ChatCoroutineImplBase() {
    override fun startChat(requests: Flow<ChatMessage>): Flow<ChatResponse> = flow {
        var responseCount = 0;

        try {
            requests.collect { chatMessage ->
                val sender = chatMessage.sender
                val message = chatMessage.message

                println("수신 메세지: ${message}")

                val response = ChatResponse.newBuilder()
                    .setSender("서버")
                    .setMessage("${sender} 님은, ${message}라고 말씀하셨습니다.(응답횟수 : ${responseCount++})")
                    .build()

                emit(response)
            }
        } catch (e: Throwable) {

        } finally {

        }
    }
}