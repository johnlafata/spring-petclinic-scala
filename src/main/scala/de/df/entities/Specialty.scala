package de.df.entities

import javax.persistence._

import scala.annotation.meta.field
import scala.beans.BeanProperty

@Entity
@Table(name = "specialties")
case class Specialty(@BeanProperty name: String) {
  @(Id@field)
  @(GeneratedValue@field)(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Int = _

  protected def this() = this(null)
}
