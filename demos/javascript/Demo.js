/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

var buyatDemo = {
  init: function()  {
  buyat.init('15a6f6e6f976c6db0608516c9d79d78b');
  },
  renderApiEcho: function(response) {
  var target = document.getElementById(buyat.targetElement);
  buyat.removeAllChildren(target);
  try {
    var response =  buyat.validateResponse(response); 
    var dl = document.createElement('dl');
    var dt = document.createElement('dt');
    dt.appendChild(document.createTextNode(response.message));
    dl.appendChild(dt);
    target.appendChild(dl);
  }  catch (buyatException){
      target.appendChild(document.createTextNode(buyatException.message ));
    }
  },
  renderProducts: function(response) {
    var target = document.getElementById(buyat.targetElement);
    buyat.removeAllChildren(target);
    try {
      var response =  buyat.validateResponse(response); 
      var h1 = document.createElement('h1');
      h1.appendChild(document.createTextNode('Results for \''+response.query+'\''));
      target.appendChild(h1);
      var h3 = document.createElement('h3');
      h3.appendChild(document.createTextNode('Total Resutls '+response.total_results));
      target.appendChild(h3);
      h3 = document.createElement('h3');
      h3.appendChild(document.createTextNode('Current Resutls '+response.current_results));
      target.appendChild(h3);
      var dl = document.createElement('dl');
      for (var i = 0; i < response.current_results; i++) {
        var dt = this.createProduct(response.products[i].value);
        dl.appendChild(dt);
      }
      target.appendChild(dl);
    } catch (buyatException){
        target.appendChild(document.createTextNode(buyatException.message ));
      }
  },
  renderProduct: function(response) {
    var target = document.getElementById(buyat.targetElement); 
    buyat.removeAllChildren(target); 
    try {
      var response =  buyat.validateResponse(response); 
      var dl = document.createElement('dl');
      var dt = this.createProduct(response.value);
      dl.appendChild(dt);
      target.appendChild(dl);
    } catch (buyatException){
        target.appendChild(document.createTextNode(buyatException.message ));
    }
  },
  createProduct: function(product) {
    var dt = document.createElement('dt');
    // Other fields can be retrieved e.g. product_id, product_sku, brand_name, currency_symbol, programme_name, programme_url, programme_id, level1_category_id, level1_category_name, level2_category_id, level2_category_name, feed_id, feed_name
    //Other fields can be retrieved check  @api.endpoint@/index.php/entities/product
    var img = document.createElement('img');
    img.src = product.image_url;
    img.border = '';
    var imga = document.createElement('a');
    imga.href = product.product_url;
    imga.appendChild(img);
    dt.appendChild(imga);
    dt.appendChild(document.createElement('br'));
    var namea = document.createElement('a');
    namea.href = product.product_url;
    namea.appendChild(document.createTextNode(product.product_name));
    dt.appendChild(namea);
    var price = document.createElement('dt');
    price.appendChild(document.createTextNode(product.currency));
    price.appendChild(document.createTextNode(' '));
    price.appendChild(document.createTextNode(product.online_price));
    dt.appendChild(price); 
    var description = document.createElement('dt');
    description.appendChild(document.createTextNode(product.description));
    dt.appendChild(description);
    dt.appendChild(document.createElement('br'));
    return dt;
  },
  renderCategories: function(response) {
    var target = document.getElementById(buyat.targetElement); 
     buyat.removeAllChildren(target);
    try {
      var response =  buyat.validateResponse(response); 
      var h4 = document.createElement('h4');
      h4.appendChild(document.createTextNode("Retrieves: 'category_id', 'level', 'category_name' fields "));
      target.appendChild(h4);
      var category, category_name, category_id,level;  
      var dl = document.createElement('dl');
      for (var i = 0; i < response.categories.length; i++) {
        category = response.categories[i].value; 
        category_id = document.createTextNode(category.category_id);
        level = document.createTextNode(category.level);
        category_name = (document.createTextNode(category.category_name));
        var dt = document.createElement('dt');
        dt.appendChild(document.createTextNode('Category: '));
        dt.appendChild(category_id);
        dt.appendChild(document.createTextNode(', '));
        dt.appendChild(level);
        dt.appendChild(document.createTextNode(', '));
        dt.appendChild(category_name);
        if  (category.subcategories && category.subcategories.length > 0) {
          dt.appendChild(document.createElement('br'));
          dt.appendChild(document.createTextNode('Subcategories: '));
          for (var j = 0; j < category.subcategories.length; j++) {
            var subcategory = category.subcategories[j].value; 
            var dd = document.createElement('dd');
            var sub_category_id = document.createTextNode(subcategory.category_id);
            dd.appendChild(sub_category_id);
            dd.appendChild(document.createTextNode(', '));
            var sub_cat_level = document.createTextNode(subcategory.level);
            dd.appendChild(sub_cat_level);
            dd.appendChild(document.createTextNode(', '));
            var sub_category_name = (document.createTextNode(subcategory.category_name));
            dd.appendChild(sub_category_name);
            dt.appendChild(dd);
          }
        }
        dl.appendChild(dt);
      }
      target.appendChild(dl);
    } catch (buyatException){
        target.appendChild(document.createTextNode(buyatException.message ));
    }
  },
  renderProgrammes: function(response) {
    var target = document.getElementById(buyat.targetElement); 
     buyat.removeAllChildren(target);
    try {
      var response =  buyat.validateResponse(response); 
      var h4 = document.createElement('h4');
      h4.appendChild(document.createTextNode("Retrieves: 'programme_id', 'programme_name', 'programme_url', 'has_feed' fields "));
      target.appendChild(h4);
      var programme, programme_id, programme_name, programme_url, has_feed;
      var dl = document.createElement('dl');
      var dt;
      for  (var i = 0; i < response.programmes.length; i++) {
        programme = response.programmes[i].value; 
        dt = document.createElement('dt');
        dt.appendChild(document.createTextNode('Programme: '));
        programme_id = (document.createTextNode(programme.programme_id));
        dt.appendChild(programme_id);
        dt.appendChild(document.createTextNode(', '));
        programme_name = (document.createTextNode(programme.programme_name));
        dt.appendChild(programme_name);
        dt.appendChild(document.createTextNode(', '));
        programme_url = (document.createTextNode(programme.programme_url));
        dt.appendChild(programme_url);
        dt.appendChild(document.createTextNode(', '));
        has_feed = (document.createTextNode(programme.has_feed));
        dt.appendChild(has_feed);
        dl.appendChild(dt);
      }
      target.appendChild(dl);
    }catch (buyatException){
      target.appendChild(document.createTextNode(buyatException.message ));
    }
  },
  renderProgramme: function(response) {
    var target = document.getElementById(buyat.targetElement); 
    buyat.removeAllChildren(target); 
    try {
      var response =  buyat.validateResponse(response); 
      var h4 = document.createElement('h4');
      h4.appendChild(document.createTextNode("Retrieves: 'programme_id', 'programme_name', 'programme_url', 'has_feed' fields "));
      target.appendChild(h4);
      var programme;
      var dl = document.createElement('dl');
      programme = response[0].value; 
      var dt = document.createElement('dt');
      dt.appendChild(document.createTextNode('Programme: '));
      var programme_id = (document.createTextNode(programme.programme_id));
      dt.appendChild(programme_id);
      dt.appendChild(document.createTextNode(', '));
      var programme_name = (document.createTextNode(programme.programme_name));
      dt.appendChild(programme_name);
      dt.appendChild(document.createTextNode(', '));
      var programme_url = (document.createTextNode(programme.programme_url));
      dt.appendChild(programme_url);
      dt.appendChild(document.createTextNode(', '));
      var has_feed = (document.createTextNode(programme.has_feed));
      dt.appendChild(has_feed);
      dl.appendChild(dt);
      target.appendChild(dl);
    } 
    catch (buyatException){
     target.appendChild(document.createTextNode(buyatException.message ));
    }
  },
  renderFeeds: function(response) {
    var target = document.getElementById(buyat.targetElement); 
     buyat.removeAllChildren(target);
    try {
      var response =  buyat.validateResponse(response); 
      var h4 = document.createElement('h4');
      h4.appendChild(document.createTextNode("Retrieves: 'feed_id', 'feed_name', 'programme_id', 'programme_name', 'programme_url', 'number_of_products', 'last_updated' fields "));
      target.appendChild(h4);
      var feed, feed_id, feed_name, programme_id, programme_name, programme_url, number_of_products, last_updated;
      var dl = document.createElement('dl');
      var dt;
      for  (var i = 0; i < response.feeds.length; i++) {
        feed = response.feeds[i].value; 
        dt = document.createElement('dt');
        dt.appendChild(document.createTextNode('Feed: '));
        feed_id = (document.createTextNode(feed.feed_id));
        dt.appendChild(feed_id);
        dt.appendChild(document.createTextNode(', '));
        feed_name = (document.createTextNode(feed.feed_name));
        dt.appendChild(feed_name);
        dt.appendChild(document.createTextNode(', '));
        programme_id = (document.createTextNode(feed.programme_id));
        dt.appendChild(programme_id);
        dt.appendChild(document.createTextNode(', '));
        programme_name = (document.createTextNode(feed.programme_name));
        dt.appendChild(programme_name);
        dt.appendChild(document.createTextNode(', '));
        programme_url = (document.createTextNode(feed.programme_url));
        dt.appendChild(programme_url);
        dt.appendChild(document.createTextNode(', '));
        number_of_products = (document.createTextNode(feed.number_of_products));
        dt.appendChild(number_of_products);
        dt.appendChild(document.createTextNode(', '));
        last_updated = (document.createTextNode(feed.last_updated));
        dt.appendChild(last_updated);
        dl.appendChild(dt);
      }
      target.appendChild(dl);
    }catch (buyatException){
      target.appendChild(document.createTextNode(buyatException.message ));
    }
  },
  renderFeed: function(response) {
    var target = document.getElementById(buyat.targetElement); 
    buyat.removeAllChildren(target); 
    try {
      var response =  buyat.validateResponse(response); 
      var h4 = document.createElement('h4');
      h4.appendChild(document.createTextNode("Retrieves: 'feed_id', 'feed_name', 'programme_id', 'programme_name', 'programme_url', 'number_of_products', 'last_updated' fields "));
      target.appendChild(h4);
      var feed; 
      var dl = document.createElement('dl');
      feed = response.value; 
      var dt = document.createElement('dt');
      dt.appendChild(document.createTextNode('Feed: '));
      var feed_id = (document.createTextNode(feed.feed_id));
      dt.appendChild(feed_id);
      dt.appendChild(document.createTextNode(', '));
      var feed_name = (document.createTextNode(feed.feed_name));
      dt.appendChild(feed_name);
      dt.appendChild(document.createTextNode(', '));
      var programme_id = (document.createTextNode(feed.programme_id));
      dt.appendChild(programme_id);
      dt.appendChild(document.createTextNode(', '));
      var programme_name = (document.createTextNode(feed.programme_name));
      dt.appendChild(programme_name);
      dt.appendChild(document.createTextNode(', '));
      var programme_url = (document.createTextNode(feed.programme_url));
      dt.appendChild(programme_url);
      dt.appendChild(document.createTextNode(', '));
      var feed_url = (document.createTextNode(feed.number_of_products));
      dt.appendChild(feed_url);
      dt.appendChild(document.createTextNode(', '));
      var has_feed = (document.createTextNode(feed.last_updated));
      dt.appendChild(has_feed);
      dl.appendChild(dt);
      target.appendChild(dl);
    }catch (buyatException){
      target.appendChild(document.createTextNode(buyatException.message ));
    }
  },
  renderDeepLink: function(response)  {
   var target = document.getElementById(buyat.targetElement); 
   buyat.removeAllChildren(target); 
   try {
      var response =  buyat.validateResponse(response); 
      var h4 = document.createElement('h4');
      h4.appendChild(document.createTextNode("Retrieves: 'Deeplink' "));
      target.appendChild(h4);                                                     
      var dl = document.createElement('dl'); 
      var dt = document.createElement('dt');
      dt.appendChild(document.createTextNode('Deeplink: '));
      dt.appendChild(document.createTextNode(response.deep_link));
      dl.appendChild(dt);
      target.appendChild(dl); 
  
  } catch(buyatException)  {
      target.appendChild(document.createTextNode(buyatException.message ));
    }
  },
  renderFeedUrl: function(response) {
  var target = document.getElementById(buyat.targetElement); 
   buyat.removeAllChildren(target); 
   try {
       var response =  buyat.validateResponse(response); 
      var h4 = document.createElement('h4');
      h4.appendChild(document.createTextNode("Get a static feed download URL "));
      target.appendChild(h4);                                                     
      var dl = document.createElement('dl'); 
      var dt = document.createElement('dt');
      dt.appendChild(document.createTextNode('Url: '));
      dt.appendChild(document.createTextNode(response.url));
      dl.appendChild(dt);
      target.appendChild(dl); 
  
  } catch(buyatException)  {
      target.appendChild(document.createTextNode(buyatException.message ));
    }
  }

};