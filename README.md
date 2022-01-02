# 환율 계산

### currencylayer.com 의 API를 사용한 웹화면 구현
![image](https://user-images.githubusercontent.com/49123158/147879507-aa82d872-d32d-482c-8e52-1e79e0553730.png)


### BACK END

- **CurrencyApiInterface**
  - currencylayer.com이 제공하는 ENDPOINT에 따른 메서드 정의
  - live, convert, historical, timeframe, change, list

<br>

- **CurrencyApi**
  - CurrencyApiInterface를 상속받아 기능을 구현, api에서 제공되는 live, list 구현

<br>

- **CalculatorController**
  - 환율 계산 화면("/")
  - 환율 계산 요청("/convert")

<br>

- **getCurrencyListService**
  - 화면 로딩시 필요한 환율 정보를 조회하는 서비스
  - 환율 계산 서비스
 
<br>
 
- **CalculatorDto**
  - 환율 계산에 필요한 DTO 클래스, 내부 클래스로 Req, Res 클래스로 분할함

<br>
 
<hr>

### FRONT END

- **calculatorTemplate.html**
  - 환율 계산 화면

<br>
 
- **calculatorTemplate.js**
  - 화면에서 필요한 셋팅 값이나 이벤트를 설정

<br>
 
- **common.js**
  - 공통 기능 (ajax 호출)

<br>
 
- **format.js**
  - 숫자 포멧팅에 관련된 기능

<br>
