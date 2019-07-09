/*
 * This file is generated by jOOQ.
 */
package jooq.db


import jooq.db.tables.FlywaySchemaHistory
import jooq.db.tables.Textaudioindex
import jooq.db.tables.Transcript

import org.jooq.Index
import org.jooq.OrderField
import org.jooq.impl.Internal


object Indexes {

  // -------------------------------------------------------------------------
  // INDEX definitions
  // -------------------------------------------------------------------------

  val FLYWAY_SCHEMA_HISTORY_FLYWAY_SCHEMA_HISTORY_S_IDX = Indexes0.FLYWAY_SCHEMA_HISTORY_FLYWAY_SCHEMA_HISTORY_S_IDX
  val FLYWAY_SCHEMA_HISTORY_PRIMARY = Indexes0.FLYWAY_SCHEMA_HISTORY_PRIMARY
  val TEXTAUDIOINDEX_PRIMARY = Indexes0.TEXTAUDIOINDEX_PRIMARY
  val TRANSCRIPT_PRIMARY = Indexes0.TRANSCRIPT_PRIMARY

  // -------------------------------------------------------------------------
  // [#1459] distribute members to avoid static initialisers > 64kb
  // -------------------------------------------------------------------------

  private object Indexes0 {
    val FLYWAY_SCHEMA_HISTORY_FLYWAY_SCHEMA_HISTORY_S_IDX : Index = Internal.createIndex("flyway_schema_history_s_idx", FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, Array[OrderField [_] ](FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SUCCESS), false)
    val FLYWAY_SCHEMA_HISTORY_PRIMARY : Index = Internal.createIndex("PRIMARY", FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, Array[OrderField [_] ](FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK), true)
    val TEXTAUDIOINDEX_PRIMARY : Index = Internal.createIndex("PRIMARY", Textaudioindex.TEXTAUDIOINDEX, Array[OrderField [_] ](Textaudioindex.TEXTAUDIOINDEX.ID), true)
    val TRANSCRIPT_PRIMARY : Index = Internal.createIndex("PRIMARY", Transcript.TRANSCRIPT, Array[OrderField [_] ](Transcript.TRANSCRIPT.ID), true)
  }
}