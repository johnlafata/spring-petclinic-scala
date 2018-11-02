package de.df.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import de.df.entities.{Pet, PetType}
import de.df.service.ClinicService
import de.df.util.BindingError
import javax.transaction.Transactional
import javax.validation.Valid
import org.springframework.http.{HttpHeaders, HttpStatus, MediaType, ResponseEntity}
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._
import org.springframework.web.util.UriComponentsBuilder

import scala.collection.JavaConverters._
import de.df.util.JacksonJsonImplicits._

@RestController
@CrossOrigin
@RequestMapping(Array("/petclinic/api/pets"))
class PetController(clinicService: ClinicService)(implicit val mapper: ObjectMapper) {

  @RequestMapping(value = Array("/{petId}"), method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def getPet(@PathVariable petId: Int): ResponseEntity[Pet] = {
    clinicService.findPetById(petId) match {
      case None => new ResponseEntity(HttpStatus.NOT_FOUND)
      case Some(pet) => new ResponseEntity(pet, HttpStatus.OK)
    }
  }

  @RequestMapping(value = Array(""), method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def getPets: ResponseEntity[Seq[Pet]] = {
    clinicService.findAllPets match {
      case Nil => new ResponseEntity(HttpStatus.NOT_FOUND)
      case pets => new ResponseEntity(pets, HttpStatus.OK)
    }
  }

  @RequestMapping(value = Array("/pettypes"), method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def getPetTypes: ResponseEntity[Seq[PetType]] = {
    new ResponseEntity(clinicService.findPetTypes, HttpStatus.OK)
  }

  @RequestMapping(value = Array(""), method = Array(RequestMethod.POST), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def addPet(@RequestBody @Valid pet: Pet, bindingResult: BindingResult, ucBuilder: UriComponentsBuilder): ResponseEntity[Pet] = {
    val headers = new HttpHeaders

    if(bindingResult.hasErrors || (pet == null)) {
      headers.add("errors", bindingResult.getFieldErrors.asScala.map(BindingError.from).asJson)

      new ResponseEntity[Pet](headers, HttpStatus.BAD_REQUEST)
    } else {
      clinicService.savePet(pet)
      headers.setLocation(ucBuilder.path("/api/pets/{id}").buildAndExpand(int2Integer(pet.id)).toUri)
      new ResponseEntity[Pet](pet, headers, HttpStatus.CREATED)
    }
  }

  @RequestMapping(value = Array("/{petId}"), method = Array(RequestMethod.PUT), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def updatePet(@PathVariable petId: Int, @RequestBody @Valid pet: Pet, bindingResult: BindingResult): ResponseEntity[Pet] = {
    val headers = new HttpHeaders

    if(bindingResult.hasErrors || (pet == null)) {
      headers.add("errors", bindingResult.getFieldErrors.asScala.map(BindingError.from).asJson)

      new ResponseEntity[Pet](headers, HttpStatus.BAD_REQUEST)
    } else {
      clinicService.findPetById(petId) match {
        case None => new ResponseEntity[Pet](HttpStatus.NOT_FOUND)
        case Some(currentPet) =>
          val updatedPet = currentPet.copy(name = pet.name, birthDate = pet.birthDate, petType = pet.petType, owner = pet.owner)
          clinicService.savePet(updatedPet)

          new ResponseEntity(updatedPet, HttpStatus.NO_CONTENT)
      }
    }
  }

  @RequestMapping(value = Array("/{petId}"), method = Array(RequestMethod.DELETE), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  @Transactional
  def deletePet(@PathVariable petId: Int): ResponseEntity[Void] = {
    clinicService.findPetById(petId) match {
      case None => new ResponseEntity(HttpStatus.NOT_FOUND)
      case Some(pet) =>
        clinicService.deletePet(pet)

        new ResponseEntity(HttpStatus.NO_CONTENT)
    }
  }
}
