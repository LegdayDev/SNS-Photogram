# Photogram
![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/32c9715b-693d-4d67-b238-04f4da7b7904)
- SNS 인스타그램과 유사한 프로젝트
## 기술 스택(Stacks)
### Back-end
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">(Version: 11)
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">(Version: 2.4.5)
<img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white">(Version: 11.1.2)

### Tool
<img src="https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=IntelliJ&logoColor=white"> <img src="https://img.shields.io/badge/DBeaver-40AEF0?style=for-the-badge&logo=DBeaver&logoColor=white"> <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white">

### Build Tool
<img src="https://img.shields.io/badge/maven-C71A36?style=for-the-badge&logo=maven&logoColor=white">

### Configuration Tool
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"> <img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white">

### dependency
- Sring Boot DevTools
- Lombok
- Spring Data JPA
- MariaDB Driver
- Spring Security
- Security Tags
- Validation
- Spring Web
- oauth2-client
- AOP
- QLRM
## DB-Diagram
![SNS_Photogram_DB](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/f2e80ee9-4d79-4209-9a8a-62a9bd7c1510)
## ER-Diagram
<img width="753" alt="ER-Diagram" src="https://github.com/LegdayDev/SNS-Photogram/assets/108314675/b4207047-c8cb-4470-b163-9f543016cd5d">

---
## 구현 기능
1. 로그인/로그아웃 : SpringSecurity 를 이용한 로그인/로그아웃, OAuth2.0 을 이용한 페이스북 로그인 구현
2. 이미지 업로드 : 프로젝트 외부 폴더에서 이미지를 저장하여 관리
3. 비밀번호 암호화 : 비밀번호를 암호화해서 DB 에 저장되도록 구현
4. Ajax 를 통한 API 통신 : 댓글 등록, 좋아요, 구독/구독취소 등 페이지를 리로드 하지 않고 데이터를 변경하는데 사용
5. 프로필 이미지 등록 : 이미지 파일이 아닌 다른 확장자 파일 업로드 시 오류메시지 출력
6. 페이징 : 싱글페이지를 구현
7. 좋아요 순으로 인기 게시물 나열
8. Validation 은 Spring Validation 이용, Exception 은 직접 CustomException 을 만들고 등록하여 예외처리 구현
9. AOP 구현 : Validation, Exception 처리를 AOP 로 구현

## 화면구성
#### 메인화면(비 로그인 시)
- `localhost:8080/auth/signin`

   ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/e1ddc163-f05f-4543-85a3-efd717b3412c)
#### 회원가입 화면
- `localhost:8080/auth/signup`

   ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/2cc80641-31a6-4a63-8407-5a3e7b54dbee)
#### 메인화면(로그인 시)
- `localhost:8080`
- 메인 화면에서는 다른 사용자의 게시물이 보이고 우측 상단에 버튼으로 `홈화면`,`인기게시물`,`프로필페이지` 로 이동이 가능하다.
- 댓글을 등록할 수 있고 댓글사용자만 댓글 삭제가 가능한 버튼이 생긴다.
  
  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/d2e0ea27-36ff-458a-b595-001d1dd64ffa)
- 게시물에 하트 버튼을 누르면 빨갛게 변하면서 좋아요 수가 늘어난다.
  
  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/59dae930-2661-4975-aae4-773c3356d7c5)
#### 인기 게시물
- `localhost:8080/image/popular`
- 메인페이지에서 우측 상단에 가운데 있는 아이콘 클릭 시 이동가능
- 좋아요 수가 많은 순서대로 게시물이 나열된다.
- 게시물 클릭 시 게시물 작성자 프로필 페이지로 이동.

  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/696ce378-76f9-47c1-86f2-6c0456ad6a31)
#### 프로필 페이지(본인)
- `localhost:8080/user/{userId}
- 로그인한 유저의 프로필 페이지 방문시 사진등록 버튼과 프로필 수정 아이콘이 나타난다.
- 그리고 자기소개와 웹사이트주소가 있고 아래에는 게시물이 나타난다.

  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/d55fb6a5-f5c8-4cf5-bf98-d28fbf0db006)
- 톱니바퀴 모양의 아이콘을 클릭하면 회원정보수정 or 로그아웃 모달창이 나타난다.

  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/a6bcfb45-8686-43ab-bd07-076f5f52cdcc)

- `구독정보` 를 누르면 구독목록이 모달창으로 나타난다.

  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/b05ea178-0433-4e60-83b2-1aeee1c9b12d)

- 프로필 이미지를 클릭하면 모달창이 나오고 사진업로드를 누르면 프로필 이미지를 변경할 수 있다.

  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/29b077aa-4ec8-4ee9-8020-61d2620f6668)

#### 프로필 페이지(다른 유저)
- 다른 유저의 프로필 페이지에 가면 구독하기 버튼이 나타난다.

  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/ed80281f-8787-4e5e-bd5f-0428aec6fb9f)

#### 이미지 업로드 페이지
- `localhost:8080/image/upload`
- 프로필 페이지에서 이동 가능
- 이미지와 설명을 작성할 수 있다.

  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/1f7e75b8-6d23-47fa-a247-73340c76a0d9)

#### 회원 정보 수정 페이지
- `localhost:8080/user/{userId}/update`
- 반드시 비밀번호를 입력해야 제출할 수 있도록 구현했다.

  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/2ed53d99-5982-4946-8509-67b987338210)
#### 게시물 상세 페이지
- `localhost:8080/image/detail/{imageId}`
- 본인 게시물일 경우 설명을 수정할 수 있는 수정 버튼과 이미지를 삭제할 수 있는 삭제 버튼이 나타난다.

  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/1bf5cf1d-b946-40a7-b2ea-cb7646ae64a9)

- 다른 이용자의 게시물일 경우 이미지와 설명만 나타난다.

  ![image](https://github.com/LegdayDev/SNS-Photogram/assets/108314675/e3a166fe-ccab-4a39-b573-653c0ba40596)
