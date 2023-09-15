<div align="center">
  <h1>🐶 PET병 <h3> Pet Community for Everyone </h3></h1>
</div>

<div align="right">last update : 2023.9.15.</div>

---
---
# 1. 프로젝트 소개 

## 개요

>반려동물 관련 콘텐츠 및 동물병원 찾기 서비스를 제공하는 웹페이지로,
>반려동물을 뜻하는 PET과 병(illness)의 발음을 이용해 **[PET병]** 이라는 이름의 프로젝트를 만들게 되었다.
>
>We are creating a project called '[Pet병]' using the pronunciation of PET, which means a companion animal, and '병(illness)' to provide a web service for pet-related content and finding animal hospitals.

## 배포 주소
>PetCommunity : https://pet-community.net/

## 프로젝트 기간
>스파르타코딩클럽 내일배움캠프 Spring 6기  
>2023.08.16. - 2023.09.18. ~ (last update date)

## 프로젝트 멤버
>송이삭  
>최혁진  
>지은영  
>최종우 ( [github](https://github.com/Jonggae) )

# 2. 프로젝트 실행 환경

## Requirements

### for building and running the application you need:

- Use IntelliJ Ultimate
- Windows 11 
- SpringBoot 3.1.2
- java 17
- other back-end stacks

# 3. 기술 스택

---
#### Front-End

<div>
<img src="https://img.shields.io/badge/Vue.js-4FC08D?style=flat&logo=vue.js&logoColor=white">
<img src="https://img.shields.io/badge/HTML-E34F26?style=flat&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/CSS-1572B6?style=flat&logo=css3&logoColor=white">
<img src="https://img.shields.io/static/v1?style=flat&message=JavaScript&color=F7DF1E&logo=JavaScript&logoColor=000000&label=">
</div>

#### Back-End

<div>
<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=OpenJDK&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=Spring&logoColor=white">
<img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=flat">
<img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=flat&logo=springsecurity&logoColor=white"/><br>
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white"/>
<img src="https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white"/>
<img src="https://img.shields.io/badge/JWT-A9225C?style=flat">

<img src="https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/Hibernate-59666C?style=flat&logo=Hibernate&logoColor=white"/>
<img src="https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=Postman&logoColor=white"/>
<img src="https://img.shields.io/badge/QueryDSL-40AEF0?style=flat">
<img src="https://img.shields.io/badge/Amazon_S3-569A31?style=flat&logo=amazons3&logoColor=white"/>
<img src="https://img.shields.io/badge/H2-E91E63?style=flat"/>
</div>

#### Server

<div>
<img src="https://img.shields.io/static/v1?style=flat&message=Amazon+ECS&color=222222&logo=Amazon+ECS&logoColor=FF9900&label=">
<img src="https://img.shields.io/static/v1?style=flat&message=Amazon+Route+53&color=8C4FFF&logo=Amazon+Route+53&logoColor=FFFFFF&label=">
<img src="https://img.shields.io/badge/Amazon_CloudFront-569A31?style=flat"/>
<img src="https://img.shields.io/badge/Amazon_RDS-527FFF?style=flat&logo=amazonrds&logoColor=white"/>
<img src="https://img.shields.io/badge/Amazon_EC2-FF9900?style=flat&logo=amazonec2&logoColor=white"/>
<img src="https://img.shields.io/badge/Linux-FCC624?style=flat&logo=linux&logoColor=black">
<img src="https://img.shields.io/badge/Amazon_S3-569A31?style=flat&logo=amazons3&logoColor=white"/>
<img src="https://img.shields.io/static/v1?style=flat&message=GitHub+Actions&color=2088FF&logo=GitHub+Actions&logoColor=FFFFFF&label="> 
</div>

#### Etc

<div>
<img src="https://img.shields.io/static/v1?style=flat&message=IntelliJ+IDEA&color=D14836&logo=IntelliJ+IDEA&logoColor=FFFFFF&label=">
<img src="https://img.shields.io/badge/Github-181717?style=flat&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/Slack-4A154B?style=flat&logo=slack&logoColor=white">
<img src="https://img.shields.io/badge/Notion-000000?style=flat&logo=notion&logoColor=white">
</div>

---

***

# 4. Service Architecture

---
<img src="image/ServiceArchitecture_v3.png">

# 5. 주요 기능

## 동물 병원 등록 / 검색
- 병원 운영진에게는 동물 병원을 등록할 수 있는 기능을 제공
- 반려동물 보호자는 등록된 병원을 검색하여 예약 할 수 있음

## 동물 전문가와의 실시간 채팅
- 등록된 병원과 직접 1:1 채팅이 가능

## 반려 동물 커뮤니티 / 팁 게시판
- 사용자들은 자신의 반려동물 사진을 올려 자랑하거나 공유할 수 있음.
- 동물 키우기에 필요한 정보들을 게시판으로 올릴 수 있음.

## 반려 동물 서비스 
- 기타 반려 동물 서비스 (식품 가게, 미용 등) 위치 제공

## 부가 기능
- 게시글 댓글, 좋아요 (팁 게시판 댓글 제외)
- 병원 진료 에약 후 시간이 지나면 별점, 리뷰 작성 가능
- 회원 탈퇴 시 사용자 정보, 게시글, 리뷰 삭제 / 요청시 복원 가능
- 카카오, 구글, 네이버 소셜 로그인
- 기타 세부 기능

***

# 6. 화면 구성
> 프론트엔드 repository 참고
