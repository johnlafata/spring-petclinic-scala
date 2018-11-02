package de.df.repositories

import de.df.entities.Owner
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.lang
import java.util.Optional

import de.df.util.ScalaJpaAdapter

import scala.collection.JavaConverters._

trait OwnerRepository extends CrudRepository[Owner, Int] with ScalaJpaAdapter[Owner, Int] {

  @Query("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName%")
  protected def findByLastName(@Param("lastName") lastName: String): lang.Iterable[Owner]

  @Query("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id")
  protected def findById(@Param("id") id: Int): Optional[Owner]

  protected def findAll(): lang.Iterable[Owner]

  def _findByLastName(@Param("lastName") lastName: String): Seq[Owner] = findByLastName(lastName).asScala.toSeq

}
