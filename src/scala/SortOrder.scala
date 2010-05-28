/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
 
package buyat.api

/**
 * Simulates the sort order enumeration.
 * Case classes > Enumeration, since we don't need to worry about importing the enum
 * and the compiler will warn us when we miss out a case in a match.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
sealed abstract class SortOrder
case object Asc extends SortOrder { override def toString():String = "asc" }
case object Desc extends SortOrder { override def toString():String = "desc" }