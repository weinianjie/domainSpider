-- wnj--2012-10-9---
grant all privileges on *.* to weinianjie@localhost identified by '1996815' with grant option;
grant all privileges on *.* to weinianjie@localhost identified by '1996815' with grant option;

create database mess;

create table domain_major(
	id int(11) not null primary key auto_increment comment 'id',
	name varchar(32) not null comment ' 域名',
	org varchar(32) not null comment '机构名',
	scancount int(11) comment '扫描次数',
	lastscandate datetime comment '最后扫描时间',
	registdate datetime comment '注册时间',
	overdate datetime comment '到期时间',
	owner varchar(64) comment '注册人',
	scroe int(11) comment '估算分值',
	isword int(1) comment '是否单词',
	cts datetime comment '记录创建时间',
	uts datetime comment '记录更新时间',
	unique key (name,org)
)engine=innoDB default character set=utf8;

-- wnj--2012-10-10---
create table server_status(
	id int(11) not null primary key auto_increment comment 'id',
	mkey varchar(64) not null comment '状态名',
	mval varchar(64) not null comment '状态值',
	descr varchar(512) not null comment '描述',
	cts datetime comment '记录创建时间',
	uts datetime comment '记录更新时间',
	unique key (mkey)
)engine=innoDB default character set=utf8; 

-- wnj--2012-10-12---
alter table server_status rename to server_status_tmp;

create table server_status_memory(
	id int(11) not null primary key auto_increment comment 'id',
	mkey varchar(64) not null comment '状态名',
	mval varchar(64) not null comment '状态值',
	descr varchar(512) not null comment '描述',
	cts datetime comment '记录创建时间',
	uts datetime comment '记录更新时间',
	unique key (mkey)
)engine=innoDB default character set=utf8;

