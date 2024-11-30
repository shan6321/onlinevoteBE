//package com.onlinevote.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class ResponseMessage<T> {
//    private T data;
//    private String message;
//    private boolean success;
//
//    public static ResponseMessage getSuccessResponse(final String message,final boolean success){
//        return getResponse(message,true);
//    }
//
//    public static <T> ResponseMessage<T> getResponseMsg(final T data,
//                                                            final String message,
//                                                            final boolean success) {
//        final ResponseMessage<T> responseMessage = getResponse(message, success);
//        responseMessage.setData(data);
//        return responseMessage;
//    }
//
//    public static ResponseMessage getResponse(final String message, final boolean success){
//        return ResponseMessage.builder()
//                .success(success)
//                .message(message)
//                .build();
//    }
//}
