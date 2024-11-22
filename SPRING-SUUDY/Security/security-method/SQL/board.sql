DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
	`no`	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY	COMMENT 'PK',
	`id`	VARCHAR(255)	NOT NULL	COMMENT 'UK',
	`title`	VARCHAR(100)	NOT NULL	COMMENT '제목',
	-- `writer`	VARCHAR(100)	NOT NULL	COMMENT '작성자',
    `user_no`	BIGINT	NOT NULL	COMMENT '회원PK',
	`content`	TEXT	NULL	COMMENT '내용',
	`created_at`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP	COMMENT '등록일자',
	`updated_at`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP	COMMENT '수정일자'
);


ALTER TABLE `board` ADD CONSTRAINT `FK_users_TO_board_1` FOREIGN KEY (
	`user_no`
)
REFERENCES `user` (
	`no`
)
ON DELETE CASCADE
;

