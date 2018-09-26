CREATE TABLE IF NOT EXISTS to_do (
  `id`     bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40),
  `status` varchar(20),
  `due_date`   date,
  PRIMARY KEY (id)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;