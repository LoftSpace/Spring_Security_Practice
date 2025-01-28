## 스프링 시큐리티를 이용한 해외 송금 구현 해보기
### 아키텍처
    MVC 패턴
    컨트롤러: user(로그인,회원가입처리), transfer(request,quote처리)
    서비스 : 
        UserService          TransferService
            |                        |
        user crud                    |
                                /         \
            CurrencyServiceFactory     QuoteService
                     |                       |
                /         \                quote crud
            USDService    JPYService  
            usd 환율 계산   jpy 환율 계산 
            usd 수수료 계산  jpy 수수료 계산
    추후 새로운 통화에 대한 확장성을 위해 통화 서비스를 인터페이스로 두고 
    각 통화에 대해 구현
### 2. ERD
    User table - Quote table (1:N)
    ----------   -----------
    userId       quoteId
    pw           userId
    idValue       ...
    idType
    
    논리적 fk를 위하여 수정,삽입,제거시마다 동일한 트랜잭션 내에서 무결성을 확보하도록 구현
### 2. 회원가입 api , 로그인 api
    spring security로 구현(jwt토큰 사용)
    비밀번호, userIdValue 암호화(bcrypt 알고리즘 사용)
### 3. 송금견적서 가져오기
    외부 api로 환율 받아오기 : restTemplate으로 구현 (비동기로 구현하고 싶었으나 webflux를 다뤄보지 않아 익숙한 restTemplate을 사용했습니다)
    환율 계산 : 부동소수점을 고려하여 (10^통화의 환율 자리수) 이용
    
### 4. 송금 접수 요청 API
    해당 api 실행시 각 조건에 대하여 검증 후 송금 접수 진행
    송금 접수 진행시 User table의 오늘 송금횟수, 금액 업데이트 (하나의 트랜잭션)

