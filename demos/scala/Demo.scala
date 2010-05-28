import buyat.api._

/**
 * Demos the buy.at Scala SDK.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
object Demo extends Application {
  try {
    var api = new BuyatAPI("01-fd288e15a739c35276a15d73a227dcc7")

    print("testing echo:\n\n")
    print(api.echo("testing")+"\n\n")

    print("testing list programmes:\n\n")
    var programmes = api.getProgrammes()
    for(programme <- programmes) yield {
      println(programme.programmeId + " " + programme.programmeName + " " + programme.programmeURL + " " + programme.hasFeed)
    }

    print("testing programme info:\n\n")
    var programme = api.getProgrammeInfo(301)
    println(programme.programmeId + " " + programme.programmeName + " " + programme.programmeURL + " " + programme.hasFeed)

    print("testing list feeds:\n\n")
    var feeds = api.getFeeds()
    for(feed <- feeds) yield {
      println(feed.feedId + " " + feed.feedName + " " + feed.programmeId + " " + feed.numberOfProducts + " " + feed.lastUpdated)
    }

    print("testing feed info:\n\n")
    var feed = api.getFeedInfo(1)
    println(feed.feedId + " " + feed.feedName + " " + feed.programmeId + " " + feed.numberOfProducts + " " + feed.lastUpdated)

    print("testing deeplink:\n\n");
    var url = api.getDeepLink("http://hmv.com/hmvweb/displayProductDetails.do?sku=952760&WT.mc_id=101450&batuid=1000")
    println(url)

    print("testing product info:\n\n")
    var product = api.getProductInfo(116966505)
    println(product.productId + " " + product.productSKU + " " + product.productURL + " " + product.productName +
            product.brandName + " " + product.description + " " + product.onlinePrice + " " + product.currency + " " +
            product.currencySymbol + " " + product.programmeId + " " + product.programmeName + " " + product.programmeURL +
            " " + product.level1CategoryId + " " + product.level1CategoryName + " " + product.level2CategoryId + " " +
            product.level2CategoryName + " " + product.feedId + " " + product.feedName)

    print("testing list level 1 categories:\n\n")
    var categories = api.getLevel1Categories()
    for(category <- categories) yield {
      println(category.categoryId + " " + category.categoryName)
    }

    print("testing list level 2 categories:\n\n")
    categories = api.getLevel2Categories(20)
    for(category <- categories) yield {
      println(category.categoryId + " " + category.categoryName)
    }

    print("testing category tree:\n\n")
    categories = api.getCategoryTree()
    for(category <- categories) yield {
      println(category.categoryId + " " + category.categoryName)
      for(subcategory <- category.subcategories) {
        println("\t" + " " + subcategory.categoryId + " " + subcategory.categoryName + " (" + subcategory.parentCategoryId + " " + subcategory.parentCategoryName + ")")
      }
    }

    print("testing product search\n\n")
    var searchResult = api.searchProducts("ipod", 1, 10, List(301), Nil, Nil, Nil, 0, 0, true, null, Relevance, Desc)
    for(p <- searchResult.products) yield {
      println(p.productId + " " + p.productName + " " + p.productSKU)
    }

    print("testing product search with criteria:\n\n")
    var criteria = new ProductSearchCriteria
    criteria.query = "black"
    criteria.includeAdult = true
    result = api.searchProducts(criteria)
    for(p <- result.products) yield {
      println(p.productId + " " + p.productName + " " + p.productSKU)
    }

    print("testing get feed url\n\n")
    println(api.getFeedURL(367, FeedXML))
    print("\n\n")

    print("testing get feed url from criteria\n\n")
    var fc = new FeedCriteria
    fc.feedId = 367
    fc.feedFormat = FeedCSV
    println(api.getFeedURL(fc))

  } catch {
    case apiEx:Exception => println("API Exception: " + apiEx.getMessage())
    case ex:Exception => println("General Exception: " + ex.getMessage())
    case _ => println("Unknown error")
  }
}