package util

import DataModel
import kotlinx.html.body
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import kotlinx.html.form
import kotlinx.html.input
import org.jsoup.Jsoup
import kotlin.jvm.Throws
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

class HTMLparser {
    fun toHTML(data: DataModel):String = createHTMLDocument().body {
        form {
            DataModel::class.memberProperties.forEach{
                input { name = it.name; value = it.getter.call(data).toString() }
            }
        }
    }.serialize(true)

    @Throws(NoSuchFieldException::class)
    fun fromHTML(htmlString: String):DataModel {
        val data = DataModel()
        with(Jsoup.parse(htmlString)){
            DataModel::class.memberProperties.filterIsInstance<KMutableProperty<*>>().forEach{
                with(select("input[name=${it.name}]")){
                    if(size == 0) throw NoSuchFieldException()
                    it.setter.call(data,DataModel.convert(get(0).`val`(),it.returnType))
                }
            }
        }
        return data
    }
}
