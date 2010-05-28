import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import at.buy.api.APIException;
import at.buy.api.BuyatAPIClient;
import at.buy.api.BuyatAPIClient.FeedFormat;
import at.buy.api.BuyatAPIClient.ProductField;
import at.buy.api.BuyatAPIClient.SortOrder;
import at.buy.entities.Category;
import at.buy.entities.Feed;
import at.buy.entities.Product;
import at.buy.entities.ProductResultSet;
import at.buy.entities.Programme;

public class TestHarness {

  private BuyatAPIClient client;

  @Before
  public void setUp() {
    this.client = new BuyatAPIClient("01-fd288e15a739c35276a15d73a227dcc7");
  }

  @Test
  public void testProductSearch() {
    try {
      ProductResultSet result = client
          .searchProducts("XKJERVdsfkdjsfkDJHFKSJDFSDFDFkjhkwjean3endwf");
      Assert.assertEquals("Expected 0 results for nonsense query", 0, result
          .getTotalResults());
      Assert.assertEquals("Expected 0 results for nonsense query", 0, result
          .getProducts().size());

      result = client.searchProducts("ipod");
      Assert.assertNotNull("Result should not be null", result);
      Assert.assertTrue("Expected more than 10 results for ipod query", result
          .getTotalResults() > 10);
      Assert.assertEquals("Expected 10 results per page", result
          .getCurrentResults(), 10);

      List<Product> products = result.getProducts();
      Assert.assertNotNull("Products should not be null", products);
      Assert.assertEquals(
          "Products list should be same size as current results", result
              .getCurrentResults(), products.size());

      Product product = products.get(0);
      Assert.assertNotNull("Product should not be null", product);
      Assert.assertTrue("Product description should contain ipod", product
          .getDescription().toLowerCase().indexOf("ipod") != -1);

      Programme programme = product.getProgramme();
      Assert.assertNotNull("Programme should not be null", programme);
      Assert.assertEquals("Programme name should match product programme name",
          product.getProgrammeName(), programme.getProgrammeName());

      Feed feed = product.getFeed();
      Assert.assertNotNull("Feed should not be null", feed);
      Assert.assertEquals("Feed name should match product feed name",
          product.getFeedName(), feed.getFeedName());
      
      ProductResultSet result2 = client.searchProducts("Shoes", 2, 5, null, ProductField.ONLINE_PRICE, SortOrder.ASC);
      Assert.assertNotNull("Result should not be null", result2);
      Assert.assertTrue("Expected more than 10 results for shoes query", result2
          .getTotalResults() > 10);
      Assert.assertEquals("Expected 5 results per page", result2
          .getCurrentResults(), 5);
      Assert.assertTrue("1st product's price should be <= 2nd product's", 
          result2.getProducts().get(0).getOnlinePrice() <= result2.getProducts().get(1).getOnlinePrice());
      
    } catch (APIException e) {
      e.printStackTrace();
      Assert.assertTrue("Got exception: " + e.getMessage(), false);
    }
  }
  
  @Test
  public void testCategories() {
    try {
      List<Category> level1Categories = client.getLevel1Categories();
      Assert.assertEquals("Number of categories", 38, level1Categories.size());
      
      Category cat = level1Categories.get(0);
      Assert.assertEquals("Category id", 20, cat.getCategoryID());
      Assert.assertEquals("Category name", "Baby", cat.getCategoryName());
      
      List<Category> subcategories = cat.getSubcategories();
      Assert.assertEquals("Number of categories", 9, subcategories.size());
      
      Category subcat = subcategories.get(0);
      Assert.assertEquals("Category id", 497, subcat.getCategoryID());
      Assert.assertEquals("Category name", "Accessories", subcat.getCategoryName());
      Assert.assertEquals("Parent category id", 20, subcat.getParentCategoryID());
      Assert.assertEquals("Parent category name", "Baby", subcat.getParentCategoryName());
      Assert.assertEquals("Subcategories", null, subcat.getSubcategories());
      
      List<Category> level2Categories = client.getLevel2Categories(20);
      Assert.assertEquals("Number of categories", subcategories.size(), level2Categories.size());
      
      List<Category> categories = client.getCategoryTree();
      Assert.assertEquals("Category tree should be same size as level 1 categories", level1Categories.size(), categories.size());
      
      for(int i=0; i<categories.size(); i++) {
        Category listCat = level1Categories.get(i);
        Category treeCat = categories.get(i);
        Assert.assertEquals("Category ids should match", listCat.getCategoryID(), treeCat.getCategoryID());
        Assert.assertEquals("Category names should match", listCat.getCategoryName(), treeCat.getCategoryName());
        //Assert.assertEquals("Category subcat list lengths should match", listCat.getSubcategories().size(), treeCat.getSubcategories().size());
      }
    } catch (APIException e) {
      e.printStackTrace();
      Assert.assertTrue("Got exception: " + e.getMessage(), false);
    }
  }
  
  @Test
  public void testFeeds() {
    try {
      List<Feed> feeds = client.getFeeds();
      Assert.assertTrue("Expected more than 100 results", feeds.size()>100);
      
      Feed feed = feeds.get(0);
      Assert.assertEquals("Feed name", "1800Pet Meds", feed.getFeedName());
      Assert.assertEquals("Programme id", 1038, feed.getProgrammeID());
      Assert.assertEquals("Programme name", "1800PetMeds", feed.getProgrammeName());
    } catch (APIException e) {
      e.printStackTrace();
      Assert.assertTrue("Got exception: " + e.getMessage(), false);
    }
  }
  
  @Test
  public void testFeedURL() {
    try {
      String feedURL = client.getFeedURL(367, FeedFormat.CSV, 0, 10, -1, -1, -1, null, "mylid", false, false, false);
      Assert.assertEquals("Feed URL", "http://localhost/feeddownload/index.php/download?OEMAIL=affiliate2@gmail.com&PX=e99a18c428cb38d5f260853678922e03&DISPLAYFORMAT=CSV&PRODUCTDB_ID=367&START=0&PERPAGE=10&LID=mylid", feedURL);

      String[] progCategories = {null, null, "Baby"};
      String feedURL2 = client.getFeedURL(367, FeedFormat.XMLGZIP, 0, 10, 20, -1, -1, progCategories, null, true, true, true);
      Assert.assertEquals("Feed URL", "https://localhost/feeddownload/index.php/download?OEMAIL=affiliate2@gmail.com&PX=e99a18c428cb38d5f260853678922e03&DISPLAYFORMAT=XMLGZIP&PRODUCTDB_ID=367&REVERSEMAPXML=true&START=0&PERPAGE=10&MAPPED_CAT_ID1=20&LEVEL3=Baby", feedURL2);
    } catch (APIException e) {
      e.printStackTrace();
      Assert.assertTrue("Got exception: " + e.getMessage(), false);
    }
  }

  @Test
  public void testDeeplinks() {
    try {
      String url = client.getDeepLink("http://www.play.com/Music/CD/4-/793109/Sea-Change-The-Choral-Music-Of/Product.html?ptsl=1&ob=Price&fb=0");
      Assert.assertEquals("Deeplink URL", "http://playcom.at/affiliate2?CTY=84&DURL=http://www.play.com/Music/CD/4-/793109/Sea-Change-The-Choral-Music-Of/Product.html?ptsl=1", url);
    } catch (APIException e) {
      e.printStackTrace();
      Assert.assertTrue("Got exception: " + e.getMessage(), false);
    }
  }

  @Test
  public void testProgrammes() {
    try {
      List<Programme> programmes = client.getProgrammes();
      Assert.assertTrue("Expected more than 100 results", programmes.size()>100);
      
      Programme prog = programmes.get(0);
      Assert.assertEquals("Programme id", 115, prog.getProgrammeID());
      Assert.assertEquals("Programme name", "Marks and Spencer", prog.getProgrammeName());
      
    } catch (APIException e) {
      e.printStackTrace();
      Assert.assertTrue("Got exception: " + e.getMessage(), false);
    }
  }
  
  @Test
  public void testProductInfo() {
    try {
      Product product = client.getProductInfo(118949289);
      Assert.assertNotNull("Product should not be null", product);
      Assert.assertEquals("Product id", 118949289, product.getProductID());
      Assert.assertEquals("Product SKU", "90998", product.getProductSKU());
      Assert.assertEquals("Product name", "St  James's Hotel and Club - Althoff  Hotel Collection", product.getProductName());
      Assert.assertEquals("Product url", "http://laterooms.at/affiliate2?CTY=84&DURL=http://www.laterooms.com/en/p1023/hotel-reservations/90998_st-james-s-club-hotel.aspx", product.getProductURL());
      Assert.assertEquals("Prog id", 293, product.getProgrammeID());
    } catch (APIException e) {
      e.printStackTrace();
      Assert.assertTrue("Got exception: " + e.getMessage(), false);
    }
  }
  
  @Test
  public void testProgrammeInfo() {
    try {
      Programme programme = client.getProgrammeInfo(293);
      Assert.assertNotNull("Programme should not be null", programme);
      Assert.assertEquals("Programme id", 293, programme.getProgrammeID());
      Assert.assertEquals("Programme name", "Laterooms.com", programme.getProgrammeName());
      Assert.assertEquals("Programme url", "http://laterooms.at/affiliate2", programme.getProgrammeURL());
    } catch (APIException e) {
      e.printStackTrace();
      Assert.assertTrue("Got exception: " + e.getMessage(), false);
    }
  }
  
  @Test
  public void testFeedInfo() {
    try {
      Feed feed = client.getFeedInfo(367);
      Assert.assertNotNull("Feed should not be null", feed);
      Assert.assertEquals("Feed id", 367, feed.getFeedID());
      Assert.assertEquals("Feed name", "1800Pet Meds", feed.getFeedName());
      Assert.assertEquals("Programme id", 1038, feed.getProgrammeID());
      Assert.assertEquals("Programme name", "1800PetMeds", feed.getProgrammeName());
    } catch (APIException e) {
      e.printStackTrace();
      Assert.assertTrue("Got exception: " + e.getMessage(), false);
    }
  }
}
