using System;
using System.Collections.Generic;
using System.Text;
using BuyAtAPI;

namespace BuyAtAPIDemo
{
    public class Demo
    {
        public static void Main()
        {
            string apiKey = "15a6f6e6f976c6db0608516c9d79d78b";

            BuyAtAPIClient client = new BuyAtAPIClient(apiKey);

            Console.WriteLine("*********** Product Search ************");
            ProductResultSet result = client.SearchProducts("ipod");
            Console.WriteLine("Found " + result.TotalResults + " results");
            foreach (Product product in result.Products)
            {
                Console.WriteLine("Product: " + product.ProductID + ", " + product.ProductName + ", "
                    + product.ProductURL + ", " + product.Currency + " " + product.OnlinePrice);
            }

            Console.WriteLine("*********** List Level 1 Categories ************");
            List<Category> categories = client.ListLevel1Categories();
            foreach (Category cat in categories)
            {
                Console.WriteLine("Category: " + cat.CategoryID + ", " + cat.CategoryName);
            }

            Console.WriteLine("*********** List Level 2 Categories ************");
            List<Category> subCategories = client.ListLevel2Categories(categories[0].CategoryID);
            foreach (Category subCat in subCategories)
            {
                Console.WriteLine("Sub-category: " + subCat.CategoryID + ", " + subCat.CategoryName);
                Console.WriteLine("Parent: " + subCat.ParentCategoryID + ", " + subCat.ParentCategoryName);
            }

            Console.WriteLine("*********** Category Tree ************");
            List<CategoryTree> categoryTrees = client.GetCategoryTree();
            foreach (CategoryTree cat in categoryTrees)
            {
                Console.WriteLine("Category: " + cat.CategoryID + ", " + cat.CategoryName);
                foreach (CategoryTree subCat in cat.Subcategories)
                {
                    Console.WriteLine("Sub-category: " + subCat.CategoryID + ", " + subCat.CategoryName);
                }
            }

            Console.WriteLine("*********** List Programmes ************");
            List<Programme> programmes = client.ListProgrammes();
            foreach (Programme prog in programmes)
            {
                Console.WriteLine("Programme: " + prog.ProgrammeID + ", " + prog.ProgrammeName + ", "
                                  + prog.ProgrammeURL + ", " + prog.HasFeed);
            }

            Console.WriteLine("*********** List Feeds ************");
            List<Feed> feeds = client.ListFeeds();
            foreach (Feed feed in feeds)
            {
                Console.WriteLine("Feed: " + feed.FeedID + ", " + feed.FeedName + ", " + feed.NumberOfProducts
                                  + ", " + feed.LastUpdated);
            }

            Console.WriteLine("*********** Get feed download URL ************");
            // Get feed URL for feed 367 in CSV format, using defaults for optional values except setting link id to "mylid"
            string feedURL = client.GetFeedURL(367, BuyAtAPIClient.FeedFormat.CSV, 0, 10, -1, -1, -1, null, "mylid", false, false, false);
            Console.WriteLine(feedURL);

            // Get feed URL for feed 367 in gzipped XML format, with level 1 category id = 20, level 2 programme category set 
            // to "Baby", using HTTPS, reverse mapping the XML, and restricting the results to Bestsellers  
            string[] progCategories = { null, "Baby" };
            string feedURL2 = client.GetFeedURL(367, BuyAtAPIClient.FeedFormat.XMLGZIP, 0, 10, 20, -1, -1, progCategories, null, true, true, true);
            Console.WriteLine(feedURL2);

            Console.WriteLine("*********** Create DeepLink ************");
            string url = client.CreateDeepLink("http://www.play.com/Music/CD/4-/793109/Sea-Change-The-Choral-Music-Of/Product.html?ptsl=1&ob=Price&fb=0");
            Console.WriteLine("Deep link: " + url);

            Console.WriteLine("*********** Get Product Info ************");
            Product product2 = client.GetProductInfo(118949289);
            Console.WriteLine("Product: " + product2.ProductID + ", " + product2.ProductName + ", "
                              + product2.ProductURL + ", " + product2.Currency + " " + product2.OnlinePrice);

            Console.WriteLine("*********** Get Programme Info ************");
            Programme programme = client.GetProgrammeInfo(115);
            Console.WriteLine("Programme: " + programme.ProgrammeID + ", " + programme.ProgrammeName + ", "
                              + programme.ProgrammeURL + ", " + programme.HasFeed);

            Console.WriteLine("*********** Get Feed Info ************");
            Feed feed2 = client.GetFeedInfo(196);
            Console.WriteLine("Feed: " + feed2.FeedID + ", " + feed2.FeedName + ", " + feed2.NumberOfProducts
                              + ", " + feed2.LastUpdated);

        }
    }
}
