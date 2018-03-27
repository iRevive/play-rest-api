CREATE TABLE t_usr
(
  usr_id       INTEGER     NOT NULL IDENTITY (1, 1),
  usr_fname    VARCHAR(25) NOT NULL,
  usr_lname    VARCHAR(25) NULL,
  last_blog_dt DATETIME    NULL
)
GO

CREATE TABLE t_blog_master
(
  blog_id   INTEGER      NOT NULL IDENTITY (1, 1),
  usr_id    INTEGER      NOT NULL,
  blog_hdr  VARCHAR(125) NOT NULL,
  create_dt DATETIME              DEFAULT CURRENT_TIMESTAMP
)
GO

CREATE TABLE t_blog_detail
(
  blog_detail_id INTEGER NOT NULL IDENTITY (1, 1),
  usr_id         INTEGER NOT NULL,
  blog_id        INTEGER NOT NULL,
  blog_content   VARCHAR(500),
  create_dt      DATETIME         DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE t_usr
  ADD CONSTRAINT "PK_user" PRIMARY KEY CLUSTERED ("usr_id" ASC)
GO

ALTER TABLE t_blog_master
  ADD CONSTRAINT "PK_blog_master" PRIMARY KEY CLUSTERED ("blog_id" ASC)
GO

ALTER TABLE t_blog_detail
  ADD CONSTRAINT "PK_blog_detail" PRIMARY KEY CLUSTERED ("blog_detail_id" ASC);
GO