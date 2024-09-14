create table students (id bigint not null auto_increment, name varchar(255), password varchar(255),
 regno varchar(255), status varchar(255), created_at datetime(6), created_by varchar(255),
 updated_at datetime(6), updated_by varchar(255), primary key (id)) engine=InnoDB);