package de.df.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import de.df.entities.Owner
import de.df.service.ClinicService
import de.df.util.BindingError
import javax.transaction.Transactional
import javax.validation.Valid
import org.springframework.http.{HttpHeaders, HttpStatus, MediaType, ResponseEntity}
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._
import org.springframework.web.util.UriComponentsBuilder

import scala.collection.JavaConverters._

@RestController
@CrossOrigin
@RequestMapping(Array("/petclinic/api/owners"))
class OwnerController(val clinicService: ClinicService, val mapper: ObjectMapper) {

  @RequestMapping(value = Array("/*/lastname/{lastName}"), method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def getOwnersList(@PathVariable ownerLastName: String): ResponseEntity[Seq[Owner]] = {
    clinicService.findOwnerByLastName(ownerLastName) match {
      case Nil => new ResponseEntity(HttpStatus.NOT_FOUND)
      case owners => new ResponseEntity(owners, HttpStatus.OK)
    }
  }

  @RequestMapping(value = Array(""), method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def getOwners: ResponseEntity[Seq[Owner]] = {
    clinicService.findAllOwners match {
      case Nil => new ResponseEntity(HttpStatus.NOT_FOUND)
      case owners => new ResponseEntity(owners, HttpStatus.OK)
    }
  }

  @RequestMapping(value = Array("/{ownerId}"), method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def getOwner(@PathVariable ownerId: Int): ResponseEntity[Owner] = {
    clinicService.findOwnerById(ownerId) match {
      case None => new ResponseEntity(HttpStatus.NOT_FOUND)
      case Some(owner) => new ResponseEntity(owner, HttpStatus.OK)
    }
  }

  @RequestMapping(value = Array(""), method = Array(RequestMethod.POST), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def addOwner(@RequestBody @Valid owner: Owner, bindingResult: BindingResult, ucBuilder: UriComponentsBuilder): ResponseEntity[Owner] = {
    val headers = new HttpHeaders

    if(bindingResult.hasErrors || (owner == null)) {
      headers.add("errors", mapper.writeValueAsString(bindingResult.getFieldErrors.asScala.map(BindingError.from)))

      new ResponseEntity[Owner](headers, HttpStatus.BAD_REQUEST)
    } else {
      clinicService.saveOwner(owner)
      headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(int2Integer(owner.id)).toUri)

      new ResponseEntity[Owner](owner, headers, HttpStatus.CREATED)
    }
  }

  @RequestMapping(value = Array("/{ownerId}"), method = Array(RequestMethod.PUT), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def updateOwner(@PathVariable ownerId: Int, @RequestBody @Valid owner: Owner, bindingResult: BindingResult): ResponseEntity[Owner] = {
    val headers = new HttpHeaders

    if(bindingResult.hasErrors || (owner == null)) {
      headers.add("errors", mapper.writeValueAsString(bindingResult.getFieldErrors.asScala.map(BindingError.from)))

      new ResponseEntity[Owner](headers, HttpStatus.BAD_REQUEST)
    } else {
      clinicService.findOwnerById(ownerId) match {
        case None => new ResponseEntity[Owner](HttpStatus.NOT_FOUND)
        case Some(currentOwner) =>
          val updatedOwner = currentOwner.copy(owner.firstName, owner.lastName, owner.address, owner.city, owner.telephone, owner.pets)
          clinicService.saveOwner(updatedOwner)

          new ResponseEntity(updatedOwner, HttpStatus.NO_CONTENT)
      }
    }
  }

  @RequestMapping(value = Array("/{ownerId}"), method = Array(RequestMethod.DELETE), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  @Transactional
  def deleteOwner(@PathVariable ownerId: Int): ResponseEntity[Void] = {
    clinicService.findOwnerById(ownerId) match {
      case None => new ResponseEntity(HttpStatus.NOT_FOUND)
      case Some(owner) =>
        clinicService.deleteOwner(owner)

        new ResponseEntity(HttpStatus.NO_CONTENT)
    }
  }

}
