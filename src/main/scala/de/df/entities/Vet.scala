package de.df.entities

import java.util.Collections
import java.util

import javax.persistence._

import scala.annotation.meta.field
import scala.beans.BeanProperty
import scala.collection.JavaConverters._

@Entity
@Table(name = "vets")
case class Vet(@(Id@field)
               @(GeneratedValue@field)(strategy = GenerationType.IDENTITY)
               @BeanProperty
               var id: Int,
               @BeanProperty
               firstName: String,
               @BeanProperty
               lastName: String,
               @(ManyToMany@field)(fetch = FetchType.EAGER)
               @(JoinTable@field)(name = "vet_specialties", joinColumns = Array(new JoinColumn(name = "vet_id")), inverseJoinColumns = Array(new JoinColumn(name = "specialty_id")))
               specialties: java.util.List[Specialty]) {

  protected def this() = this(null.asInstanceOf[Int], null, null, Collections.emptyList())

  def getSpecialties: util.List[Specialty] = {
    specialties.asScala.sortBy(_.name).toList.asJava
  }

}
