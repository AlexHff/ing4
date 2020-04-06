--
-- 1. DROP all tables
--
-- Note: beware of foreign keys
--
-- drop table MISSION;
-- drop table EMP;
-- drop table DEPT;

--
-- 2. CREATE database 
--

-- Change default storage engine so that
-- primary and foreign keys are enforced
-- set storage_engine = INNODB;

-- Force MySQL to comply with standard
-- SQL regarding "group by"
set session sql_mode = 'ONLY_FULL_GROUP_BY';

create table DEPT (
    DID     int,
    DNAME   varchar(20) not null,
    DLOC    varchar(30) not null,
    constraint DEPT_PK primary key (DID)
) engine=InnoDB;

create table EMP (
    EID     int,
    ENAME   varchar(20) not null check(length(ENAME) > 0 and ENAME = UPPER(ENAME)),
    JOB     varchar(20) not null,
    MGR     int,
    HIRED   date not null check(HIRED <= GetDate()),
    SAL     decimal(6 , 2) not null check(SAL >= 0),
    COMM    decimal(6 , 2),
    DID     int,
    constraint EMP_PK primary key (EID),
    constraint EMP_FK_MNGR foreign key (MGR) references EMP (EID),
    constraint EMP_FK_DID  foreign key (DID) references DEPT (DID)
) engine=InnoDB;

create table MISSION (
    MID     int,
    EID     int not null,
    CNAME   varchar(30) not null,
    MLOC    varchar(30) not null,
    ENDD    date,
    constraint MISSION_PK primary key (MID),
    constraint MISSION_FK_EID foreign key (EID) references EMP (EID)
) engine=InnoDB;

--
-- 3. POPULATE database
--
-- Note: beware of foreign keys
--

-- Table DEPT
insert into DEPT values(10, 'ACCOUNTING',	'NEW-YORK');
insert into DEPT values(20, 'RESEARCH', 	'DALLAS');
insert into DEPT values(30, 'SALES',		'CHICAGO');
insert into DEPT values(40, 'OPERATIONS',	'BOSTON');

-- Table EMP
insert into EMP values(7839, 'KING',    'PRESIDENT',    null, date '1981-11-17', 5000.00,   null,   null);
insert into EMP values(7566, 'JONES',   'MANAGER',      7839, date '1981-04-02', 2975.00,   null,   20);
insert into EMP values(7698, 'BLAKE',   'MANAGER',      7839, date '1981-05-01', 2850.00,   null,   30);
insert into EMP values(7782, 'CLARK',   'MANAGER',      7839, date '1981-06-09', 2450.00,   null,   10);
insert into EMP values(8000, 'SMITH',   'MANAGER',      7839, date '1980-12-17', 3000.00,   null,   10);
--
insert into EMP values(7788, 'SCOTT',   'ANALYST',      7566, date '1981-11-09', 3000.00,   null,   20);
insert into EMP values(7902, 'FORD',    'ANALYST',      7566, date '1981-12-03', 3000.00,   null,   20);
--
insert into EMP values(7499, 'ALLEN',   'SALESMAN',     7698, date '1981-02-20', 1600.00,   300.00, 30);
insert into EMP values(7521, 'WARD',    'SALESMAN',      7698, date '1981-02-22', 1250.00,   500.00, 30);
insert into EMP values(7654, 'MARTIN',  'SALESMAN',     7698, date '1981-09-28', 1250.00,   1400.00, 30);
insert into EMP values(7844, 'TURNER',  'SALESMAN',     7698, date '1981-09-08', 1500.00,   0.00,   30);
--
insert into EMP values(7900, 'JAMES',   'CLERK',        7698, date '1981-12-03', 950.00,    null,   30);
insert into EMP values(7934, 'MILLER',  'CLERK',        7782, date '1982-01-23', 1300.00,   null,   10);
insert into EMP values(7876, 'ADAMS',   'CLERK',        7788, date '1981-09-23', 1100.00,   null,   20);
insert into EMP values(7369, 'SMITH',   'CLERK',        7902, date '1980-12-17', 800.00,    null,   20);

-- Table MISSION
insert into MISSION values(218, 7499, 'Decathlon',  'LYON',     date '2011-12-24');
insert into MISSION values(209, 7654, 'BMW',        'BERLIN',   date '2011-02-09'); -- Martin
insert into MISSION values(212, 7698, 'MacDo',      'CHICAGO',  date '2011-03-04'); -- Blake
insert into MISSION values(216, 7698, 'IBM',        'CHICAGO',  date '2011-02-09'); -- Blake
insert into MISSION values(219, 7782, 'BMW',        'CHICAGO',  date '2011-08-16');
insert into MISSION values(214, 7900, 'Fidal',      'PARIS',    date '2011-06-07');
insert into MISSION values(213, 7902, 'Oracle',     'DALLAS',   date '2011-04-11');
insert into MISSION values(220, 7369, 'IBM',        'LONDON',   date '2015-06-20');
insert into MISSION values(300, 8000, 'ECE',        'PARIS',    date '2018-06-11');

