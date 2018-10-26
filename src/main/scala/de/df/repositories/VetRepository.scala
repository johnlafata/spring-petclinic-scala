package de.df.repositories

import de.df.entities.Vet
import org.springframework.data.repository.CrudRepository

trait VetRepository extends CrudRepository[Vet, Integer] {
}
