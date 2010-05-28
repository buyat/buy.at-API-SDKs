/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
var buyat = {
  init: function(apiKey) {
    this.apiKey = apiKey;
    this.api_endpoint = '@API_ENDPOINT@?api_key='+this.apiKey+'&responsemethod=js&action=';
    this.targetElement = '';
  },
  apiEcho: function(targetElement, message, callback) {
    this.validateTargetElement(targetElement);
    message = escape(message);
    var url = this.api_endpoint+'buyat.test.echo&message='+message+'&callback='+callback ;
    this.createScript(url);
  },
  
  searchProducts: function(targetElement, query, callback, page,  perPage, sort, sortOrder, programmeIDs, excludedProgrammeIds, excludedProgrammeCategoryIds, feedIds, level1CategoryId, level2CategoryId, includeAdult, lid) {
    this.validateTargetElement(targetElement); 
    query = escape(query);
    var url = this.api_endpoint+'buyat.affiliate.product.search&query='+query+'&callback='+callback; 
    if  (page != undefined) {
      url += '&page='+ page;
    } 
    if  (typeof perPage != 'undefined') {
      url += '&perPage='+ perPage;
    } 
    if  (typeof sort != 'undefined') {
      url += '&sort='+ sort;
    } 
    if  (typeof sortOrder != 'undefined') {
      url += '&sortOrder='+ sortOrder;
    } 
    if  (typeof programmeIDs != 'undefined') {
      url += '&programmeIDs='+ programmeIDs;
    } 
    if  (typeof excludedProgrammeIds != 'undefined') {
      url += '&excludedProgrammeIds='+ excludedProgrammeIds;
    } 
    if  (typeof excludedProgrammeCategoryIds != 'undefined') {
      url += '&excludedProgrammeCategoryIds='+ excludedProgrammeCategoryIds;
    } 
    if  (typeof feedIds != 'undefined') {
      url += '&feedIds='+ feedIds;
    } 
    if  (typeof level1CategoryId != 'undefined') {
      url += '&level1CategoryId='+ level1CategoryId;
    } 
    if  (typeof level2CategoryId != 'undefined') {
      url += '&level2CategoryId='+ level2CategoryId;
    }  
    if  (typeof includeAdult != 'undefined') {
      url += '&includeAdult='+ includeAdult;
    }  
    if  (typeof lid != 'undefined') {
      url += '&lid='+ lid;
    } 
    this.createScript(url);
  },
  getProduct: function(targetElement, productId, callback) {
    this.validateTargetElement(targetElement);
    if(typeof productId !== 'number')  {
       return document.getElementById(this.targetElement).appendChild(document.createTextNode('Parameter \'product_id\' required and must be numeric' ))
    }    
    var url = this.api_endpoint+'buyat.affiliate.product.info&product_id='+productId+'&callback='+callback; 
    this.createScript(url); 
  },
  listLevel1Categories: function(targetElement, callback) {
    this.validateTargetElement(targetElement); 
    var url = this.api_endpoint+'buyat.affiliate.category.listlevel1&callback='+callback; 
    this.createScript(url);
  },
  listLevel2Categories: function(targetElement, level1CategoryId, callback) {
    this.validateTargetElement(targetElement);
    if(typeof level1CategoryId !== 'number')  {
       return document.getElementById(this.targetElement).appendChild(document.createTextNode('Parameter \'level1_category_id\' required and must be numeric' ))
    }
    this.validateCallback(callback);
    var url = this.api_endpoint+'buyat.affiliate.category.listlevel2&level1_category_id='+ level1CategoryId +'&callback='+callback; 
    this.createScript(url);
  },
  getCategoryTree: function(targetElement, callback) {
     this.validateTargetElement(targetElement);
    this.validateCallback(callback); 
    var url = this.api_endpoint+'buyat.affiliate.category.tree&callback='+callback; 
    this.createScript(url);
  },
  lisProgrammes: function(targetElement, callback) {
     this.validateTargetElement(targetElement); 
    var url = this.api_endpoint+'buyat.affiliate.programme.list&callback='+callback; 
    this.createScript(url);
  },
  getProgramme: function(targetElement, programmeId, callback) {
     this.validateTargetElement(targetElement);
    if(typeof programmeId !== 'number')  {
       throw new Error ('Parameter \'programme_id\' required and must be numeric' );
    }  
    var url = this.api_endpoint+'buyat.affiliate.programme.info&programme_id='+programmeId+'&callback='+callback; 
    this.createScript(url);
  },
  lisFeeds: function(targetElement, callback) {
     this.validateTargetElement(targetElement); 
    var url = this.api_endpoint+'buyat.affiliate.feed.list&callback='+callback; 
    this.createScript(url);
  },
  getFeed: function(targetElement, feedId, callback) {
     this.validateTargetElement(targetElement);
    if(typeof feedId !== 'number')  {
       return document.getElementById(this.targetElement).appendChild(document.createTextNode('Parameter \'feed_id\' required and must be numeric' ))
    }  
    var url = this.api_endpoint+'buyat.affiliate.feed.info&feed_id='+feedId+'&callback='+callback; 
    this.createScript(url); 
  },
  getDeepLink: function(targetElement, url, callback) {
     this.validateTargetElement(targetElement); 
    url = encodeURI(url);
    var url = this.api_endpoint+'buyat.affiliate.linkengine.create&url='+url+'&callback='+callback;
    this.createScript(url); 
  },
  getFeedUrl: function(targetElement, feedId, callback, format, start, perPage, lid, useHTPS, reverseMapXML, bestseller, level1CategoryId, level2CategoryId, programmeCategoryId, level1, level2, level3, level4, level5, level6, level7,level8  ) {
     this.validateTargetElement(targetElement); 
    if(typeof feedId !== 'number')  {
       return document.getElementById(this.targetElement).appendChild(document.createTextNode('Parameter \'feed_id\' required and must be numeric' ))
    } 
    var url = this.api_endpoint+'buyat.affiliate.feed.geturl&feed_id='+feedId+'&callback='+callback;
    for(var i=1; i< 13; i++)  {
      if  (typeof arguments[i] != 'undefined') {
        if(i === 3)  {
           url += '&format='+ arguments[i]; 
        }
        else if(i === 4)  {
           url += '&start='+ arguments[i]; 
        }
        else if(i === 5)  {
           url += '&perPage='+ arguments[i]; 
        }
        else if(i === 6)  {
           url += '&lid='+ arguments[i]; 
        }
        if(i === 7)  {
           url += '&useHTPS='+ arguments[i]; 
        }
        else if(i === 8)  {
           url += '&reverseMapXML='+ arguments[i]; 
        }
        else if(i === 9)  {
           url += '&bestseller='+ arguments[i]; 
        }
        else if(i === 10)  {
           url += '&level1CategoryId='+ arguments[i]; 
        }
        else if(i === 11)  {
           url += '&level2CategoryId='+ arguments[i]; 
        }
        else if(i === 12)  {
           url += '&programmeCategoryId='+ arguments[i]; 
        }
      } 
    }
    for (var i = 1; i <= 8; i++) {
      var level = 'level'+i; 
      if(typeof (eval(level)) !='undefined') {
         url += '&'+ level + '='+ eval(level); 
      }
    } 
    this.createScript(url); 
  },
  createScript: function(url)  {
    var head = document.getElementsByTagName("head").item(0);
    var script = document.createElement("script");
    script.setAttribute("type", "text/javascript");
    script.setAttribute("src", url);
    head.appendChild(script);
  },
  removeAllChildren: function(element) {
  if  (element.childNodes[0]){
      while(element.hasChildNodes()){
        element.removeChild(element.lastChild);
      }
    }
  },
  validateResponse: function(response) { 
    if  (typeof response[0] != 'undefined' && typeof response[0].error =='object' && response[0].error.error_code) {
          if  (typeof response[0].error.error_message != 'undefined')  {
            throw new Error ('Error from API: Error message:  '+response[0].error.error_message+', Error code: '+ response[0].error.error_code);
          } 
         else {
          throw new Error ('Error from API: Error code: '+response[0].error.error_code);
         }
     }
     return response;
  },
  validateCallback: function(callback) { 
    
    if(typeof callback !== 'string')  {
       return document.getElementById(this.targetElement).appendChild(document.createTextNode('Parameter \'callback\' required. It is the name of the function to call when the result is back from Buyat API ' ));
    } 
  },
  validateTargetElement: function(targetElement) { 
    if(typeof targetElement !== 'string')  {
      alert('Parameter \'results\' required, it is the ID of the Div (or any HTML container) to display the function results   ' );
    }
    this.targetElement = targetElement;  
  }
};