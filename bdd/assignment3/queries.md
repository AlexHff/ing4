# Quick start
Before running the python code, it is necessary to add the following to the code:
```python
import pymongo
client = pymongo.MongoClient('mongodb://localhost:27017/')
db = client['company']
col = db['employees']
```

# 1.

## Shell
```json
db.employees.find( { job: "clerk" }, { salary: 1, _id: 0 }).sort({ salary: -1 }).limit(1)
```

## Python
```python
x = col.find( { 'job': 'clerk' }, { 'salary': 1, '_id': 0 } ).sort( "salary", -1 ).limit(1)
for i in x:
    print(i)
```

# 2. The total salary of managers

## Shell
```json
db.employees.aggregate(
    [
        {
            $group:
            {
                _id: { job: "$job" },
                count: { $sum: 1 },
                totalamount: { $sum :"$salary"}
            }
        }
    ]
)
```

## Python
```python
```

# 3. The lowest, average and highest salary of the employees

## Shell

## Python

# 4. The name of the departments

## Shell
``` json
db.employees.distinct("department.name")
```

## Python
```python
x = col.distinct("department.name")
print(x)
```

# 5. For each job: the job and the average salary for that job

## Shell
```json
db.employees.aggregate(
    [
        {
            $group:
            {
                _id: { job: "$job" },
                avg: { $avg :"$salary"}
            }
        }
    ]
)
```

```python
x = col.aggregate(
    [
        {
            "$group":
            {
                "_id": { "job": "$job" },
                "avg": { "$avg" :"$salary"}
            }
        }
    ]
)
for i in x:
    print(i)
```

# 6. For each department: its name, the number of employees and the average salary in that department (null departments excluded)

## Shell
```json
```

## Python
```python
```

# 7. The highest of the per-department average salary (null departments excluded)

## Shell
```json
```

## Python
```python
```

# 8. The name of the departments with at least 5 employees (null departments excluded)

## Shell
```json
```

## Python
```python
```
