package util

import DataModel
import org.redundent.kotlin.xml.*
import java.io.ByteArrayInputStream
import kotlin.jvm.Throws
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

class XMLparser {
    fun toXML(data: DataModel): String = xml("data") {
        DataModel::class.memberProperties.forEach {
            it.name { -it.getter.call(data).toString() }
        }
    }.toString(true)

    @Throws(NoSuchFieldException::class)
    fun fromXML(xmlString: String):DataModel {
        val data = DataModel()
        with(parse(ByteArrayInputStream(xmlString.toByteArray()))){
            DataModel::class.memberProperties.filterIsInstance<KMutableProperty<*>>().forEach{ property ->
                with(children.filterIsInstance<Node>().filter{ it.nodeName == property.name }){
                    if(size == 0) throw NoSuchFieldException()
                    property.setter.call(data,DataModel.convert((get(0).children[0] as TextElement).text,property.returnType))
                }
            }
        }
        return data
    }
}