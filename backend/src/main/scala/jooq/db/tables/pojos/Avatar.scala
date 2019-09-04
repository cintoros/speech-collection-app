/*
 * This file is generated by jOOQ.
 */
package jooq.db.tables.pojos


import java.io.Serializable
import java.lang.Integer
import java.lang.StringBuilder

import scala.Array
import scala.Byte


case class Avatar(
    id : Integer
  , userid : Integer
  , avatar : Array[Byte]
) extends Serializable {

  def this (value : Avatar) = {
    this(
        value.id
      , value.userid
      , value.avatar
    )
  }

  def getId : Integer = {
    this.id
  }

  def getUserid : Integer = {
    this.userid
  }

  def getAvatar : Array[Byte] = {
    this.avatar
  }

  override def toString : String = {
    val sb = new StringBuilder("Avatar (")

    sb.append(id)
    sb.append(", ").append(userid)
    sb.append(", ").append("[binary...]")

    sb.append(")")
    return sb.toString
  }
}
