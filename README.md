# 주문 서비스 입니다.

### 문제점(2024.01.24)
+ CommandOrderControllerTest에서 주문 성공, 실패를 테스트하는 과정에서 응답값인 data부분이 null이 반환되는 문제 발생
+ 인자로 any()를 넣어 해결
+ 가급적 any(OrderRequestDto.class) 와 같이 클래스를 명시해 주는게 좋음.
