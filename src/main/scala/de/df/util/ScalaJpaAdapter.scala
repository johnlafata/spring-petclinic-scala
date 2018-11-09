package de.df.util

trait ScalaJpaAdapter[T, ID] {
  def findAll(): Array[T]
  def findById(id: ID): Option[T]
  def save[S <: T](entity: S): S
  def deleteById(id: ID): Unit
}
