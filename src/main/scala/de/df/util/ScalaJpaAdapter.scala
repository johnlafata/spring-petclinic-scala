package de.df.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

import scala.collection.JavaConverters._
import de.df.util.JavaOptionals._

trait ScalaJpaAdapter[T, ID] { self: CrudRepository[T, ID] =>
  def _findById(@Param("id") id: ID): Option[T] = findById(id).toOption
  def _findAll(): Seq[T] = findAll().asScala.toSeq
}
