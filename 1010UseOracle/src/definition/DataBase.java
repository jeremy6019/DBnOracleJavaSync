package definition;

public class DataBase {
/*
 
 *  인덱스(Index)
 =>여러 개의 어떤 데이터의 위치를 찾아가기 위해서 사용하는 번호나 포인터(이름 이나 참조)
 =>프로그래밍 언어에서 배열이나 리스트의 경우에는 번호를 이용해서 데이터를 참조하고
 맵의 경우에는 이름을 이용해서 데이터를 참조 
 =>데이터베이스에서는 데이터를 빠르게 찾기 위한 포인터 
 
  ** 오라클에서의 인덱스 
 
  트리(Tree)
 => 자신의 다음 데이터를 가리키는 구조로 만들어진 자료구조 
 다음데이터를 자식이라고 함 parent - child 구조 
 2진 Binary Tree: 작으면 왼쪽, 크면 오른쪽  
 Balanced Tree: 밸런스에 맞게 재배치함  
 Heap: 부모가 자식보다 큰 트리 
 
 => 오라클에서는 인덱스를 B*(2/3이상 데이터로 채워진 트리)로 구성합니다. 
 => 데이터베이스에서는 데이터의 위치를 알 수 없다.(순서대로 저장되지 않는다.) 
 => 특정한 컬럼을 가지고 데이터를 자주 검색한다면 빠르게 검색할 수 있도록 인덱스
 를 설정해주는 것이 좋습니다., 
 => 데이터베이스는 데이터를 순서대로 저장하지 않기 때문에 2개 이상의 데이터를 검색
 할 때는 정렬을 해주는 것이 좋습니다. 
 => 인덱스를 설정하게 되면 다른 데이터의 참조를 저장하는  포인터 때문에 공간이 낭비가
 될 수 있고 빈공간이 생기는 문제가 발생할 수 있습니다.
 => 인덱스를 설정하게 되면 삽입이나 삭제가 발생할 때 데이터의 위치를 변경하는 문제가 
 발생하기도 합니다. 오히려 늦어질 수도 있습니다. 
=> 인덱스를 설정해서 효과를 보기 위해서는 자주 검색하는 항목이어야 하고 검색을 할 때
결과가 2~4%정도 일 때 합니다. 

1. 각 사용자의 인덱스 설정 정보는 user_ind_columns테이블에 저장되어 있습니다. 
 
2. primary key(not null이고 unique)와 unique제약조건을 사용하면 인덱스가 자동으로 생성됩니다. 
  
3. 인덱스를 직접 생성
create index 인덱스이름 
on 테이블이름(컬럼이름); 
=> 게시판 같은 경우 제목에 생성해주면 좋습니다. 
  
4. 인덱스 제거하기 
  drop index 인덱스 이름 
  
5. 인덱스를 생성하는 것은 충분히 여러가지 상황을 고려해서 설정 
=>삽입이나 삭제가 빈번하게 발생하는 경우에는 인덱스를 설정하지 않는 것이 좋습니다. 
  
실습
- 자신의 계정에서 설정한 인덱스를 조회 
select * 
from user_ind_columns;  
 
- emp테이블에서 ename을 이용해서 조회하는 일이 빈번히 발생 
기본키나 unique속성이 아닌 컬럼을 가지고 조회하는 일이 빈번히 발생할 것 같으면 
인덱스를 설정해 주는 것이 좋습니다. 
create index idxname 
on emp(ename);  
 
- emp테이블을 사용하다 보니 삽입, 삭제, 갱신이 빈번히 발생 
인덱스가 존재하면 데이터 조작을 할 때 인덱스도 조정해야 하기 때문에 속도가 현저하게 
떨어집니다.  
이럴 때는 불필요한 인덱스를 제거해 주는 것이 좋습니다. 
emp테이블에서 idxname이라는 인덱스를 제거 

drop index idxname; 

** 별명의 사용 
원본 -> 프로그래밍 언어에서 직접 사용 
원본 -> 별명 -> 별명을 프로그래밍 언어에서 사용 
=> 프로그래밍 과정에서 가장 많은 비용과 기간이 소모되는 것은 유지보수 
처음부터 완전한 프로그래밍을 할 수는 없습니다. 
프로그램을 개발할 때 유지보수를 편리하게 할 수 있도록 개발하는 것은 중요한 
개발자의 능력  

원본 -> 프로그래밍에서 원본을 직접 사용 
=> 원본의 이름을 변경하면 프로그램에서 원본을 사용하는 부분의 모든 코드를 수정 

원본 -> 별명 ->프로그래밍에서 별명을 사용 
=> 원본의 이름을 변경한 경우 원본과 별명을 다시 연결하기만 하면 됩니다. 

=>데이터베이스에서 이런 용도로 사용하는 것이 뷰, 동의어(synonym),프로시저 등이
있습니다. 

=> 스마트폰 애플리케이션을 만들때 서버를 이용하는 이유도 비슷합니다. 

 앱(데이터): 데이터를 변경할 때 앱을 수정해야 합니다. 
 스마트폰 앱은 앱을 수정하면 마켓에서 다시 심사를 받아야 합니다.  
 
 서버(데이터) -> 앱에서 사용하게 되면 데이터 변경은 서버에서 수행하면 됩니다.
 데이터가 변경되었다고 앱을 수정할 필요는 없기 때문에 마켓에서 다시 심사를 받을 
 필요가 없어 집니다. 
 
 ** Synonym(동의어)
=> 데이터베이스 개체(테이블,뷰,인덱스,프로시저,트리거 등)에 별명을 부여
=> 보통은 데이블에 부여 

 1. 생성  
 Create synonym 별명 
 for 데이터베이스개체이름 ; 
 
 2. 삭제 
 drop synonym 별명; 
 
실습 
=> empcopy테이블에서 sal이 3000이상인 데이터만 조회하는 EmpView를 읽기전용으로 
생성  
1. emp테이블의 모든 데이터를 가지고 empcopy테이블을 생성 
create table empcopy 
as 
select * 
from emp; 
=> 테이블이 존재한다고 에러메세지가 발생하면 
drop table empcopy;를 수행하고 실행  

2. 뷰생성 
create or replace view 뷰이름 
select 구문; 

create or replace view empview 
as
select * 
from empcopy
where sal >= 3000;

3. empcopy테이블의 이름을 emp3000으로 변경 
RENAME EMPCOPY TO EMP3000;

4. 	기존 view의 모든 내용을 조회하는 select구문 실행 

SELECT * 
FROM EMPVIEW;  
 => empcopy테이블의 이름이 emp3000으로 변경되었기 때문에 에러 
 => 테이블의 이름을 변경하면 테이블 이름을 사용하는 모든 코드를 수정해야 합니다. 
 
 5. EMP3000테이블의 동의어를 생성 - PRACTICE 
 
 CREATE SYNONYM PRACTICE 
 FOR EMP3000;
 
 6. EMP3000테이블의 별명인 PRACTICE를 이용해서  ENAME에 L이 포함된 모든 
 데이터를 조회하는 EMPVIEW를 
 생성 
 CREATE OR REPLACE VIEW EMPVIEW 
 AS 
 SELECT * 
 FROM PRACTICE
 WHERE UPPER(ENAME) LIKE '%L%'; 
 
 7. EMP3000테이블의 이름을 EMPCOPY로 변경 
 RENAME EMP3000 TO EMPCOPY;
 
 8.synonym을 변경하면 view의 코드를 수정하지 않아도 됨 
 drop synonym practice;
 
 CREATE SYNONYM PRACTICE  
 FOR EMPCOPY; 
 
 9.EMPVIEW의 모든 데이터를 조회하는 구문을 실행 
SELECT * 
FROM EMPVIEW;

=>SYNONYM을 사용하는 경우는 데이터베이스 내에서보다는 데이터베이스를 활용하
는 프로그램에서 좋은 효과를 볼 수 있습니다. 

**PL/SQL
=>오라클에서 프로시저를 작성하기 위해서 사용하는 문법 
=>PL/SQL은 다른 데이터베이스에서는 사용할 수 없습니다. 
=> 이런 용도로 MS-SQL Server에서 사용하는 문법을 T-SQL이라고 합니다.

 **Procedure 
 =>자주 사용하는 구문을 하나의 이름으로 묶어 두었다가 이름만으로 사용하기 위한 개념 
 => 프로그래밍 언어의 함수와 유사하지만 데이터베이스에서는 함수는 반드시 리턴을 
 해야하고 프로시저는 리턴여부에 상관없습니다. 
 => 사용하는 이유는 보안과 속도 향상 
 =>외부 사용자에게는 어떤 형태로 동작하는지 알려줄 필요가 없습니다. 
 실제 테이블의 이름이나 구조를 노출하지 않고도 데이터베이스를 사용할 수 있도록 할 수
 있습니다.
 => 뷰나 프로시저는 처음 실행하면 메모리에 로드되서 다음부터는 보조 기억장치에서 
 불러오지 않고 메인 메모리에서 바로 호출해서 사용하기 때문에 실행속도가 빨라지게 
 됩니다. 
 
  1.프로시저 생성 
  CREATE [OR REPLACE] PROCEDURE 프로시저이름(매개변수 모드 자료형,...)
  is
  프로시저 안에서 사용할 지역변수 
  begin 
      SQL문장 
  end;
  /
  => /는 DBever에서는 생략 
  => or replace는 존재하는 경우에는 지우고 다시 만들기 
 => 모드는 in과 out이 있는데 생략하면 in 
 in은 프로시저를 호출할 때 대입해줘야 하는 데이터이고 
 out은 프로시저를 수행하고 그 결과를 넘겨받기 위한 변수입니다. 
 
  2.프로시저 삭제 
  drop procedure 프로시저이름; 
  
  3. 프로시저 실행 
  =>sqlplus에서는 execute 프로시저이름(매개변수...);
  
  => 다른 접속도구에서는 begin 프로시저이름(매개변수...) end; 
  
실습
- empno를 입력받아서 emp테이블에서 삭제하는 프로시저를 만들고 실행   
 delete from empcopy where empno = ? ; 
 
 1. 프로시저 생성 
 create or replace procedure 
     delempproc(
     vempno in empcopy.empno%type)
 is
 begin
     delete from empcopy where empno = vempno;
end; 

2.프로시저 실행 
begin
    delempproc(7788);
end;

3. 데이터 확인 - 7788인 scott이 삭제 되었는지 확인 

select * from empcopy;

=> 주의할 점은 procedure와 trigger는 문법에 에러가 있어도 생성이 됩니다.
실행이 안됩니다. 

- DEPT테이블의 모든 내용을 복사한 DEPTCOPY테이블을 생성하고 DEPTCOPY테이블에
3개의 매개변수(deptno - 숫자2자리, dname-문자13자리, loc-문자11자리)를 
받아서 삽입하는 프로시저를 생성하고 실행  

1. 테이블 복사 
drop table deptcopy;

CREATE TABLE DEPTCOPY 
AS 
SELECT * 
FROM DEPT;

2. 프로시저를 생성 
CREATE OR REPLACE PROCEDURE insdeptproc(
  vdeptno IN deptcopy.deptno%type,
  vdname IN deptcopy.dname%type,
  vloc IN deptcopy.loc%type)
IS 
BEGIN 
    INSERT INTO deptcopy(
      deptno,dname,loc)
    VALUES(vdeptno,vdname,vloc);
END; 

3. 프로시저를 실행 
begin
    insdeptproc(50,'비서', '서울');
end;

4. 데이터 확인 
select * 
from deptcopy;
=> deptno가 50번인 데이터가 삽입되었는지 확인 

=>프로시저를 만들때는 SQL구문 뒤와 END뒤에 반드시 ;을 추가해야 합니다.
삽입하지 않으면 에러발생 
=>데이터베이스 접속프로그램이나 java에서는;을 하지 않아도 에러가 발생하지 
않습니다. 

** 트랜잭션  
=> 데이터베이스에서 한 번에 수행되어야 하는 작업단위 

** Trigger 
=>어떤 DML(insert, delete,update)문장이 수행되기 전이나 수행된 후에 다른 동작을 같이 
수행하도록 하기 위한 개체 
=>내용은 PL/SQL문법을 따라야 합니다. 

1. 생성 
CREATE TRIGGER 트리거 이름 
타이밍(BEFORE 또는 AFTER ) 이벤트(insert | delete | update)
on 테이블 이름 

[for each row] 
[when 조건] 

begin 
    수행할 내용 
end;

=>이벤트는 하나만 작성해도 되고 or 로 묶어서 여러 개를 작성해도 됨 
=> for each row가 있는데 만일 삽입이나 삭제나 수정이 여러 개의 행에 걸쳐서 
발생하는 경우 트리거를 매번 수행할 때 사용하는 옵션 

 deptcopy테이블에 5개의 행이 있는데 delete from deptcopy;를 수행하는 경우 
 for each row가 있으면 트리거가 5번 수행되고 없으면 1번만 수행 
 
=> when은 트리거가 수행할 조건을 기재 
 주로 if 구문을 이용해서 작성 

=>sql구문을 실행하지 않기 위해서 에러를 발생하기 
raise_application_error(에러코드, 에러메시지); 

2. 트리거 삭제 
drop trigger 트리거 이름; 

3. 트리거 활용 
=>테이블에서 데이터가 삭제될 때 다른 테이블에 복사를 한다던가 테이블에 삽입이나
삭제 및 갱신 등의 작업이 발생할 때 로그를 기록할 목적으로 활용 
=> 특정한 조건을 만족하지 않으면 데이터 조작을 하지 않도록 하기도 합니다. 

4. 작업을 수행하기전에 무엇인가를 한다는 것은 유효성 검사를 수행해서 조건에 맞지 
않으면 작업을 하지 않도록 하기 위해서입니다.
작업을 수행한 후에 무엇인가를 한다는 것은 작업내용을 기록하기 위한 경우가 많습니다.
작업내용을 기록하는 것은 로그를 기록한다라고 합니다. 

5. 트리거 안에서 수정이나 삽입을 위해서 새로 입력하는 데이터는 :NEW.컬럼이름의 
형태로 접근하고 수정이나 삭제를 하기 위한 데이터는 :OLD.컬럼이름으로 접근합니다. 

실습1
DEPTCOPY테이블에서 데이터가 삭제될 때 삭제된 DEPTNO값을 DELDEPT테이블에 
날짜와 함께 저장 

1.DEPTCOPY 테이브이 없거나 새로 만들고자 하는 경우 
drop table deptcopy; 

create table deptcopy 
as 
select * 
from dept; 
=> 데이터가 10,20,30,40을 deptno로 갖는 4개의 데이터를 갖는 테이블이 생성 
 
2. 삭제된 데이터를 저장할 deldept테이블을 생성 
=>일련번호(정수 5자리), deptno(정수 2자리), deldate(날짜)컬럼을 갖고 일련번호
를 기본키로 설정 

create table deldept(
    num number(5) primary key,
    deptno number(2),
    deldate date);

3. 일련번호에 삽입할 시퀀스를 생성 

create sequence delseq 
    start with 1; 

4. 트리거 생성 
CREATE OR REPLACE TRIGGER DELCOPYTRI 
BEFORE DELETE 
ON DEPTCOPY 
FOR EACH ROW 
BEGIN 
    INSERT INTO DELDEPT(NUM, DEPTNO, DELDATE)
    VALUES(DELSEQ.NEXTVAL, :OLD.DEPTNO, SYSDATE);
END;

=> DEPTCOPY테이블에서 데이터가 삭제되기 전에 DELDEPT테이블에 시퀀스와 삭제된 
DEPTNO 그리고 삭제된 시간을 삽입하는 트리거 

 5. 확인 
 DELETE FROM deptcopy 
WHERE deptno = 10;
=>deptcopy테이블에서 deptno가 10번인 데이터를 삭제 

select * 
from deldept; 
=> 삭제된 데이터가 보이는 지 확인 

실습2 
DEPTCOPY테이블에 조작하는 명령이 실행된 후 작업내용과 시간을 기록하는 트리거
를 생성 

1. 로그를 기록할 테이블 생성 
=> 어떤 데이터에 어떤 작업이 언제 이루어졌는지 기록 
 
CREATE TABLE LOGDEPT(
    DEPTNO NUMBER(2), 
    TASK VARCHAR2(30),
    TASKDATE  DATE DEFAULT SYSDATE);

2.로그 기록하는 트리거 
CREATE OR REPLACE TRIGGER LOGDEPTTRIGGER
AFTER INSERT OR UPDATE OR DELETE 
ON DEPTCOPY 
FOR EACH ROW 
BEGIN 
   IF INSERTING THEN INSERT INTO 
   LOGDEPT(DEPTNO, TASK) 
   VALUES(:NEW.DEPTNO,'삽입');
   ELSIF UPDATING THEN INSERT INTO 
   LOGDEPT(DEPTNO, TASK)
   VALUES(:OLD.DEPTNO,'수정'); 
   ELSIF DELETING THEN INSERT INTO 
   LOGDEPT(DEPTNO, TASK) 
   VALUES(:OLD.DEPTNO,'삭제');
   END IF;
END;  

3. 작업을 수행하고 확인 
INSERT INTO DEPTCOPY(DEPTNO, DNAME, LOC)
VALUES(88,'총무','제주');

UPDATE DEPTCOPY 
SET LOC = '강남' WHERE DEPTNO = 88; 

DELETE FROM DEPTCOPY WHERE DEPTNO ='88'; 

 4.확인 
 SELECT * 
 FROM LOGDEPT; 
 
** 오라클을 직접 설치하고 실습 
=> 계정을 생성하고 권한 부여 : SYSTEM이나 SYS로 로그인 
1. 유저 생성 명령 
CREATE USER 유저이름 IDENTIFIED BY 비밀번호 

2.유저에게 권한 부여 :connect가 접속 권한, resource가 자원 사용 권한 
DBA가 모든 작업을 할 수 있도록 해주는 권한 

GRANT CONNECT, RESOURCE, DBA TO 유저이름; 

3. SCOTT을 가지고 할 때는 1번 명령 대신에 이명령 수행 
 ALTER USER SCOTT IDENTIFIED BY TIGER ACCOUNT UNLOCK; 
 
4. 우리가 사용한 샘플데이터 
블로그의 2. 오라클 설치 및 샘플 작성에서 SCOTT.SQL을 열어서 수행 
  
**JDBC 
=> JAVA에서 데이터베이스를 사용하는 기술   
1. Java에서 데이터베이스를 사용하는 방법 
1) 순수 java코드를 이용해서 작업하는 방법 
=> 구현만 잘한다면 속도는 프레임워크를 이용하는 것보다 빠르고 프레임워크에 
종속되지 않기 대문에 새로 구현할 필요가 없습니다. 

2) 데이터베이스 연동 프레임워크(MyBatis, Hibernate, Spring등) 를 연동하는 방법 
=>Hibernate연동하는 것을 JPA라고 하는 경우가 있는데 JPA는 표준을 애기하는 
것이고 실제 구현체는 대부분 Hibernate연
=> 코드를 간결하게 만들 수 있고 쉽게 사용할 수 있다는 장점이 있습니다.
=> 프레임워크가 변경되면 학습을 다시 해야할 수 있고 속도가 느려질 수 있습니다. 

3) 프레임워크 
=> 사용하기 편리하게 만들어서 실제 구동이 될때는 Java코드로 변환해서 
사용하는 것 
=> 순수 코드를 이용해서 여러 명이 개발을 하게되면 표준이 없기 때문에 코드가 가독성이 
떨어지는데 프레임워크를 이용하게 되면 동일한 방식으로 개발하기 때문에 코드의 가독성
이 높아져서 유지보수가 쉬워집니다. 

4) Spring은 필수 
=> SI업체 에서는 MyBatis를 많이 사용 
=> 솔루션이나 애플리케이션 개발 업체에서는 Hibernate를 많이 사용 
배달의 민족과 카카오는 Hibernate로 만들어져 있습니다. 

 2.JDBC준비사항 
 1)데이터베이스 드라이버 파일 :오라클의 경우는 OJDBC?.jar 
 
 2)데이터베이스 접속정보 
 url: 192.168.0.13:1521/xe 
 1521은 포트번호 
 xe는 데이터베이스 SID 
 
- 계정: user05 
- 비밀번호: user05 

=>접속하고자 하는 데이터베이스 종류에 따라 계정이나 비밀번호가 없어도 
되는 경우가 있음 

3. 외부 jar(자바압축파일)파일을 프로젝트에서 사용하기 
=> Java Application Project에서는 프로젝트 안에 있는 경우에는 파일을 선택하고 
마우스 오른쪽 버튼을 눌러서 Add To Build Path 를 클릭합니다. 

=> 프로젝트 안에 없다면 프로젝트를 선택하고 마우스 오른쪽을 클릭해서 
configure Build path 메뉴를 실행한 후 add jar파일메뉴를 실행해서 선택
하면 됩니다. 

**실습: 데이터베이스 연결 프로젝트 만들기 
1. java application 프로젝트 생성 

2. 데이터베이스 드라이버 파일을 프로젝트 내의 SRC디렉토리에 복사 
=> ojcbc6.jar파일을 프로젝트의 src디렉토리에 복사 

 3.복사한 파일을 build path에 추가 
 => 파일을 선택하고 마우스오른쪽을 클릭해서 [build path] - [add to build path]를
 실행 
 
 ** 데이터베이스 연결 드라이버 클래스 로드 
 =>이 작업은 Java Application프로젝트에서는 안해도 됨 
 웹프로젝트 에서는 반드시 해야함  
 Class.forName("드라이버 이름")
 => 드라이버 이름은 데이터베이스 종류 별로 다릅니다. 
1. 오라클 
oracle.jdbc.driver.OracleDriver

2. MySQL 
org.jgt.mm.mysql.Driver 

3. MS-SQL 
com.microsoft.jdbc.sqlserver.SQLServerDriver 

** 데이터베이스 연결 
Connection 변수명 = DriverManager.getConnection("데이터베이스 경로",
"아이디","비밀번호");

1. oracl경로 
jdbc:oracle:thin:@ip:포트번호:sid
jdbc:oracle:thin:@192.168.0.13:1521:xe

2.MySQL
jdbc:mysql://ip:포트번호/데이터베이스이름 

2. 자주 발생하는 예외 
No suitable driver found for jdbc:.....
=> 데이터베이스 드라이버가 build path에 추가가 안된 경우 

The Network Adapter could not establish the connection
=> 데이터베이스 경로가 잘못된 경우 

Invalid username /password logon denies 
=> 아이디와 비밀번호가 잘못된 경우 

4. Connection은 사용이 끝나면 반드시 close()를 호출해서 
닫아주어야 합니다. 

5. 실습 
=> 데이터베이스 드라이버를 로드하고 데이터베이스 연결 

=> main메소드를 소유한 클래스를 생성하고 main메소드 안에 작성해서 테스트 

import java.sql.Connection;
import java.sql.DriverManager;

public class UserOracleMain {

	public static void main(String[] args) {
		// 데이터베이스 드라이버 클래스 로드 
		try {
			Class.forName(
					"oracle.jdbc.driver.OracleDriver");
			//데이터베이스 연결 
			Connection con = 
					DriverManager.getConnection("jdbc:oracle:thin:"
							+"@192.168.0.13:1521:xe",
							"user05","user05");
			System.out.printf("데이터베이스 연결 성공\n");
		} catch (Exception e) {
			System.out.printf(e.getMessage());
			e.printStackTrace();
		}

	}
	
**SQL사용 
1. SQL사용객체 
1) java.sql.Statement 
=>완성된 SQL을 실행하는 실행 객체 
=> SQL에 데이터를 바인딩할 수 없습니다. 

=> 생성 
Connection 객체.createStatement("실행할 SQL"); 

=> 실행메소드 
int executeUpdate(): select구문을 제외한 SQL 
리턴되는 값은 영양받은 행의 개수 입니다. 
이 리턴되는 값을 가지고 성공 및 실패 여부를 판정 
문법에는 이상이 없지만 하나의 행도 영향을 받지 않으면 0이 리턴 
update나 delete는 where절이 있기 때문에 0이 리턴되면 실패한 것이 아니고 
조건에 맞는 데이터가 없다는 의미입니다. 

ResultSet excuteQuery(): select 구문 실행 

=> ResultSet 클래스
select 구문의 실행결과를 사용하기 위한 클래스 
boolean next(): 읽을 데이터가 있으면 true 없으면 false리턴 
                         읽을 데이터가 있는 경우에는 다음 데이터로 포인터를 이동 

데이터 읽기 
자료형 get자료형(컬럼의 인덱스를 정수로 대입 또는 컬럼의 이름을 문자열로 대입); 

select deptno, dname, loc  from dept; 를 실행한 경우

 while(rs.next()) {
     int deptno = rs.getInt(1); // rs.getInt("deptno");
 
  }
=> 모든 자료형의 데이터는 getString을 이용해서 읽을 수 있습니다. 
  
=> Statement 나 ResultSet도 사용이 끝나면 close()를 호출해서 닫아 주어야 
합니다. 

=> Statement 는 보안의 문제와 작성의 어려움 때문에 잘 사용하지 않습니다. 

실습1. Statement를 사용한 데이터 삽입 
  => dept테이블에 데이터 삽입 
  dept테이블은 숫자 2자리의 deptno, 문자 13자리 dname, 문자 11자리 loc로 구성 
  
  Connection을 생성하는 부분 과 close()호출하는 코드 사이에 작성 
  
  Statement stmt = 
					con.createStatement();
//SQL을 작성할 때 예약어는 대소문자 구분을 안하고 앞뒤에 공백을 추가 
			int r = stmt.executeUpdate(
					"insert into dept(deptno, dname, loc) "
					+ "values(99, '비서', '서울' )" );
			//삽입일때는 0이 리턴되면 실패 
			if(r > 0) {
				System.out.printf("삽입성공\n");
			} else { 
				System.out.printf("삽입실패\n");
			}
			
			stmt.close();

=> 삽입하다가 예외가 발생하는 경우 
무결성 제약조건 위반(중복된 값을 설정하거나 사용할 수 없는 외래키 사용)

SQLException:sql문법오류 

=>삽입은 실패하면 예외가 발생합니다. 
0이 리턴되지 않습니다. 

=>dept테이블에서 deptno가 10번인 데이터의 loc를 종로로 수정하는 SQL을 실행 
SQL구문만 변경하면 됩니다. 
update데이블이름 
set 수정할 내용 
where수정할 조건;
수정할 조건을 생략하면 모든 데이터가 수정 

update dept 
set loc = '종로' 
where deptno = 10 

Statement stmt =
					con.createStatement();
			int r = stmt.executeUpdate(
					"update dept " + 
					"set loc = '종로' " + 
					"where deptno = 10 " );
			
			if( r > 0 ) {
				System.out.printf("수정 성공\n");
			} else { 
				System.out.printf("수정 실패\n");
			}			
			stmt.close();

데이터를 읽어오는 메소드는 executequery(select구문)로 실행하고 결과는 
ResultSet(cursor, iterator)으로 받습니다. 
=>cursor:여러 개의 행으로 구성된 데이터를 순차적으로 접근할 수 있도록 만든 포인터 
next()라는 메소드 다음 데이터의 존재여부를 리턴하면서 포인터를 다음 데이터로 
이동시킵니다. 

while(객체.next()){
   임시변수 = 객체.get자료형(인덱스 또는 이름);
}
=> 다른 곳에서 사용할 때 유의할 점은 인덱스가 0부터 시작하느냐 아니면 1부터 시작 
하느냐 입니다. 
기본적으로 가져올 때는 String으로 가져온 후 자료형으로 변환합니다. 
컬럼의 자료형이 어떤 자료형이던 String으로 읽을 수 있습니다. 

		   Statement stmt = 
				   con.createStatement(); 
		   ResultSet rs = 
				   stmt.executeQuery(
						   "select * from dept order by dname desc" ); 
		   while(rs.next()) {
			   String deptno = rs.getString(1);
			   String dname = rs.getString("dname");
			   String loc = rs.getString("loc");
			   System.out.printf(
					   "부서번호:%s  부서번호:%s  지역:%s\n",
					   deptno, dname, loc);
		   }
		   rs.close(); 
		   stmt.close();		   

Statement stmt = 
					   con.createStatement(); 
 ResultSet rs = 
     stmt.executeQuery(
	     "select * from sample "); 
 while(rs.next()) {
	   String cname = rs.getString("cname");
	   String vname = rs.getString("vname");
	   System.out.printf("%b\n", cname.trim().equals("HELLO"));
	   System.out.printf("%b\n", vname.equals("HELLO"));
				   
 }
 rs.close(); 
   
 stmt.close();		   
 

2. 문자열을 읽을때 주의할 점 
1)영문의 경우 대소문자 문제 
 => upper나 lower를  이용해서 모두 대문자나 소문자로 변경? 
2) char문제 
=> char는 자릿수가 축소되지 않기 때문에 크기에 비해서 작은 사이즈의 데이터를 저
장하면 뒤에 공백이 포함되어 있습니다. 
비교하거나 출력할 때는 trim()을 이용해서 공백을 제거해야 합니다 

3.Auto commit
=>java는 Auto commit이고 데이터베이스는 기본적으로 manual Commit입니다,. 
데이터베이스에서 데이터를 삽입, 삭제, 갱신한 경우에 자바에 바로 적용하려면 
commit을 실행해야 합니다. 
=>java에서 Manual commit을 하고자 하면 Connection객체가 
setAutoCommit(false)를 호출하고 작업이 완료될때만다 commit()또는 rollback()
을 호출하면 됩니다. 
실제 업무에서는 manual commit을 설정하고 정상적으로 수행되면 commit()예외
가 발생하면 rollback()을 호출합니다. 

4. 2개 이상의 프로그램에서 데이터베이스를 사용할 때 문제점 
=> 데이터베이스 접속도구와 데이터베이스를 사용하는 프로그래밍 언어의 IDE를 같
 사용할 때 발생하는 문제점 
데이터베이스 접속도구에서 테이블에 삽입, 삭제, 갱신작업을 수행한 상태에서 
commit이나 rollback을 하지 않으면 IDE에서 데이터 삽입, 삭제, 갱신작업이 무한
대기 상태에 빠질 수 있습니다. 
하나의 테이블에 데이터 조작작업을 수행하면 Lock이 걸리게 되고 다른 곳에서 동일한
작업을 하려고 하면 Lock이 해제될때까지 무한대기를 합니다.
 
 
 
  */
}
