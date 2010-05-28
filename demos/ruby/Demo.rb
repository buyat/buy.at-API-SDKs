=begin

@LICENCE@ 

=end

require 'BuyatApiClient'

# Change the API key from this test one to your own
client = BuyatApiClient.new("01-fd288e15a739c35276a15d73a227dcc7")

puts "\n*********** Category Tree ************\n"
categories = client.categoryTree();
for cat in categories
  puts "Category: " << cat.category_id.to_s << ", " << cat.category_name
  for subcat in cat.subcategories
    puts "SubCategory: " << subcat.category_id.to_s << ", " << subcat.category_name
  end
end

puts "\n*********** Level 1 Categories ************\n"
categories = client.listLevel1Categories();
for cat in categories
  puts "Category: " << cat.category_id.to_s << ", " << cat.category_name
end

puts "\nSubcategories for category " << categories[0].category_name
for subcat in categories[0].subcategories
  puts "SubCategory: " << subcat.category_id.to_s << ", " << subcat.category_name
end

puts "\n*********** Level 2 Categories ************\n"
categories = client.listLevel2Categories(20);
for cat in categories
  puts "Category: " << cat.category_id.to_s << ", " << cat.category_name
end

puts "\n*********** Product Search ************\n"
result = client.searchProducts('ipod')
puts "Search for ipod returned " << result.total_results.to_s << " results"
for product in result.products
  puts "Product: " << product.product_id.to_s << ", " << product.product_name << ", " << product.currency << " " << product.online_price.to_s
end

product = client.getProduct(result.products[0].product_id)
puts "\nProduct by id: " << product.product_id.to_s << ", " << product.product_name << ", " << product.currency << " " << product.online_price.to_s
programme = product.programme
puts "Product's programme: " << programme.programme_id.to_s << ", " << programme.programme_name << ", " << programme.programme_url
feed = product.feed
puts "Product's feed: " << feed.feed_id.to_s << ", " << feed.feed_name << ", " << feed.number_of_products.to_s << ", " << feed.last_updated.to_s

puts "\n*********** Programmes ************\n"
programmes = client.listProgrammes()
for programme in programmes
  puts "Programme: " << programme.programme_id.to_s << ", " << programme.programme_name << ", " << programme.programme_url
end

programme = client.getProgramme(programmes[0].programme_id)
puts "\nProgramme by id: " << programme.programme_id.to_s << ", " << programme.programme_name << ", " << programme.programme_url

puts "\n*********** List Feeds ************\n"
feeds = client.listFeeds()
for feed in feeds
  puts "Feed: " << feed.feed_id.to_s << ", " << feed.feed_name << ", " << feed.number_of_products.to_s << ", " << feed.last_updated.to_s
end

feed = client.getFeed(feeds[0].feed_id)
puts "\nFeed by id: " << feed.feed_id.to_s << ", " << feed.feed_name << ", " << feed.number_of_products.to_s << ", " << feed.last_updated.to_s

programme = feed.programme
puts "Feed's programme: " << programme.programme_id.to_s << ", " << programme.programme_name << ", " << programme.programme_url

puts "\n*********** Get Feed URL ************\n"
puts "Feed URL: " << client.getFeedUrl(367, 'CSV', 0, 10, -1, -1, -1, nil, "mylid", false, false, false)
progCategories = Array[nil, nil, "Baby"];
puts "Feed URL: " << client.getFeedUrl(367, 'XMLGZIP', 0, 10, 20, -1, -1, progCategories, nil, true, true, true)

puts "\n*********** Get Deeplink ************\n"
puts "Deeplink: " << client.createDeeplink("http://www.play.com/Music/CD/4-/793109/Sea-Change-The-Choral-Music-Of/Product.html?ptsl=1&ob=Price&fb=0")
        