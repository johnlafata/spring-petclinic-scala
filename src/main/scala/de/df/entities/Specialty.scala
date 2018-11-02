package de.df.entities

import javax.persistence._

import scala.annotation.meta.field
import scala.beans.BeanProperty

@Entity
@Table(name = "specialties")
case class Specialty(@(Id@field)
                     @(GeneratedValue@field)(strategy = GenerationType.IDENTITY)
                     @BeanProperty
                     var id: Int,
                     @BeanProperty
                     name: String) {

  protected def this() = this(null.asInstanceOf[Int], null)
}
