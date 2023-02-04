package com.errabi.ishop.grpc;

import com.errabi.common.exception.IShopException;
import com.errabi.ishop.CommonRpc;
import com.errabi.ishop.ProductRequest;
import com.errabi.ishop.ProductResponse;
import com.errabi.ishop.ProductServiceGrpc;
import com.errabi.ishop.grpc.mapper.ProductRpcMapper;
import com.errabi.ishop.services.ProductService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class ProductServiceGrpcImpl extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService ;
    private final ProductRpcMapper mapper ;
    @Override
    public void addProduct(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        try{
            log.info("Received rpc request {} ",request);
            var product =  productService.saveProduct(mapper.toModel(request));
            responseObserver.onNext(mapper.toRpc(product));
            responseObserver.onCompleted();
        }catch (IShopException ex){
            log.error("grpc error : " ,ex);
            var rpcResponse  =  ProductResponse.newBuilder()
                    .setResponseInfo(CommonRpc.ResponseInfo.newBuilder()
                            .setErrorCode(ex.getErrorCode())
                            .setResponseId(UUID.randomUUID().toString())
                            .setReturnStatus("NOK")
                            .setErrorMessage(StringUtils.hasLength(ex.getErrorDescription())?ex.getErrorDescription():"")
                            .build())
                    .build();

            responseObserver.onNext(rpcResponse);
            responseObserver.onCompleted();
        }

    }
}
