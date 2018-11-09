package de.df.repositories

import de.df.entities.Visit
import de.df.util.ScalaJpaAdapter
import org.springframework.data.repository.Repository

trait VisitRepository extends Repository[Visit, Int] with ScalaJpaAdapter[Visit, Int] {
  def findByPetId(petId: Int): Array[Visit]
}
