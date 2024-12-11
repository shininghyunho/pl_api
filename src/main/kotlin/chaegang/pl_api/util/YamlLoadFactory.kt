package chaegang.pl_api.util

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory

class YamlLoadFactory : PropertySourceFactory {
    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> {
        val yamlFactory = YamlPropertiesFactoryBean()
        yamlFactory.setResources(resource.resource)

        val properties = yamlFactory.getObject()
        val fileName = resource.resource.filename
        return PropertiesPropertySource(fileName!!, properties!!)
    }
}