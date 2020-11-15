package jzsqlhelper


import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class MysqlConnect {
    companion object {
        var root = ""
        var admin = ""
        var password = ""
        fun setUp(root: String, admin: String, password: String) {
            this.admin = admin
            this.password = password
            this.root = root
        }
    }

    var conn: Connection
    var stmt: Statement

    init {
        Class.forName("com.mysql.jdbc.Driver")
        conn = DriverManager.getConnection(root, admin, password)
        stmt = conn.createStatement()
    }


    fun close() {
        conn.close()
        stmt.close()
    }

    fun startRequest(callback:(conn: Connection,stmt: Statement)->Unit){
        callback(conn, stmt)
        close()
    }
}

interface callback {
    fun callback(rs: ResultSet)
}

fun String.mySqlQuery(callback: callback) {
    val a = MysqlConnect()
    val rs = a.stmt.executeQuery(this)
    while (rs.next()) {
        callback.callback(rs)
    }
    a.close()
}


