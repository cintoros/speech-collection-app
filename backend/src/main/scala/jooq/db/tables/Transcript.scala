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
import jooq.db.tables.records.TranscriptRecord

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


object Transcript {

  val TRANSCRIPT = new Transcript
}

class Transcript(
  alias : Name,
  child : Table[_ <: Record],
  path : ForeignKey[_ <: Record, TranscriptRecord],
  aliased : Table[TranscriptRecord],
  parameters : Array[ Field[_] ]
)
extends TableImpl[TranscriptRecord](
  alias,
  LabelingTool.LABELING_TOOL,
  child,
  path,
  aliased,
  parameters,
  DSL.comment("")
)
{

  override def getRecordType : Class[TranscriptRecord] = {
    classOf[TranscriptRecord]
  }

  val ID : TableField[TranscriptRecord, Integer] = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), "")

  val FILE : TableField[TranscriptRecord, Array[Byte]] = createField("file", org.jooq.impl.SQLDataType.BLOB, "")

  def this() = {
    this(DSL.name("transcript"), null, null, null, null)
  }

  def this(alias : String) = {
    this(DSL.name(alias), null, null, jooq.db.tables.Transcript.TRANSCRIPT, null)
  }

  def this(alias : Name) = {
    this(alias, null, null, jooq.db.tables.Transcript.TRANSCRIPT, null)
  }

  private def this(alias : Name, aliased : Table[TranscriptRecord]) = {
    this(alias, null, null, aliased, null)
  }

  def this(child : Table[_ <: Record], key : ForeignKey[_ <: Record, TranscriptRecord]) = {
    this(Internal.createPathAlias(child, key), child, key, jooq.db.tables.Transcript.TRANSCRIPT, null)
  }

  override def getSchema : Schema = LabelingTool.LABELING_TOOL

  override def getIndexes : List[ Index ] = {
    return Arrays.asList[ Index ](Indexes.TRANSCRIPT_PRIMARY)
  }

  override def getIdentity : Identity[TranscriptRecord, Integer] = {
    Keys.IDENTITY_TRANSCRIPT
  }

  override def getPrimaryKey : UniqueKey[TranscriptRecord] = {
    Keys.KEY_TRANSCRIPT_PRIMARY
  }

  override def getKeys : List[ UniqueKey[TranscriptRecord] ] = {
    return Arrays.asList[ UniqueKey[TranscriptRecord] ](Keys.KEY_TRANSCRIPT_PRIMARY)
  }

  override def as(alias : String) : Transcript = {
    new Transcript(DSL.name(alias), this)
  }

  override def as(alias : Name) : Transcript = {
    new Transcript(alias, this)
  }

  override def rename(name : String) : Transcript = {
    new Transcript(DSL.name(name), null)
  }

  override def rename(name : Name) : Transcript = {
    new Transcript(name, null)
  }
}
