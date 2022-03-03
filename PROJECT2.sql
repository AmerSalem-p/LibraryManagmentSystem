use library;
select * from book;
select * from employee;

insert into section value (1,'Science');
insert into section value (2,'Computer And Tech');
insert into section value (2,'Medical');
insert into section value (1,'kids');
insert into section value (2,'History');
insert into section value (2,'literature');
insert into section value (1,'Religion');
insert into section value (1,'Entertament');
insert into section value (1,'Business');

insert into address value (01,"Jerusalem");
insert into address value (02,"Ramallah");
insert into address value (03,"Nablus");
insert into address value (04,"Hebron");
insert into address value (05,"Bethlehem");
insert into address value (06,"Jericho");
insert into address value (07,"Tulkarem");
insert into address value (08,"Jenin");
insert into address value (09,"Qalqilyah");
insert into address value (10,"Tubas");
insert into address value (11,"Salfit");

insert into employee (EID,DateOfBirth,ename,salary,job,email,livesin,section,password,phone,gender,status) values (1,'1999-3-1', "John Khader", 5000, "Secretary", "John@gmail.com", 02, "science",'c4ca4238a0b923820dcc509a6f75849b','002839723','Male',1);


insert into book values ("Eloquent JavaScript, Second Edition", "Marijn Haverbeke", 9781593275846, 7, "No Starch Press", "programing", 2000, "2020-01-05", "USA");
insert into book values ("SAM", "Marijn Haverbeke", 1781593275846, 7, "No Starch Press", "drama", 2000, "2020-01-05", "USA");
insert into employee (DateOfBirth,ename,salary,job,email,livesin,section,password,phone,gender) values ('1932-1-3', "Rami Mahmoud", 2500, "Organizer", "Rami@gmail.com", 03, "programing",'1234','0028333','Male');
insert into employee (DateOfBirth,ename,salary,job,email,livesin,section,password,phone,gender) values ('1932-1-3', "Mohammed Ahmad Mohammed Ahmad", 2211, "Organizer", "Rami@gmail.com", 03, "drama",'1234','3434343','Female');
