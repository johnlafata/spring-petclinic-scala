package de.df.entities

import java.util
import java.util.{Collections, Date}

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}
import javax.persistence._
import org.springframework.format.annotation.DateTimeFormat

import scala.annotation.meta.field
import scala.beans.BeanProperty
import scala.collection.JavaConverters._

@Entity
@Table(name = "pets")
case class Pet(@BeanProperty
               name: String,
               @BeanProperty
               @Temporal(TemporalType.DATE)
               @DateTimeFormat(pattern = "yyyy/MM/dd")
               birthDate: Date,
               @BeanProperty
               @(ManyToOne@field)
               @(JoinColumn@field)(name = "type_id")
               @(JsonProperty@field)(value = "type")
               petType: PetType,
               @BeanProperty
               @(JsonIgnoreProperties@field)(value = Array("pets"))
               @(ManyToOne@field)
               @(JoinColumn@field)(name = "owner_id")
               owner: Owner,
               @(OneToMany@field)(cascade = Array(CascadeType.ALL), mappedBy = "pet", fetch = FetchType.EAGER)
               @(JsonIgnoreProperties@field)(value = Array("pet"))
               visits: util.List[Visit]) {

  @(Id@field)
  @(GeneratedValue@field)(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Int = _

  protected def this() = this(null, null, null, null, Collections.emptyList())

  def getVisits: util.List[Visit] = {
    visits.asScala.sortBy(_.date).toList.asJava
  }

  def addVisit(visit: Visit): Unit = {
    visits.add(visit)
  }
}
