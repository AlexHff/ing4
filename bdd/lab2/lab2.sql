-- 1. Find the employees whose commission is specified (i.e. including 0.0 commissions).
SELECT * FROM EMP
WHERE COMM IS NOT NULL;

-- 2. Find the number of employees whose commission is specified (2 methods).
SELECT COUNT(*) FROM EMP
WHERE COMM IS NOT NULL;

-- 3. Find the number of employees whose commission is not specified (2 methods).
SELECT COUNT(*) FROM EMP
WHERE COMM IS NULL;

-- 4. Find the lowest, average and highest commission over all the employees (nulls ignored).
SELECT MIN(COMM) FROM EMP;
SELECT AVG(COMM) FROM EMP;
SELECT MAX(COMM) FROM EMP;

-- 5. Find the average commission over all the employees (nulls counted as 0.0).
SELECT AVG(coalesce(comm, 0)) FROM EMP;

-- 6. Find the name and commission, expressed in Euro (1 € = $ 1.2) of all the employees.
SELECT ENAME as 'Name', COMM/1.2 as 'Comm en euro' FROM EMP;

-- 7. Find the name and total salary (including commission) of all the employees.
SELECT ENAME as 'Name', SAL + coalesce(COMM, 0) as 'Salary' FROM EMP;

-- 8. Find the name of the company’s top managers (i.e. who don’t have a manager).
SELECT * FROM EMP
WHERE MGR IS NULL;

-- 9. Find the employees whose commission is less than 25% (nulls excluded).
SELECT * FROM EMP
WHERE (COMM / (SAL + COMM) < 0.25);

-- 10. Find the employees whose commission is less than 25% (nulls counted as 0.0).
SELECT * FROM EMP
WHERE (coalesce(COMM, 0) / (SAL + coalesce(COMM, 0)) < 0.25);

-- 1. Display (a) the product of tables EMP and DEPT, (b) the theta-join of EMP and DEPT on DID, and
-- (c) the natural join of EMP and DEPT. Compare the schema and the population of the resulting
-- tables.
SELECT * FROM EMP CROSS JOIN DEPT;
SELECT * FROM EMP INNER JOIN DEPT ON EMP.DID = DEPT.DID;
SELECT * FROM EMP NATURAL JOIN DEPT;
-- The INNER JOIN using ON clause do the same job as the NATURAL JOIN.

-- 2. Find the name and the department of the employees who work in New-York.
SELECT ENAME, DNAME FROM EMP NATURAL JOIN DEPT
WHERE DLOC = 'NEW-YORK';

-- 3. Find the name of the employees who did a mission in the city they work in.
SELECT ENAME FROM (EMP NATURAL JOIN DEPT) NATURAL JOIN MISSION
WHERE MLOC = DLOC;

-- 4. Find the name of the employees along with the name of their manager.
SELECT EMP1.ENAME AS 'Employee', EMP2.ENAME AS 'Manager'
FROM EMP AS EMP1 INNER JOIN EMP AS EMP2 ON EMP2.EID = EMP1.MGR;

-- 5. Find the name of the employees who have the same manager as Allen.
SELECT ENAME FROM EMP
WHERE MGR = (SELECT MGR FROM EMP WHERE ENAME = 'ALLEN');

-- 6. Find the name and hire date of the employees who were hired before their manager; also
-- display the manager’s hire date.
SELECT EMP1.ENAME, EMP1.HIRED, EMP2.HIRED FROM EMP AS EMP1 INNER JOIN EMP AS EMP2
ON EMP2.EID = EMP1.MGR
WHERE EMP1.HIRED < EMP2.HIRED;

-- 7. Find the name of the employees in the Sales department who were hired the same day as an
-- employee in the Research department.
SELECT EMP1.ENAME FROM (SELECT * FROM EMP NATURAL JOIN DEPT WHERE DNAME = 'SALES') AS EMP1,
(SELECT * FROM EMP NATURAL JOIN DEPT WHERE DNAME = 'RESEARCH') AS EMP2
WHERE EMP1.HIRED = EMP2.HIRED;

-- 8. Find the departments that do not have any employee.
