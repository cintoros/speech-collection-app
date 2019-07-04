/*
 * This file is generated by jOOQ.
 */
package jooq.db.tables.pojos


import java.io.Serializable
import java.lang.Byte
import java.lang.Integer
import java.lang.StringBuilder


case class Textaudioindex(
    id : Integer
  , samplingrate : Integer
  , textstartpos : Integer
  , textendpos : Integer
  , audiostartpos : Integer
  , audioendpos : Integer
  , speakerkey : Integer
  , labeled : Byte
) extends Serializable {

  def this (value : Textaudioindex) = {
    this(
        value.id
      , value.samplingrate
      , value.textstartpos
      , value.textendpos
      , value.audiostartpos
      , value.audioendpos
      , value.speakerkey
      , value.labeled
    )
  }

  def getId : Integer = {
    this.id
  }

  def getSamplingrate : Integer = {
    this.samplingrate
  }

  def getTextstartpos : Integer = {
    this.textstartpos
  }

  def getTextendpos : Integer = {
    this.textendpos
  }

  def getAudiostartpos : Integer = {
    this.audiostartpos
  }

  def getAudioendpos : Integer = {
    this.audioendpos
  }

  def getSpeakerkey : Integer = {
    this.speakerkey
  }

  def getLabeled : Byte = {
    this.labeled
  }

  override def toString : String = {
    val sb = new StringBuilder("Textaudioindex (")

    sb.append(id)
    sb.append(", ").append(samplingrate)
    sb.append(", ").append(textstartpos)
    sb.append(", ").append(textendpos)
    sb.append(", ").append(audiostartpos)
    sb.append(", ").append(audioendpos)
    sb.append(", ").append(speakerkey)
    sb.append(", ").append(labeled)

    sb.append(")")
    return sb.toString
  }
}
