CREATE TABLE IF NOT EXISTS tag (
  `id`   bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;