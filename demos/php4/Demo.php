<?php
/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
/**
 * Path to BuyatAPIClient.php file.
 * You may edit this to actual path of the BuyatAPIClient.php file e.g $path = 'buyat_api_client-php4-1.0/'; 
 */
$path = '';

/**
 * Include the affiliate API client
 */
require_once($path.'BuyatAPIClient.php');
/**
 * The API Key passed here is  from our demo accounts. 
 *  You may want to change this own key.
 */

$client = new BuyatAPIClient('15a6f6e6f976c6db0608516c9d79d78b');

echo "\n*********** Echo ************\n";
echo $client->test_echo('Hello');

echo "\n*********** Level 1 Categories ************\n" ;
echo "List of level 1 categories: Show only  'category_id, category_name' fields \n";
$categories = $client->listLevel1Categories(); 
if(!isset($categories['error']))
{
  foreach($categories as $category)
  {
    echo "Category:  {$category['category_id']}, {$category['category_name']} \n";
  }
} 
else 
{
  echo  $categories['error'];
}

echo "\n*********** Level 2 Categories ************\n" ;
echo "List of level 2 categories: Show only  'category_id, category_name' fields for parent_category_id = 20  \n";
$categories = $client->listLevel2Categories(20); 
if(!isset($categories['error']))
{
  foreach($categories as $category)
  {
    echo "Category:  {$category['category_id']}, {$category['category_name']} \n";
  }
} 
else 
{
  echo  $categories['error'];
}

echo "\n***********  Category Tree ************\n" ;
echo "Category tree: Show only 'category_id, category_name, subcategories' fields  \n";
$categoryTree = $client->categoryTree(); 

if(!isset($categoryTree['error']))
{
  foreach($categoryTree as $category)
  {
    $cat = "Category: {$category['category_id']}, {$category['category_name']} \n"; 
    if($category['subcategories'])
    {
      $cat .= "\n\tsubcategories\n";
      foreach ($category['subcategories']  as $subCategory)
      {
        $cat .=  "\t\t{$subCategory['value']['category_id']}, {$subCategory['value']['category_name']} \n";
      }
      
    }
       
    echo $cat;
  }
} 
else 
{
  echo  $categoryTree['error'];
}

echo "\n*********** Programme List ************\n" ;
echo "List of programmes: Show only 'programme_id, programme_name, programme_url, has_feed' fields \n";
$programmes = $client->listProgrammes();
if(!isset($programmes['error']))
{
  foreach($programmes as $programme)
  {
    echo "Programme:  {$programme['programme_id']}, {$programme['programme_name']}, {$programme['programme_url']}, {$programme['has_feed']} \n";
  }
} 
else 
{
  echo  $programmes['error'];
}

echo "\n*********** Get Programme Info ************\n";
echo "Programme info for  programme_id ='115' : Show only 'programme_id, programme_name, programme_url, has_feed' fields \n";
$programme = $client->getProgramme(115);
if(!isset($programme['error']))
{
  echo  "Got programme: {$programme['programme_id']}, {$programme['programme_name']}, {$programme['programme_url']}, {$programme['has_feed']}  \n";
} 
else 
{
  echo  $programme['error'];
}

echo "\n*********** Feed List ************\n" ;
echo "List of feeds: Show only  'feed_id, feed_name, number_of_products, last_updated' fields \n";
$feeds = $client->listFeeds();

if(!isset($feed['error']))
{
  foreach($feeds as $feed)
  {
    echo "Feed:  {$feed['feed_id']}, {$feed['feed_name']}, {$feed['number_of_products']}, {$feed['last_updated']} \n";
  }
} 
else 
{
  echo  $feed['error'];
}

echo "\n*********** Get Feed Info ************\n";
echo "Feed info for  feed_id ='367' : Show only  'feed_id, feed_name, number_of_products, last_updated' fields \n";
$feed = $client->getFeed(367);
if(!isset($feed['error']))
{
  echo "Feed: {$feed['feed_id']}, {$feed['feed_name']}, {$feed['number_of_products']}, {$feed['last_updated']} \n";
} 
else 
{
  echo  $feed['error'];
}

echo "\n*********** Feed Download URL ************\n" ;
echo "Feed URL for feed 367  using defaults for optional values except setting  \n";
$feed = $client->getFeedUrl(367) ;
if(is_string($feed))
{
  echo "Got url: $feed \n"; 
} 
elseif(isset($feed['error'])) 
{
  echo  $feed['error'];
}

echo "\nFeed URL for feed 367, format='XML', start=0, perpage=50, lid='BuyAtDemo', use_https='yes', reverse_map_xml='yes'   \n";
$feed = $client->getFeedUrl(367, 'XML', 0, 50, 'BuyAtDemo', 'yes') ;
if(is_string($feed))
{
  echo "Got url: $feed \n"; 
} 
elseif(isset($feed['error'])) 
{
  echo  $feed['error'];
}
echo "\n*********** Product Search ************\n";
echo "Show only 'product_id, product_name, product_url, price' fields \n";
$products = $client->searchProducts('ipod'); 
if(!isset($products['error']))
{
  echo "Searching for ipod...\n"; 
  echo "Total Results found $products[total_results] \n";
  echo "Retrieved $products[current_results] \n"; 
  
  foreach($products['products'] as $product)
  {
    echo "\nProduct: {$product['product_id']}, {$product['product_name']} , {$product['product_url']}, {$product['currency_symbol']} {$product['currency']} \n"; 
  }
} 
else 
{
  echo  $products['error'];
}

echo "\n*********** Get Product Info ************\n";
echo "Product info for  product_id ='115' : Show only 'product_id, product_name, product_url, price' fields \n";
$product = $client->getProduct(119426388);
if(!isset($product['error']))
{
  echo "GotProduct:  {$product['product_id']}, {$product['product_name']} , {$product['product_url']}, {$product['currency_symbol']} {$product['currency']} \n"; 
} 
else 
{
  echo  $product['error'];
}
 
echo "\n*********** Create DeepLink ************\n";
echo "DeepLink for  'http://www.virginmobile.com/vm/home.do' \n";
$url = $client->createDeeplink('http://www.virginmobile.com/vm/home.do');
if(is_string($url))
{
  echo  "Got url: $url \n"; 
} 
elseif(isset($url['error'])) 
{
  echo  $url['error'];
}
