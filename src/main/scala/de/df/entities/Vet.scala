package de.df.entities

import java.util.Collections
import java.util

import javax.persistence._

import scala.annotation.meta.field
import scala.beans.BeanProperty
import scala.collection.JavaConverters._

@Entity
@Table(name = "vets")
case class Vet(@BeanProperty
               firstName: String,
               @BeanProperty
               lastName: String,
               @(ManyToMany@field)(fetch = FetchType.EAGER)
               @(JoinTable@field)(name = "vet_specialties", joinColumns = Array(new JoinColumn(name = "vet_id")), inverseJoinColumns = Array(new JoinColumn(name = "specialty_id")))
               specialties: java.util.List[Specialty]) {

  @(Id@field)
  @(GeneratedValue@field)(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Int = _

  protected def this() = this(null, null, Collections.emptyList())

  def getSpecialties: util.List[Specialty] = {
    specialties.asScala.sortBy(_.name).toList.asJava
  }

  def getNrOfSpecialties: Int = specialties.size

  def addSpecialty(specialty: Specialty): Unit = {
    specialties.add(specialty)
  }
}
