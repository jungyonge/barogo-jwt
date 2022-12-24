# barogo 과제테스트

## 요구사항
~~~
회원 가입, 로그인, 배달 조회, 배달 주문 수정 서비스를 위한 Back-End API 를 정의하고, 
	JAVA (Spring Boot) 프로젝트로 구현해 주세요.

전제조건 (1) : 회원 가입 API를 구현해 주세요.
1.	회원가입시 필요한 정보는 ID, 비밀번호, 사용자 이름 입니다.
2.	비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 
		12자리 이상의 문자열로 생성해야 합니다.

전제조건 (2) : 로그인 API 를 구현해 주세요.
1.	사용자로부터 ID, 비밀번호를 입력받아 로그인을 처리합니다.
2.	ID와 비밀번호가 이미 가입되어 있는 회원의 정보와 일치하면 로그인이 되었다는 
		응답으로 AccessToken 을 제공합니다.

전제조건 (3) : 배달 조회 API 를 구현해 주세요.
1.	배달 조회 시 필요한 정보는 기간 입니다. (기간은 최대 3일)
2.	기간 내에 사용자가 주문한 배달의 리스트를 제공합니다.

전제조건 (4) : 배달 주문 수정 API (도착지 주소 변경) 를 구현해 주세요.
1.	사용자로부터 도착지 주소를 요청 받아 처리합니다.
2.	사용자가 변경 가능한 배달인 경우에만 수정이 가능합니다.

기타
1.	DataBase 를 반드시 사용해주세요. 
	(필수 데이터 외에 추가가 기본적으로 필요하다고 생각되는 데이터들도 같이 설계 해주세요.)
2.	Spring MVC 기반으로 전체 Application 설계를 해주세요.
3.	최대한 많은 예외케이스를 표현해 주세요.
4.	AccessToken 은 특별한 사유가 없다면 JWT 를 적용해주세요.
5.	Test Code 는 작성이 가능하다면 작성해주세요.
6.	API 명세서 작성이 가능하다면 같이 제공해주세요.
~~~

## 개발 환경
- 기본 환경
    - IDE: IntelliJ IDEA
    - OS: Mac
    - GIT
- Server
    - Java11
    - Spring Boot
    - JPA
    - gradle
    - h2

## 빌드. 실행
Intellij 기준입니다.
- 빌드 ```./gradlew clean build```
- 실행 ```디버그 모드로 실행(control + D)```

## 전체로직
![](./apilogic.png)

## API 명세서 

----
### /api/v1/user
회원 관련 API 요청요청 
* **섦명**
  
  회원가입요청

* **URL**

  /api/v1/user/signup

* **Method:**

  `POST`

* **URL Params**

    None

* **Data Params**

  - username
  - password
  - nickname
  - usertype

* **Success Response:**

    * **Code:** 200 <br />
      **Content:** `{ username : "admin", nickname : "admin" }`

* **Error Response:**

    * **Code:** 500 BAD REQUEST <br />
      **Content:** 
    * `{
      "errorCode": 10001,
      "errorMessage": "이미 가입된 아이디 입니다."
      }`
----
### /api/v1/authorization
인증관련 요청
* **설명**

  로그인

* **URL**

  /api/v1/authorization/auth

* **Method:**

  `POST`

* **URL Params**

  None

* **Data Params**

  - username
  - password

* **Success Response:**

  * **Code:** 200 <br />
    **Content:**
  * `{
      "access_token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdW5neW9uZyIsImF1dGgiOiJST0.eugvzXbesInB4WlmLRA",
      "refresh_token": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjMzd9.5QmXRB5svPqmm_v74RqP9g4_gb4pTkmvRwLhQ"
      }`

* **Error Response:**

  * **Code:** 500 BAD REQUEST <br />
    **Content:**
  * `{
    "errorCode": 10007,
    "errorMessage": "사용자가 존재하지 않거나 비밀번호가 틀렸습니다."
    }`
----- 

* **설명**

  access_token 재발급요청

* **URL**

  /api/v1/authorization/regeneration

* **Method:**

  `POST`

* **URL Params**

  None

* **Data Params**

  - access_token
  - refresh_token

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  * `{
    "access_token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdW5neW9uZyIsImF1dGgiOiJST0.eugvzXbesInB4WlmLRA",
    "refresh_token": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjMzd9.5QmXRB5svPqmm_v74RqP9g4_gb4pTkmvRwLhQ"
    }`

* **Error Response:**

  * **Code:** 500 BAD REQUEST <br />
    **Content:**
  * `{
    "errorCode": 10006,
    "errorMessage": "만료된 JWT refresh token 토큰입니다. 로그인을 다시 해주세요."
    }`

---- 
### /api/v1/delivery
배달관련 요청
* **설명**

  배달조회

* **URL**

  /api/v1/delivery

* **Method:**

  `GET`

* **URL Params**

  **Required:**

  `searchStartDate=[yyyy-MM-dd]`

  `searchEndDateTime=[yyyy-MM-dd]`

  **Optional:**

  `shopId=[long]`

  `deliveryStatus=[String]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:**
  *`[
    {
    "id": 2,
    "status": "COURIER_QUEUEING",
    "deliveryAddress": "수원",
    "shopId": 1,
    "shopName": null,
    "userId": 3,
    "created": "2022-12-20 12:00:00",
    "updated": "2022-12-20 12:00:00"
    }
  ]`

* **Error Response:**

  * **Code:** 500 BAD REQUEST <br />
    **Content:**
  * `{
    "errorCode": 10001,
    "errorMessage": "3일 이상은 조회가 불가능 합니다."
    }`

    
---

* **설명**

  배달주소변경

* **URL**

  /api/v1/delivery/address

* **Method:**

  `POST`

* **URL Params**

  None

* **Data Params**

  - id
  - newDeliveryAddress

* **Success Response:**

  * **Code:** 200 <br />
    **Content:**
  * 
    `
    {
    "id": 2,
    "deliveryAddress": "수원",
    "shopId": 1,
    }
    `

* **Error Response:**

  * **Code:** 500 BAD REQUEST <br />
    **Content:**
  * `{
    "errorCode": 10002,
    "errorMessage": "배달원 배차 전에만 주소변경이 가능합니다."
    }`
