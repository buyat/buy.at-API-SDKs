'''
Created on 3 Dec 2009

@author: aclarke
'''
import unittest
from buyat.api.BuyatAPIClient import BuyatAPIClient

class TestHarness(unittest.TestCase):
    
    def setUp(self):
        self.client = BuyatAPIClient('01-fd288e15a739c35276a15d73a227dcc7');
        
        
    def testProductSearch(self):
        result = self.client.search_products('XKJERVdsfkdjsfkDJHFKSJDFSDFDFkjhkwjean3endwf')
        assert result.total_results == 0, result.total_results
        assert len(result.products) == 0, len(result.products)
        
        result = self.client.search_products('ipod')
        assert result.total_results > 10, result.total_results
        assert result.current_results == 10, result.current_results
        assert result.current_results == len(result.products), (result.total_results, len(result.products))
        
        assert len(result.products[0].description.lower()) > 0, len(result.products[0].description.lower())
        
        
    def testCategories(self):
        categories = self.client.get_level1_categories()
        assert len(categories) == 38, len(categories)
        
        assert categories[0].category_id == 20, categories[0].category_id
        assert categories[0].category_name == 'Baby', categories[0].category_name
        assert len(categories[0].subcategories) == 9, len(categories[0].subcategories)
            
        subcategories = self.client.get_level2_categories(categories[0].category_id)
        assert len(subcategories) == 9, len(subcategories)
        assert subcategories[0].subcategories == None, subcategories[0].subcategories
            
        both_subcats = zip(subcategories, categories[0].subcategories)
        for cat1, cat2 in both_subcats:
            assert cat1.category_id == cat2.category_id
            assert cat1.category_name == cat2.category_name
            assert cat1.level == cat2.level
            
        category_tree = self.client.get_category_tree()
        assert len(category_tree) == 38, len(category_tree)
        
        both_cats = zip(categories, category_tree)
        for cat1, cat2 in both_cats:
            assert cat1.category_id == cat2.category_id
            assert cat1.category_name == cat2.category_name
        
        
    def testProgrammes(self):
        programmes = self.client.get_programmes()
        assert len(programmes) == 273, len(programmes)
        
        assert programmes[0].programme_id == 115, programmes[0].programme_id
        assert programmes[0].programme_name == "Marks and Spencer", programmes[0].programme_name
        
        programme = self.client.get_programme(115)
        assert programme.programme_id == 115, programme.programme_id
        assert programme.programme_name == "Marks and Spencer", programme.programme_name
        assert programme.programme_url == programmes[0].programme_url, (programme.programme_url, programmes[0].programme_url)
        assert programme.has_feed == programmes[0].has_feed, (programme.has_feed, programmes[0].has_feed)
        
        
    def testFeeds(self):
        feeds = self.client.get_feeds()
        assert len(feeds) > 100, len(feeds)
        
        assert feeds[0].feed_id == 367, feeds[0].feed_id
        assert feeds[0].feed_name == "1800Pet Meds", feeds[0].feed_name
        
        feed = self.client.get_feed(367)
        assert feed.feed_id == 367, feed.feed_id
        assert feed.feed_name == "1800Pet Meds", feed.feed_name
        assert feed.programme_name == feeds[0].programme_name, (feed.programme_name, feeds[0].programme_name)
        assert feed.programme_url == feeds[0].programme_url, (feed.programme_url, feeds[0].programme_url)
        assert feed.number_of_products == feeds[0].number_of_products, (feed.number_of_products, feeds[0].number_of_products)
        assert feed.last_updated == feeds[0].last_updated, (feed.last_updated, feeds[0].last_updated)
        
    def testFeedURL(self):
        url = self.client.get_feed_url(367, "CSV", perpage=10, lid="mylid")
        assert url == "http://localhost/feeddownload/index.php/download?OEMAIL=affiliate2@gmail.com&amp;PX=e99a18c428cb38d5f260853678922e03&amp;DISPLAYFORMAT=CSV&amp;PRODUCTDB_ID=367&amp;START=0&amp;PERPAGE=10&amp;LID=mylid", url
        
        prog_categories = [None, None, "Baby"]
        url2 = self.client.get_feed_url(367, "XMLGZIP", perpage=10, level1_category_id=20, levels=prog_categories, reverse_map_xml=True, use_https=True);
        assert url2 == "https://localhost/feeddownload/index.php/download?OEMAIL=affiliate2@gmail.com&amp;PX=e99a18c428cb38d5f260853678922e03&amp;DISPLAYFORMAT=XMLGZIP&amp;PRODUCTDB_ID=367&amp;REVERSEMAPXML=y&amp;START=0&amp;PERPAGE=10&amp;MAPPED_CAT_ID1=20&amp;LEVEL3=Baby", url2
        
        
    def testGetDeeplink(self):
        url = self.client.get_deeplink("http://www.play.com/Music/CD/4-/793109/Sea-Change-The-Choral-Music-Of/Product.html?ptsl=1&ob=Price&fb=0");
        assert url == "http://playcom.at/affiliate2?CTY=84&amp;DURL=http://www.play.com/Music/CD/4-/793109/Sea-Change-The-Choral-Music-Of/Product.html?ptsl=1", url

    def testProductInfo(self):
        product = self.client.get_product(118949289)
        assert product.product_id == 118949289
        assert product.product_sku == "90998"
        assert product.product_name == "St  James's Hotel and Club - Althoff  Hotel Collection"
        assert product.product_url == "http://laterooms.at/affiliate2?CTY=84&amp;DURL=http://www.laterooms.com/en/p1023/hotel-reservations/90998_st-james-s-club-hotel.aspx"
        
        feed = product.feed
        assert feed.feed_name == product.feed_name
        assert feed.feed_id == product.feed_id
        
        programme = product.programme
        assert programme.programme_name == product.programme_name
        assert programme.programme_id == product.programme_id
        assert programme.programme_url == product.programme_url, (programme.programme_url, product.programme_url)
        
        

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()