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
db.employees.find(
    {
        job: "clerk"
    },
    {
        salary: 1,
        _id: 0
    }
).sort(
    {
        salary: -1
    }
).limit(1)
```

## Python
```python
x = col.find(
    {
        'job': 'clerk'
    },
    {
        'salary': 1,
        '_id': 0
    }
).sort(
    "salary",
    -1
).limit(1)
for i in x:
    print(i)
```

# 2. The total salary of managers

## Shell
```json
db.employees.aggregate(
    [
        {
            $match:
            {
                job: "manager"
            }
        },
        {
            $group:
            {
                _id: "$job",
                totalamount: { $sum :"$salary"}
            }
        }
    ]
)
```

## Python
```python
x = col.aggregate(
    [
        {
            "$match":
            {
                "job": "manager"
            }
        },
        {
            "$group":
            {
                "_id": "$job",
                "totalamount": { "$sum" :"$salary"}
            }
        }
    ]
)
for i in x:
    print(i)
```

# 3. The lowest, average and highest salary of the employees

## Shell
```json
db.employees.aggregate(
    [
        {
            $group:
            {
                _id: null,
                min: { $min: "$salary" },
                max: { $max: "$salary" },
                sum: { $sum: "$salary" },
                avg: { $avg: "$salary" }
            }
        }
    ]
)
```

## Python
```python
x = col.aggregate(
    [
        {
            "$group":
            {
                "_id": "null",
                "min": { "$min": "$salary" },
                "max": { "$max": "$salary" },
                "sum": { "$sum": "$salary" },
                "avg": { "$avg": "$salary" }
            }
        }
    ]
)
for i in x:
    print(i)
```

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
                _id: "$job",
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
                "_id": "$job",
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
db.employees.aggregate(
    [
        {
            "$match":
            {
                "department.name":
                {
                    "$exists": true,
                    "$ne": null
                }
            }
        },
        {
            "$group":
            {
                _id: "$department.name",
                totalEmployees: { $sum: 1 },
                avgSalary: { $avg: "$salary" }
            }
        }
    ]
)
```

## Python
```python
x = col.aggregate(
    [
        {
            "$match":
            {
                "department.name":
                {
                    "$exists": "true",
                    "$ne": "null"
                }
            }
        },
        {
            "$group":
            {
                "_id": "$department.name",
                "totalEmployees": { "$sum": 1 },
                "avgSalary": { "$avg": "$salary" }
            }
        }
    ]
)
for i in x:
    print(i)
```

# 7. The highest of the per-department average salary (null departments excluded)

## Shell
```json
db.employees.aggregate(
    [
        {
            $match:
            {
                "department.name":
                {
                    "$exists": true,
                    "$ne": null
                }
            }
        },
        {
            $group:
            {
                _id: "$department.name",
                avg: { $avg: "$salary" }
            }
        },
        {
            $sort: { avg: -1 }
        },
        {
            $limit: 1
        }
    ]
)
```

## Python
```python
x = col.aggregate(
    [
        {
            "$match":
            {
                "department.name":
                {
                    "$exists": "true",
                    "$ne": "null"
                }
            }
        },
        {
            "$group":
            {
                "_id": "$department.name",
                "avg": { "$avg": "$salary" }
            }
        },
        {
            "$sort": { "avg": -1 }
        },
        {
            "$limit": 1
        }
    ]
)
for i in x:
    print(i)
```

# 8. The name of the departments with at least 5 employees (null departments excluded)

## Shell
```json
db.employees.aggregate(
    [
        {
            $group:
            {
                _id: "$department.name",
                count: { $sum: 1 }
            }
        },
        {
            $match:
            {
                _id: { $ne: null },
                count: { "$gte": 5 }
            }
        }
    ]
)
```

## Python
```python
x = col.aggregate(
    [
        {
            "$group":
            {
                "_id": "$department.name",
                "count": { "$sum": 1 }
            }
        },
        {
            "$match":
            {
                "_id": { "$ne": "null" },
                "count": { "$gte": 5 }
            }
        }
    ]
)
for i in x:
    print(i)
```

# 9 The cities where at least 2 missions took place

## Shell
```json
db.employees.aggregate(
    [
        {
            $unwind: "$missions"
        },
        {
            $group:
            {
                _id: "$missions.location",
                count: { $sum: 1 }
            }
        },
        {
            $match:
            {
                _id: { $ne: null },
                count: { "$gte": 2 }
            }
        }
    ]
)
```

## Python
```python
x = col.aggregate(
    [
        {
            "$unwind": "$missions"
        },
        {
            "$group":
            {
                "_id": "$missions.location",
                "count": { "$sum": 1 }
            }
        },
        {
            "$match":
            {
                "_id": { "$ne": "null" },
                "count": { "$gte": 2 }
            }
        }
    ]
)
for i in x:
    print(i)
```

# 10 The highest salary

```json
db.employees.find(
    {},
    {
        salary: 1,
        _id: 0
    }
).sort(
    {
        salary: -1
    }
).limit(1)
```

```python
x = col.find(
    {},
    {
        'salary': 1,
        '_id': 0
    }
).sort(
    "salary",
    -1
).limit(1)
for i in x:
    print(i)
```

# 11 The name of the departments with the highest average salary

