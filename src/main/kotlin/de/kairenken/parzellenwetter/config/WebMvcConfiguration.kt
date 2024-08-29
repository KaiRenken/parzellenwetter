package de.kairenken.parzellenwetter.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * This is needed for the routing in the react ui; spring must forward all ui urls to the html page with the react client
 */
@Configuration
class WebMvcConfiguration : WebMvcConfigurer {

    override fun addViewControllers(registry: ViewControllerRegistry) {
        listOf(
            "/gui",
            "/gui/",
            "/gui/historie",
            "/gui/historie/",
            "/gui/statistik",
            "/gui/statistik/"
        ).forEach {
            addViewController(registry, it)
        }
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowedOrigins("*")
    }

    private fun addViewController(registry: ViewControllerRegistry, urlPath: String) {
        registry.addViewController(urlPath).setViewName("forward:/gui/index.html")
    }
}