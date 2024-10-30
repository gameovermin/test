package org.example.springbootDeveloper.common;

public class ResponseMessage {

    // 성공 및 일반 메시지
    public static final String SUCCESS = "Success"; // 성공 시 반환 메시지
    public static final String VALIDATION_FAIL = "Validation failed."; // 유효성 검사 실패 시 반환 메시지
    public static final String DATABASE_ERROR = "Database error."; // 데이터베이스 에러 시 반환 메시지

    // 존재 여부 관련 메시지
    public static final String EXIST_DATA = "Data already exists."; // 데이터가 이미 존재할 때 반환 메시지

    public static final String NOT_EXIST_DATA = "Data does not exist."; // 데이터가 존재하지 않을 때 반환 메시지
    public static final String NOT_EXIST_USER = "User does not exist."; // 사용자가 존재하지 않을 때 반환 메시지
    public static final String NOT_EXIST_MENU = "Menu does not exist."; // 사용자가 존재하지 않을 때 반환 메시지

    // EX) 존재 여부 관련 메시지 예시 - 확인 후 생략 가능
    // : 검색한 데이터에 대한 응답 메시지
    public static final String NOT_EXIST_POST = "Post does not exist."; // 게시글이 존재하지 않을 때 반환 메시지
//    public static final String NOT_EXIST_ORDER = "Order does not exist."; // 주문이 존재하지 않을 때 반환 메시지
//    public static final String NOT_EXIST_CART = "Cart does not exist."; // 장바구니가 존재하지 않을 때 반환 메시지
//    public static final String NO_EXIST_CUSTOMER = "Customer does not exist."; // 고객이 존재하지 않을 때 반환 메시지

    // 중복 관련 메시지
    // : 중복될 수 없는 값에 대한 응답 메시지
    public static final String DUPLICATED_USER_ID = "Duplicated user ID."; // 사용자 ID 중복 시 반환 메시지
    public static final String DUPLICATED_TEL_NUMBER = "Duplicated telephone number."; // 전화번호 중복 시 반환 메시지

    // 인증 및 권한 관련 메시지
    public static final String SIGN_IN_FAIL = "Sign in failed."; // 로그인 실패 시 반환 메시지
    public static final String AUTHENTICATION_FAIL = "Authentication failed."; // 인증 실패 시 반환 메시지
    public static final String NO_PERMISSION = "No permission."; // 권한이 없을 때 반환 메시지
    public static final String TEL_AUTH_FAIL = "Telephone number authentication failed."; // 전화번호 인증 실패 시 반환 메시지
    public static final String NOT_MATCH_PASSWORD = "Password does not match."; // 비밀번호 불일치 시 반환 메시지

    // 토큰 및 메시지 관련 에러
    public static final String TOKEN_CREATE_FAIL = "Token creation failed."; // 토큰 생성 실패 시 반환 메시지
    public static final String MESSAGE_SEND_FAIL = "Failed to send authentication number."; // 인증 번호 전송 실패 시 반환 메시지
}
