-- 1. Find the employees whose commission is specified (i.e. including 0.0 commissions).
SELECT * FROM EMP
WHERE COMM IS NOT NULL;

-- 2. Find the number of employees whose commission is specified (2 methods).
SELECT COUNT(*) FROM EMP
WHERE COMM IS NOT NULL;
SELECT COUNT(COMM) FROM EMP;

-- 3. Find the number of employees whose commission is not specified (2 methods).
SELECT COUNT(*) FROM EMP
WHERE COMM IS NULL;
SELECT COUNT(*) - COUNT(COMM) FROM EMP;

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
-- NO JOIN HERE

-- 6. Find the name and hire date of the employees who were hired before their manager; also
-- display the manager’s hire date.
SELECT EMP1.ENAME, EMP1.HIRED, EMP2.HIRED FROM EMP AS EMP1 INNER JOIN EMP AS EMP2
ON EMP2.EID = EMP1.MGR
WHERE EMP1.HIRED < EMP2.HIRED;

-- 7. Find the name of the employees in the Sales department who were hired the same day as an
-- employee in the Research department.
SELECT EMP1.ENAME FROM (SELECT * FROM EMP NATURAL JOIN DEPT WHERE DNAME = 'SALES') AS EMP1 INNER JOIN
(SELECT * FROM EMP NATURAL JOIN DEPT WHERE DNAME = 'RESEARCH') AS EMP2
ON EMP1.HIRED = EMP2.HIRED;

-- 8. Find the departments that do not have any employee.
SELECT DNAME FROM DEPT LEFT OUTER JOIN EMP ON DEPT.DID = EMP.DID
WHERE EID IS NULL;

-- 9. Find the name of the employees with the highest salary.
SELECT E1.ENAME, E1.SAL, E2.ENAME, E2.SAL FROM EMP E1 LEFT OUTER JOIN EMP E2 ON E1.SAL < E2.SAL
WHERE E2.EID IS NULL

-- 10. Find the name of the employees who were hired before all the employees of the Accounting
-- department.
SELECT ENAME FROM EMP NATURAL JOIN DEPT
WHERE DNAME = 'ACCOUNTING' AND HIRED = (
    SELECT MIN(HIRED) FROM EMP NATURAL JOIN DEPT
    WHERE DNAME = 'ACCOUNTING'
);

-- 1. Find the employees with the highest salary (2 methods).
SELECT * FROM EMP
WHERE SAL = (SELECT MAX(SAL) FROM EMP);

-- 2. Find the employees who earn less than all managers (2 methods).
SELECT * FROM EMP
WHERE SAL < (SELECT MIN(SAL) FROM EMP WHERE JOB = 'MANAGER');

-- 3. Find the employees who earn more than some analyst (2 methods).
SELECT * FROM EMP
WHERE SAL > (SELECT MIN(SAL) FROM EMP WHERE JOB = 'ANALYST');

-- 4. Find the employees who work in the Research or Sales departments.
SELECT * FROM EMP
WHERE DID = (SELECT DID FROM DEPT WHERE DNAME = 'RESEARCH')
OR DID = (SELECT DID FROM DEPT WHERE DNAME = 'SALES');

-- 5. Find the departments without any employee (3 methods).
SELECT * FROM DEPT
WHERE DID NOT IN (
    SELECT DID FROM EMP WHERE DID IS NOT NULL
);
SELECT * FROM DEPT
WHERE (SELECT COUNT(*) FROM EMP WHERE DID = DEPT.DID) = 0;
SELECT * FROM DEPT
WHERE EXISTS (SELECT * FROM EMP WHERE DID = DEPT.DID);

-- 6. Find the departments with at least 3 employees
SELECT * FROM DEPT
WHERE (SELECT COUNT(*) FROM EMP WHERE DID = DEPT.DID) >= 3;

-- 7. Find the name of the employees who did a mission.
SELECT ENAME FROM EMP
WHERE EXISTS (SELECT * FROM MISSION WHERE EID = EMP.EID);

-- 8. Find the employees who did a mission in the city they work in.

-- 1. For each employee who did at least one mission, display their ID and the number of
-- missions they did.
SELECT EID, COUNT(MID) FROM MISSION
GROUP BY EID;

-- 2. For each employee who did at least one mission, display their name and the number of
-- missions they did.
SELECT ENAME, COUNT(MID) FROM EMP NATURAL JOIN MISSION
GROUP BY EID, ENAME;

-- 3. For each employee listed in EMP, display their name and the number of missions they
-- did.
SELECT ENAME, COUNT(MID) FROM EMP LEFT OUTER JOIN MISSION ON EMP.EID = MISSION.EID
GROUP BY EMP.EID, EMP.ENAME;

-- 4. Find the number of employees each manager (i.e. an employee listed in the MGR
-- column) manages, along with the manager’s name.
SELECT COUNT(*), ENAME FROM EMP
GROUP BY EID;