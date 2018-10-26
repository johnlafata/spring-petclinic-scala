package de.df.entities

import javax.persistence._
import java.util
import java.util.Collections

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.{Digits, NotEmpty}

import scala.annotation.meta.field
import scala.beans.BeanProperty
import scala.collection.JavaConverters._

@Entity
@Table(name = "owners")
case class Owner(@BeanProperty
                 firstName: String,
                 @BeanProperty
                 lastName: String,
                 @BeanProperty
                 @NotEmpty
                 address: String,
                 @BeanProperty
                 @NotEmpty
                 city: String,
                 @BeanProperty
                 @NotEmpty
                 @Digits(fraction = 0, integer = 10)
                 telephone: String,
                 @(OneToMany@field)(cascade = Array(CascadeType.ALL), mappedBy = "owner", fetch = FetchType.EAGER)
                 @(JsonIgnoreProperties@field)(value = Array("owner"))
                 pets: util.List[Pet]) {

  @(Id@field)
  @(GeneratedValue@field)(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Int = _

  protected def this() = this(null, null, null, null, null, Collections.emptyList())

  def getPets: util.List[Pet] = {
    pets.asScala.sortBy(_.name).toList.asJava
  }

  def addPet(pet: Pet): Unit = {
    pets.add(pet)
  }
}
