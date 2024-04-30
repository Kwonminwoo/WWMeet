# WWMeet
약속 시간과 장소를 정해주는 안드로이드 어플리케이션
When Where Meet

## 🏠 기간
* v1: 2023.07 ~ 2023.12
* v2: 2024.03 ~
  
## 👷‍♂️ 권민우 구현 내용
- 전체 기획
  - ERD 설계
  ![wwmeet (2)](https://github.com/Kwonminwoo/WWMeet/assets/34360434/3731ae64-46fd-4aff-b44c-81158b9179d7)

  - Figma를 이용한 흐름도 설계
  ![image](https://github.com/Kwonminwoo/WWMeet/assets/34360434/2f232bf0-aa06-41cf-bc15-4d11c405dde4)

- RESTful API 개발
  - 약속 생성, 약속 조회, 약속 입장
  - 로그인, 회원가입
- 인증/인가 구현
  - Spring Security를 이용
  - JWT를 이용해 로그인 유지
  - Redis를 이용한 refresh 토큰 활용
- 네이버 지도 API 사용
- 동적 웹 크롤링

## 🪛 기능
- 인증/인가
  - 로그인/로그아웃
  - 회원가입
- 약속 조회
  - 참가하고 있는 약속 전체 조회
  - 하나의 약속 조회
- 약속 생성
- 약속 참여
  - 참여 코드를 이용한 약속 참여
- 투표
  - 원하는 약속 일정을 투표
  - 지도에서 원하는 약속 장소를 선택 
- 맛집 조회
  - 카카오 검색 API를 이용한 약속 장소의 맛집 조회

## ⛰️ 구현 환경
* Java 17
* Spring Boot 3.1.5
* JPA
* Spring Security + JWT
* Redis
* MySQL
* Android - java
