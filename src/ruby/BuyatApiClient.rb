=begin

@LICENCE@ 

=end

require 'net/http'
require 'cgi'
require 'rexml/document'
require 'date'
require 'BuyAtProductClasses.rb'

class BuyatApiClient
  def apiKey
    @apiKey
  end
  
  def apiEndpoint
    @apiEndpoint
  end
  
  attr_writer :apiKey
  
  def initialize(apiKey)
    @apiKey = apiKey
    @apiEndpoint = "@API_ENDPOINT@"
  end
  
  def downloadXmlFromApi(url)
    return Net::HTTP.get_response(URI.parse(url)).body
  end
  
  def parseXml(xmlData)
    doc = REXML::Document.new(xmlData)
    
    doc.elements.each('results/error') do |errorElement|
      errorElement.elements.each('error_message') do |errorMessage|
        raise errorMessage.text
      end
    end
    
    return doc
  end
  
  def parseCategories(doc)
    categories = Array.new()
    doc.elements.each('results/categories/category') do |category|
      cat = Category.new(self)
      cat.category_id = Integer(category.elements["category_id"].text)
      cat.level = Integer(category.elements["level"].text)
      cat.category_name = category.elements["category_name"].text
      if cat.level == 2
        cat.parent_category_id = Integer(category.elements["parent_category_id"].text)
        cat.parent_category_name = category.elements["parent_category_name"].text
      end
      categories << cat
    end
    return categories
  end
  
  def parseCategoryTree(doc)
    categories = Array.new()
    doc.elements.each('category') do |category|
      cat = Category.new(self)
      cat.category_id = Integer(category.elements["category_id"].text)
      cat.level = Integer(category.elements["level"].text)
      cat.category_name = category.elements["category_name"].text
      if !category.elements["subcategories"].nil?
        cat.subcategories = parseCategoryTree(category.elements["subcategories"])
      end
      categories << cat
    end
    return categories
  end
  
  def parseProduct(el)
    product = Product.new(self)
    product.product_id = Integer(el.elements["product_id"].text)
    product.product_sku = el.elements["product_sku"].text
    product.product_url = el.elements["product_url"].text
    product.product_name = el.elements["product_name"].text
    product.brand_name = el.elements["brand_name"].text
    product.description = el.elements["description"].text
    product.online_price = Float(el.elements["online_price"].text.gsub(',',''))
    product.currency = el.elements["currency"].text
    product.currency_symbol = el.elements["currency_symbol"].text
    product.image_url = el.elements["image_url"].text
    product.programme_id = Integer(el.elements["programme_id"].text)
    product.programme_name = el.elements["programme_name"].text
    product.programme_url = el.elements["programme_url"].text
    product.level1_category_id = Integer(el.elements["level1_category_id"].text)
    product.level1_category_name = el.elements["level1_category_name"].text
    product.level2_category_id = Integer(el.elements["level2_category_id"].text)
    product.level2_category_name = el.elements["level2_category_name"].text
    product.feed_id = Integer(el.elements["feed_id"].text)
    product.feed_name = el.elements["feed_name"].text
    return product
  end
  
  def parseProgramme(el)
    programme = Programme.new()
    programme.programme_id = Integer(el.elements["programme_id"].text)
    programme.programme_name = el.elements["programme_name"].text
    programme.programme_url = el.elements["programme_url"].text
    programme.has_feed = el.elements["has_feed"].text == 'Y'
    return programme
  end
  
  def parseFeed(el)
    feed = Feed.new(self)
    feed.feed_id = Integer(el.elements["feed_id"].text)
    feed.feed_name = el.elements["feed_name"].text
    feed.programme_id = Integer(el.elements["programme_id"].text)
    feed.programme_name = el.elements["programme_name"].text
    feed.programme_url = el.elements["programme_url"].text
    feed.number_of_products = Integer(el.elements["number_of_products"].text)
    begin
      feed.last_updated = DateTime.strptime(el.elements["last_updated"].text, '%Y-%m-%d %H:%M:%S')
    rescue
      feed.last_updated = nil
    end
    return feed
  end
  
  def echo(message)
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.test.echo"
    url << "&requestmethod=rest&responsemethod=xml"
    url << "&message=" << message
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    doc.elements.each('results/message') do |retMessage|
      return retMessage.text
    end
  end
  
  def searchProducts(query=nil, page=1, perpage=10, programme_ids=nil,
                     excluded_programme_ids=nil, excluded_programme_category_ids=nil,
                     feed_ids=nil, level1_category_id=nil, level2_category_id=nil,
                     include_adult=false, lid=nil, sort=nil, sortorder=nil)
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.affiliate.product.search"
    url << "&requestmethod=rest&responsemethod=xml"
    
    url << "&query=" << query unless query.nil?
    url << "&page=" << page.to_s
    url << "&perpage=" << perpage.to_s
    
    unless programme_ids.nil? || programme_ids.length == 0
      url << "&programme_ids=" << programme_ids.join(",")
    end
      
    unless excluded_programme_ids.nil? || excluded_programme_ids.length == 0
      url << "&excluded_programme_ids=" << excluded_programme_ids.join(",")
    end
      
    unless excluded_programme_category_ids.nil? || excluded_programme_category_ids.length == 0
      url << "&excluded_programme_category_ids=" << excluded_programme_category_ids.join(",")
    end
      
    unless feed_ids.nil? || feed_ids.length == 0
      url << "&feed_ids=" << feed_ids.join(",")
    end
    
    url << "&level1_category_id=" << level1_category_id unless level1_category_id.nil?
    url << "&level2_category_id=" << level2_category_id unless level2_category_id.nil?
    url << "&include_adult=true" if include_adult
    url << "&lid=" << lid unless lid.nil?
    url << "&sort=" << sort unless sort.nil?
    url << "&sortorder=" << sortorder unless sortorder.nil?
    
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    
    resultSet = ProductResultSet.new()
    resultEl = doc.elements['results']
    resultSet.total_results = Integer(resultEl.elements['total_results'].text)
    resultSet.current_results = Integer(resultEl.elements['current_results'].text)
    resultSet.start = Integer(resultEl.elements['start'].text)
    resultSet.limit = Integer(resultEl.elements['limit'].text)
    resultSet.query = resultEl.elements['query'].text
    
    products = Array.new()
    resultEl.elements.each('products/product') do |productEl|
      products << parseProduct(productEl)
    end

    resultSet.products = products
    return resultSet
  end
  
  def listLevel1Categories()
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.affiliate.category.listlevel1"
    url << "&requestmethod=rest&responsemethod=xml"
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    return parseCategories(doc)
  end
  
  def listLevel2Categories(level1_id)
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.affiliate.category.listlevel2"
    url << "&requestmethod=rest&responsemethod=xml"
    url << "&level1_category_id=" << level1_id.to_s
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    return parseCategories(doc)
  end
  
  def categoryTree()
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.affiliate.category.tree"
    url << "&requestmethod=rest&responsemethod=xml"
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    return parseCategoryTree(doc.elements['results/categories'])
  end
  
  def listProgrammes()
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.affiliate.programme.list"
    url << "&requestmethod=rest&responsemethod=xml"
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    
    programmes = Array.new()
    doc.elements.each('results/programmes/programme') do |programmeEl|
      programmes << parseProgramme(programmeEl)
    end
    return programmes
  end
  
  def listFeeds()
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.affiliate.feed.list"
    url << "&requestmethod=rest&responsemethod=xml"
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    
    feeds = Array.new()
    doc.elements.each('results/feeds/feed') do |feedEl|
      feeds << parseFeed(feedEl)
    end
    return feeds
  end
  
  def getFeedUrl(feed_id, format, start=0, perpage=100,
                 level1_category_id=nil, level2_category_id=nil,
                 programme_category_id=nil, levels=nil,
                 lid=nil, use_https=false, reverse_map_xml=false,
                 bestseller=false)
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.affiliate.feed.geturl"
    url << "&requestmethod=rest&responsemethod=xml"
    
    url << "&feed_id=" << feed_id.to_s
    url << "&format=" << format
    url << "&start=" << start.to_s
    url << "&perpage=" << perpage.to_s
    url << "&level1_category_id=" << level1_category_id.to_s unless level1_category_id.nil?
    url << "&level2_category_id=" << level2_category_id.to_s unless level2_category_id.nil?
    url << "&programme_category_id=" << programme_category_id.to_s unless programme_category_id.nil?
    
    unless levels.nil? || levels.length == 0
      levels.each_with_index do |name, i|
        url << "&level" << (i+1).to_s << "=" << name unless name.nil? 
      end
    end
    
    url << "&lid=" << lid unless lid.nil?
    url << "&use_https=true" if use_https
    url << "&reverse_map_xml=true" if reverse_map_xml
    url << "&bestseller=true" if bestseller
    
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    return doc.elements['results/url'].text   
  end
  
  def createDeeplink(durl)
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.affiliate.linkengine.create"
    url << "&requestmethod=rest&responsemethod=xml"
    url << "&url=" << durl
    
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    return doc.elements['results/deep_link'].text
  end
  
  def getProduct(product_id)
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.affiliate.product.info"
    url << "&requestmethod=rest&responsemethod=xml"
    url << "&product_id=" << product_id.to_s
    
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    return parseProduct(doc.elements['results/product'])
  end
  
  def getProgramme(programme_id)
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.affiliate.programme.info"
    url << "&requestmethod=rest&responsemethod=xml"
    url << "&programme_id=" << programme_id.to_s
    
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    return parseProgramme(doc.elements['results/programme'])
  end
  
  def getFeed(feed_id)
    url = String.new()
    url << @apiEndpoint << "?api_key=" << @apiKey
    url << "&action=buyat.affiliate.feed.info"
    url << "&requestmethod=rest&responsemethod=xml"
    url << "&feed_id=" << feed_id.to_s
    
    xmlData = downloadXmlFromApi(url)
    doc = parseXml(xmlData)
    return parseFeed(doc.elements['results/feed'])
  end
  
  private :downloadXmlFromApi, :parseXml, :parseCategories, :parseCategoryTree, :parseProduct, :parseFeed
  
end
