# WWMeet
약속 시간과 장소를 정해주는 서비스
When Where Meet

## 기간, 참여인원
* v1: 2023.07 ~ 2023.12
* v2: 2024.03 ~ 

|                                         Backend & Android                               |                                        AI & Android                                    |                                       
|:---------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------:|
| <img src="https://avatars.githubusercontent.com/u/34360434?v=4" width=130px alt="민우"/> | <img src="https://avatars.githubusercontent.com/u/85169153?v=4" width=130px alt="기수"/> |
|                            [민우](https://github.com/Kwonminwoo)                             |                          [기수](https://github.com/Hangisu)                           |


<br/>

## 구현 환경
* Java 17
* Spring Boot 3.1.5
* JPA
* Spring Security + JWT
* Mysql
* gradle

## 구현 내용
- 전체 적인 기획
  - ERD 설계
  - Figma를 이용한 흐름도 설계
- RESTful API 개발
  - 약속 생성, 약속 조회, 약속 입장
  - 로그인, 회원가입
- 인증/인가 구현
  - Spring Security를 이용
  - JWT를 이용해 로그인 유지
- 네이버 지도 API 사용
- 동적 웹 크롤링
