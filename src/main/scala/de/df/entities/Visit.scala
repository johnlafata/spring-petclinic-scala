package de.df.entities

import java.util.Date

import com.fasterxml.jackson.annotation.{JsonFormat, JsonIgnoreProperties}
import javax.persistence._
import javax.validation.constraints.NotEmpty
import org.springframework.format.annotation.DateTimeFormat

import scala.annotation.meta.field
import scala.beans.BeanProperty

@Entity
@Table(name = "visits")
case class Visit(@(Id@field)
                 @(GeneratedValue@field)(strategy = GenerationType.IDENTITY)
                 @BeanProperty
                 var id: Int,
                 @BeanProperty
                 @(Temporal@field)(TemporalType.TIMESTAMP)
                 @(DateTimeFormat@field)(pattern = "yyyy/MM/dd")
                 @(JsonFormat@field)(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
                 @(Column@field)(name = "visit_date")
                 date: Date,
                 @BeanProperty
                 @NotEmpty
                 description: String,
                 @BeanProperty
                 @(ManyToOne@field)
                 @(JoinColumn@field)(name = "pet_id")
                 @(JsonIgnoreProperties@field)(value = Array("visits"))
                 pet: Pet) {

  protected def this() = this(null.asInstanceOf[Int], null, null, null)
}
