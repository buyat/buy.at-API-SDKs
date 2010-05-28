<?php

/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

/**
 * Represents a product from a buy.at feed.
 *
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
class Product
{
  
  /**
   * Product ID
   */
  protected $mProductId;

  /**
   * SKU
  */
  protected $mProductSku;
  
  /**
   * Tracking deeplink URL.
   */
  protected $mProductURL;
  
  /**
  * Product name.
  */  
  protected $mProductName;
  
  /**
   * Brand name.
   */
  protected $mBrandName;  
  
  /**
   * Product description.
   */
  protected $mDescription;  
  
  /**
   * Price of the product.
   */
  protected $mOnlinePrice;
  
  /**
   * Three-letter currency code.
   */
  protected $mCurrency;
  
  /**
   * HTML-safe currency symbol.
   */
  protected $mCurrencySymbol;
  
  /**
   * URL of the product image.
   */
  protected $mImageUrl;

  /**
   * ID of programme this product belongs to
   */
  protected $mProgrammeId;
    
  /**
   * Name of programme this product belongs to
   */
  protected $mProgrammeName;
  
  /**
   * URL of programme this product belongs to
   */
  protected $mProgrammeUrl;
  
  /**
   * ID of level 1 category this product belongs to.
   */
  protected $mLevel1CategoryId;

  /**
   * Name of level 1 category this product belongs to.
   */  
  protected $mLevel1CategoryName;

  /**
   * ID of level 2 category this product belongs to.
   */  
  protected $mLevel2CategoryId;
    
  /**
   * Name of level 2 category this product belongs to.
   */  
  protected $mLevel2CategoryName;

  /**
   * ID of feed this product belongs to.
   */  
  protected $mFeedId;

  /**
   * Name of feed this product belongs to.
   */  
  protected $mFeedName;

  /**
   * Constructor
   *
   */
  public function __construct()
  {}
  
  /**
   * @return the productID
   */
  public function getProductId()
  {
    return $this->mProductId;
  }
  
  /**
   * @param integer $productId
   */
  public function setProductId($productId)
  {
    $this->mProductId = $productId;
  }
  
  /**
   * @return the productSKU
   */
  public function getProductSKU()
  {
    return $this->mProductSKU;
  }
  
  /**
   * @param string $productSKU
   */
  public function setProductSKU($productSKU)
  {
    $this->mProductSKU = $productSKU;
  }
  
  /**
   * @return the productURL
   */
  public function getProductURL()
  {
    return $this->mProductURL;
  }
  
  /**
   * @param string $productURL
   */
  public function setProductURL($productURL)
  {
    $this->mProductURL = $productURL;
  }
  
  /**
   * @return the productName
   */
  public function getProductName()
  {
    return $this->mProductName;
  }

  /**
   * @param string $productName
   */
  public function setProductName($productName)
  {
    $this->mProductName = $productName;
  }
  
  /**
   * @return the brandName
   */
  public function getBrandName()
  {
    return $this->mBrandName;
  }

  /**
   * @param string $vBrandName
   */
  public function setBrandName($vBrandName)
  {
    $this->mBrandName = $vBrandName;
  }
  
  /**
   * @return the description 
   */
  public function getDescription()
  {
    return $this->mDescription;
  }
  
  /**
   * @param string $vDescription
   */
  public function setDescription($vDescription)
  {
    $this->mDescription = $vDescription;
  }
   
  /**
   * @return the onlinePrice 
   */
  public function getOnlinePrice()
  {
    return $this->mOnlinePrice;
  }
  
  /**
   * @param string $onlinePrice
   */
  public function setOnlinePrice($onlinePrice)
  {
    $this->mOnlinePrice = $onlinePrice;
  }
    
  /**
   * @return the currency 
   */
  public function getCurrency()
  {
    return $this->mCurrency;
  }
  
  /**
   * @param float $currency
   */
  public function setCurrency($currency)
  {
    $this->mCurrency = $currency;
  }
   
  /**
   * @return the currencySymbol 
   */
  public function getCurrencySymbol()
  {
    return $this->mCurrencySymbol;
  }
  
  /**
   * @param string $currencySymbol
   */
  public function setCurrencySymbol($currencySymbol)
  {
    $this->mCurrencySymbol = $currencySymbol;
  }
   
  /**
   * @return the imageURL 
   */
  public function getImageURL()
  {
    return $this->mImageURL;
  }
  
  /**
   * @param string $imageURL
   */
  public function setImageURL($imageURL)
  {
    $this->mImageURL = $imageURL;
  }
  
  /**
   * @return the programmeID 
   */
  public function getProgrammeID()
  {
    return $this->mProgrammeID;
  }
  
  /**
   * @param integer $programmeID
   */
  public function setProgrammeID($programmeID)
  {
    $this->mProgrammeID = $programmeID;
  }
   
  /**
   * @return the programmeName 
   */
  public function getProgrammeName()
  {
    return $this->mProgrammeName;
  }
  
  /**
   * @param string $programmeName
   */
  public function setProgrammeName($programmeName)
  {
    $this->mProgrammeName = $programmeName;
  }
  
  /**
   * @return the programmeURL 
   */
  public function getProgrammeURL()
  {
    return $this->mProgrammeURL;
  }
  
  /**
   * @param string $programmeURL
   */
  public function setProgrammeURL($programmeURL)
  {
    $this->mProgrammeURL = $programmeURL;
  }
  
  /**
   * @return the level1CategoryID 
   */
  public function getLevel1CategoryID()
  {
    return $this->mLevel1CategoryID;
  }
  
  /**
   * @param integer $level1CategoryID
   */
  public function setLevel1CategoryID($level1CategoryID)
  {
    $this->mLevel1CategoryID = $level1CategoryID;
  }
    
  /**
   * @return the level1CategoryName 
   */
  public function getLevel1CategoryName()
  {
    return $this->mLevel1CategoryName;
  }
  
  /**
   * @param string $level1CategoryName
   */
  public function setLevel1CategoryName($level1CategoryName)
  {
    $this->mLevel1CategoryName = $level1CategoryName;
  }
    
  /**
   * @return the level2CategoryID 
   */
  public function getLevel2CategoryID()
  {
    return $this->mLevel2CategoryID;
  }
  
  /**
   * @param integer $level2CategoryID
   */
  public function setLevel2CategoryID($level2CategoryID)
  {
    $this->mLevel2CategoryID = $level2CategoryID;
  }
   
  /**
   * @return the level2CategoryName 
   */
  public function getLevel2CategoryName()
  {
    return $this->mLevel2CategoryName;
  }
  
  /**
   * @param integer $level2CategoryName
   */
  public function setLevel2CategoryName($level2CategoryName)
  {
    $this->mLevel2CategoryName = $level2CategoryName;
  }
   
  /**
   * @return the feedID 
   */
  public function getFeedID()
  {
    return $this->mFeedID;
  }
  
  /**
   * @param integer $feedID
   */
  public function setFeedID($feedID)
  {
    $this->mFeedID = $feedID;
  }
  
  /**
   * @return the feedName 
   */
  public function getFeedName()
  {
    return $this->mFeedName;
  }
  
  /**
   * @param string $feedName
   */
  public function setFeedName($feedName)
  {
    $this->mFeedName = $feedName;
  }
  
}

/**
 * Represents a buy.at category.
 * 
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
class Category
{
  
  /**
   * ID of this category
   */
  protected $mCategoryID;
 
  /**
   * Level of this category
   */
  protected $mLevel;
  
  /**
   *  Name of this category
   */
  protected $mCategoryName;
  
  /**
   * ID of parent category 
   */
  protected $mParentCategoryID;
  
  /**
   * Name of parent category
   */
  protected $mParentCategoryName;

  
  /**
   * Level2 categories
   */
  protected $mLevel2Categories = array();
  
  /**
   * Constructor
   *
   */
  public function __construct()
  {} 
   
  /**
   * @return the categoryID 
   */
  public function getCategoryID()
  {
    return $this->mCategoryID;
  }
  
  /**
   * @param integer $categoryID
   */
  public function setCategoryID($categoryID)
  {
    $this->mCategoryID = $categoryID;
  }
    
  /**
   * @return the level 
   */
  public function getLevel()
  {
    return $this->mLevel;
  }
  
  /**
   * @param string $level
   */
  public function setLevel($level)
  {
    $this->mLevel = $level;
  }
    
  /**
   * @return the categoryName 
   */
  public function getCategoryName()
  {
    return $this->mCategoryName;
  }
  
  /**
   * @param string $categoryName
   */
  public function setCategoryName($categoryName)
  {
    $this->mCategoryName = $categoryName;
  }
 
  /**
   * @return the parentCategoryID 
   */
  public function getParentCategoryID()
  {
    return $this->mParentCategoryID;
  }
  
  /**
   * @param integer $parentCategoryID
   */
  public function setParentCategoryID($parentCategoryID)
  {
    $this->mParentCategoryID = $parentCategoryID;
  } 
   
  /**
   * @return the parentCategoryName 
   */
  public function getParentCategoryName()
  {
    return $this->mParentCategoryName;
  }
  
  /**
   * @param string $parentCategoryName
   */
  public function setParentCategoryName($parentCategoryName)
  {
    $this->mParentCategoryName = $parentCategoryName;
  }
   
  /**
   * @return the level2Categories 
   */
  public function getSubcategories()
  {
    return $this->mLevel2Categories;
  }
  
  /**
   * @param array $level2Categories
   */
  public function setSubcategories($level2Categories)
  {
    $this->mLevel2Categories = $level2Categories;
  }
  
}

/**
 * Represents a buy.at programme.
 *
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
class Programme
{

  /**
   * Unique ID of the programme.
   */
  protected $mProgrammeID;
 
  /**
   * Name of the programme.
   */
  protected $mProgrammeName;
 
  /**
   * Tracking URL for the programme.
   */
  protected $mProgrammeURL;
 
  /**
   *  Whether or not this programme has feeds.
   */
  protected $mHasFeed;
  
  /**
   * Constructor
   *
   */
  public function __construct()
  {} 
   
  /**
   * @return the programmeID 
   */
  public function getProgrammeID()
  {
    return $this->mProgrammeID;
  }
  
  /**
   * @param integer $programmeID
   */
  public function setProgrammeID($programmeID)
  {
    $this->mProgrammeID = $programmeID;
  }
    
  /**
   * @return the programmeName 
   */
  public function getProgrammeName()
  {
    return $this->mProgrammeName;
  }
  
  /**
   * @param string $programmeName
   */
  public function setProgrammeName($programmeName)
  {
    $this->mProgrammeName = $programmeName;
  }
    
  /**
   * @return the programmeURL 
   */
  public function getProgrammeURL()
  {
    return $this->mProgrammeURL;
  }
  
  /**
   * @param string $programmeURL
   */
  public function setProgrammeURL($programmeURL)
  {
    $this->mProgrammeURL = $programmeURL;
  }
   
  /**
   * @return the hasFeed 
   */
  public function getHasFeed()
  {
    return $this->mHasFeed;
  }
  
  /**
   * @param boolean $hasFeed
   */
  public function setHasFeed($hasFeed)
  {
    $this->mHasFeed = $hasFeed;
  }
  
}

/**
 * Represents a buy.at feed.
 *
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
class Feed
{

  /**
   * Unique ID of the feed.
   */
  protected $mfeedID;
 
  /**
   * Unique ID of the programme.
   */
  protected $mFeedName;
 
  /**
   * Unique ID of the programme.
   */
  protected $mProgrammeID;
 
  /**
   * Name of programme this feed belongs to
   */
  protected $mProgrammeName;
 
  /**
   *  The programme object this feed belongs to.
   */
  protected $mProgrammeURL;
 
  /**
   * The number of products in the feed.
   */
  protected $mNumberOfProducts;
 
  /**
   * The last date this feed was updated.
   */
  protected $mLastUpdated;
  
  /**
   * Constructor
   *
   */
  public function __construct()
  {} 
   
  /**
   * @return the feedID 
   */
  public function getFeedID()
  {
    return $this->mFeedID;
  }
  
  /**
   * @param integer $feedID
   */
  public function setFeedID($feedID)
  {
    $this->mFeedID = $feedID;
  }
   
  /**
   * @return the feedName 
   */
  public function getFeedName()
  {
    return $this->mFeedName;
  }
  
  /**
   * @param string $feedName
   */
  public function setFeedName($feedName)
  {
    $this->mFeedName = $feedName;
  }
       
  /**
   * @return the programmeID 
   */
  public function getProgrammeID()
  {
    return $this->mProgrammeID;
  }
  
  /**
   * @param integer $programmeID
   */
  public function setProgrammeID($programmeID)
  {
    $this->mProgrammeID = $programmeID;
  }
    
  /**
   * @return the programmeName 
   */
  public function getProgrammeName()
  {
    return $this->mProgrammeName;
  }
  
  /**
   * @param string $programmeName
   */
  public function setProgrammeName($programmeName)
  {
    $this->mProgrammeName = $programmeName;
  }
    
  /**
   * @return the programmeURL 
   */
  public function getProgrammeURL()
  {
    return $this->mProgrammeURL;
  }
  
  /**
   * @param string $programmeURL
   */
  public function setProgrammeURL($programmeURL)
  {
    $this->mProgrammeURL = $programmeURL;
  }
   
  /**
   * @return the numberOfProducts 
   */
  public function getNumberOfProducts()
  {
    return $this->mNumberOfProducts;
  }
  
  /**
   * @param integer $numberOfProducts
   */
  public function setNumberOfProducts($numberOfProducts)
  {
    $this->mNumberOfProducts = $numberOfProducts;
  } 
  /**
   * @return the lastUpdated 
   */
  public function getLastUpdated()
  {
    return $this->mLastUpdated;
  }
  
  /**
   * @param datetime $lastUpdated
   */
  public function setLastUpdated($lastUpdated)
  {
    $this->mLastUpdated = $lastUpdated;
  }
        
}

