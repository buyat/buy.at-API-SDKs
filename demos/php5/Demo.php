<?php
/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
/**
 * Path to BuyatAPIClient.php and Entities.php files.
 * You may edit this to actual path of the files e.g $path = 'buyat_api_client-php5-1.0/'; 
 * 
 */
$path = '';

/**
 * Include the affiliate API client
 */
require_once($path.'BuyatAPIClient.php');
require_once($path.'Entities.php');

try
{
  /**
   * The API Key passed here is  from our demo accounts. 
   *  You need to change this to your own key.
   */
  $client = new BuyatAPIClient('15a6f6e6f976c6db0608516c9d79d78b');
  
  echo "\n*********** Echo ************\n";
  echo $client->test_echo('Hello');
  
  echo "\n*********** Level 1 Categories ************\n" ;
  echo "List of level 1 categories: Show only  'category_id, category_name' fields \n";
  $categories = $client->listLevel1Categories();
  foreach($categories as $category)
  {
    echo "Category:  {$category->getCategoryId()}, {$category->getCategoryName()} \n";
  }
  
  echo "\n*********** Level 2 Categories ************\n" ;
  echo "List of level 2 categories: Show only  'category_id, category_name' fields for parent_category_id = 20  \n";
  $categories = $client->listLevel2Categories(20); 
  foreach($categories as $category)
  {
    echo "Category:  {$category->getCategoryId()}, {$category->getCategoryName()} \n";
  }
  
  echo "\n***********  Category Tree ************\n" ;
  echo "Category tree: Show only 'category_id, category_name, subcategories' fields  \n";
  $categoryTree = $client->categoryTree();
  
  foreach($categoryTree as $category)
  {
    $cat = "Category: {$category->getCategoryId()}, {$category->getCategoryName()}"; 
    if($category->getSubcategories())
    {
      $cat .= "\n\tsubcategories\n";
      foreach ($category->getSubcategories() as $subCategory)
      {
        $cat .=  "\t\t{$subCategory->getCategoryId()}, {$subCategory->getCategoryName()} \n"; 
      }
      
    }
       
    echo $cat;
  }
  
  echo "\n*********** Programme List ************\n" ;
  echo "List of programmes: Show only 'programme_id, programme_name, programme_url, has_feed' fields \n";
  $programmes = $client->listProgrammes();
  foreach($programmes as $programme)
  {
    echo "Programme:  {$programme->getProgrammeID()}, {$programme->getProgrammeName()}, {$programme->getProgrammeUrl()}, {$programme->getHasFeed()} \n"; 
  }
  
  echo "\n*********** Get Programme Info ************\n";
  echo "Programme info for  programme_id ='115' : Show only 'programme_id, programme_name, programme_url, has_feed' fields \n";
  $programme = $client->getProgramme(115);
  echo  "Got programme: {$programme->getProgrammeID()}, {$programme->getProgrammeName()}, {$programme->getProgrammeUrl()}, {$programme->getHasFeed()} \n"; 
  
  echo "\n*********** Feed List ************\n" ;
  echo "List of feeds: Show only  'feed_id, feed_name, number_of_products, last_updated' fields \n";
  $feeds = $client->listFeeds();
  
  foreach($feeds as $feed)
  {
    echo "Feed:  {$feed->getFeedID()}, {$feed->getFeedName()}, {$feed->getNumberOfProducts()}, {$feed->getLastUpdated()}  \n";
  }
  
  echo "\n*********** Get Feed Info ************\n";
  echo "Feed info for  feed_id ='367' : Show only  'feed_id, feed_name, number_of_products, last_updated' fields \n";
  $feed = $client->getFeed(367);
  echo "Feed: {$feed->getFeedID()}, {$feed->getFeedName()}, {$feed->getNumberOfProducts()}, {$feed->getLastUpdated()}  \n";
  
  echo "\n*********** Feed Download URL ************\n" ;
  echo "Feed URL for feed 367  using defaults for optional values except setting  \n";
  echo "Got url: {$client->getFeedUrl(367)} \n";; 
  
  echo "\nFeed URL for feed 367, format='XML', start=0, perpage=50, lid='BuyAtDemo', use_https='yes', reverse_map_xml='yes'   \n";
  echo "Got url: {$client->getFeedUrl(367, 'XML', 0, 50, 'BuyAtDemo', 'yes')}  \n"; 
  
  echo "\n*********** Product Search ************\n";
  echo "Show only 'product_id, product_name, product_url, price' fields \n";
  $products = $client->searchProducts('ipod');
  echo "Searching for ipod...\n"; 
  echo "Total Results found $products[total_results] \n";
  echo "Retrieved $products[current_results] \n"; 
  
  foreach($products['products'] as $product)
  {
    echo "\nProduct: {$product->getProductId()}, {$product->getProductName()}, {$product->getProductURL()},  {$product->getCurrency()} {$product->getOnlinePrice()} \n";  
  }
  
  echo "\n*********** Get Product Info ************\n";
  echo "Product info for  product_id ='115' : Show only 'product_id, product_name, product_url, price' fields \n";
  $product = $client->getProduct(119426388);
  echo "GotProduct:  {$product->getProductId()}, {$product->getProductName()}, {$product->getProductURL()},  {$product->getCurrency()} {$product->getOnlinePrice()} \n";  
   
  
  echo "\n*********** Create DeepLink ************\n";
  echo "DeepLink for  'http://www.virginmobile.com/vm/home.do' \n";
  echo  "Got url: {$client->createDeeplink('http://www.virginmobile.com/vm/home.do')} \n"; 
}   
   
catch (BuyatException  $e)
{
  echo $e->getMessage(); 
}
   