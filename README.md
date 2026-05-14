# VRGuild Backend

VRGuild(DevLink)는 개발자와 기획자, 아티스트, 기업 사용자가 프로젝트와 팀을 만들고 협업할 수 있도록 지원하는 커뮤니티형 협업 플랫폼입니다. 이 저장소는 DevLink 서비스의 Spring Boot 백엔드 API 서버이며, Epic Games OAuth 기반 사용자 인증, 프로젝트/길드/팀/지원/평가 도메인, 메타버스 공간 데이터, 파일 업로드, 배포 자동화까지 담당합니다.

## 프로젝트 개요

- 개발 기간: 2024.09 ~ 2024.11
- 주요 영역: Backend / Infra
- 핵심 구성: Spring Boot API, MySQL/MongoDB 저장소 분리, Epic Games JWT 검증, S3 파일 업로드, GitHub Actions 기반 AWS 배포 자동화
- 배포 환경: Docker, Amazon ECR, AWS Elastic Beanstalk

## 주요 기능

- Epic Games OAuth2 로그인 및 JWT 검증
- 개발자/기업 사용자 프로필 관리
- 프로젝트 등록, 조회, 지원, 팀 수락
- 길드 생성, 가입 신청, 멤버 관리
- 팀 생성, 팀원 관리, 지원 현황 조회
- 프로젝트 댓글, 질문, 답변, 평가 관리
- 캐릭터, 경험치, 재화(Ether) 등 서비스 내 사용자 활동 데이터 관리
- 채널, 타일, 메모, PDF, 오브젝트 정보 등 메타버스 공간 데이터 관리
- AWS S3 기반 파일 업로드 및 URL 저장
- Swagger/OpenAPI 기반 API 문서 제공
- `/health` 엔드포인트 기반 로드 밸런서 상태 확인

## 기술 스택

| 영역 | 기술 |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 3.3.4, Spring MVC, Spring Security |
| Persistence | Spring Data JPA, Spring Data MongoDB |
| Database | MySQL, MongoDB |
| Auth | Epic Games OAuth2, JWT, JWK |
| API Docs | Springdoc OpenAPI, Swagger UI |
| File Storage | AWS S3 |
| Infra | Docker, Amazon ECR, AWS Elastic Beanstalk |
| CI/CD | GitHub Actions |
| Build | Gradle |

## 아키텍처

```text
Client
  |
  | REST API + Bearer Token
  v
Spring Boot Application
  |
  +-- Security Layer
  |     Spring Security
  |     JwtAuthenticationFilter
  |     Epic Games JWK cache
  |
  +-- API Layer
  |     Controller
  |     Request / Response DTO
  |     Swagger OpenAPI
  |
  +-- Domain Layer
  |     User / Dev / Business
  |     Project / Team / Guild / Member / Support
  |     Channel / Tile / Memo / PDF / Object
  |     Comment / Reply / Question / Evaluation
  |
  +-- Persistence Layer
  |     JPA Repository -> MySQL
  |     MongoRepository -> MongoDB
  |
  +-- External Services
        Epic Games OAuth API
        AWS S3
```

## 저장소 분리 전략

서비스 데이터 성격에 따라 MySQL과 MongoDB를 함께 사용합니다.

- MySQL: 사용자, 개발자/기업 프로필, 프로젝트, 팀, 길드, 지원, 평가처럼 관계와 무결성이 중요한 데이터
- MongoDB: 채널, 타일, 메모, PDF, 오브젝트처럼 메타버스 공간에서 구조가 유동적이고 문서 형태로 다루기 쉬운 데이터

이 구조를 통해 사용자/프로젝트 중심의 관계형 데이터는 JPA로 안정적으로 관리하고, 공간 편집 데이터는 MongoDB 문서로 유연하게 저장합니다.

## 인증 구조

Epic Games OAuth2를 기반으로 사용자 인증을 처리합니다.

1. 클라이언트가 Epic Games 인증 후 Bearer Token을 API 요청에 포함합니다.
2. `JwtAuthenticationFilter`가 요청의 Authorization Header에서 토큰을 추출합니다.
3. `JwtUtil`이 Epic Games JWK를 이용해 JWT 서명, issuer, audience, iat, exp를 검증합니다.
4. 검증된 `sub` 값을 Epic Account ID로 사용해 사용자 데이터를 조회합니다.
5. JWK는 `EpicGamesJWKCache`에서 주기적으로 갱신합니다.

## 주요 도메인

- User: Epic 계정 기반 사용자 정보
- Dev / Business: 개발자와 기업 사용자 프로필
- Project: 프로젝트 등록, 목록, 상세, 지원/수락 흐름
- Team / Member / Support: 팀 구성과 프로젝트 지원 관리
- Guild: 길드 생성, 가입 신청, 구성원 조회
- Channel / Tile / Memo / PDF / Object: 메타버스 공간 편집 데이터
- File: S3 업로드 파일 URL 저장
- Comment / Reply / Question: 커뮤니티 상호작용
- Evaluation / Experience / Ether: 평가, 경험치, 서비스 내 재화

## 역할 분배

### 박인성(Hexeong)

- RDB와 NoSQL의 schema / document 구조 공동 설계
  - DDD 기반 Bounded Context 별로 도메인 분리
  - 도메인별 특성에 맞는 DBMS 선택 및 설정
  - map 도메인은 NoSQL, user 도메인은 RDB 중심으로 구성
- 유저 인증 로직 구현
  - Epic Games 기반 JWT 검증 로직 구현
  - Scheduler를 사용한 Epic Games JWK 갱신 로직 구현
  - Spring Security 기반 JWT 검증 Filter 구현
- Java/Spring 기반 주요 도메인 CRUD API 개발 및 테스트 코드 작성
- AWS S3 파일 객체 스토리지 환경 설정 및 파일 CRUD 로직 구현

### 내 담당 영역

이력서와 저장소 구현 기준으로 별도 정리한 담당 영역입니다.

- Spring Boot 기반 DevLink 백엔드 API 설계 및 구현 참여
- 프로젝트, 팀, 길드, 지원, 사용자 등 주요 서비스 도메인 API 구현
- MySQL/JPA 기반 관계형 데이터와 MongoDB 문서형 데이터 연동 구조 적용
- AWS 배포 흐름 구성 참여
  - Docker 이미지 빌드
  - Amazon ECR 이미지 push
  - Elastic Beanstalk 배포
  - `Dockerrun.aws.json` 기반 컨테이너 실행 설정
- GitHub Actions 기반 `dev` 브랜치 자동 배포 파이프라인 구성
- S3 파일 업로드 및 URL 저장 흐름 연동
- Swagger/OpenAPI 기반 API 문서화 및 협업용 API 명세 정리
- 도메인별 Controller, Service, Repository 테스트 코드 작성 및 검증

## CI/CD

`dev` 브랜치에 push되면 GitHub Actions가 배포 파이프라인을 실행합니다.

```text
push to dev
  -> JDK 17 설정
  -> Gradle build
  -> Docker image build
  -> Amazon ECR push
  -> Dockerrun.aws.json 패키징
  -> Elastic Beanstalk deploy
```

Elastic Beanstalk는 `Dockerrun.aws.json`을 통해 ECR의 `devlink-server:latest` 이미지를 가져오고, 컨테이너 포트 `8443`으로 애플리케이션을 실행합니다.

## 실행 방법

### 로컬 실행

```bash
./gradlew bootRun
```

Windows 환경에서는 다음 명령을 사용할 수 있습니다.

```bash
gradlew.bat bootRun
```

### 빌드

```bash
./gradlew clean build
```

테스트를 제외하고 빌드하려면 다음 명령을 사용합니다.

```bash
./gradlew clean build -x test
```

### Docker

```bash
./gradlew clean build -x test
docker build -t devlink-server .
docker run -p 8443:8443 --env-file .env devlink-server
```

## 환경 변수

`application.yml`은 다음 환경 변수를 참조합니다.

```env
MYSQL_URL=
MYSQL_USERNAME=
MYSQL_PASSWORD=

MONGO_USERNAME=
MONGO_PASSWORD=
MONGO_HOST=
MONGO_PORT=
MONGO_DATABASE=

EPIC_GAMES_CLIENT_ID=
EPIC_GAMES_CLIENT_SECRET=
EPIC_GAMES_DEPLOYMENT_ID=

AWS_S3_BUCKET_NAME=
AWS_S3_REGION=
AWS_S3_BUCKET_ACCESS_KEY=
AWS_S3_BUCKET_SECRET_KEY=

MAIL_USERNAME=
MAIL_PASSWORD=
```

## API 문서

애플리케이션 실행 후 Swagger UI에서 API 문서를 확인할 수 있습니다.

```text
http://localhost:8443/swagger-ui.html
```

상태 확인 엔드포인트는 로드 밸런서 헬스 체크에 사용됩니다.

```text
GET /health
POST /health
```

## 테스트

도메인별 CRUD 테스트가 `src/test/java/com/mtvs/devlinkbackend/crud` 아래에 구성되어 있습니다.

```bash
./gradlew test
```

외부 DB, MongoDB, S3, Epic Games OAuth 설정이 필요한 테스트는 로컬 환경 변수와 테스트 인프라가 준비되어 있어야 정상 동작합니다.
