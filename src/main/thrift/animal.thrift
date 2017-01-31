namespace java com.example.thrift.java
 #@namespace scala com.example.thrift.scala  
typedef string AnimalId

  enum AnimalType { 
CAT = 0, 
DOG = 1, 
GOAT = 2
 }

  struct Fur { 
1: required string colour 
2: optional string pattern 
}  

struct Cat { 
1: required string name 
2: optional Fur fur 
3: optional string description
 }  

struct Dog { 
1: required string name 
2: optional Fur fur 
3: optional string description
 }

  struct Goat { 
1: required string name 
2: optional string description 
}

  union AnimalData { 
1: Cat cat 
2: Dog dog 
3: Goat goat 
}  

struct Flags { 
1: optional bool isDangerous 
2: optional bool isWild 
}  

typedef i64 DateTime

  struct Person { 
1: required string email 
2: optional string firstName 
3: optional string lastName 
}  

struct Record { 
1: required DateTime date 
2: optional Person person
 }  

struct ImportantDates { 
1: optional Record found 
2: optional Record adopted
 }  

struct Animal { 
1: required AnimalId id 
2: required AnimalType animalType 
3: required list<string> keywords 
4: required string description 
5: required AnimalData data 
6: required ImportantDates importantDates 
7: optional Flags flags 
}