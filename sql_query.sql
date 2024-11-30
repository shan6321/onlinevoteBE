delete from students;
select *from students;
truncate students;
commit;

create table students (id bigint not null auto_increment, regno varchar(255), password varchar(255), name varchar(255), email varchar(255),
 status varchar(255), zone_Id VARCHAR(255), user_Utc_Time VARCHAR(255), created_At VARCHAR(255), created_By varchar(255),
 updated_At VARCHAR(255), updated_By varchar(255), primary key (id)) engine=InnoDB;

create table students (id bigint not null auto_increment, created_at varchar(255), created_by varchar(255), updated_at varchar(255), updated_by varchar(255), user_utc_time varchar(255), zone_id varchar(255), email varchar(255), name varchar(255), password varchar(255), regno varchar(255), status varchar(255), primary key (id)) engine=InnoDB

 
