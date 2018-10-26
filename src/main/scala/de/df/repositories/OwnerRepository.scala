package de.df.repositories

import de.df.entities.Owner
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.lang
import java.util.Optional

trait OwnerRepository extends CrudRepository[Owner, Integer]{

  @Query("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName%")
  def findByLastName(@Param("lastName") lastName: String): lang.Iterable[Owner]

  @Query("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id")
  def findById(@Param("id") id: Int): Optional[Owner]
}
