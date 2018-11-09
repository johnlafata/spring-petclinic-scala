package de.df.repositories

import de.df.entities.Owner
import de.df.util.ScalaJpaAdapter
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

trait OwnerRepository extends Repository[Owner, Int] with ScalaJpaAdapter[Owner, Int]{

  def findAll(): Array[Owner]

  @Query("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName%")
  def findByLastName(@Param("lastName") lastName: String): Array[Owner]

  @Query("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id")
  def findById(@Param("id") id: Int): Option[Owner]

}
