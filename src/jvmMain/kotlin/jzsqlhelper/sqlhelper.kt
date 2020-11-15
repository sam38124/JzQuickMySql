package jzsqlhelper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.sql.ResultSet
import java.text.SimpleDateFormat
import kotlin.reflect.full.memberProperties

data class IndexType(val UNIQUE_KEY: Int = 0, val INDEX: Int = 1, val PRIMARY_KEY: Int = 2)
data class StringType(val TEXT: Int = 0, val VARCHAR: Int = 1, val DATETIME: Int = 2)

//Create Mysql Table
inline fun <reified T : Any> T.createMysqlTableString(
        database: String,
        tableName: String = T::class.java.simpleName,
        dataLength: ((MutableMap<String, Int>) -> Unit) = { b -> },
        addIndex: (MutableMap<String, Int>, IndexType) -> Unit = { b, c -> },
        setStringType: (MutableMap<String, Int>, StringType) -> Unit = { b, c -> }
): String {
    var sql = "CREATE TABLE IF NOT EXISTS `${database}`.`${tableName ?: T::class.java.simpleName}` (\n" +
            " `id` int NOT NULL AUTO_INCREMENT"
    val theDataLength: MutableMap<String, Int> = mutableMapOf()
    val theDataKey: MutableMap<String, Int> = mutableMapOf()
    val stringType: MutableMap<String, Int> = mutableMapOf()
    dataLength(theDataLength)
    addIndex(theDataKey, IndexType())
    setStringType(stringType, StringType())
    T::class.memberProperties.forEach { member ->
        val data = member.get(this)
        when (member.returnType.toString()) {
            "kotlin.String", "kotlin.String?" -> {
                val type=StringType()
                sql += when(stringType[member.name]){
                    type.DATETIME->{
                        ",\n`${member.name}` datetime DEFAULT ${(if (data == null || data == "CURRENT_TIMESTAMP") "CURRENT_TIMESTAMP" else "'$data'")}"
                    }
                    type.TEXT->{
                        ",\n`${member.name}` TEXT DEFAULT ${(if (data == null) "null" else "'${data}'")}"
                    }
                    else->{
                        ",\n`${member.name}` VARCHAR(${if (theDataLength[member.name] == null) 45 else theDataLength[member.name]}) DEFAULT ${(if (data == null) "null" else "'${data}'")}"
                    }
                }
            }
            "kotlin.Int", "kotlin.Int?" -> {
                sql += ",\n`${member.name}` INTEGER(${if (theDataLength[member.name] == null) 11 else theDataLength[member.name]}) DEFAULT ${(if (data == null) "null" else "$data")} "
            }
            "kotlin.Double", "kotlin.Double?" -> {
                sql += ",\n`${member.name}` Double DEFAULT ${(if (data == null) "null" else "$data")} "
            }
            else -> {
                sql += ",\n`${member.name}` VARCHAR(${if (theDataLength[member.name] == null) 45 else theDataLength[member.name]}) DEFAULT ${(if (data == null) "null" else "'${Gson().toJson(
                        data
                )}'")}"
            }
        }
    }
    val type = IndexType()
    var idextName = 0
    for (i in theDataKey) {
        idextName++
        when (i.value) {
            type.INDEX -> {
                sql += ",\nKEY `index$idextName` (`${i.key}`)"
            }
            type.PRIMARY_KEY -> {
                sql += ",\nPRIMARY KEY (`${i.key}`)"
            }
            type.UNIQUE_KEY -> {
                sql += ",\nUNIQUE KEY `UNIQUE$idextName` (`${i.key}`)"
            }
        }
    }
    sql += "\n ,PRIMARY KEY (`id`));"
    return sql
}

//Insert Mysql Table
inline fun <reified T : Any> T.insertMysqlTableString(
        database: String, tableName: String = T::class.java.simpleName
): String {
    var sql = "insert into `$database`.`${tableName}`"
    var col = "("
    var value = "("
    T::class.memberProperties.forEach { member ->
        val data = member.get(this)
        if (data != null) {
            if (col !== "(") {
                col += ","
                value += ","
            }
            col += ("${member.name}")
            when (member.returnType.toString()) {
                "java.util.Date", "java.util.Date?" -> {
                    value += "'${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data)}'"
                }
                "kotlin.String", "kotlin.String?" -> {
                    value += "'$data'"
                }
                "kotlin.Int", "kotlin.Int?" -> {
                    value += "$data"
                }
                "kotlin.Double", "kotlin.Double?" -> {
                    value += "$data"
                }
                else -> {
                    value += "'${Gson().toJson(data)}'"
                }
            }
        }
    }
    col += ")"
    value += ")"
    sql += col
    sql += " values "
    sql += value
    return sql
}


//REPLACE Mysql Table
inline fun <reified T : Any> T.replaceMysqlTableString(
        database: String, tableName: String = T::class.java.simpleName
): String {
    var sql = "replace into `$database`.`${tableName}`"
    var col = "("
    var value = "("
    T::class.memberProperties.forEach { member ->
        val data = member.get(this)
        if (data != null) {
            if (col !== "(") {
                col += ","
                value += ","
            }
            col += ("${member.name}")
            when (member.returnType.toString()) {
                "java.util.Date", "java.util.Date?" -> {
                    value += "'${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data)}'"
                }
                "kotlin.String", "kotlin.String?" -> {
                    value += "'$data'"
                }
                "kotlin.Int", "kotlin.Int?" -> {
                    value += "$data"
                }
                "kotlin.Double", "kotlin.Double?" -> {
                    value += "$data"
                }
                else -> {
                    value += "'${Gson().toJson(data)}'"
                }
            }
        }
    }
    col += ")"
    value += ")"
    sql += col
    sql += " values "
    sql += value
    return sql
}


//Select Mysql Table
inline fun <reified T : Any> T.selectMysqlTableString(
        database: String,
        tableName: String = T::class.java.simpleName,
        orderByString: String = "",
        filter: ((MutableMap<String, String>) -> Unit) = { b -> }
): String {
    val map: MutableMap<String, String> = mutableMapOf()
    filter(map)
    var col = ""
    T::class.memberProperties.forEach { member ->
        col += if (col.isNotEmpty()) {
            ",`${member.name}`"
        } else {
            "`${member.name}`"
        }
    }
    var queryString = ""
    for (i in map) {
        if (queryString.isNotEmpty()) {
            queryString += " and "
        }
        queryString += "${i.key}=${i.value}"
    }
    return "select $col from `$database`.`$tableName` ${if (queryString.isNotEmpty()) "where $queryString" else ""}"
}

//Update Mysql Table
inline fun <reified T : Any> T.updateMysqlTableString(
        database: String, tableName: String = T::class.java.simpleName,
        filter: ((MutableMap<String, String>) -> Unit) = { b -> }
): String {
    var sql = "UPDATE `$database`.`${tableName}`"
    var col = " SET "
    val map: MutableMap<String, String> = mutableMapOf()
    filter(map)
    T::class.memberProperties.forEach { member ->
        val data = member.get(this)
        if (data != null) {
            if (col !== " SET ") {
                col += ","
            }
            when (member.returnType.toString()) {
                "java.util.Date", "java.util.Date?" -> {
                    col += "${(member.name)}='${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data)}'"
                }
                "kotlin.String", "kotlin.String?" -> {
                    col += "${(member.name)}='$data'"
                }
                "kotlin.Int", "kotlin.Int?" -> {
                    col += "${(member.name)}=$data"
                }
                "kotlin.Double", "kotlin.Double?" -> {
                    col += "${(member.name)}=$data"
                }
                else -> {
                    col += "${(member.name)}='${Gson().toJson(data)}'"
                }
            }

        }
    }
    sql += col
    var queryString = ""
    for (i in map) {
        if (queryString.isNotEmpty()) {
            queryString += " and "
        }
        queryString += "${i.key}=${i.value} "
    }
    sql += if (queryString.isNotEmpty()) " where $queryString" else ""
    return sql
}
//getDataFromResultSet
inline fun <reified T : Any> T.getDataFromResultSet(
        rs: ResultSet
): ArrayList<T> {
    val returnData = ArrayList<T>()
    while (rs.next()) {
        val map: MutableMap<String, Any> = mutableMapOf()
        T::class.memberProperties.forEach { member ->
            when (member.returnType.toString()) {
                "java.util.Date", "java.util.Date?" -> {
                    map[member.name] = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(member.name))
                }
                "kotlin.String", "kotlin.String?" -> {
                    map[member.name] = rs.getString(member.name)
                }
                "kotlin.Int", "kotlin.Int?" -> {
                    map[member.name] = rs.getInt(member.name)
                }
                "kotlin.Double", "kotlin.Double?" -> {
                    map[member.name] = rs.getDouble(member.name)
                }
                else -> {
                    val siMap: MutableMap<String, Any> = Gson().fromJson<MutableMap<String, Any>>(rs.getString(member.name),object :TypeToken<MutableMap<String, Any>>(){}.type)
                    map[member.name] = siMap
                }
            }
        }
        val json=Gson().toJson(map)
        returnData.add(Gson().fromJson(json,T::class.java))
    }
    return returnData
}

