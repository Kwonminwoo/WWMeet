# WWMeet
약속 시간과 장소를 정해주는 서비스
When Where Meet

## 🏠 기간, 참여인원
* v1: 2023.07 ~ 2023.12
* v2: 2024.03 ~ 
  
|                                         Backend & Android                               |                                        AI & Android                                    |                                       
|:---------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------:|
| <img src="https://avatars.githubusercontent.com/u/34360434?v=4" width=130px alt="민우"/> | <img src="https://avatars.githubusercontent.com/u/85169153?v=4" width=130px alt="기수"/> |
|                            [민우](https://github.com/Kwonminwoo)                             |                          [기수](https://github.com/Hangisu)                           |


<br/>

## 👷‍♂️ 권민우 구현 내용
- 전체 적인 기획
  - ERD 설계
  - Figma를 이용한 흐름도 설계
- RESTful API 개발
  - 약속 생성, 약속 조회, 약속 입장
  - 로그인, 회원가입
- 인증/인가 구현
  - Spring Security를 이용
  - JWT를 이용해 로그인 유지
  - Redis를 이용한 refresh 토큰 활용
- 네이버 지도 API 사용
- 동적 웹 크롤링

<br>

## 🪛 기능
- 약속 조회
  - 참가하고 있는 약속 전체 조회
  - 하나의 약속 조회
- 약속 생성
- 약속 참여
  - 참여 코드를 통해 약속에 참여
- 투표
  - 참여 중인 약속의 일정을 투표
  - 약속 장소를 지도로 투표
- 맛집 조회
  - 투표 완료 된 약속의 약속 장소의 맛집 추천

<br>

## ⛰️ 구현 환경
* Java 17
* Spring Boot 3.1.5
* JPA
* Spring Security + JWT
* Redis
* MySQL
* Android - java
