package de.df.util

import com.fasterxml.jackson.databind.ObjectMapper

object JacksonJsonImplicits {

  implicit class AsJsonSingle[T](val value: T) extends AnyVal {
    def asJson(implicit mapper: ObjectMapper): String = mapper.writeValueAsString(value)
  }

  implicit class AsJsonMultiple[T](val values: Traversable[T]) extends AnyVal {
    def asJson(implicit mapper: ObjectMapper): String = mapper.writeValueAsString(values)
  }

}
