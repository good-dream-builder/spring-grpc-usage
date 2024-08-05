package com.songko.server.service

import com.songko.grpc.UploadRequest
import com.songko.grpc.UploadResponse
import com.songko.grpc.UploaderGrpc
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import net.devh.boot.grpc.server.service.GrpcService
import kotlin.io.path.Path
import kotlin.io.path.writeBytes

@GrpcService
class UploaderService : UploaderGrpc.UploaderImplBase(), CoroutineScope by CoroutineScope(Dispatchers.IO) {

    override fun uploadFiles(responseObserver: StreamObserver<UploadResponse>?): StreamObserver<UploadRequest> {
//        return object : StreamObserver<UploadRequest> {
//
//            private var sender: String? = null
//            private val pdfBytes = mutableListOf<ByteArray>()
//            private val requestChannel = Channel<UploadRequest>()
//
//            init {
//                launch {
//                    processRequests(requestChannel.consumeAsFlow())
//                }
//            }
//
//            override fun onNext(request: UploadRequest) {
//                launch {
//                    requestChannel.send(request)
//                }
//            }
//
//            override fun onError(t: Throwable) {
//                // Handle any errors that occur during streaming
//                t.printStackTrace()
//                responseObserver.onError(t)
//            }
//
//            override fun onCompleted() {
//                launch {
//                    // Close the channel to signal completion
//                    requestChannel.close()
//                }
//            }
//
//            private suspend fun processRequests(requests: Flow<UploadRequest>) {
//                requests.collect { request ->
//                    // 각 요청 청크에서 데이터 검색
//                    if (request.hasField(request.descriptorForType.findFieldByName("sender"))) {
//                        sender = request.sender
//                    }
//
//                    if (request.hasField(request.descriptorForType.findFieldByName("pdf_file"))) {
//                        // PDF 파일에서 받은 바이트를 추가합니다.
//                        pdfBytes.add(request.pdfFile.toByteArray())
//                    }
//                }
//
//                // PDF 파일 전체를 처리
//                // pdfBytes에 저장된 바이트 배열을 파일에 쓰거나, DB에 저장할 수 있습니다
//                try {
//                    val path = Path("C://Users/psj/Desktop/Study/gRPCExamples/_server_data_center/address_proof.pdf")
//                    val combinedBytes = pdfBytes.flatten().toByteArray()
//                    path.writeBytes(combinedBytes)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//
//                // 응답 메시지를 생성하고 전송
//                val response = UploadResponse.newBuilder()
//                    .setSuccess(true)
//                    .build()
//                responseObserver.onNext(response)
//                responseObserver.onCompleted()
//            }
//        }
    }


}