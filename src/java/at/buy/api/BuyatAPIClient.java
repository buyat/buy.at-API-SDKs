/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

package at.buy.api;

import at.buy.entities.*;
import at.buy.entities.Category.Level;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.w3c.dom.*;

import javax.xml.parsers.*;

import org.xml.sax.*;

/**
 * Buy.at API client.
 *
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
public class BuyatAPIClient {

  /**
   * Product sort field enum
   * 
   * @author buy.at
   */
  public enum ProductField {
    /** Product name */ PRODUCT_NAME,
    /** Product SKU */ PRODUCT_SKU,
    /** Brand name */ BRAND_NAME,
    /** Online price */ ONLINE_PRICE,
    /** Relevance */ RELEVANCE
  };
  
  /**
   * Sort order enum
   * 
   * @author buy.at
   */
  public enum SortOrder { 
    /** Ascending */ ASC, 
    /** Descending */ DESC 
  };
  
  /**
   * Feed format enum
   * 
   * @author buy.at
   */
  public enum FeedFormat { 
    /** Gzipped XML */ XMLGZIP,
    /** XML */ XML,
    /** Gzipped CSV */ CSVGZIP,
    /** Comma-separated */ CSV,
    /** Gzipped pipe-separated */ PIPEGZIP,
    /** Pipe-separated */ PIPE,
    /** Gzipped SCSV */ SCSVGZIP,
    /** Simple CSV (no quotes round fields) */ SCSV,
    /** Simple CSV (no quotes round fields) */ SIMPLECSV,
    /** Headers only */ HEAD,
    /** Sample feed file */ SAMPLE,
  };
    
  /**
   * Client API key.
   */
  private String apiKey;

  /**
   * API endpoint.
   */
  private String apiEndpoint = "@API_ENDPOINT@";

  /**
   * Constructor.
   *
   * @param apiKey Client API key.
   */
  public BuyatAPIClient(String apiKey) {
    this.apiKey = apiKey;
  }

  /**
   * Call the API HTTP GET style and receive an XML document in response.
   *
   * @param serviceURL
   * @return The API response as an XML document
   * @throws APIException
   */
  protected Document downloadURLAsXML(String serviceURL) throws APIException {
    Document doc = null;
    try { 
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
      docBuilderFactory.setIgnoringElementContentWhitespace(true);
      docBuilderFactory.setValidating(false);
      DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
      doc = docBuilder.parse(serviceURL);
      return doc;
    } catch(SAXException saxEx) {
      throw new APIException("Error parsing XML: "+saxEx.getMessage());
    } catch(IOException ioEx) {
      throw new APIException("Unable to call the API");
    } catch(ParserConfigurationException pcEx) {
      throw new APIException("Unable to instantiate XML parser");
    }
  }

  /**
   * Parse the API response (as XML), checking for errors.
   *
   * @param doc The API response as an XML document
   * @throws APIException
   */
  protected void handleErrors(Document doc) throws APIException {
    doc.getDocumentElement().normalize();
    NodeList errorElements = doc.getElementsByTagName("error");
    if(errorElements.getLength() > 0) {
      NodeList errorMessageElements = doc.getElementsByTagName("error_message");
      if(errorMessageElements.getLength() > 0) {
        Node errorMessage = errorMessageElements.item(0);
        throw new APIException(errorMessage.getTextContent());
      } else {
        throw new APIException("Unknown API error");
      }
    }
  }
  
  /**
   * Echo a message back to the client.
   *
   * @param message The message to echo.
   * @return the message
   */
  public String echo(String message) throws APIException {
    try {
      message = URLEncoder.encode(message, "UTF-8");
    } catch(UnsupportedEncodingException ex) {
      throw new APIException(ex.getMessage());
    }
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+"&requestmethod=rest&responsemethod=xml"+
      "&action=buyat.test.echo&message="+message;
    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);

    Node messageEl = doc.getElementsByTagName("message").item(0);
    if (messageEl != null) {
        return messageEl.getTextContent();
    }
    return null;
  }
  
  private static String implode(int[] intArray, String separator) {
    StringBuilder sb = new StringBuilder();
    
    for (int i=0; i<intArray.length-1; i++) {
      sb.append(intArray[i]);
      sb.append(separator);
    }
    
    sb.append(intArray[intArray.length-1]);
    return sb.toString();
  }
  
  private Product parseProduct(Element productEl) throws APIException {
    String productIDString = BuyatAPIClient.getChildNodeText(productEl, "product_id");
    int productID;
    try {
      productID = Integer.parseInt(productIDString);
    }
    catch (NumberFormatException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String productSKU = BuyatAPIClient.getChildNodeText(productEl, "product_sku");
    String productURL = BuyatAPIClient.getChildNodeText(productEl, "product_url");
    String productName = BuyatAPIClient.getChildNodeText(productEl, "product_name");
    String brandName = BuyatAPIClient.getChildNodeText(productEl, "brand_name");
    String description = BuyatAPIClient.getChildNodeText(productEl, "description");
    
    String onlinePriceString = BuyatAPIClient.getChildNodeText(productEl, "online_price");
    NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
    float onlinePrice;
    try {
      onlinePrice = nf.parse(onlinePriceString).floatValue();
    }
    catch (ParseException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String currency = BuyatAPIClient.getChildNodeText(productEl, "currency");
    String currencySymbol = BuyatAPIClient.getChildNodeText(productEl, "currency_symbol");
    String imageURL = BuyatAPIClient.getChildNodeText(productEl, "image_url");
    
    String programmeIDString = BuyatAPIClient.getChildNodeText(productEl, "programme_id");
    int programmeID;
    try {
      programmeID = Integer.parseInt(programmeIDString);
    }
    catch (NumberFormatException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String programmeName = BuyatAPIClient.getChildNodeText(productEl, "programme_name");
    String programmeURL = BuyatAPIClient.getChildNodeText(productEl, "programme_url");
    
    String level1CategoryIDString = BuyatAPIClient.getChildNodeText(productEl, "level1_category_id");
    int level1CategoryID;
    try {
      level1CategoryID = Integer.parseInt(level1CategoryIDString);
    }
    catch (NumberFormatException e) {
      level1CategoryID = -1;
    }
    
    String level1CategoryName = BuyatAPIClient.getChildNodeText(productEl, "level1_category_name");
    
    String level2CategoryIDString = BuyatAPIClient.getChildNodeText(productEl, "level2_category_id");
    int level2CategoryID;
    try {
      level2CategoryID = Integer.parseInt(level2CategoryIDString);
    }
    catch (NumberFormatException e) {
      level2CategoryID = -1;
    }
    
    String level2CategoryName = BuyatAPIClient.getChildNodeText(productEl, "level2_category_name");
    
    String feedIDString = BuyatAPIClient.getChildNodeText(productEl, "feed_id");
    int feedID;
    try {
      feedID = Integer.parseInt(feedIDString);
    }
    catch (NumberFormatException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String feedName = BuyatAPIClient.getChildNodeText(productEl, "feed_name");
    
    return new Product(productID, productSKU, productURL, productName, brandName,
        description, onlinePrice, currency, currencySymbol, imageURL,
        programmeID, programmeName, programmeURL, level1CategoryID,
        level1CategoryName, level2CategoryID, level2CategoryName, feedID,
        feedName, this);
  }
  
  
  /**
   * Searches products
   * @param query
   * @param page
   * @param perPage
   * @param programmeIds
   * @param excludedProgrammeIds
   * @param excludedProgrammeCategoryIds
   * @param feedIds
   * @param categoryId
   * @param subCategoryId
   * @param includeAdult
   * @param lid
   * @param sort
   * @param sortOrder
   * @return a result set containing products matching the search query
   * @throws APIException
   */
  public ProductResultSet searchProducts(String query, int page, int perPage,
          int[] programmeIds, int[] excludedProgrammeIds, int[] excludedProgrammeCategoryIds,
          int[] feedIds, int categoryId, int subCategoryId, boolean includeAdult,
          String lid, ProductField sort, SortOrder sortOrder) throws APIException {
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+
      "&requestmethod=rest&responsemethod=xml&action=buyat.affiliate.product.search";
    if (query != null && !query.equals("")) {
      serviceURL += "&query=" + query;
    }
    if (page != -1) {
      serviceURL += "&page=" + page;
    }
    if (perPage != -1) {
      serviceURL += "&perpage=" + perPage;
    }
    if (programmeIds != null && programmeIds.length > 0) {
      serviceURL += "&programme_ids=" + BuyatAPIClient.implode(programmeIds, ",");
    }
    if (excludedProgrammeIds != null && excludedProgrammeIds.length > 0) {
      serviceURL += "&excluded_programme_ids=" + BuyatAPIClient.implode(excludedProgrammeIds, ",");
    }
    if (excludedProgrammeCategoryIds != null && excludedProgrammeCategoryIds.length > 0) {
      serviceURL += "&excluded_programme_category_ids=" + BuyatAPIClient.implode(excludedProgrammeCategoryIds, ",");
    }
    if (feedIds != null && feedIds.length > 0) {
      serviceURL += "&feed_ids=" + BuyatAPIClient.implode(feedIds, ",");
    }
    if (categoryId != -1) {
      serviceURL += "&level1_category_id=" + categoryId;
    }
    if (subCategoryId != -1) {
      serviceURL += "&level2_category_id=" + subCategoryId;
    }
    if (includeAdult) {
      serviceURL += "&include_adult=y";
    }
    if (lid != null && !lid.equals("")) {
      serviceURL += "&lid=" + lid;
    }
    if (sort != null) {
      serviceURL += "&sort=" + sort.toString().toLowerCase();
    }
    if (sortOrder != null) {
      serviceURL += "&sortorder=" + sortOrder.toString().toLowerCase();
    }
          
    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);
    
    Element resultEl = doc.getDocumentElement();
    
    String totalResultString = BuyatAPIClient.getChildNodeText(resultEl, "total_results");
    int totalResults;
    try {
      totalResults = Integer.parseInt(totalResultString);
    }
    catch (NumberFormatException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String currentResultString = BuyatAPIClient.getChildNodeText(resultEl, "current_results");
    int currentResults;
    try {
      currentResults = Integer.parseInt(currentResultString);
    }
    catch (NumberFormatException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String startString = BuyatAPIClient.getChildNodeText(resultEl, "start");
    int start;
    try {
      start = Integer.parseInt(startString);
    }
    catch (NumberFormatException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String limitString = BuyatAPIClient.getChildNodeText(resultEl, "limit");
    int limit;
    try {
      limit = Integer.parseInt(limitString);
    }
    catch (NumberFormatException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String queryString = BuyatAPIClient.getChildNodeText(resultEl, "query");
    
    List<Product> products = new ArrayList<Product>();
    NodeList productEls = doc.getElementsByTagName("product");
    
    for(int i=0; i<productEls.getLength(); i++) {
      Element productEl = (Element) productEls.item(i);
      products.add(this.parseProduct(productEl));
    }
    
    return new ProductResultSet(totalResults, currentResults, start, limit, queryString, products);
  }
  
  /**
   * Searches products
   * @param query
   * @param page
   * @param perPage
   * @param lid
   * @param sort
   * @param sortOrder
   * @return a result set containing products matching the search query
   * @throws APIException
   */
  public ProductResultSet searchProducts(String query, int page, int perPage,
          String lid, ProductField sort, SortOrder sortOrder) throws APIException {
    return searchProducts(query, page, perPage, null, null, null, null, -1,
            -1, false, lid, sort, sortOrder);
  }
  
  /**
   * Searches products
   * @param query
   * @return a result set containing products matching the search query
   * @throws APIException
   */
  public ProductResultSet searchProducts(String query) throws APIException {
      return searchProducts(query, 1, 10, null, null, null, null, -1,
              -1, false, null, null, null); 
  }
  
  private List<Category> parseCategories(Document doc) throws APIException {
    List<Category> categoryList = new ArrayList<Category>();
    NodeList categoryEls = doc.getElementsByTagName("category");
    
    for(int i=0; i<categoryEls.getLength(); i++) {
      if (categoryEls.item(i) instanceof Element) {
        Element catEl = (Element) categoryEls.item(i);
        String categoryIDString = BuyatAPIClient.getChildNodeText(catEl, "category_id");
        int categoryID;
        try {
          categoryID = Integer.parseInt(categoryIDString);
        }
        catch (NumberFormatException e) {
          throw new APIException("Invalid XML returned from API: " + e.getMessage());
        }
        
        String levelString = BuyatAPIClient.getChildNodeText(catEl, "level");
        Level level = levelString.equals("1") ? Level.LEVEL1 : Level.LEVEL2;
        
        String categoryName = BuyatAPIClient.getChildNodeText(catEl, "category_name");
        
        int parentCategoryID;
        String parentCategoryName;
        if (level.equals(Level.LEVEL2)) {
          String parentCategoryIDString = BuyatAPIClient.getChildNodeText(catEl, "parent_category_id");
          if ("".equals(parentCategoryIDString)) {
            parentCategoryID = -1;
          }
          else {
            try {
              parentCategoryID = Integer.parseInt(parentCategoryIDString);
            }
            catch (NumberFormatException e) {
              throw new APIException("Invalid XML returned from API: " + e.getMessage());
            }
          }
        
          parentCategoryName = BuyatAPIClient.getChildNodeText(catEl, "parent_category_name");
        }
        else
        {
          parentCategoryID = -1;
          parentCategoryName = null;
        }
        
        categoryList.add(new Category(categoryID,
            level,
            categoryName,
            parentCategoryID,
            parentCategoryName,
            this
        ));
      }
    }
      
    return categoryList;
  }
  
  /**
   * Gets all level 1 categories
   * (Difference between this and getCategoryTree is that
   * this does not pre-populate subcategories; it is faster
   * if you don't need the sub-categories)
   * @return a list of level 1 categories
   * @throws APIException
   */
  public List<Category> getLevel1Categories() throws APIException {
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+"&requestmethod=rest&responsemethod=xml"+
      "&action=buyat.affiliate.category.listlevel1";
    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);
    return this.parseCategories(doc);
  }
  
  /**
   * Gets all level 2 categories for the given level 1 category
   * @param level1id
   * @return a list of level 2 categories
   * @throws APIException
   */
  public List<Category> getLevel2Categories(int level1id) throws APIException {
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+"&requestmethod=rest&responsemethod=xml"+
    "&action=buyat.affiliate.category.listlevel2&level1_category_id="+level1id;
    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);
    return this.parseCategories(doc);
  }
  
  /**
   * Gets all level 2 categories for the given level 1 category
   * @param level1Category
   * @return a list of level 2 categories
   * @throws APIException
   */
  public List<Category> getLevel2Categories(Category level1Category) throws APIException {
      return getLevel2Categories(level1Category.getCategoryID());
  }
  
  /**
   * recursively parses categorytree elements to return a list of Categories
   * @param nodes
   * @param parentCategoryID
   * @param parentCategoryName
   * @return
   * @throws APIException
   */
  private List<Category> parseCategoryTree(NodeList nodes, int parentCategoryID, String parentCategoryName) throws APIException {
    List<Category> categoryList = new ArrayList<Category>();
    for(int i=0; i<nodes.getLength(); i++) {
      if (nodes.item(i) instanceof Element) {
        Element catEl = (Element) nodes.item(i);
        String categoryIDString = BuyatAPIClient.getChildNodeText(catEl, "category_id");
        int categoryID;
        try {
          categoryID = Integer.parseInt(categoryIDString);
        }
        catch (NumberFormatException e) {
          throw new APIException("Invalid XML returned from API: " + e.getMessage());
        }
        
        String levelString = BuyatAPIClient.getChildNodeText(catEl, "level");
        Level level = levelString.equals("1") ? Level.LEVEL1 : Level.LEVEL2;
        
        String categoryName = BuyatAPIClient.getChildNodeText(catEl, "category_name");
        
        List<Category> subcategories = null;
        Element subcatEl = BuyatAPIClient.getChildElement(catEl, "subcategories");
        
        if (subcatEl != null) {
          subcategories = this.parseCategoryTree(subcatEl.getChildNodes(),
              categoryID, categoryName);
        }
        
        categoryList.add(new Category(categoryID,
            level,
            categoryName,
            parentCategoryID,
            parentCategoryName,
            subcategories,
            this
        ));
      }
    }
      
    return categoryList;
  }
  
  /**
   * Gets all categories, pre-populated with subcategories
   * @return a list of level 1 categories
   * @throws APIException
   */
  public List<Category> getCategoryTree() throws APIException {
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+"&requestmethod=rest&responsemethod=xml"+
    "&action=buyat.affiliate.category.tree";
    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);
    NodeList categoryEls = BuyatAPIClient.getChildElement(doc.getDocumentElement(), "categories").getChildNodes();
    return this.parseCategoryTree(categoryEls, -1, null);
  }
  
  
  private Programme parseProgramme(Element progEl) throws APIException {
    String programmeIDString = BuyatAPIClient.getChildNodeText(progEl, "programme_id");
    int programmeID;
    try {
      programmeID = Integer.parseInt(programmeIDString);
    }
    catch (NumberFormatException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String programmeName = BuyatAPIClient.getChildNodeText(progEl, "programme_name");
    String programmeURL = BuyatAPIClient.getChildNodeText(progEl, "programme_url");
    
    String hasFeedString = BuyatAPIClient.getChildNodeText(progEl, "has_feed");
    boolean hasFeed = hasFeedString.equalsIgnoreCase("Y");
    
    return new Programme(programmeID, programmeName, programmeURL, hasFeed);
  }

  /**
   * Gets all programmes
   * @return a list of programmes
   * @throws APIException
   */
  public List<Programme> getProgrammes() throws APIException {
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+"&requestmethod=rest&responsemethod=xml"+
    "&action=buyat.affiliate.programme.list";
    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);
    
    List<Programme> programmeList = new ArrayList<Programme>();
    NodeList programmeEls = doc.getElementsByTagName("programme");
    
    for(int i=0; i<programmeEls.getLength(); i++) {
      if (programmeEls.item(i) instanceof Element) {
        Element progEl = (Element) programmeEls.item(i);
        programmeList.add(this.parseProgramme(progEl));
      }
    }
    return programmeList;
  }
  
  private Feed parseFeed(Element feedEl) throws APIException {
    String feedIDString = BuyatAPIClient.getChildNodeText(feedEl, "feed_id");
    int feedID;
    try {
      feedID = Integer.parseInt(feedIDString);
    }
    catch (NumberFormatException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String feedName = BuyatAPIClient.getChildNodeText(feedEl, "feed_name");
    
    String programmeIDString = BuyatAPIClient.getChildNodeText(feedEl, "programme_id");
    int programmeID;
    try {
      programmeID = Integer.parseInt(programmeIDString);
    }
    catch (NumberFormatException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String programmeName = BuyatAPIClient.getChildNodeText(feedEl, "programme_name");
    String programmeURL = BuyatAPIClient.getChildNodeText(feedEl, "programme_url");
    
    String numberOfProductsString = BuyatAPIClient.getChildNodeText(feedEl, "number_of_products");
    int numberOfProducts;
    try {
      numberOfProducts = Integer.parseInt(numberOfProductsString);
    }
    catch (NumberFormatException e) {
      throw new APIException("Invalid XML returned from API: " + e.getMessage());
    }
    
    String lastUpdatedString = BuyatAPIClient.getChildNodeText(feedEl, "last_updated");
    Date lastUpdated;
    if (!lastUpdatedString.equals("")) {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try {
        lastUpdated = df.parse(lastUpdatedString);
      }
      catch (ParseException e) {
        throw new APIException("Invalid XML returned from API: " + e.getMessage());
      }
    }
    else {
      lastUpdated = null;
    }
      
    
    return new Feed(feedID, feedName, programmeID, programmeName, programmeURL, numberOfProducts, lastUpdated, this);
  }
  
  /**
   * Gets all feeds
   * @return a list of feeds
   * @throws APIException
   */
  public List<Feed> getFeeds() throws APIException {
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+"&requestmethod=rest&responsemethod=xml"+
    "&action=buyat.affiliate.feed.list";
    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);
    
    List<Feed> feedList = new ArrayList<Feed>();
    NodeList feedEls = doc.getElementsByTagName("feed");
    
    for(int i=0; i<feedEls.getLength(); i++) {
      if (feedEls.item(i) instanceof Element) {
        Element feedEl = (Element) feedEls.item(i);
        feedList.add(this.parseFeed(feedEl));
      }
    }
    return feedList;
  }
  
  /**
   * Get a static feed download URL for a known feed.
   * @return the URL
   * @throws APIException
   */
  public String getFeedURL(int feedID, FeedFormat format, int start, int perPage,
      int level1CategoryID, int level2CategoryID, int programmeCategoryID, String[] programmeCategoryLevels,
      String lid, boolean useHTTPS, boolean reverseMapXML, boolean bestseller) throws APIException {
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+
    "&requestmethod=rest&responsemethod=xml&action=buyat.affiliate.feed.geturl"+
    "&feed_id="+feedID+"&format="+format;
    
    if (start != -1) {
      serviceURL += "&start=" + start;
    }
    if (perPage != -1) {
      serviceURL += "&perpage=" + perPage;
    }
    if (level1CategoryID != -1) {
      serviceURL += "&level1_category_id=" + level1CategoryID;
    }
    if (level2CategoryID != -1) {
      serviceURL += "&level2_category_id=" + level2CategoryID;
    }
    if (programmeCategoryID != -1) {
      serviceURL += "&programme_category_id=" + programmeCategoryID;
    }
    if (programmeCategoryLevels != null) {
      for (int i=0; i<programmeCategoryLevels.length; i++) {
        if (programmeCategoryLevels[i] != null && programmeCategoryLevels[i] != "") {
          serviceURL += "&level"+(i+1)+"="+programmeCategoryLevels[i];
        }
      }
    }
    if (lid != null && !lid.equals("")) {
      serviceURL += "&lid=" + lid;
    }
    if (useHTTPS) {
      serviceURL += "&use_https=" + useHTTPS;
    }
    if (reverseMapXML) {
      serviceURL += "&reverse_map_xml=" + reverseMapXML;
    }
    if (bestseller) {
      serviceURL += "&bestseller=" + serviceURL;
    }

    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);
    
    Element el = doc.getDocumentElement();
    return BuyatAPIClient.getChildNodeText(el, "url");
  }
  
  /**
   * Creates a tracking affiliate deep link for a URL.
   * @param url the original URL
   * @return the tracking URL
   * @throws APIException
   */
  public String getDeepLink(String url) throws APIException {
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+
    "&requestmethod=rest&responsemethod=xml&action=buyat.affiliate.linkengine.create&url="
    +url;
    
    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);
    
    Element el = doc.getDocumentElement();
    return BuyatAPIClient.getChildNodeText(el, "deep_link");
  }
  
  /**
   * Gets information about a single product
   * @param productId
   * @return the product
   * @throws APIException
   */
  public Product getProductInfo(int productId) throws APIException {
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+
    "&requestmethod=rest&responsemethod=xml&action=buyat.affiliate.product.info"
    +"&product_id="+productId;
    
    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);
    
    Element el = doc.getDocumentElement();
    return this.parseProduct(BuyatAPIClient.getChildElement(el, "product"));
  }
  
  /**
   * Gets information about a single programme
   * @param programmeId
   * @return the programme
   * @throws APIException
   */
  public Programme getProgrammeInfo(int programmeId) throws APIException {
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+
    "&requestmethod=rest&responsemethod=xml&action=buyat.affiliate.programme.info"
    +"&programme_id="+programmeId;
    
    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);
    
    Element el = doc.getDocumentElement();
    return this.parseProgramme(BuyatAPIClient.getChildElement(el, "programme"));
  }
  
  /**
   * Gets information about a single feed
   * @param feedId
   * @return the feed
   * @throws APIException
   */
  public Feed getFeedInfo(int feedId) throws APIException {
    String serviceURL = this.apiEndpoint+"?api_key="+this.apiKey+
    "&requestmethod=rest&responsemethod=xml&action=buyat.affiliate.feed.info"
    +"&feed_id="+feedId;
    
    Document doc = this.downloadURLAsXML(serviceURL);
    this.handleErrors(doc);
    
    Element el = doc.getDocumentElement();
    return this.parseFeed(BuyatAPIClient.getChildElement(el, "feed"));
  }

  private static String getChildNodeText(Element el, String childNodeName) throws APIException
  {
    // We only want direct descendants so use getChildNodes and iterate,
    // rather than using getElementsByTagName
    NodeList childElements = el.getChildNodes();
    for(int i=0; i < childElements.getLength(); i++) {
      Node childNode = childElements.item(i);
      if (childNode.getNodeName().equals(childNodeName)) {
        return childNode.getTextContent();
      }
    }
    
    // Element not found so throw exception
    throw new APIException("Invalid XML returned from API: no " + 
        childNodeName + " child nodes in element " + el.getNodeName());
  }
  
  private static Element getChildElement(Element el, String childNodeName) throws APIException
  {
    NodeList childElements = el.getElementsByTagName(childNodeName);
    if(childElements.getLength() == 1) {
      Node childNode = childElements.item(0);
      if (childNode instanceof Element) {
        return (Element) childNode;
      }
    }
    return null;
  }
}
