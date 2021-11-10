import util.HTMLparser
import util.JSONparser
import util.XMLparser
import kotlin.jvm.Throws
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure


/**
 * Data class for sensor data encapsulation
 *
 * @param current analog float value from current sensor
 * @param smoke percentage of CO2 gas persistence
 * @param temp celsius degrees from temp sensor
 * @param voltage current voltage from voltage sensor
 * @param relay current power relay state
 *
 */
data class DataModel(
    var socket_id:Int = 0,
    var socket_name:String = "",
    var current:Float = 0f,
    var smoke:Int = 0,
    var temp:Float = 0f,
    var voltage:Float = 0f,
    var relay:Boolean = false
) {
    companion object {
        /**
         * Type fix function for reflection based assignment
         * @param str string value to be converted to type
         * @param type kotlin type str val to be converted into
         */
        @Throws(IllegalArgumentException::class)
        fun convert(str: String, type: KType):Any = when (type.jvmErasure) {
            Float::class -> str.toFloat()
            Int::class -> str.toInt()
            Short::class -> str.toShort()
            Boolean::class ->str.toBoolean()
            Byte::class -> str.toByte()
            String::class -> str
            else -> throw IllegalArgumentException("'$str' cannot be converted to $type")
        }
    }
}

/**
 * Main function.
 * Sample function to demonstrate data parsers.
 */
fun main(args: Array<String>) {
    val testData = DataModel(300,"duck",2.1f,20,25.0f,220.0f,true)
    //HTML forming and parsing
    val htmlParser = HTMLparser()
    with(htmlParser.toHTML(testData)){
        println(this)
        println(htmlParser.fromHTML(this))
    }
    //JSON forming and parsing
    val jsonParser = JSONparser()
    with(jsonParser.toJSON(testData)){
        println(this)
        println(jsonParser.fromJSON(this))
    }
    //XML forming and parsing
    val xmlParser = XMLparser()
    with(xmlParser.toXML(testData)){
        println(this)
        println(xmlParser.fromXML(this))
    }

}