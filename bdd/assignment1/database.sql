--
-- Supplier database
--

--
-- 1. Database schema
--

drop table orders;
drop table products;
drop table customers;

create table products (
    pid     	int,		
    pname   	varchar(30) not null,
    price   	decimal(7,2) not null check (price >= 0),
    origin  	varchar(20),                            -- country of origin
    constraint products_pk primary key (pid)
);

create table customers (
    cid     	int,
    cname   	varchar(30) not null,
    residence 	varchar(50),                            -- country of residence
    constraint customers_pk primary key (cid)
);

create table orders (
    pid         int,
    cid         int,
    odate       date not null,                          -- date of order
    quantity    int not null check (quantity > 0),      -- quantity ordered
    constraint orders_pk primary key (pid, cid, odate),
    constraint orders_fk_pid foreign key (pid) references products (pid),
    constraint orders_fk_cid foreign key (cid) references customers (cid)
);

--
-- 2. Database population
--

-- products
insert into products values (1, 'chocolate', 5.0, 'Belgium');
insert into products values (2, 'sugar', 0.75, 'India');
insert into products values (3, 'milk', 0.6, 'France');
insert into products values (4, 'tea', 10, 'India');
insert into products values (5, 'chocolate', 7.5, 'Switzerland');

-- customers
insert into customers values (1, 'Jones', 'USA');
insert into customers values (2, 'Blake', null);
insert into customers values (3, 'Dupond', 'France');
insert into customers values (4, 'Smith', 'Great Britain');
insert into customers values (5, 'Gupta', 'India');

-- orders
insert into orders values (1, 1, date '2014-12-12', 2);
insert into orders values (2, 1, date '2014-12-12', 2);
insert into orders values (3, 1, date '2014-12-12', 1);
insert into orders values (4, 1, date '2014-12-12', 3);
insert into orders values (2, 1, date '2014-12-20', 6);

insert into orders values (2, 2, date '2010-11-12', 1);
insert into orders values (3, 2, date '2011-01-01', 1);
insert into orders values (4, 2, date '2014-06-11', 2);
insert into orders values (4, 2, date '2014-06-21', 3);

insert into orders values (2, 3, date '2014-11-12', 2);
insert into orders values (3, 3, date '2014-11-12', 2);
insert into orders values (4, 3, date '2014-11-12', 2);

insert into orders values (1, 4, date '2014-11-12', 2);
insert into orders values (2, 4, date '2014-11-12', 2);
insert into orders values (3, 4, date '2014-11-12', 2);

insert into orders values (4, 5, date '2014-11-12', 2);
insert into orders values (2, 5, date '2014-11-12', 2);


