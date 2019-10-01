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
import jooq.db.tables.records.RecordingsRecord

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


object Recordings {

  val RECORDINGS = new Recordings
}

class Recordings(
  alias : Name,
  child : Table[_ <: Record],
  path : ForeignKey[_ <: Record, RecordingsRecord],
  aliased : Table[RecordingsRecord],
  parameters : Array[ Field[_] ]
)
extends TableImpl[RecordingsRecord](
  alias,
  LabelingTool.LABELING_TOOL,
  child,
  path,
  aliased,
  parameters,
  DSL.comment("")
)
{

  override def getRecordType : Class[RecordingsRecord] = {
    classOf[RecordingsRecord]
  }

  val ID : TableField[RecordingsRecord, Integer] = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), "")

  val TEXT : TableField[RecordingsRecord, String] = createField("text", org.jooq.impl.SQLDataType.CLOB, "")

  val USERID : TableField[RecordingsRecord, Integer] = createField("userId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), "")

  val AUDIO : TableField[RecordingsRecord, Array[Byte]] = createField("audio", org.jooq.impl.SQLDataType.BLOB, "")

  def this() = {
    this(DSL.name("recordings"), null, null, null, null)
  }

  def this(alias : String) = {
    this(DSL.name(alias), null, null, jooq.db.tables.Recordings.RECORDINGS, null)
  }

  def this(alias : Name) = {
    this(alias, null, null, jooq.db.tables.Recordings.RECORDINGS, null)
  }

  private def this(alias : Name, aliased : Table[RecordingsRecord]) = {
    this(alias, null, null, aliased, null)
  }

  def this(child : Table[_ <: Record], key : ForeignKey[_ <: Record, RecordingsRecord]) = {
    this(Internal.createPathAlias(child, key), child, key, jooq.db.tables.Recordings.RECORDINGS, null)
  }

  override def getSchema : Schema = LabelingTool.LABELING_TOOL

  override def getIndexes : List[ Index ] = {
    return Arrays.asList[ Index ](Indexes.RECORDINGS_PRIMARY)
  }

  override def getIdentity : Identity[RecordingsRecord, Integer] = {
    Keys.IDENTITY_RECORDINGS
  }

  override def getPrimaryKey : UniqueKey[RecordingsRecord] = {
    Keys.KEY_RECORDINGS_PRIMARY
  }

  override def getKeys : List[ UniqueKey[RecordingsRecord] ] = {
    return Arrays.asList[ UniqueKey[RecordingsRecord] ](Keys.KEY_RECORDINGS_PRIMARY)
  }

  override def as(alias : String) : Recordings = {
    new Recordings(DSL.name(alias), this)
  }

  override def as(alias : Name) : Recordings = {
    new Recordings(alias, this)
  }

  override def rename(name : String) : Recordings = {
    new Recordings(DSL.name(name), null)
  }

  override def rename(name : Name) : Recordings = {
    new Recordings(name, null)
  }
}
