package de.df

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class PetClinicApplication(val mapper: ObjectMapper) {
  mapper
    .registerModule(DefaultScalaModule)
    .registerModule(new JavaTimeModule)
}

object PetClinicApplication {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[PetClinicApplication], args: _*)
  }
}
