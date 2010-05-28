/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
 
package buyat.api

/**
 * Simulates the product field enumeration.
 * Case classes > Enumeration, since we don't need to worry about importing the enum
 * and the compiler will warn us when we miss out a case in a match.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
sealed abstract class ProductField
case object Relevance extends ProductField { override def toString():String = "relevance" }
case object ProductName extends ProductField { override def toString():String = "product_name" }
case object ProductSKU extends ProductField { override def toString():String = "product_sku" }
case object BrandName extends ProductField { override def toString():String = "brand_name" }
case object OnlinePrice extends ProductField { override def toString():String = "online_price" }