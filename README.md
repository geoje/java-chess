## 📝 구현 기능 목록

### 🎯 체스판

- [x] 체스판의 크기는 `8 x 8`이다.
- [x] 체스판에서 말의 위치 값은 다음과 같이 구현한다.
    - 가로 위치는 파일이라 하며 왼쪽부터 오른쪽으로 `a ~ h`
    - 세로 위치는 랭크라 하며 아래부터 위로 `1 ~ 8`
- [x] 체스판에서 각 진영의 말들을 알파벳으로 구분한다.
    - 검은색 진영: 대문자 `R N B Q K P`
    - 흰색 진영: 소문자 `r n b q k p`
- [x] 게임 시작 시 총 32개의 기물을 아래와 같이 초기화 한다.
    ```
    RNBQKBNR  8 (rank 8)
    PPPPPPPP  7
    ........  6
    ........  5
    ........  4
    ........  3
    pppppppp  2
    rnbqkbnr  1 (rank 1)
    
    abcdefgh
    ```
- [x] 체스판 위에 있는 기물을 움직일 수 있다.
    - [x] 흰색 기물 부터 움직일 수 있다.
    - [x] 한 턴씩 번갈아 움직일 수 있다.

### ♟️ 기물

- [x] 출발지와 목적지를 받아 이동 방식에 따라 움직일 수 있는지 판별한다.
- [x] 목적지로 가는 경로에 다른 기물이 있는지 판별한다.
    - [x] 단, 나이트는 제외한다.
- [x] 각 기물의 점수를 계산한다
    - [x] 점수는 queen은 `9`점, rook은 `5`점, bishop은 `3`점, knight는 `2.5`점, pawn은 `1`점, king은 `0`점 이다.
    - [x] 단, pawn은 같은 세로줄에 같은 색의 폰이 있는 경우 `0.5`점으로 계산한다.

### 🕹️ 체스 게임

- [x] 사용자의 명령어에 따라 진행한다.
    - [x] `start` 게임을 시작하며 초기 보드를 출력한다.
    - [x] `resume [방번호]` 끝나지 않았던 게임을 이어 진행한다.
    - [x] `room` 끝나지 않는 모든 방 번호와 플레이어들을 출력한다.
    - [x] `user [유저이름]` 해당 유저의 전적과 진행중인 방을 출력한다.
    - [x] `move [source위치] [target위치]` source위치의 기물을 target으로 이동한다.
    - [x] `status`를 입력 받으면 아래 내역들을 출력한다.
        - 방 번호
        - 각 진영의 점수
    - [x] `end`를 입력 받으면 게임을 종료한다.
        - [x] king 이 잡힐 경우에도 게임이 종료된다.
- [x] 이전에 진행했던 게임을 계속 이어할 수 있다.
    - [x] 게임 실행 전 이전 게임의 상태를 불러온다.
    - [x] 기물이 정상적으로 움직였을 경우 움직임을 저장한다.

---

## 🗃️ 데이터베이스
프로그램 실행 시 아래 정의된 `DDL`이 실행되어 테이블이 자동으로 생성됩니다.

연결 정보는 [DatabaseConfiguration.java](https://github.com/geoje/java-chess/blob/step2/src/main/java/chess/db/DatabaseConfiguration.java) 에서 수정 가능 합니다.


### DDL
``` sql
CREATE DATABASE IF NOT EXISTS chess DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE chess;

CREATE TABLE IF NOT EXISTS room
(
    id         INT AUTO_INCREMENT,
    user_white VARCHAR(20) NOT NULL,
    user_black VARCHAR(20) NOT NULL,
    winner     VARCHAR(20) NOT NULL DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS move
(
    room_id    INT     NOT NULL,
    source     CHAR(2) NOT NULL,
    target     CHAR(2) NOT NULL,
    created_at TIMESTAMP(3) DEFAULT NOW(3),
    FOREIGN KEY (room_id) REFERENCES room (id)
);
```

### Entity Relationship Diagram
``` mermaid
erDiagram
    room ||--o{ move : "room_id:id"
    room {
        int id
        varchar(20) user_white
        varchar(20) user_black
        varchar(20) winner
    }
    move {
        int room_id
        char(2) source
        char(2) target
        timestamp(3) created_at
    }
```
