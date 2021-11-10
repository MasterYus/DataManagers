package util

import DataModel
import kotlinx.serialization.json.*
import kotlin.jvm.Throws
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

class JSONparser {
    fun toJSON(data: DataModel):String =
        buildJsonObject {
            DataModel::class.memberProperties.forEach{
                put(it.name, it.getter.call(data).toString())
            }
        }.toString()

    @Throws(NoSuchFieldException::class)
    fun fromJSON(jsonString: String):DataModel {
        val data = DataModel()
        with(Json.parseToJsonElement(jsonString)){
            DataModel::class.memberProperties.filterIsInstance<KMutableProperty<*>>().forEach{
                with(jsonObject){
                    it.setter.call(data,DataModel.convert(getValue(it.name).jsonPrimitive.content,it.returnType))
                }
            }
        }
        return data
    }
}