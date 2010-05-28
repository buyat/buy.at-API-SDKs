/******************************************************************************
 
@LICENCE@
 
 ******************************************************************************/

import at.buy.api.BuyatAPIClient;
import at.buy.entities.Category;
import at.buy.entities.Feed;
import at.buy.entities.Product;
import at.buy.entities.ProductResultSet;
import at.buy.entities.Programme;
import java.util.*;

public class Demo
{
  public static void main(String[] args)
  {
    try {
        // Change the API key from this test one to your own
        BuyatAPIClient client = new BuyatAPIClient("01-fd288e15a739c35276a15d73a227dcc7");
        
        System.out.println("\n*********** Echo ************\n");
        String message = client.echo("Hello!");
        System.out.println(message);
        
        System.out.println("\n*********** Level 1 Categories ************\n");
        List<Category> level1Categories = client.getLevel1Categories();
        for(Category cat : level1Categories) {
          System.out.println("Category: " + cat.getCategoryID() + ", " + cat.getCategoryName());
        }
        
        System.out.println();
        Category cat = level1Categories.get(0);
        System.out.println("Getting subcategory for cat " + cat.getCategoryID() + ", " + cat.getCategoryName());
        List<Category> subcategories = cat.getSubcategories();
        for(Category subcat : subcategories) {
          System.out.println("SubCategory: " + subcat.getCategoryID() + ", " + subcat.getCategoryName());
        }
        
        System.out.println("\n*********** Category Tree ************\n");
        List<Category> categories = client.getCategoryTree();
        for(Category cat2 : categories) {
          System.out.println("Category: " + cat2.getCategoryID() + ", " + cat2.getCategoryName());
          for(Category subcat : cat2.getSubcategories()) {
            System.out.println("SubCategory: " + subcat.getCategoryID() + ", " + subcat.getCategoryName());
          }
          System.out.println();
        }
        
        System.out.println("\n*********** Programme List ************\n");
        List<Programme> programmes = client.getProgrammes();
        for(Programme prog : programmes) {
          System.out.println("Programme: " + prog.getProgrammeID() + ", " + 
              prog.getProgrammeName() + ", " + prog.getProgrammeURL() + ", " +
              prog.hasFeed());
        }
        
        System.out.println("\n*********** Feed List ************\n");
        List<Feed> feeds = client.getFeeds();
        for(Feed feed : feeds) {
          System.out.println("Feed: " + feed.getFeedID() + ", " + 
              feed.getFeedName() + ", " + feed.getNumberOfProducts() + ", " +
              feed.getLastUpdated());
        }
        
        System.out.println("\n*********** Product Search ************\n");
        ProductResultSet result = client.searchProducts("ipod");
        System.out.println("Search found "+result.getTotalResults()+" results, retrieved "+result.getCurrentResults());
        List<Product> products = result.getProducts();
        for(Product product: products) {
          System.out.println("Product: " + product.getProductID() + ", " + product.getProductName() + ", " 
              + product.getProductURL() + ", " + product.getCurrency() + " " + product.getOnlinePrice());
        }
        
        System.out.println("\n*********** Generate deeplinks ************\n");
        String url = client.getDeepLink("http://www.play.com/Music/CD/4-/793109/Sea-Change-The-Choral-Music-Of/Product.html?ptsl=1&ob=Price&fb=0");
        System.out.println("Deep link: "+url);
    }
    catch(Exception ex) {
        ex.printStackTrace();
        System.err.println(ex.getMessage());
        System.exit(1);
    }
    
  }
}