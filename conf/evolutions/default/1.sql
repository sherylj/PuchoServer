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

create table user (
  id                        bigint not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  profession                varchar(255),
  auth_token                varchar(255),
  username                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  sha_password              varbinary(255),
  linkedin                  varchar(255),
  personal_url              varchar(255),
  country_id                bigint,
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

create sequence user_seq;

create sequence user_education_seq;

alter table user add constraint fk_user_country_1 foreign key (country_id) references country (id) on delete restrict on update restrict;
create index ix_user_country_1 on user (country_id);
alter table user_education add constraint fk_user_education_user_2 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_education_user_2 on user_education (user_id);
alter table user_education add constraint fk_user_education_area_3 foreign key (area_id) references area (id) on delete restrict on update restrict;
create index ix_user_education_area_3 on user_education (area_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists area;

drop table if exists country;

drop table if exists language;

drop table if exists user;

drop table if exists user_education;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists area_seq;

drop sequence if exists country_seq;

drop sequence if exists language_seq;

drop sequence if exists user_seq;

drop sequence if exists user_education_seq;

