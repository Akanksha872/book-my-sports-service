# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table events (
  id                            bigint auto_increment not null,
  event_name                    varchar(255),
  start_time                    datetime(6),
  end_time                      datetime(6),
  event_type_id                 bigint,
  constraint pk_events primary key (id)
);

create table event_type (
  id                            bigint auto_increment not null,
  category                      varchar(255),
  image                         varchar(255),
  constraint pk_event_type primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  email                         varchar(255),
  created_at                    datetime(6),
  constraint pk_user primary key (id)
);

create table user_event_registration (
  id                            bigint auto_increment not null,
  event_id                      integer not null,
  user_id                       integer not null,
  created_at                    datetime(6),
  constraint pk_user_event_registration primary key (id)
);

create index ix_events_event_type_id on events (event_type_id);
alter table events add constraint fk_events_event_type_id foreign key (event_type_id) references event_type (id) on delete restrict on update restrict;


# --- !Downs

alter table events drop foreign key fk_events_event_type_id;
drop index ix_events_event_type_id on events;

drop table if exists events;

drop table if exists event_type;

drop table if exists user;

drop table if exists user_event_registration;

