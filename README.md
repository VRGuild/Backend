# VRGuild Backend
VR길드(DevLink) 백엔드 서버

## 목적
- VRGuild(DevLink) 서비스를 위한 API 서버를 구축한다.

## 역할 분배
- 박인성(Hexeong)
  - RDB와 NoSQL의 schema / document 구조 공동 설계
      - DDD 기반의 Bounded Context 별로 도메인을 분리
      - 도메인 별로 적합한 형태의 DBMS를 선택 및 설정 → map 도메인(NoSQL), user 도메인(RDB)
  - 유저 인증 로직 구현
      - 에픽 게임즈 기반 JWT 검증 로직 구현 → 사용자의 번거로운 가입 순서를 간소화함.
      - Scheduler를 사용한 에픽게임즈 JWK 갱신 로직 구현
      - SpringSecurity 기반 JWT 검증 Filter 구현
  - Java - Spring 프레임워크로 전반적인 도메인(user, project, map 등등)에 대한 CRUD API 개발 및 TestCode 작성
  - AWS S3 파일 객체 스토리지 환경설정 및 파일 CRUD 로직 구현

