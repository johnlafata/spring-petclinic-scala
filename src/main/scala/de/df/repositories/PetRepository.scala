package de.df.repositories

import de.df.entities.{Pet, PetType}
import de.df.util.ScalaJpaAdapter
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository

trait PetRepository extends Repository[Pet, Int] with ScalaJpaAdapter[Pet, Int] {

  def findAll(): Array[Pet]

  @Query("SELECT ptype FROM PetType ptype ORDER BY ptype.name")
  def findPetTypes: Array[PetType]

}
