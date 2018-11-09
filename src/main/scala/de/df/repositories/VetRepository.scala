package de.df.repositories

import de.df.entities.Vet
import de.df.util.ScalaJpaAdapter
import org.springframework.data.repository.Repository

trait VetRepository extends Repository[Vet, Int] with ScalaJpaAdapter[Vet, Int] {
}
