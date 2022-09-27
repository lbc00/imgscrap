# 이미지스크랩 프로젝트

### 개요
이미지 스크랩 서비스의 Backend 서버 기능 REST API로 구현. 외부 이미지를 스크랩하여 로컬 시스템에 저장하고 수정, 삭제가 가능하며 다른 사용자들이 볼 수 있음

### 개발환경
1. 기본 구현

    jdk : openjdk 11

    Spring Tool Suite(sts) : 4.13.1.RELEASE

    Spring Boot : 2.7.3

    H2 Database : 1.4.197
    
    SQL MAPPER : Mybatis

2. 선택 구현

    jms : ActiveMQ

    redis : Embedded-redis 0.7.3
    
### 기본설정
1. DB Schema는 resources/db_schema.sql 파일 참고
2. 이미지 저장 로컬 파일시스템 경로  
    
    **※해당 폴더는 사전에 생성 되어 있어야함**

    * 원본이미지: C:\DEV\img\org
  
    * 썸네일이미지 : C:\DEV\img\thumbnail  
    
    
3. 선택 구현 기능을 위한 설정
    
    ActiveMQ 설치 
      * ActiveMQ 접속 https://activemq.apache.org/components/classic/download
      * apache-activemq-5.17.2-bin.zip 파일 다운로드
      * 다운로드 받은 파일 압축 해제
      * 커맨드 창으로 압축 해제 폴더 하위의 bin 폴더로 이동
      * activemq start 명령어 실행
      
    Redis : redis는 Embedded-redis를 사용하므로 별도의 설치 불필요(로컬 서버 기동시 함께 기동)
    
### 기본 구현 기능설명
1. create

    * 외부의 이미지를 스크랩하여 로컬 파일시스템에 원본의 이미지를 저장하고 썸네일 저장 및 스크랩 정보 DB 테이블에 저장
    
    * 썸네일 크기는 원본의 50프로 크기로 생성
    
    * 저장된 원본 이미지 및 썸네일이미지는 아래의 URL로 이미지 확인 가능
    
      * 원본이미지 : http://localhost:8080/img/org/저장된파일명
      
      * 썸네일이미지 : http://localhost:8080/img/thumbnail/thumbnail_저장된파일명
    
    * api url : http://localhost:8080/imgscrap
    
    * method : POST
      
    * api 규격
    
        title : 제목
        
        cont : 설명
        
        img_url : 이미지URL
        
        user_no : 사용자번호(임의의 문자열)
        
      ```c
        {
            "title": "무협의귀환", 
            "cont": "게임",
            "img_url": "https://ssl.pstatic.net/melona/libs/1395/1395757/0b9b8c86c89ee20b600e_20220825131226766.jpg", 
            "user_no": "A001"
        }
      ```
      
2. modify

    * 스크랩 데이터의 제목 및 설명 수정
    
    * 사용자번호를 받아서 등록한 사용자번호와 비교하여 같을 경우에만 수정가능
    
    * api url : http://localhost:8080/imgscrap/{scrap_no}
    
    * method : PATCH
    
    * api 규격
    
        scrap_no : 스크랩번호(key)
    
        title : 제목
        
        cont : 설명
        
        user_no : 사용자번호(임의의 문자열)
        
      ```c
        {
            "title": "무협의귀환_수정", 
            "cont": "게임아님",
            "user_no": "A001"
        }
      ```

3. remove

    * 스크랩 데이터의 삭제(이미지 파일과 썸네일 파일 물리적 삭제는 하지 않음)
    
    * 사용자번호를 받아서 등록한 사용자번호와 비교하여 같을 경우에만 삭제가능
    
    * api url : http://localhost:8080/imgscrap/{scrap_no}
    
    * method : DELETE
    
    * api 규격
    
        scrap_no : 스크랩번호(key)
    
        user_no : 사용자번호(임의의 문자열)
        
      ```c
        {
            "user_no": "A001"
        }
      ```
    
  
4. list

    * 생성된 모든 사용자의 스크랩 목록 조회
    
    * Pagination 처리를 위해 페이지 번호와 페이지당 사이즈 항목을 받음
    
    * 목록에는 스크랩번호, 제목, 설명, 썸네일URL, 조회수가 포함되며 스크랩번호가 get, modify, remove 호출시 key가됨
    
    * 목록의 썸네일URL 정보로 내부시스템에 저장된 썸네일 이미지를 호출할 수 있음
    
    * api url : http://localhost:8080/imgscrap
    
    * method : GET
    
    * api 규격(request)
    
        page : 페이지번호
        
        page_size : 페이지당 사이즈
        
      ```c
        {
            "page": 1,
            "page_size": 3
        }
      ```
      
    * api 규격(response)
    
        msg : 결과메시지
        
        tot_cnt : 총목록수
        
        page : 페이지번호
        
        total_page : 총페이지수
        
        data_list : 스크랩 목록
          
        scrap_no : 스크랩번호(key)

        title : 제목

        cont : 설명

        thumbnail_img_url : 썸네일URL
        
        hits : 조회수
          
        
      ```c
        {
            "msg": "정상처리 되었습니다.",
            "tot_cnt": 1,
            "page": 1,
            "total_page": 1,
            "data_list": [
                {
                    "scrap_no": 1,
                    "title": "무협의귀환",
                    "cont": "게임",
                    "thumbnail_img_url": "http://localhost:8080/img/thumbnail/thumbnail_72945712-46c0-4286-a48d-ce6b53d65f42.jpg",
                    "hits": 0
                }
            ]
        }
      ```  

5. get

    * 생성된 이미지 스크랩 1개 상세조회
    
    * 상세조회 데이터 조회 후 조회수가 1증가됨
    
    * 상세조회에는 스크랩번호, 제목, 설명, 이미지URL, 조회수가 포함됨
    
    * 상세조회의 이미지URL 정보로 내부시스템에 저장된 이미지를 호출할 수 있음
    
    * api url : http://localhost:8080/imgscrap/{scrap_no}
    
    * method : GET
    
    * api 규격(request)
    
        scrap_no : 스크랩번호
        
      ```c
        {
        }
      ```
      
    * api 규격(response)
    
        msg : 결과메시지
        
        data : 상세조회 데이터
        
        scrap_no : 스크랩번호(key)

        title : 제목

        cont : 설명

        img_url : 이미지URL
        
        hits : 
          
        
      ```c
        {
            "msg": "정상처리 되었습니다.",
            "tot_cnt": 1,
            "page": 1,
            "total_page": 1,
            "data_list": [
                {
                    "scrap_no": 1,
                    "title": "무협의귀환",
                    "cont": "게임",
                    "thumbnail_img_url": "http://localhost:8080/img/thumbnail/thumbnail_72945712-46c0-4286-a48d-ce6b53d65f42.jpg",
                    "hits": 0
                }
            ]
        }
      ```  



### 선택 구현 기능설명

1. 1,000명 이상의 사용자가 하나의 스크랩을 동시에 조회하는 경우가 있음

    * get api 호출 시 상세조회 쿼리 service에 redis 캐시를 등록하여 같은 스크랩 번호에 대해서 최초 1회만 쿼리 수행되도록 하여 수행속도를 향상
    
    * 캐시 유효시간은 10초로 설정하였음
    
    * modify, remove api가 호출되면 캐시를 삭제하여 정보가 실시간 반영 될 수 있도록 함
    
    * get api 호출 시 상세조회 이후 조회수가 증가하여야 하는데 성능향상을 위해서 조회수 증가 처리는 ActiveMQ를 이용하여 비동기 메시지 처리하고 get요청에 대해서는 사용자에게 먼저 응답 처리함, 이후 @JmsListener를 통해 조회수 증가처리를 하도록 구성
    
    * 조회수 증가의 실시간 반영은 고려하지 않았고, 캐시가 갱신되는 10초에 한번씩 조회수가 증가하도록 하였음
