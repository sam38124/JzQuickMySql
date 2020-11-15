package sample
expect class Sample() {
    fun checkMe(): Int
}

expect object Platform {
    val name: String
}

fun hello(): String {
    return  "Hello from ${Platform.name}"}