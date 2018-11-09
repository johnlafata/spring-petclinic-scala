package de.df.service

import de.df.entities.{Owner, Pet, PetType, Visit}
import de.df.repositories.{OwnerRepository, PetRepository, VisitRepository}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClinicService(visitRepository: VisitRepository, petRepository: PetRepository, ownerRepository: OwnerRepository) {

  @Transactional(readOnly = true)
  def findAllOwners: Seq[Owner] = ownerRepository.findAll()

  def findOwnerByLastName(lastName: String): Seq[Owner] = {
    ownerRepository.findByLastName(lastName)
  }

  @Transactional(readOnly = true)
  def findOwnerById(ownerId: Int): Option[Owner] = {
    ownerRepository.findById(ownerId)
  }

  @Transactional
  def saveOwner(owner: Owner): Unit = {
    ownerRepository.save(owner)
  }

  @Transactional
  def deleteOwner(owner: Owner): Unit = {
    ownerRepository.deleteById(owner.id)
  }

  @Transactional
  def deletePet(pet: Pet): Unit = {
    petRepository.deleteById(pet.id)
  }

  @Transactional
  def savePet(pet: Pet): Unit = {
    petRepository.save(pet)
  }

  @Transactional(readOnly = true)
  def findPetTypes: Seq[PetType] = petRepository.findPetTypes

  @Transactional(readOnly = true)
  def findAllPets: Seq[Pet] = petRepository.findAll()

  @Transactional(readOnly = true)
  def findPetById(id: Int): Option[Pet] = {
    petRepository.findById(id)
  }

  @Transactional
  def deleteVisit(visit: Visit): Unit = {
    visitRepository.deleteById(visit.id)
  }

  @Transactional
  def saveVisit(visit: Visit): Unit = {
    visitRepository.save(visit)
  }

  @Transactional(readOnly = true)
  def findAllVisits: Seq[Visit] = visitRepository.findAll()

  @Transactional(readOnly = true)
  def findVisitById(visitId: Int): Option[Visit] = {
    visitRepository.findById(visitId)
  }
}
