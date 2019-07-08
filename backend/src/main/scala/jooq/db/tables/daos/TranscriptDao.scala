/*
 * This file is generated by jOOQ.
 */
package jooq.db.tables.daos


import java.lang.Integer
import java.lang.String
import java.util.List

import jooq.db.tables.Transcript
import jooq.db.tables.records.TranscriptRecord

import org.jooq.Configuration
import org.jooq.impl.DAOImpl


class TranscriptDao(configuration : Configuration) extends DAOImpl[TranscriptRecord, jooq.db.tables.pojos.Transcript, Integer](Transcript.TRANSCRIPT, classOf[jooq.db.tables.pojos.Transcript], configuration) {

  def this() = {
    this(null)
  }

  override protected def getId(o : jooq.db.tables.pojos.Transcript) : Integer = {
    o.getId
  }

  def fetchById(values : Integer*) : List[jooq.db.tables.pojos.Transcript] = {
    fetch(Transcript.TRANSCRIPT.ID, values:_*)
  }

  def fetchOneById(value : Integer) : jooq.db.tables.pojos.Transcript = {
    fetchOne(Transcript.TRANSCRIPT.ID, value)
  }

  def fetchByText(values : String*) : List[jooq.db.tables.pojos.Transcript] = {
    fetch(Transcript.TRANSCRIPT.TEXT, values:_*)
  }
}
