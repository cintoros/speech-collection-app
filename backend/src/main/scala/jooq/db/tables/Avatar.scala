/*
 * This file is generated by jOOQ.
 */
package jooq.db.tables


import java.lang.Class
import java.lang.Integer
import java.lang.String
import java.util.Arrays
import java.util.List

import jooq.db.Indexes
import jooq.db.Keys
import jooq.db.LabelingTool
import jooq.db.tables.records.AvatarRecord

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Identity
import org.jooq.Index
import org.jooq.Name
import org.jooq.Record
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.TableImpl

import scala.Array
import scala.Byte


object Avatar {

  val AVATAR = new Avatar
}

class Avatar(
  alias : Name,
  child : Table[_ <: Record],
  path : ForeignKey[_ <: Record, AvatarRecord],
  aliased : Table[AvatarRecord],
  parameters : Array[ Field[_] ]
)
extends TableImpl[AvatarRecord](
  alias,
  LabelingTool.LABELING_TOOL,
  child,
  path,
  aliased,
  parameters,
  DSL.comment("")
)
{

  override def getRecordType : Class[AvatarRecord] = {
    classOf[AvatarRecord]
  }

  val ID : TableField[AvatarRecord, Integer] = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), "")

  val USERID : TableField[AvatarRecord, Integer] = createField("userId", org.jooq.impl.SQLDataType.INTEGER, "")

  val AVATAR_ : TableField[AvatarRecord, Array[Byte]] = createField("avatar", org.jooq.impl.SQLDataType.BLOB, "")

  def this() = {
    this(DSL.name("avatar"), null, null, null, null)
  }

  def this(alias : String) = {
    this(DSL.name(alias), null, null, jooq.db.tables.Avatar.AVATAR, null)
  }

  def this(alias : Name) = {
    this(alias, null, null, jooq.db.tables.Avatar.AVATAR, null)
  }

  private def this(alias : Name, aliased : Table[AvatarRecord]) = {
    this(alias, null, null, aliased, null)
  }

  def this(child : Table[_ <: Record], key : ForeignKey[_ <: Record, AvatarRecord]) = {
    this(Internal.createPathAlias(child, key), child, key, jooq.db.tables.Avatar.AVATAR, null)
  }

  override def getSchema : Schema = LabelingTool.LABELING_TOOL

  override def getIndexes : List[ Index ] = {
    return Arrays.asList[ Index ](Indexes.AVATAR_PRIMARY)
  }

  override def getIdentity : Identity[AvatarRecord, Integer] = {
    Keys.IDENTITY_AVATAR
  }

  override def getPrimaryKey : UniqueKey[AvatarRecord] = {
    Keys.KEY_AVATAR_PRIMARY
  }

  override def getKeys : List[ UniqueKey[AvatarRecord] ] = {
    return Arrays.asList[ UniqueKey[AvatarRecord] ](Keys.KEY_AVATAR_PRIMARY)
  }

  override def as(alias : String) : Avatar = {
    new Avatar(DSL.name(alias), this)
  }

  override def as(alias : Name) : Avatar = {
    new Avatar(alias, this)
  }

  override def rename(name : String) : Avatar = {
    new Avatar(DSL.name(name), null)
  }

  override def rename(name : Name) : Avatar = {
    new Avatar(name, null)
  }
}
