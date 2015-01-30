# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table area (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_area primary key (id))
;

create table country (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_country primary key (id))
;

create table language (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_language primary key (id))
;

create table profession (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_profession primary key (id))
;

create table question (
  id                        bigint auto_increment not null,
  asker_id                  bigint,
  skill_id                  bigint,
  asker_name                varchar(255),
  title                     varchar(255),
  content                   varchar(255),
  upvotes                   integer,
  downvotes                 integer,
  share_count               integer,
  audio_file_url            varchar(255),
  asked_on                  datetime not null,
  constraint pk_question primary key (id))
;

create table question_skills (
  question_id               bigint,
  skill_id                  bigint)
;

create table skill (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_skill primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  full_name                 varchar(255),
  profession                varchar(255),
  auth_token                varchar(255),
  username                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  sha_password              varbinary(255),
  linkedin                  varchar(255),
  personal_url              varchar(255),
  constraint pk_user primary key (id))
;

create table user_education (
  id                        integer auto_increment not null,
  user_id                   bigint,
  degree                    varchar(255),
  institution               varchar(255),
  start_year                datetime,
  end_year                  datetime,
  area_id                   bigint,
  constraint pk_user_education primary key (id))
;

alter table question add constraint fk_question_asker_1 foreign key (asker_id) references user (id) on delete restrict on update restrict;
create index ix_question_asker_1 on question (asker_id);
alter table question add constraint fk_question_skill_2 foreign key (skill_id) references skill (id) on delete restrict on update restrict;
create index ix_question_skill_2 on question (skill_id);
alter table question_skills add constraint fk_question_skills_question_3 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_question_skills_question_3 on question_skills (question_id);
alter table question_skills add constraint fk_question_skills_skill_4 foreign key (skill_id) references skill (id) on delete restrict on update restrict;
create index ix_question_skills_skill_4 on question_skills (skill_id);
alter table user_education add constraint fk_user_education_user_5 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_education_user_5 on user_education (user_id);
alter table user_education add constraint fk_user_education_area_6 foreign key (area_id) references area (id) on delete restrict on update restrict;
create index ix_user_education_area_6 on user_education (area_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table area;

drop table country;

drop table language;

drop table profession;

drop table question;

drop table question_skills;

drop table skill;

drop table user;

drop table user_education;

SET FOREIGN_KEY_CHECKS=1;

