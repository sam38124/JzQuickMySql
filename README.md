[![Platform](https://img.shields.io/badge/Platform-%20JVM%20-brightgreen.svg)](https://github.com/sam38124)
[![characteristic](https://img.shields.io/badge/特點-%20輕量級%20%7C%20簡單易用%20%20%7C%20穩定%20-brightgreen.svg)](https://github.com/sam38124)
# JzQuickMySql
This is a very simple and fast MySQL auxiliary tool that allows you to quickly create, insert and query tables!!

## List
* [Configuration](#Import)
* [Usage](#Use)
* [About me](#About)


<a name="Import"></a>
## Configuration
> gradle。 <br/>
```kotlin
implementation "com.jzLibrary:JzQuickSql-jvm:1.3"
```
<a name="Use"></a>
## Usage

### 1.Define your data class and set up your default value 
```kotlin
data class TestData(
        var admin: String = "adminString",
        var password: String = "passwordString",
        var userSetting: UserSetting = UserSetting(true),
        var date: String = "CURRENT_TIMESTAMP")

data class UserSetting(var click: Boolean, var date: String = "2019-11-12 12:23:22")
```

### 2.Set up your mysql connector
```kotlin
 MysqlConnect.setUp(
"jdbc:mysql://127.0.0.1:3306?autoReconnect=false&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
            "admin",
            "password")
```

### 3.Begin execution
```kotlin
 MysqlConnect().startRequest { conn,stmt ->
 //String to create table
        val createTable=TestData().createMysqlTableString("DBNAME", dataLength = {
            it["admin"] = 300
            it["password"] = 400
            it["userSetting"] = 300
        }, addIndex = { map, indexType ->
            map["admin"] = indexType.UNIQUE_KEY
            map["password"] = indexType.PRIMARY_KEY
        },setStringType = {
                map,stringType ->
            map["date"]=stringType.DATETIME
        })
        //Start create
        stmt.executeUpdate(createTable)
        //String to insert table 
        val insert=TestData("admin","sam12334",date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())).insertMysqlTableString("DBNAME")
        //Start insert
        stmt.executeUpdate(insert)
        //Select and return array of TestData
        val data:ArrayList<TestData> = TestData().getDataFromResultSet(stmt.executeQuery("select * from tsport.TestData")
}

/**Output of create Table
CREATE TABLE IF NOT EXISTS `TestData`.`TestData` (
 `id` int NOT NULL AUTO_INCREMENT,
`admin` VARCHAR(300) DEFAULT 'adminString',
`date` datetime DEFAULT CURRENT_TIMESTAMP,
`password` VARCHAR(400) DEFAULT 'passwordString',
`userSetting` VARCHAR(300) DEFAULT '{"click":true,"date":"2019-11-12 12:23:22"}',
UNIQUE KEY `UNIQUE1` (`admin`),
PRIMARY KEY (`password`)
 ,PRIMARY KEY (`id`));
***/

/**Output of insert Table
insert into `TestData`.`TestData`(admin,date,password,userSetting) values ('adminString', 2019-11-12 12:23:22,'passwordString','{"click":true,"date":"2019-11-12 12:23:22"}')
***/
```

<a name="About"></a>
# About me
#### <font color="#0000dd"> Work for: </font><br /> 
+ ##### <font color="#660000">【Orange Electronic】橙的電子-Deputy Head of R&D </font><br /> 
+ ##### <font color="#660000">【Square Studio】四方工作室-CEO </font><br />
#### <font color="#0000dd"> Main skill: </font><br /> 
+ ##### Android and IOS(4 years)<br/>  
+ ##### Jsp(2 years)<br/> 
+ ##### Javascript and Jquery and Ktor(1 years)<br /> 
#### <font color="#0000dd"> Contact information: </font><br /> 
+  ##### line:sam38124<br /> 

+  ##### gmail:sam38124@gmail.com
