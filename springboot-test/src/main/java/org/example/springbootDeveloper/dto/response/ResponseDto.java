package org.example.springbootDeveloper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// @AllArgsConstructor
// : 모든 필드 값을 매개변수로 받는 생성자를 자동 생성
// - staticName="set"
//      :  set()이라는 이름의 정적 메서드를 제공
@AllArgsConstructor(staticName="set")
public class ResponseDto<D> {
    private boolean result;
    private String message;
    private D data;

    // 생성자의 매개변수의 수를 다르게 하기 위함
    // : 전체 매개변수를 가진 생성자 이름을 set으로 설정하고
    //      , 해당 생성자의 사용을 원하는 메서드 내에서 활용

    // 성공 응답을 설정하는 정적 메서드
    // : 성공 메시지와 데이터를 받아 처리
    public static <D> ResponseDto<D> setSuccess(String message, D data) {
        return ResponseDto.set(true, message, data);
    }

    // 실패 응답을 설정하는 정적 메서드
    // : 실패 메시지만 받아 처리
    public static <D> ResponseDto<D> setFailed(String message) {
        return ResponseDto.set(false, message, null);
    }
}
