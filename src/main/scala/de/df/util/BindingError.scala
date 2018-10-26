package de.df.util

import org.springframework.validation.FieldError

case class BindingError(objectName: String, fieldName: String, fieldValue: String, errorMessage: String)

object BindingError {
  def from(fieldError: FieldError): BindingError = {
    BindingError(fieldError.getObjectName, fieldError.getField, fieldError.getRejectedValue.toString, fieldError.getDefaultMessage)
  }
}
