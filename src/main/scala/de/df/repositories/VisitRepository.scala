package de.df.repositories

import java.util

import de.df.entities.Visit
import org.springframework.data.repository.CrudRepository

trait VisitRepository extends CrudRepository[Visit, Int] {
  def save(visit: Visit): Unit
  def findByPetId(petId: Int): util.List[Visit]
}
