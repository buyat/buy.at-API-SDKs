/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
 
package buyat.api

/**
 * Simulates the feed format enumeration.
 * Case classes > Enumeration, since we don't need to worry about importing the enum
 * and the compiler will warn us when we miss out a case in a match.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
sealed abstract class FeedFormat
case object FeedXML extends FeedFormat { override def toString():String = "XML" }
case object FeedXMLGZIP extends FeedFormat { override def toString():String = "XMLGZIP" }
case object FeedCSV extends FeedFormat { override def toString():String = "CSV" }
case object FeedCSVGZIP extends FeedFormat { override def toString():String = "CSVGZIP" }
case object FeedPIPE extends FeedFormat { override def toString():String = "PIPE" }
case object FeedPIPEGZIP extends FeedFormat { override def toString():String = "PIPEGZIP" }
case object FeedSCSV extends FeedFormat { override def toString():String = "SCSV" }
case object FeedSIMPLECSV extends FeedFormat { override def toString():String = "SIMPLECSV" }
case object FeedSCSVGZIP extends FeedFormat { override def toString():String = "SCSVGZIP" }
case object FeedHEAD extends FeedFormat { override def toString():String = "HEAD" }
case object FeedSAMPLE extends FeedFormat { override def toString():String = "SAMPLE" }