create table TEST_Goods (
	uuid_ VARCHAR(75) null,
	goodsId LONG not null primary key,
	name VARCHAR(500) null,
	description TEXT null,
	length DOUBLE,
	height DOUBLE,
	area DOUBLE,
	nr TEXT null
);