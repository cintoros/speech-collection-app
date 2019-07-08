/*
 * This file is generated by jOOQ.
 */
package jooq.db.tables.pojos


import java.io.Serializable
import java.lang.Integer
import java.lang.String
import java.lang.StringBuilder


case class Transcript(
    id : Integer
  , text : String
) extends Serializable {

  def this (value : Transcript) = {
    this(
        value.id
      , value.text
    )
  }

  def getId : Integer = {
    this.id
  }

  def getText : String = {
    this.text
  }

  override def toString : String = {
    val sb = new StringBuilder("Transcript (")

    sb.append(id)
    sb.append(", ").append(text)

    sb.append(")")
    return sb.toString
  }
}
