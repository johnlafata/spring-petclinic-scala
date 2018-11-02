package de.df.repositories

import de.df.entities.{Pet, PetType}
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

import java.lang

trait PetRepository extends CrudRepository[Pet, Int]{

  @Query("SELECT ptype FROM PetType ptype ORDER BY ptype.name")
  def findPetTypes: lang.Iterable[PetType]

}
