package de.df.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import de.df.entities.Visit
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
@RequestMapping(Array("/petclinic/api/visits"))
class VisitController(clinicService: ClinicService, mapper: ObjectMapper) {

  @RequestMapping(value = Array(""), method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def getAllVisits: ResponseEntity[Seq[Visit]] = {
    clinicService.findAllVisits match {
      case Nil => new ResponseEntity(HttpStatus.NOT_FOUND)
      case visits => new ResponseEntity(visits, HttpStatus.OK)
    }
  }

  @RequestMapping(value = Array("/{visitId}"), method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def getVisit(@PathVariable visitId: Int): ResponseEntity[Visit] = {
    clinicService.findVisitById(visitId) match {
      case None => new ResponseEntity(HttpStatus.NOT_FOUND)
      case Some(visit) => new ResponseEntity(visit, HttpStatus.OK)
    }
  }

  @RequestMapping(value = Array(""), method = Array(RequestMethod.POST), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def addVisit(@RequestBody @Valid visit: Visit, bindingResult: BindingResult, ucBuilder: UriComponentsBuilder): ResponseEntity[Visit] = {
    val headers = new HttpHeaders

    if(bindingResult.hasErrors || (visit == null) || (visit.pet == null)) {
      headers.add("errors", mapper.writeValueAsString(bindingResult.getFieldErrors.asScala.map(BindingError.from)))

      new ResponseEntity[Visit](headers, HttpStatus.BAD_REQUEST)
    } else {
      clinicService.saveVisit(visit)
      headers.setLocation(ucBuilder.path("/api/visits/{id}").buildAndExpand(int2Integer(visit.id)).toUri)

      new ResponseEntity[Visit](visit, headers, HttpStatus.CREATED)
    }
  }

  @RequestMapping(value = Array("/{visitId}"), method = Array(RequestMethod.PUT), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  def updateVisit(@PathVariable visitId: Int, @RequestBody @Valid visit: Visit, bindingResult: BindingResult): ResponseEntity[Visit] = {
    val headers = new HttpHeaders

    if(bindingResult.hasErrors || (visit == null) || (visit.pet == null)) {
      headers.add("errors", mapper.writeValueAsString(bindingResult.getFieldErrors.asScala.map(BindingError.from)))

      new ResponseEntity[Visit](headers, HttpStatus.BAD_REQUEST)
    } else {
      clinicService.findVisitById(visitId) match {
        case None => new ResponseEntity[Visit](HttpStatus.NOT_FOUND)
        case Some(currentVisit) =>
          val updatedVisit = currentVisit.copy(date = visit.date, description = visit.description, pet = visit.pet)
          clinicService.saveVisit(updatedVisit)

          new ResponseEntity(updatedVisit, HttpStatus.NO_CONTENT)
      }
    }
  }

  @RequestMapping(value = Array("/{visitId}"), method = Array(RequestMethod.DELETE), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  @Transactional
  def deleteVisit(@PathVariable visitId: Int): ResponseEntity[Void] = {
    clinicService.findVisitById(visitId) match {
      case None => new ResponseEntity(HttpStatus.NOT_FOUND)
      case Some(visit) =>
        clinicService.deleteVisit(visit)

        new ResponseEntity(HttpStatus.NO_CONTENT)
    }
  }
}
