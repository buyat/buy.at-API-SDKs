/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
 
package buyat.api

import buyat.api.entities._
import java.io.{IOException, UnsupportedEncodingException}
import java.net.{URL, URLEncoder}
import java.text.{SimpleDateFormat}
import scala.collection.mutable.{ListBuffer}
import scala.xml.{Elem, Node, XML}

/**
 * The buy.at Scala API client.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 * @param _apiKey User's API key.
 */
class BuyatAPI(_apiKey:String) {

  /**
   * Alternative constructor, allows changing of endpoint for testing purposes.
   * @param _apiKey User's API key.
   * @param _apiEndpoint API endpoint.
   */
  def this(_apiKey:String, _apiEndpoint:String) = {
    this(_apiKey)
    apiEndpoint = _apiEndpoint
  }

  /**
   * API endpoint.
   */
  var apiEndpoint:String = "@API_ENDPOINT@"

  /**
   * API key.
   */
  var apiKey:String = _apiKey

  /**
   * Echo a message back to the client (test function).
   * @param message The message to echo.
   * @return The message.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def echo(message:String):String = {
    try {
      var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.test.echo&message="+URLEncoder.encode(message, "UTF-8")
      (apiCall(serviceURL) \ "message").elements.next.asInstanceOf[Elem].text
    } catch {
      case apiEx:APIException => throw new APIException(apiEx.getMessage, apiEx.errorCode)
      case ex:Exception => throw new APIException(ex.getMessage)
    }
  }

  /**
   * Creates a tracking deep link for a URL.
   * @param url The original URL.
   * @return The tracking URL.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getDeepLink(url:String):String = {
    try {
      var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.affiliate.linkengine.create&url="+URLEncoder.encode(url, "UTF-8")
      (apiCall(serviceURL) \ "deep_link").elements.next.asInstanceOf[Elem].text
    } catch {
      case apiEx:APIException => throw new APIException(apiEx.getMessage, apiEx.errorCode)
      case ex:Exception => throw new APIException(ex.getMessage)
    }
  }

  /**
   * Gets all active programmes for the current user.
   * @return A list of programmes.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getProgrammes():List[Programme] = {
    var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.affiliate.programme.list"
    try {
      var programmes = new ListBuffer[Programme]
      for(programme <- (apiCall(serviceURL) \\ "programme")) {
        programmes += parseProgramme(programme.asInstanceOf[Elem])
      }
      programmes.toList
    } catch {
      case apiEx:APIException => throw new APIException(apiEx.getMessage, apiEx.errorCode)
      case ex:Exception => throw new APIException(ex.getMessage)
    }
  }

  /**
   * Gets all feeds belonging to active programmes for the current user.
   * @return A list of feeds.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getFeeds():List[Feed] = {
    var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.affiliate.feed.list"
    try {
      var feeds = new ListBuffer[Feed]
      for(feed <- (apiCall(serviceURL) \\ "feed")) {
        feeds += parseFeed(feed.asInstanceOf[Elem])
      }
      feeds.toList
    } catch {
      case apiEx:APIException => throw new APIException(apiEx.getMessage, apiEx.errorCode)
      case ex:Exception => throw new APIException(ex.getMessage)
    }
  }

  /**
   * Retrieves information about a single programme.
   * @param ID of the programme.
   * @return The programme.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getProgrammeInfo(programmeId:Int):Programme = {
    var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.affiliate.programme.info&programme_id="+programmeId
    try {
      parseProgramme((apiCall(serviceURL) \ "programme").elements.next.asInstanceOf[Elem])
    } catch {
      case apiEx:APIException => throw new APIException(apiEx.getMessage, apiEx.errorCode)
      case ex:Exception => throw new APIException(ex.getMessage)
    }
  }

  /**
   * Gets information about a single feed.
   * @param feedId ID of the feed.
   * @return The feed.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getFeedInfo(feedId:Int):Feed = {
    var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.affiliate.feed.info&feed_id="+feedId
    try {
      parseFeed((apiCall(serviceURL) \ "feed").elements.next.asInstanceOf[Elem])
    } catch {
      case apiEx:APIException => throw new APIException(apiEx.getMessage, apiEx.errorCode)
      case ex:Exception => throw new APIException(ex.getMessage)
    }
  }

  /**
   * Get a download URL for a feed.
   * @param criteria Feed criteria.
   * @return The feed URL.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getFeedURL(criteria:FeedCriteria):String = {
    getFeedURL(
      criteria.feedId,
      criteria.feedFormat,
      criteria.start,
      criteria.perPage,
      criteria.level1CategoryId,
      criteria.level2CategoryId,
      criteria.programmeCategoryId,
      criteria.programmeCategoryLevels,
      criteria.lid,
      criteria.useHTTPS,
      criteria.reverseMapXML,
      criteria.bestseller
    )
  }

  /**
   * Get a download URL for a feed.
   * @param feedId ID of the feed.
   * @param feedFormat Format of the feed. See FeedFormat for possible values.
   * @return The feed URL.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getFeedURL(feedId:Int, feedFormat:FeedFormat):String = getFeedURL(feedId, feedFormat, -1, -1, -1, -1, -1, Nil, null, true, true, false)

  /**
   * Get a download URL for a feed.
   * @param feedId ID of the feed.
   * @param feedFormat Format of the feed. See FeedFormat for possible values.
   * @param start Record # to start at.
   * @param perPage Number of products to retrieve per 'page'.
   * @param level1CategoryId Level 1 category ID.
   * @param level2CategoryId Level 2 category ID.
   * @param programmeCategoryId Programme category ID.
   * @param programmeCategoryLevels Programme category levels.
   * @param lid Link identifier to be appended to tracking URLs within the feed.
   * @param useHTTPS Whether to use HTTPS for the feed URL.
   * @param reverseMapXML Whether to reverse map XML node names.
   * @param bestseller Whether to restict the feed to 'best seller' items only.
   * @return The feed URL.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getFeedURL(feedId:Int, feedFormat:FeedFormat, start:Int, perPage:Int, level1CategoryId:Int, level2CategoryId:Int,
                 programmeCategoryId:Int, programmeCategoryLevels:List[String], lid:String, useHTTPS:Boolean,
                 reverseMapXML:Boolean, bestseller:Boolean):String = {
    try {
      var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.affiliate.feed.geturl"+
                       "&feed_id="+feedId+"&format="+feedFormat
      if(start >= 0) {
        serviceURL += "&start="+start
      }
      if(perPage != -1) {
        serviceURL += "&perpage="+perPage;
      }
      if(level1CategoryId != -1) {
        serviceURL += "&level1_category_id="+level1CategoryId;
      }
      if(level2CategoryId != -1) {
        serviceURL += "&level2_category_id="+level2CategoryId;
      }
      if(programmeCategoryId != -1) {
        serviceURL += "&programme_category_id="+programmeCategoryId;
      }
      for(i <- 0 until programmeCategoryLevels.length; p <- programmeCategoryLevels) {
        serviceURL += "&level"+(i+1)+"="+p;
      }
      if(lid != null && lid.length > 0) {
        serviceURL += "&lid="+lid;
      }
      if(useHTTPS) {
        serviceURL += "&use_https=y";
      }
      if(!reverseMapXML) {
        serviceURL += "&reverse_map_xml=n";
      }
      if (bestseller) {
        serviceURL += "&bestseller=yes";
      }
      (apiCall(serviceURL) \ "url").text
    } catch {
      case apiEx:APIException => throw new APIException(apiEx.getMessage, apiEx.errorCode)
      case ex:Exception => throw new APIException(ex.getMessage)
    }
  }

  /**
   * Gets information about a single product.
   * @param productId ID of the product.
   * @return The product.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getProductInfo(productId:Int):Product = {
    var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.affiliate.product.info&product_id="+productId
    try {
      parseProduct((apiCall(serviceURL) \ "product").elements.next.asInstanceOf[Elem])
    } catch {
      case apiEx:APIException => throw new APIException(apiEx.getMessage, apiEx.errorCode)
      case ex:Exception => throw new APIException(ex.getMessage)
    }
  }

  def searchProducts(criteria:ProductSearchCriteria):ProductResultSet = {
    searchProducts(
      criteria.query,
      criteria.page,
      criteria.perPage,
      criteria.programmeIds,
      criteria.excludedProgrammeIds,
      criteria.excludedProgrammeCategoryIds,
      criteria.feedIds,
      criteria.categoryId,
      criteria.subcategoryId,
      criteria.includeAdult,
      criteria.lid,
      criteria.sort,
      criteria.sortOrder
    )
  }

  /**
   * Search for products.
   * @param query Query string.
   * @param page Page number of results to retrieve.
   * @param perPage Number of results per page.
   * @param programmeIds IDs of programmes to include.
   * @param excludedProgrammeIds IDs of programmes to exclude.
   * @param excludedProgrammeCategoryIds: IDs of programme categories to exclude.
   * @param feedIds IDs of feeds to include.
   * @param categoryId ID of category to restrict the search to.
   * @param subcategoryId ID of subcategory to restrict the search to.
   * @param includeAdult Whether to include age restricted products in the results.
   * @param lid Link identifier, to be appended to tracking URLs.
   * @param sort Field to sort the results by.
   * @param sortOrder Direction to order the sort.
   * @return The product result set.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def searchProducts(query:String, page:Int, perPage:Int, programmeIds:List[Int], excludedProgrammeIds:List[Int],
                     excludedProgrammeCategoryIds:List[Int], feedIds:List[Int], categoryId:Int, subcategoryId:Int,
                     includeAdult:Boolean, lid:String, sort:ProductField, sortOrder:SortOrder):ProductResultSet = {
    try {
      var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.affiliate.product.search"
      if(query != null && query.length > 0) {
        serviceURL += "&query=" + URLEncoder.encode(query, "UTF-8")
      }
      serviceURL += "&page=" + page + "&perpage=" + perPage
      if(programmeIds.length > 0) {
        serviceURL += "&programme_ids=" + programmeIds.mkString(",")
      }
      if(excludedProgrammeIds.length > 0) {
        serviceURL += "&excluded_programme_ids=" + excludedProgrammeIds.mkString(",")
      }
      if(excludedProgrammeCategoryIds.length > 0) {
        serviceURL += "&excluded_programme_category_ids=" + excludedProgrammeCategoryIds.mkString(",")
      }
      if(feedIds.length > 0) {
        serviceURL += "&feed_ids=" + feedIds.mkString(",")
      }
      if(categoryId > 0) {
        serviceURL += "&level1_category_id=" + categoryId
      }
      if(subcategoryId > 0) {
        serviceURL += "&level2_category_id=" + subcategoryId
      }
      if(includeAdult) {
        serviceURL += "&include_adult=y"
      }
      if(lid != null && lid.length > 0) {
        serviceURL += "&lid=" + URLEncoder.encode(lid, "UTF-8")
      }
      if(sort != null) {
        serviceURL += "&sort=" + sort;
      }
      if(sortOrder != null) {
        serviceURL += "&sortorder=" + sortOrder;
      }
      parseProductResultSet(apiCall(serviceURL))
    } catch {
      case apiEx:APIException => throw new APIException(apiEx.getMessage, apiEx.errorCode)
      case ex:Exception => throw new APIException(ex.getMessage)
    }
  }

  /**
   * Search for products.
   * @param query Query string.
   * @param page Page number of results to retrieve.
   * @param perPage Number of results per page.
   * @param lid Link identifier, to be appended to tracking URLs.
   * @param sort Field to sort the results by.
   * @param sortOrder Direction to order the sort.
   * @return The product result set.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def searchProducts(query:String, page:Int, perPage:Int, lid:String, sort:ProductField, sortOrder:SortOrder):ProductResultSet = {
    searchProducts(query, page, perPage, Nil, Nil, Nil, Nil, -1, -1, false, lid, sort, sortOrder)
  }

  /**
   * Search for products.
   * @param query Query string.
   * @param page Page number of results to retrieve.
   * @param perPage Number of results per page.
   * @return The product result set.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def searchProducts(query:String, page:Int, perPage:Int):ProductResultSet = {
    searchProducts(query, page, perPage, Nil, Nil, Nil, Nil, -1, -1, false, null, Relevance, Desc)
  }

  /**
   * Search for products.
   * @param query Query string.
   * @return The product result set.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def searchProducts(query:String):ProductResultSet = {
    searchProducts(query, 1, 10, Nil, Nil, Nil, Nil, -1, -1, false, null, Relevance, Desc)
  }

  /**
   * Gets all level 1 categories.
   * @return List[Category] A list of level 1 categories.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getLevel1Categories():List[Category] = {
    var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.affiliate.category.listlevel1"
    try {
      buildCategories(serviceURL)
    } catch {
      case apiEx:APIException => throw new APIException(apiEx.getMessage, apiEx.errorCode)
      case ex:Exception => throw new APIException(ex.getMessage)
    }
  }

  /**
   * Gets all level 2 categories for the given level 1 category.
   * @param level1id The parent category ID.
   * @return List[Category] A list of level 2 categories.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getLevel2Categories(level1CategoryId:Int):List[Category] = {
    var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.affiliate.category.listlevel2&level1_category_id="+level1CategoryId
    try {
      buildCategories(serviceURL)
    } catch {
      case apiEx:APIException => throw new APIException(apiEx.getMessage, apiEx.errorCode)
      case ex:Exception => throw new APIException(ex.getMessage)
    }
  }

  /**
   * Construct a list of categories.
   * @param serviceURL GET URL for the API.
   * @return List[Category] A list of categories.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  private def buildCategories(serviceURL:String):List[Category] = {
    var categories = new ListBuffer[Category]
    for(category <- (apiCall(serviceURL) \\ "category")) {
      categories += parseCategory(category.asInstanceOf[Elem], -1, null)
    }
    categories.toList
  }

  /**
   * Gets all categories, pre-populated with subcategories.
   * @return a list of level 1 categories.
   * @throws APIException On any error encountered during the API call or the parsing of the result; check the expection's errorCode for more information.
   */
  def getCategoryTree():List[Category] =
    try {
      var serviceURL = apiEndpoint+"?api_key="+apiKey+"&requestmethod=rest&responsemethod=xml"+"&action=buyat.affiliate.category.tree"
      var categories = new ListBuffer[Category]
      for(category <- (((apiCall(serviceURL)) \ "categories") \ "category")) {
        categories += parseCategory(category.asInstanceOf[Elem], -1, null)
      }
      categories.toList
    } catch {
      case ex:Exception => throw new APIException(ex.getMessage())
    }

  /**
   * Construct a category object from an XML representation of a category.
   * @param xml XML fragment.
   * @param _parentCategoryId ID of parent category.
   * @param _parentCategoryName Name of parent category.
   * @return category
   */
  private def parseCategory(xml:Elem, _parentCategoryId:Int, _parentCategoryName:String):Category = {
    var categoryId:Int = (xml \ "category_id").text.toInt
    var categoryName:String = (xml \ "category_name").text
    var level:Int = (xml \ "level").text.toInt
    var subcategories = new ListBuffer[Category]
    var parentCategoryId = _parentCategoryId
    var parentCategoryName = _parentCategoryName
    if(level == 1) {
      for(subcategory <- ((xml \ "subcategories") \ "category")) {
        subcategories += parseCategory(subcategory.asInstanceOf[Elem], categoryId, categoryName)
      }
    } else if (level == 2) {
      if((xml \ "parent_category_id").length > 0 && (xml \ "parent_category_name").length > 0) {
        parentCategoryId = (xml \ "parent_category_id").text.toInt
        parentCategoryName = (xml \ "parent_category_name").text
      }
    }
    new Category(
      categoryId,
      categoryName,
      level,
      parentCategoryId,
      parentCategoryName,
      subcategories.toList
    )
  }

  /**
   * Construct a programme object from an XML representation of a programme.
   * @param xml XML fragment.
   * @return Programme
   */
  private def parseProgramme(xml:Elem):Programme =
    new Programme(
      (xml \ "programme_id").text.toInt,
      (xml \ "programme_name").text,
      (xml\ "programme_url").text,
      ((xml \ "has_feed").text == "Y")
    )

  /**
   * Construct a feed object from an XML representation of a feed.
   * @param xml XML fragment.
   * @return Feed
   */
  private def parseFeed(xml:Elem):Feed =
    new Feed(
      (xml \ "feed_id").text.toInt,
      (xml \ "feed_name").text,
      (xml \ "programme_id").text.toInt,
      (xml \ "number_of_products").text.toInt,
      new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse((xml \ "last_updated").text)
    )

  /**
   * Construct a product object from an XML representation of a product.
   * @param xml XML fragment.
   * @return Product
   */
  private def parseProduct(xml:Elem):Product =
    new Product(
      (xml \ "product_id").text.toInt,
      (xml \ "product_sku").text,
      (xml \ "product_url").text,
      (xml \ "product_name").text,
      (xml \ "brand_name").text,
      (xml \ "description").text,
      (xml \ "online_price").text.toFloat,
      (xml \ "currency").text,
      (xml \ "currency_symbol").text,
      (xml \ "image_url").text,
      (xml \ "programme_id").text.toInt,
      (xml \ "programme_name").text,
      (xml \ "programme_url").text,
      (xml \ "level1_category_id") match {
        case x:Any if(x != null && x.text.length > 0) => x.text.toInt
        case _ => -1
      },
      (xml \ "level1_category_name") match {
        case x:Any if(x != null && x.text.length > 0) => x.text
        case _ => ""
      },
      (xml \ "level2_category_id") match {
        case x:Any if(x != null && x.text.length > 0) => x.text.toInt
        case _ => -1
      },
      (xml \ "level2_category_name") match {
        case x:Any if(x != null && x.text.length > 0) => x.text
        case _ => ""
      },      
      (xml \ "feed_id").text.toInt,
      (xml \ "feed_name").text
    )

  private def parseProductResultSet(xml:Elem):ProductResultSet = {
    var products = new ListBuffer[Product]
    for(p <- (xml \\ "product")) {
      products += parseProduct(p.asInstanceOf[Elem])
    }
    new ProductResultSet(
      (xml \ "total_results").text.toInt,
      (xml \ "current_results").text.toInt,
      (xml \ "start").text.toInt,
      (xml \ "limit").text.toInt,
      (xml \ "query").text,
      products.toList
    )
  }

  /**
   * Call the API HTTP GET style and receive an XML document in response.
   * @param serviceURL
   * @return The API response as an XML document
   * @throws APIException On any error encountered during the API call; check the expection's errorCode for more information.
   */
  private def apiCall(serviceURL:String):Elem = handleErrors(XML.load(serviceURL))

  /**
   * Check an XML API response for errors.
   * @param doc The API response as an XML document.
   * @return The input document; allows chaining.
   * @throws APIException If the XML was formed like an error message from the buy.at API.
   */
  private def handleErrors(doc:Elem):Elem = {
    doc match {
      case doc if((doc \\ "error_code").length > 0) => {
        throw new APIException(
          (doc \\ "error_message").elements.next.asInstanceOf[Elem].text,
          (doc \\ "error_code").elements.next.asInstanceOf[Elem].text.toInt
        )
      }
      case _ => doc
    }
  }
}