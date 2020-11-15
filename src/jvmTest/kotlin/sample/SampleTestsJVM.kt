package sample

import kotlin.test.Test
import kotlin.test.assertTrue
import com.google.gson.Gson
import jzsqlhelper.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

data class TestData(
        var admin: String = "adminString",
        var password: String = "passwordString",
        var userSetting: UserSetting = UserSetting(true),
        var date: String = "CURRENT_TIMESTAMP")

data class UserSetting(var click: Boolean, var date: String = "2019-11-12 12:23:22")
class SampleTestsJVM {
    @Test
    fun testHello() {
        assertTrue("JVM" in hello())
        println(TestData())
    }
}

fun main() {
    //Create table
    println(TestData().createMysqlTableString("TestData", dataLength = {
        it["admin"] = 300
        it["password"] = 400
        it["userSetting"] = 300
    }, addIndex = { map, indexType ->
        map["admin"] = indexType.UNIQUE_KEY
        map["password"] = indexType.PRIMARY_KEY
    },setStringType = {
        map,stringType ->
        map["date"]=stringType.DATETIME
    }))
    //Insert Table
    println(TestData().insertMysqlTableString("TestData"))
    //Replace Table
    println(TestData().replaceMysqlTableString("TestData"))
    //Select Table
    println(TestData().selectMysqlTableString("TestData"))
    //Select With Filter
    println(TestData().selectMysqlTableString("TestData") {
        it["admin"] = "'10'"
    })
    println(TestData().updateMysqlTableString("TestData") {
        it["admin"] = "'10'"
        it["password"] = "'sam2842'"
    })

    MysqlConnect.setUp("jdbc:mysql://54.199.109.182:3306?autoReconnect=false&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
            "admin",
            "password")

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
        println()
        //Start create
        stmt.executeUpdate(createTable)
        //String to insert table
        val insert=TestData("admin","sam12334",date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())).insertMysqlTableString("DBNAME")
        //Start insert
        stmt.executeUpdate(insert)
        //Select and return array of TestData
        val data:ArrayList<TestData> = TestData().getDataFromResultSet(stmt.executeQuery("select * from tsport.TestData")
        )
    }
}