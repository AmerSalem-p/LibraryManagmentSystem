CREATE DATABASE library;
SHOW TABLES;
USE library;
drop database library;

CREATE TABLE Borrow(
	B_Date date not Null,
	Ret_Date date,
	ISBN bigint,
    SID int,
    foreign key (ISBN) references Book(ISBN),
    foreign key (SID) references Subscriber(SID),
	PRIMARY KEY (ISBN, SID)
);
select * from borrow;

CREATE TABLE Fine(
	SID int,
    ISBN bigint,
	Amount real not Null,
    foreign key (SID) references Subscriber(SID),
    foreign key (ISBN) references Book(ISBN),
	PRIMARY KEY (SID,ISBN)
);
select * from fine;

CREATE TABLE Book(
	Title varchar(50),
	Author varchar(50),
	ISBN bigint,
	Edition int,
	Publisher varchar(50),
	Section varchar(32),
	YearOfProduction int,
	DateOfEntry date,
	Country varchar(32),
	primary key(ISBN),
    FOREIGN KEY (Section) REFERENCES Section (SName)
);
select * from book;

CREATE TABLE Employee(
	EID int not null AUTO_INCREMENT,
	DateOfBirth date,
	EName varchar(50),
	Salary int,
	Job varchar(20),
	Email varchar(32),
	LivesIn int,
	Section varchar(20),
    Password varchar(64) not null,
    Phone varchar(20),
    Gender varchar(6),
    Status boolean,
	FOREIGN KEY (Section) REFERENCES Section (SName),
	FOREIGN KEY (LivesIn) REFERENCES Address (ZIP),
	primary key(EID)
);
select * from employee;

CREATE TABLE Address(
	ZIP int,
	City varchar(32),
	primary key (ZIP)
);

CREATE TABLE Section(
   Floor int,
  SName varchar(32),
  PRIMARY KEY (SName)
);
select * from section;

CREATE TABLE Subscriber(
	SID int not null,
    SName varchar(50) not null,
	DateOfBirth date,
    Phone varchar(20),
    Gender varchar(6),
    LivesIn int,
    primary key (SID),
    FOREIGN KEY (LivesIn) REFERENCES Address (ZIP)
);
select * from subscriber ;

CREATE TABLE Subscribtion(
	Amount real,
    SubDate date,
    SID int,
	foreign key (SID) references subscriber(SID) on delete cascade
);
select * from Subscribtion;
