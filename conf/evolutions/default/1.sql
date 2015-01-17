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

create table user (
  id                        bigint auto_increment not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  profession                varchar(255),
  username                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  linkedin                  varchar(255),
  personal_url              varchar(255),
  country_id                bigint,
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

alter table user add constraint fk_user_country_1 foreign key (country_id) references country (id) on delete restrict on update restrict;
create index ix_user_country_1 on user (country_id);
alter table user_education add constraint fk_user_education_user_2 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_education_user_2 on user_education (user_id);
alter table user_education add constraint fk_user_education_area_3 foreign key (area_id) references area (id) on delete restrict on update restrict;
create index ix_user_education_area_3 on user_education (area_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table area;

drop table country;

drop table language;

drop table user;

drop table user_education;

SET FOREIGN_KEY_CHECKS=1;

