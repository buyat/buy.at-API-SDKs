'''
@LICENCE@
'''
from buyatapi.BuyatAPIClient import BuyatAPIClient

if __name__ == "__main__":

    # Change the API key from this test one to your own
    client = BuyatAPIClient('01-fd288e15a739c35276a15d73a227dcc7')
    
    print "\n*********** Product Search ************\n"
    result = client.search_products("ipod")
    print "Search found %s results, retrieved %s" %(result.total_results, result.current_results)
    for product in result.products:
        print "Product: %s, %s, %s" %(product.product_id, product.product_name,
                                      product.product_url)
    print
    
    result2 = client.search_products("shoes",programme_ids=[115,279], level1_category_id=26)
    print "Search found %s results, retrieved %s" %(result2.total_results, result2.current_results)
    for product in result2.products:
        print "Product: %s, %s, %s" %(product.product_id, product.product_name,
                                      product.product_url)
    
    print "\n*********** Categories ************\n"
    categories = client.get_level1_categories()
    for cat in categories:
        print "Got category: %s, %s" %(cat.category_id, cat.category_name)
        
    subcategories = client.get_level2_categories(categories[0].category_id)
    for cat in subcategories:
        print "Got subcategory: %s, %s" %(cat.category_id, cat.category_name)
        
    print "\n*********** Category Tree ************\n"
    categories = client.get_category_tree()
    for cat in categories:
        print "Category: %s, %s" %(cat.category_id, cat.category_name)
        for subcat in cat.subcategories:
            print "SubCategory: %s, %s" %(subcat.category_id, subcat.category_name)
        print
        
    print "\n*********** Programme List ************\n"
    programmes = client.get_programmes()
    for prog in programmes:
        print "Got programme: %s, %s" %(prog.programme_id, prog.programme_name)
        
    print "\n*********** Feed List ************\n"
    feeds = client.get_feeds()
    for feed in feeds:
        print "Got feed: %s, %s, %s, %s" %(feed.feed_id, feed.feed_name, 
                                           feed.number_of_products,
                                           feed.last_updated)
        
    print "\n*********** Feed Download URL ************\n"
    # Get feed URL for feed 367 in CSV format, using defaults for optional values except setting 
    # link id to "mylid"
    url = client.get_feed_url(367, "CSV", perpage=10, lid="mylid")
    print "Got url: %s" %url

    # Get feed URL for feed 367 in gzipped XML format, with level 1 category id = 20, level 2 programme 
    # category set to "Baby", using HTTPS, reverse mapping the XML, and restricting the results to Bestsellers
    url2 = client.get_feed_url(367, "XMLGZIP", perpage=10, level1_category_id=20, levels=[None, "Baby"], reverse_map_xml=True, use_https=True);
    print "Got url: %s" %url2
    
    print "\n********** Create DeepLink ***************\n"
    url = client.get_deeplink("http://www.play.com/Music/CD/4-/793109/Sea-Change-The-Choral-Music-Of/Product.html?ptsl=1&ob=Price&fb=0");
    print "Got url: %s" %url
    
    print "\n********** Get Product Info ***************\n"
    product = client.get_product(118949289)
    print "Product: %s, %s, %s" %(product.product_id, product.product_name,
                                  product.product_url)
                                  
    print "\n********** Get Programme Info ***************\n"
    prog = client.get_programme(115)
    print "Got programme: %s, %s" %(prog.programme_id, prog.programme_name)
    
    print "\n********** Get Feed Info ***************\n"
    feed = client.get_feed(367)
    print "Feed: %s, %s, %s, %s" %(feed.feed_id, feed.feed_name, feed.number_of_products, feed.last_updated)
    