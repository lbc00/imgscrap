DROP TABLE TB_IMG_SCRAP_INFO IF EXISTS;

CREATE TABLE TB_IMG_SCRAP_INFO COMMENT '이미지스크랩정보' (
    SCRAP_NO      			INTEGER            	NOT NULL   		COMMENT '스크랩번호'
  , TITLE      				VARCHAR(50)     	NOT NULL    	COMMENT '제목'
  , CONT        			VARCHAR(2000)       NOT NULL   		COMMENT '설명'
  , ORG_IMG_URL        		VARCHAR(100)        NOT NULL        COMMENT '원본이미지URL'
  , ORG_IMG_FILE_NAME		VARCHAR(1000)       NOT NULL       	COMMENT '원본이미지파일명'
  , IMG_SAVE_PATH			VARCHAR(1000)       NOT NULL       	COMMENT '원본이미지저장경로'
  , IMG_SAVE_FILE_NAME		VARCHAR(1000)       NOT NULL       	COMMENT '이미지저장파일명'
  , THUMBNAIL_SAVE_PATH		VARCHAR(1000)       NOT NULL       	COMMENT '썸네일저장경로'
  , THUMBNAIL_FILE_NAME		VARCHAR(1000)       NOT NULL       	COMMENT '썸네일파일명'
  , IMG_FILE_EXT			VARCHAR(10)         NOT NULL       	COMMENT '이미지파일확장자'
  , HITS        			INTEGER             NOT NULL   		COMMENT '조회수'
  , IMG_SERVICE_URL			VARCHAR(1000)       NOT NULL       	COMMENT '이미지서비스URL'
  , INSERT_USER_NO        	VARCHAR(10)         NOT NULL       	COMMENT '등록사용자번호'
  , DEL_YN					VARCHAR(1)			NOT NULL       	COMMENT '삭제여부'
  , INSERT_DT        		TIMESTAMP           NOT NULL     	COMMENT '등록일시'
  , UPDATE_USER_NO        	VARCHAR(10)         NOT NULL       	COMMENT '수정사용자번호'
  , UPDATE_DT        		TIMESTAMP           NOT NULL     	COMMENT '수정일시'
  , PRIMARY KEY (SCRAP_NO)
); 

DROP SEQUENCE SCRAP_NO_SEQ IF EXISTS;

CREATE SEQUENCE SCRAP_NO_SEQ 
MINVALUE 1 
MAXVALUE 999999999 
INCREMENT BY 1 
START WITH 1 
NOCACHE 
NOCYCLE;