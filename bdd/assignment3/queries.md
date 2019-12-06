# Quick start
Before running the python code, it is necessary to add the following to the code:
```python
import pymongo
client = pymongo.MongoClient('mongodb://localhost:27017/')
db = client['company']
col = db['employees']
```

# 1 The highest salary of clerks

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

## Shell
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

## Python
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

# 12 For each city in which a mission took place, its name (output field "city") and the number of missions in that city

## Shell
```json
db.employees.aggregate(
    [
        {
            $unwind: "$missions"
        },
        {
            $group: {
                _id: "$missions.location",
                count: { $sum: 1 }
            }
        },
        {
            $match: {
                _id: { $ne: null }
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
            "$group": {
                "_id": "$missions.location",
                "count": { "$sum": 1 }
            }
        },
        {
            "$match": {
                "_id": { "$ne": "null" }
            }
        }
    ]
)
for i in x:
    print(i)
```

# 13 The name of the departments with at most 5 employees

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
                count: { "$lte": 5 }
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
                "count": { "$lte": 5 }
            }
        }
    ]
)
for i in x:
    print(i)
```

# 14 The average salary of analysts

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
        },
        {
            $match:
            {
                _id: "analyst"
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
                "_id": "$job",
                "avg": { "$avg" :"$salary"}
            }
        },
        {
            "$match":
            {
                "_id": "analyst"
            }
        }
    ]
)
for i in x:
    print(i)
```

# 15 The lowest of the per-job average salary

## Shell
```json
db.employees.aggregate(
    [
        {
            $match:
            {
                job: { $exists: true }
            }
        },
        {
            $group:
            {
                _id: "$job",
                avg: { $avg: "$salary" }
            }
        },
        {
            $group:
            {
                _id: "$department.name",
                min: { $min: "$avg" }
            }
        },
        {
            $project:
            {
                _id: 0
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
                "job": { "$exists": "true" }
            }
        },
        {
            "$group":
            {
                "_id": "$job",
                "avg": { "$avg": "$salary" }
            }
        },
        {
            "$group":
            {
                "_id": "$department.name",
                "min": { "$min":"$avg"}
            }
        },
        {
            "$project":
            {
                "_id": 0
            }
        }
    ]
)
for i in x:
    print(i)
```

# 16 For each department: its name and the highest salary in that department

## Shell
```json
db.employees.aggregate(
    [
        {
            $group:
            {
                _id: "$department.name",
                max: { $max: "$salary" }
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
                "max": { "$max": "$salary" }
            }
        }
    ]
)
for i in x:
    print(i)
```

# 17 The number of employees

## Shell
```json
db.employees.countDocuments({})
```

## Python
```python
x = col.count_documents({})
print(x)
```

# 18 One of the employees, with pretty printing (2 methods)

## Shell
```json
db.employees.findOne();
db.employees.find().limit(1).pretty()
```

## Python
```python
x = col.find_one()
print(x)
x = col.find().limit(1)
for i in x:
    print(i)
```

# 19 All the information about employees, except their salary, commission and missions

## Shell
```json
db.employees.find({}, { salary: 0, commission: 0, missions: 0 }).pretty()
```

## Python
```python
x = col.find({}, { "salary": 0, "commission": 0, "missions": 0 })
for i in x:
    print(i)
```

# 20 The name and salary of all the employees (without the field _id)

## Shell
```json
db.employees.find({}, { _id: 0, name: 1, salary: 1 }).pretty()
```

## Python
```python
x = col.find({}, { "_id": 0, "name": 1, "salary": 1 })
for i in x:
    print(i)
```
