package com.frdx.tcgstats.config.documentation

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration


@Configuration
@OpenAPIDefinition(info = Info(title = "Web API"))
class SwaggerConfiguration