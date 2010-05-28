=begin

@LICENCE@ 

=end

class Product 
  attr_accessor :product_id
  attr_accessor :product_sku
  attr_accessor :product_url
  attr_accessor :product_name
  attr_accessor :brand_name
  attr_accessor :description
  attr_accessor :online_price
  attr_accessor :currency
  attr_accessor :currency_symbol
  attr_accessor :image_url
  attr_accessor :programme_id
  attr_accessor :programme_name
  attr_accessor :programme_url
  attr_accessor :level1_category_id
  attr_accessor :level1_category_name
  attr_accessor :level2_category_id
  attr_accessor :level2_category_name
  attr_accessor :feed_id
  attr_accessor :feed_name
  
  def initialize(api_client)
    @api_client = api_client
  end
  
  def feed
    if @feed.nil?
      @feed = @api_client.getFeed(@feed_id)
    end
    return @feed
  end
  
  def programme
    if @programme.nil?
      @programme = @api_client.getProgramme(@programme_id)
    end
    return @programme
  end
  
end

class ProductResultSet
  attr_accessor :total_results
  attr_accessor :current_results
  attr_accessor :start
  attr_accessor :limit
  attr_accessor :query
  attr_accessor :products
end

class Category
  attr_accessor :category_id
  attr_accessor :level
  attr_accessor :category_name
  attr_accessor :parent_category_id
  attr_accessor :parent_category_name
  attr_writer :subcategories
  
  def initialize(api_client)
    @api_client = api_client
  end
  
  def subcategories
    if @subcategories.nil? and @level == 1
      @subcategories = @api_client.listLevel2Categories(@category_id) 
    end
    return @subcategories
  end
end

class Programme
  attr_accessor :programme_id
  attr_accessor :programme_name
  attr_accessor :programme_url
  attr_accessor :has_feed
end

class Feed
  attr_accessor :feed_id
  attr_accessor :feed_name
  attr_accessor :programme_id
  attr_accessor :programme_name
  attr_accessor :programme_url
  attr_accessor :number_of_products
  attr_accessor :last_updated
  
  def initialize(api_client)
    @api_client = api_client
  end
  
  def programme
    if @programme.nil?
      @programme = @api_client.getProgramme(@programme_id)
    end
    return @programme
  end
end
