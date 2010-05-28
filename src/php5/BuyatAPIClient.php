<?php
/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
/**
 * Path to Entities.php file.
 */
$path = '';

/**
 * Include Buyat Retail Objects
 */
require_once($path.'Entities.php');

/**
 * Buyat API client library.
 */

class BuyatAPIClient
{
  /**
   * Your buy.at API key.
   * You may edit your key into this variable, or pass your key to the
   * constructor of this class.
   *
   * @var string
   */

  protected $m_api_key = '';

   /**
   * Show error messages 
   * If true it will show error message and code
   * If false it will only show error code as a message
   *
   * @var string
   */

  protected $m_allow_error = true;
  
  /**
   * API HTTP client.
   *
   * @var BuyatAPICore
   */

  protected $m_api_core = null;

  /**
   * Constructor.
   *
   * @param string $api_key Your API key (optional)
   */

  public function __construct($api_key = null)
  {
    if(!is_null($api_key))
    {
      $this->m_api_key = $api_key;
    }

    $this->m_api_core = new BuyatAPICore();
  }

  /**
   * buyat.affiliate.product.search
   *
   * @param string $query
   * @param integer $page
   * @param integer $perPage
   * @param string $programmeIDs
   * @param string $excludedProgrammeIds
   * @param string $excludedProgrammeCategoryIds
   * @param string $feedIds
   * @param integer $level1CategoryId
   * @param integer $level2CategoryId
   * @param boolean $includeAdult
   * @param string $lid
   * @param string $sort
   * @param string $sortOrder
   * @return ProductResultSet
   */

  public function searchProducts($query = null, $page = 1, $perPage = 10,$sort ='relevance', $sortOrder = 'desc', $programmeIDs = null, $excludedProgrammeIds = null, $excludedProgrammeCategoryIds = null, $feedIds = null,$level1CategoryId = null, $level2CategoryId =  null, $includeAdult = 'n', $lid = null)
  {
    $args = array('page' => $page, 'perpage' => $perPage, 'sort' => $sort, 'sortorder' => $sortOrder, 'include_adult' => $includeAdult);
    
    if(isset($query))
    {
      $args['query'] = urlencode($query);
    }
  
    if(isset($programmeIDs))
    {
      $args['programme_ids'] = $programmeIDs;
    }
    
    if(isset($excludedProgrammeIds))
    {
      $args['excluded_programme_ids'] = $excludedProgrammeIds;
    }
  
    if(isset($excludedProgrammeCategoryIds))
    {
      $args['excluded_programme_category_ids'] = $excludedProgrammeCategoryIds;
    }
  
    if(isset($feedIds))
    {
      $args['feed_ids'] = $feedIds;
    }
  
    if(isset($level1CategoryId))
    {
      $args['level1_category_id'] = $level1CategoryId;
    }
  
    if(isset($level2CategoryId))
    {
      $args['level2_category_id'] = $level2CategoryId;
    }
  
    if(isset($lid))
    {
      $args['lid'] = $lid;
    }
    
    $search_results =  $this->m_api_core->api_call('buyat.affiliate.product.search', $this->m_api_key, $args, $this->m_allow_error);
   
    $products = array();
    $products['current_results'] = $search_results['current_results'];
    $products['total_results'] = $search_results['total_results'];
    $products['start'] = $search_results['start'];
    $products['limit'] = $search_results['limit'];
    $products['query'] = $search_results['query'];
   
    foreach ($search_results['products'] as $product)
    {
      $products ['products'][] = $this->getProductObject($product['value']);
    }

    return  $products;
  }


  /**
   * buyat.affiliate.product.search
   *
   * @param string $query
   * @param integer $page
   * @param integer $perPage
   * @param string $programmeIDs
   * @param string $excludedProgrammeIds
   * @param string $excludedProgrammeCategoryIds
   * @param string $feedIds
   * @param integer $level1CategoryId
   * @param integer $level2CategoryId
   * @param boolean $includeAdult
   * @param string $lid
   * @param string $sort
   * @param string $sortOrder
   * @return array 
   */

  public function searchProductsArray($query = null, $page = 1, $perPage = 10,$sort ='relevance', $sortOrder = 'desc', $programmeIDs = null, $excludedProgrammeIds = null, $excludedProgrammeCategoryIds = null, $feedIds = null,$level1CategoryId = null, $level2CategoryId =  null, $includeAdult = 'n', $lid = null)
  {
    $args = array('page' => $page, 'perpage' => $perPage, 'sort' => $sort, 'sortorder' => $sortOrder, 'include_adult' => $includeAdult);
    
    if(isset($query))
    {
      $args['query'] = urlencode($query);
    }
  
    if(isset($programmeIDs))
    {
      $args['programme_ids'] = $programmeIDs;
    }
    
    if(isset($excludedProgrammeIds))
    {
      $args['excluded_programme_ids'] = $excludedProgrammeIds;
    }
  
    if(isset($excludedProgrammeCategoryIds))
    {
      $args['excluded_programme_category_ids'] = $excludedProgrammeCategoryIds;
    }
  
    if(isset($feedIds))
    {
      $args['feed_ids'] = $feedIds;
    }
  
    if(isset($level1CategoryId))
    {
      $args['level1_category_id'] = $level1CategoryId;
    }
  
    if(isset($level2CategoryId))
    {
      $args['level2_category_id'] = $level2CategoryId;
    }
  
    if(isset($lid))
    {
      $args['lid'] = $lid;
    }
    
    $search_results =  $this->m_api_core->api_call('buyat.affiliate.product.search', $this->m_api_key, $args, $this->m_allow_error);
 
    $products = array();
    $products['current_results'] = $search_results['current_results'];
    $products['total_results'] = $search_results['total_results'];
    $products['start'] = $search_results['start'];
    $products['limit'] = $search_results['limit'];
    $products['query'] = $search_results['query'];
   
    foreach ($search_results['products'] as $product)
    {
      $products ['products'][] = $product['value'];
    }

    return  $products;
  }
  
  /**
   * buyat.affiliate.category.listlevel1
   * 
   * @return array of Category Objects
   */

  public function listLevel1Categories()
  { 
    $response =  $this->m_api_core->api_call('buyat.affiliate.category.listlevel1', $this->m_api_key, null, $this->m_allow_error);
    
    $categories = array();
    foreach ($response['categories'] as $category)
    {
      $categories [] = $this->getCategory($category['value']);
    }

    return $categories;
  }
  
  
  /**
   * buyat.affiliate.category.listlevel1
   * 
   * @return array of Categories 
   */

  public function listLevel1CategoriesArray()
  { 
    $response =  $this->m_api_core->api_call('buyat.affiliate.category.listlevel1', $this->m_api_key, null, $this->m_allow_error);
    
    $categories = array();
    foreach ($response['categories'] as $category)
    {
      $categories [] = $category['value'];
    }

    return $categories;
  }
  
  /**
   * buyat.affiliate.category.listlevel2
   * 
   * @param integer $level1CategoryId
   * @return array of Category Objects
   */

  public function listLevel2Categories($level1CategoryId)
  {
    if(!isset($level1CategoryId))
    {
      throw new BuyatException('Missing parameter \'level1_category_id\' ' );
    }

    $args = array('level1_category_id' => $level1CategoryId);
    
    $response =  $this->m_api_core->api_call('buyat.affiliate.category.listlevel2', $this->m_api_key, $args, $this->m_allow_error);
    
    $categories = array();
    if(isset($response['categories']))
    {
      foreach ($response['categories'] as $category)
      {
        $categories [] = $this->getCategory($category['value']);
      }
    }
    return $categories;
  }
  
  /**
   * buyat.affiliate.category.listlevel2
   * 
   * @param integer $level1CategoryId
   * @return array of Categories 
   */

  public function listLevel2CategoriesArray($level1CategoryId)
  {
    if(!isset($level1CategoryId))
    {
      throw new BuyatException('Missing parameter \'level1_category_id\' ');
    }
    
    $args = array('level1_category_id' => $level1CategoryId);
    
    $response =  $this->m_api_core->api_call('buyat.affiliate.category.listlevel2', $this->m_api_key, $args, $this->m_allow_error);
    
    $categories = array();
    if(isset($response['categories']))
    {
      foreach ($response['categories'] as $category)
      {
        $categories [] = $category['value'];
      }
    }
    return $categories;
  }
  
  /**
   * buyat.affiliate.category.tree
   * 
   * @return array of Category Objects 
   */

  public function categoryTree()
  {
   
    $response =  $this->m_api_core->api_call('buyat.affiliate.category.tree', $this->m_api_key, null, $this->m_allow_error);

    $categories = array();
    foreach ($response['categories'] as $category)
    {
      $categories [] = $this->getCategory($category['value']);
    }

    return $categories;
   
  }  

  /**
   * buyat.affiliate.category.tree
   * 
   * @return array of Categories as tree 
   */

  public function categoryTreeArray()
  {
    $response =  $this->m_api_core->api_call('buyat.affiliate.category.tree', $this->m_api_key, null, $this->m_allow_error);
    
    $categories = array();
    foreach ($response['categories'] as $category)
    {
      $categories [] = $category['value'];
    }

    return $categories;
   
  }  
  
  /**
   * buyat.affiliate.programme.list
   * 
   * @return array of Programme Objects 
   */

  public function listProgrammes()
  {
    $response =  $this->m_api_core->api_call('buyat.affiliate.programme.list', $this->m_api_key, null, $this->m_allow_error);
    
    $programmes = array();
    foreach ($response['programmes'] as $programme)
    {
      $programmes [] = $this->getProgrammeObject($programme['value']);
    }
    
    return $programmes;
  } 
  
  /**
   * buyat.affiliate.programme.list
   * 
   * @return array of Programmes  
   */

  public function listProgrammesArray()
  {
    
    $response =  $this->m_api_core->api_call('buyat.affiliate.programme.list', $this->m_api_key, null, $this->m_allow_error);
    
    $programmes = array();
    foreach ($response['programmes'] as $programme)
    {
      $programmes [] = $programme['value'];
    }
    
    return $programmes;
  } 
  
  /**
   * buyat.affiliate.feed.list
   * 
   * @return array of Feed Objects 
   */

  public function listFeeds()
  {
    $response =  $this->m_api_core->api_call('buyat.affiliate.feed.list', $this->m_api_key, null, $this->m_allow_error);

    $feeds = array();
    foreach ($response['feeds'] as $feed)
    {
      $feeds [] = $this->getFeedObject($feed['value']);
    }
    
    return $feeds;
  } 
  
  /**
   * buyat.affiliate.feed.list
   * 
   * @return array of Feed  
   */

  public function listFeedsArray()
  {
    $response =  $this->m_api_core->api_call('buyat.affiliate.feed.list', $this->m_api_key, null, $this->m_allow_error);
       
    $feeds = array();
    foreach ($response['feeds'] as $feed)
    {
      $feeds [] = $feed['value'];
    }
    
    return $feeds;
  } 
  
  /**
   * buyat.affiliate.feed.geturl
   *
   * @param integer $feedId
   * @param string $format
   * @param integer $start
   * @param integer $perPage
   * @param string $lid
   * @param string $useHTPS
   * @param string $reverseMapXML
   * @param string $bestseller
   * @param integer $level1CategoryId
   * @param integer $level2CategoryId
   * @param integer $programmeCategoryId
   * @param string $level1
   * @param string $level2
   * @param string $level3
   * @param string $level4
   * @param string $level5
   * @param string $level6
   * @param string $level7
   * @param string $level8
   * @return string
   */ 
  
  public function  getFeedUrl($feedId, $format = null, $start = null, $perPage = null, $lid = null, $useHTPS = null, $reverseMapXML = null, $bestseller = null, $level1CategoryId = null, $level2CategoryId =  null, $programmeCategoryId = null, $level1 = null, $level2 = null, $level3 = null, $level4 = null, $level5 = null, $level6 = null, $level7 = null, $level8 = null )
  {
    
    $args = array('feed_id' => $feedId);
  
    if(isset($format))
    {
      $args['format'] = $format;
      
    }
    if(isset($start))
    {
      $args['start'] = $start;
      
    }
    
    if(isset($perPage))
    {
      $args['perpage'] = $perPage;
    }
    
    if(isset($useHTPS))
    {
      $args['use_https'] = $useHTPS;
    }
    
    if(isset($reverseMapXML))
    {
      $args['reverse_map_xml'] = $reverseMapXML;
    }
    
    if(isset($lid))
    {
      $args['lid'] = $lid;
    }
    
    if(isset($bestseller))
    {
      $args['bestseller'] = $bestseller;
    }
    
    if(isset($level1CategoryId))
    {
      $args['level1_category_id'] = $level1CategoryId;
    }
    
    if(isset($level2CategoryId))
    {
      $args['level2_category_id'] = $level2CategoryId;
    }
  
    if(isset($programmeCategoryId))
    {
      $args['programme_category_id'] = $programmeCategoryId ;
    }
    
    for($i=1; $i <= 8 ; $i++)
    {
      $level = 'level'.$i ;
      if(isset($$level))
      {
         $args[$level] = $$level ;
      }
    }
    
    $response =  $this->m_api_core->api_call('buyat.affiliate.feed.geturl', $this->m_api_key, $args, $this->m_allow_error);
    
    return $response['url'];
  }
 
  /**
   * buyat.affiliate.linkengine.create
   * 
   * @return string
   */

  public function  createDeeplink($url)
  {
    
    $args = array('url' => $url);
     
    $response =  $this->m_api_core->api_call('buyat.affiliate.linkengine.create', $this->m_api_key, $args, $this->m_allow_error);
     
    return $response[0]['deep_link'];
  }
 
  /**
   * buyat.affiliate.product.info
   * 
   * @param integer $productId
   * @return object 
   */

  public function  getProduct($productId)
  {
    $args = array('product_id' => $productId);
     
    $response =  $this->m_api_core->api_call('buyat.affiliate.product.info', $this->m_api_key, $args, $this->m_allow_error);
    
    return  $this->getProductObject($response['value']);
   
  }
 
  /**
   * buyat.affiliate.programme.info
   * 
   * @param integer $programmeId
   * @return string
   */

  public function  getProgramme($programmeId)
  {
    $args = array('programme_id' => $programmeId);
     
    $response =  $this->m_api_core->api_call('buyat.affiliate.programme.info', $this->m_api_key, $args, $this->m_allow_error);
    
    return $this->getProgrammeObject($response[0]['value']);
  }

  /**
   * buyat.affiliate.feed.info
   * 
   * @param integer $feedId
   * @return object 
   */

  public function  getFeed($feedId)
  {
    $args = array('feed_id' => $feedId);
     
    $response =  $this->m_api_core->api_call('buyat.affiliate.feed.info', $this->m_api_key, $args, $this->m_allow_error);
    
    return  $this->getFeedObject($response['value']);
   
  }
  
  /**
   * Populate product object 
   *
   * @param array $product
   * @return object 
   */
  protected function getProductObject($product)
  {
    if(empty($product))
    {
      throw new BuyatException('Malformed response from server');
    }
    $productObject = new Product();
  
    $productObject->setProductID($product['product_id']);
    $productObject->setProductSKU($product['product_sku']);
    $productObject->setProductURL($product['product_url']);
    $productObject->setProductName($product['product_name']);
    $productObject->setBrandName($product['brand_name']);
    $productObject->setDescription($product['description']);
    $productObject->setOnlinePrice($product['online_price']);
    $productObject->setCurrency($product['currency']);
    $productObject->setCurrencySymbol($product['currency_symbol']);
    $productObject->setImageURL($product['image_url']);
    $productObject->setProgrammeName($product['programme_name']);
    $productObject->setProgrammeURL($product['programme_url']);
    $productObject->setProgrammeID($product['programme_id']);
    $productObject->setLevel1CategoryID($product['level1_category_id']);
    $productObject->setLevel1CategoryName($product['level1_category_name']);
    $productObject->setLevel2CategoryID($product['level2_category_id']);
    $productObject->setLevel2CategoryName($product['level2_category_name']);
    $productObject->setFeedID($product['feed_id']);
    $productObject->setFeedName($product['feed_name']);
    
    return  $productObject;
  }
  
    /**
   * Populate category object 
   *
   * @param array $category
   * @return object Category
   */

  protected function getCategory($category)
  {
    if(empty($category))
    {
      throw new BuyatException('Malformed response from server');
    }

    $categoryObject = new Category();

    $categoryObject->setCategoryID($category['category_id']);
    $categoryObject->setLevel($category['level']);
    $categoryObject->setCategoryName($category['category_name']);
    if(isset($category['subcategories']))
    {
      $vSubCategories = array();
      foreach ($category['subcategories'] as $vSubCategory)
      {
        $vSubCategories [] = $this->getCategory($vSubCategory['value']);
      }
      
      $categoryObject->setSubcategories($vSubCategories);
  
    }
    
    if(isset($category['parent_category_id']))
    {
      $categoryObject->setParentCategoryID($category['parent_category_id']);
    }
    if(isset($category['parent_category_name']))
    {
      $categoryObject->setParentCategoryName($category['parent_category_name']);
    }

    return $categoryObject;
    
  }

    /**
   * Populate programme object 
   *
   * @param array $programme
   * @return object
   */

  protected function getProgrammeObject($programme)
  {
    if(empty($programme))
    {
      throw new BuyatException('Malformed response from server');
    }   
  
    $programmeObject = new Programme();

    $programmeObject->setProgrammeID($programme['programme_id']);
    $programmeObject->setProgrammeName($programme['programme_name']);
    $programmeObject->setProgrammeURL($programme['programme_url']);
    $programmeObject->setHasFeed($programme['has_feed']);

    return $programmeObject;

  }
   
    /**
   *  Populate feed object 
   *
   * @param array $feed
   * @return object
   */

  protected function getFeedObject($feed)
  {
    if(empty($feed))
    {
      throw new BuyatException('Malformed response from server');
    } 

    $feedObject = new Feed();

    $feedObject->setFeedID($feed['feed_id']);
    $feedObject->setFeedName($feed['feed_name']);
    $feedObject->setProgrammeID($feed['programme_id']);
    $feedObject->setProgrammeName($feed['programme_name']);
    $feedObject->setProgrammeURL($feed['programme_url']);
    $feedObject->setNumberOfProducts($feed['number_of_products']);
    $feedObject->setLastUpdated($feed['last_updated']);

    return $feedObject;
     
  }
  
  /**
   * Test function. Echoes a parameter back to the client.
   *
   * @params string $message The message to echo.
   * @return string
   *
   * @throws BuyatException
   */

  public function test_echo($message)
  {
    $result = $this->m_api_core->api_call('buyat.test.echo', $this->m_api_key, array('message' => $message));
    if(array_key_exists('message', $result))
    {
      return $result['message'];
    }
    else
    {
      throw new BuyatException('Invalid response from API');
    }
  }
  
}

/**
 * Handles the conversation with the API at the HTTP level.
 */

class BuyatAPICore
{
  /**
   * API host name.
   *
   * @var string
   */

  private $m_api_host = '@API_HOST@';

  /**
   * Path to the API endpoint.
   *
   * @var string
   */
  private $m_api_path = '@API_PATH@';

  /**
   * Constructor.
   *
   * @param string $api_host API host name (optional).
   * @param string $api_path Path to the API endpoint (optional).
   */

  public function __construct($api_host = null, $api_path = null)
  {
    if(!is_null($api_host))
    {
      $this->m_api_host = $api_host;
    }
    if(!is_null($api_path))
    {
      $this->m_api_path = $api_path;
    }
  }

  /**
   * Call the Buyat API.
   *
   * @param string $action The Buyat API function name.
   * @param string $api_key Affiliate's API key.
   * @param array $args An associative array of parameters to pass to the API function.
   * @param boolean $allow_errors Whether or not to pass the response back even if it was an error.
   * @return array The API's response as an associative array.
   */

  public function api_call($action, $api_key, $args, $allow_errors = false)
  {
    if(($response = $this->perform_post($this->m_api_host, $this->m_api_path, $this->construct_api_request($action, $api_key, $args))) === false)
    {
      throw new BuyatException('Unable to contact API');
    }

    $response = unserialize($response);

    if(!is_array($response))
    {
      throw new BuyatException('Invalid response from API');
    }

    if(!$allow_errors && array_key_exists('error_code', $response))
    {
      throw new BuyatException("Error code from API: $response[error_code]");
    
    }
    elseif(array_key_exists('error_code', $response))
    {
      if(array_key_exists('error_message', $response))
      {
        throw new BuyatException("Error from API: $response[error_message],  Error code: $response[error_code]"); 
      }
      else
      {
        throw new BuyatException("Error code from API: $response[error_code]");
      }
    }
    return $response;
  }

  /**
   * Construct a request that can be POSTed to the Buyat API.
   * 
   * @param string $action Buyat API function name.
   * @param string $api_key Affiliate's API key.
   * @param array $args An associative array of parameters to pass to the API function.
   * @return string A fragment of XML that can be POSTed to the Buyat API.
   */

  private function construct_api_request($action, $api_key, $args)
  {
    $request = "<request><action>$action</action><parameters><api_key>$api_key</api_key>";
    
    if(is_array($args))
    {
      foreach($args AS $key => $value)
      {
        $request .= "<$key>$value</$key>";
      }
      
    }
    
    $request .= '</parameters></request>';
    return $request;
  }

  /** 
   * POST a payload to a host/path.
   *
   * @param string $host The hostname of the remote server to POST to.
   * @param string $path The request path on the remote server.
   * @param string $payload The content to POST.
   * @return mixed The server's response (minus HTTP gubbins) on success; boolean false on failure.
   */

  private function perform_post($host, $path, $payload)
  {
    $request = "POST $path HTTP/1.0\r\nHost: $host\r\n";
    $request .= "Content-Type: application/x-www-form-urlencoded; charset=iso-8859-1\r\n";
    $request .= 'Content-Length: '.strlen($payload)."\r\nUser-Agent: RetailAPI\r\n\r\n$payload";

    $response = '';

    if(($socket = @fsockopen($host, 80, $errno, $errstr, 10)) === false)
    {
      return false;
    }

    fwrite($socket, $request);

    while(!feof($socket))
    {
      $response .= fgets($socket, 1160);
    }

    fclose($socket);
    $socket = null;
    $response = explode("\r\n\r\n", $response, 2);
    return $response[1];
  }
  
}

/**
 * Exceptions thrown by the buy.at API.
 */

class BuyatException extends Exception
{}
