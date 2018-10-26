package de.df.util

import java.util.Optional

object JavaOptionals {
  implicit def toRichOption[T](opt: Option[T]): RichOption[T] = new RichOption[T](opt)
  implicit def toRichOptional[T](optional: Optional[T]): RichOptional[T] = new RichOptional[T](optional)
}

class RichOption[T] (opt: Option[T]) {
  def toOptional: Optional[T] = Optional.ofNullable(opt.getOrElse(null).asInstanceOf[T])
}

class RichOptional[T] (opt: Optional[T]) {
  def toOption: Option[T] = if (opt.isPresent) Some(opt.get()) else None
}
