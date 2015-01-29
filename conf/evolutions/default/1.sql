# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table area (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_area primary key (id))
;

create table country (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_country primary key (id))
;

create table language (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_language primary key (id))
;

create table profession (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_profession primary key (id))
;

create table question (
  id                        bigint not null,
  asker_id                  bigint,
  skill_id                  bigint,
  asker_name                varchar(255),
  title                     varchar(255),
  content                   varchar(255),
  upvotes                   integer,
  downvotes                 integer,
  share_count               integer,
  audio_file_url            varchar(255),
  asked_on                  timestamp not null,
  constraint pk_question primary key (id))
;

create table question_skills (
  question_id               bigint,
  skill_id                  bigint)
;

create table skill (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_skill primary key (id))
;

create table user (
  id                        bigint not null,
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
  id                        integer not null,
  user_id                   bigint,
  degree                    varchar(255),
  institution               varchar(255),
  start_year                timestamp,
  end_year                  timestamp,
  area_id                   bigint,
  constraint pk_user_education primary key (id))
;

create sequence area_seq;

create sequence country_seq;

create sequence language_seq;

create sequence profession_seq;

create sequence question_seq;

create sequence skill_seq;

create sequence user_seq;

create sequence user_education_seq;

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

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists area;

drop table if exists country;

drop table if exists language;

drop table if exists profession;

drop table if exists question;

drop table if exists question_skills;

drop table if exists skill;

drop table if exists user;

drop table if exists user_education;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists area_seq;

drop sequence if exists country_seq;

drop sequence if exists language_seq;

drop sequence if exists profession_seq;

drop sequence if exists question_seq;

drop sequence if exists skill_seq;

drop sequence if exists user_seq;

drop sequence if exists user_education_seq;

