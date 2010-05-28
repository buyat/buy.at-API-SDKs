'''
@LICENCE@
'''
from datetime import datetime
from urllib2 import urlopen, HTTPError
from xml.dom.minidom import parse
from xml.parsers.expat import ExpatError

from APIError import APIError
from Entities import Category, Feed, Product, ProductResultSet, Programme

class BuyatAPIClient():
    '''
    classdocs
    '''

    def __init__(self, api_key):
        '''
        Constructor
        '''
        self.api_key = api_key
        self.api_endpoint = "@API_ENDPOINT@";
        
    def _download_url_as_xml(self, url):
        try:
            sock = urlopen(url)
            xmldoc = parse(sock).documentElement
            sock.close()
            return xmldoc
        except (HTTPError, ExpatError) as e:
            raise APIError("Error from API: %s" %e)
    
    def _get_child_node_text(self, el, child_node_name):
        for child in el.childNodes:
            if (child.nodeName == child_node_name):
                text = ''
                for grandchild in child.childNodes:
                    if grandchild.nodeType == grandchild.TEXT_NODE:
                        text = text + grandchild.data
                        
                return text
            
        raise APIError("Invalid XML returned from API: no %s " \
                       "child nodes in element %s" % (child_node_name, el.nodeName));
    
    def _get_child_element(self, el, child_node_name):
        child_elements = el.getElementsByTagName(child_node_name)
        
        if len(child_elements) == 1:
            return child_elements[0]

        return None;
    
    def _handle_errors(self, doc):
        errors = doc.getElementsByTagName('error');
        if len(errors):
            error_message = self._get_child_node_text(errors[0], 'error_message')
            if error_message:
                raise APIError(error_message)
            else:
                raise APIError("Unknown API Error")
            
    def _parse_product(self, el):
        product = Product(self)
        product.product_id = int(self._get_child_node_text(el, "product_id"))
        product.product_sku = self._get_child_node_text(el, "product_sku")
        product.product_url = self._get_child_node_text(el, "product_url")
        product.product_name = self._get_child_node_text(el, "product_name")
        product.brand_name = self._get_child_node_text(el, "brand_name")
        product.description = self._get_child_node_text(el, "description")
        product.online_price = float(self._get_child_node_text(el, "online_price").replace(',',''))
        product.currency = self._get_child_node_text(el, "currency")
        product.currency_symbol = self._get_child_node_text(el, "currency_symbol")
        product.image_url = self._get_child_node_text(el, "image_url")
        product.programme_id = int(self._get_child_node_text(el, "programme_id"))
        product.programme_name = self._get_child_node_text(el, "programme_name")
        product.programme_url = self._get_child_node_text(el, "programme_url")
        level1_category_id_string = self._get_child_node_text(el, "level1_category_id")
        product.level1_category_id = int(level1_category_id_string) if level1_category_id_string else None
        product.level1_category_name = self._get_child_node_text(el, "level1_category_name")
        level2_category_id_string = self._get_child_node_text(el, "level2_category_id")
        product.level2_category_id = int(level2_category_id_string) if level2_category_id_string else None
        product.level2_category_name = self._get_child_node_text(el, "level2_category_name")
        product.feed_id = int(self._get_child_node_text(el, "feed_id"))
        product.feed_name = self._get_child_node_text(el, "feed_name")
        return product
    
    def search_products(self, query=None, page=1, perpage=10, programme_ids=None,
                        excluded_programme_ids=None, excluded_programme_category_ids=None,
                        feed_ids=None, level1_category_id=None, level2_category_id=None,
                        include_adult=False, lid=None, sort=None, sortorder=None):
        url = "%s?api_key=%s&requestmethod=rest&responsemethod=xml" \
            "&action=buyat.affiliate.product.search" \
            % (self.api_endpoint, self.api_key)
        
        if query:
            url += "&query=%s" %query
        
        if page:
            url += "&page=%s" %page
        
        if perpage:
            url += "&perpage=%s" %perpage
            
        if programme_ids:
            url += "&programme_ids=%s" %(','.join(map(str,programme_ids)))
            
        if excluded_programme_ids:
            url += "&excluded_programme_ids=%s" %(','.join(map(str,excluded_programme_ids)))
            
        if excluded_programme_category_ids:
            url += "&excluded_programme_category_ids=%s" %(','.join(map(str,excluded_programme_category_ids)))
            
        if feed_ids:
            url += "&feed_ids=%s" %(','.join(map(str,feed_ids)))
        
        if level1_category_id:
            url += "&level1_category_id=%s" %level1_category_id
        
        if level2_category_id:
            url += "&level2_category_id=%s" %level2_category_id
            
        if include_adult:
            url += "&include_adult=y"
            
        if lid:
            url += "&lid=%s" %lid
            
        if sort:
            url += "&sort=%s" %sort
            
        if sortorder:
            url += "&sortorder=%s" %sortorder
            
        doc = self._download_url_as_xml(url)
        self._handle_errors(doc)
        
        result_set = ProductResultSet()
        result_set.total_results = int(self._get_child_node_text(doc, "total_results"))
        result_set.current_results = int(self._get_child_node_text(doc, "current_results"))
        result_set.start = int(self._get_child_node_text(doc, "start"))
        result_set.limit = int(self._get_child_node_text(doc, "limit"))
        result_set.query = self._get_child_node_text(doc, "query")
        
        products = []
        for el in doc.getElementsByTagName('product'):
            products.append(self._parse_product(el))
            
        result_set.products = products
        
        doc.unlink()
        return result_set
            
            
    def get_product(self, product_id):
        api_url = "%s?api_key=%s&requestmethod=rest&responsemethod=xml" \
                  "&action=buyat.affiliate.product.info&product_id=%s" \
                  % (self.api_endpoint, self.api_key, product_id)
        
        doc = self._download_url_as_xml(api_url)
        self._handle_errors(doc)
        
        product = self._parse_product(self._get_child_element(doc, 'product'))
        doc.unlink()
        return product

    
    def _parse_categories(self, doc):
        categories = []
        for el in doc.getElementsByTagName('category'):
            cat = Category(self)
            cat.category_id = int(self._get_child_node_text(el, "category_id"))
            cat.category_name = self._get_child_node_text(el, "category_name")
            cat.level = int(self._get_child_node_text(el, "level"))
            if cat.level == 2:
                parent_category_id_string = self._get_child_node_text(el, "parent_category_id")
                cat.parent_category_id = int(parent_category_id_string) if parent_category_id_string else None 
                cat.parent_category_name = self._get_child_node_text(el, "parent_category_name")
            else:
                cat.parent_category_id = None
                cat.parent_category_name = None
            categories.append(cat)
        return categories
    
    def get_level1_categories(self):
        url = "%s?api_key=%s&requestmethod=rest&responsemethod=xml" \
            "&action=buyat.affiliate.category.listlevel1" \
            % (self.api_endpoint, self.api_key)
        doc = self._download_url_as_xml(url)
        self._handle_errors(doc)
        cats = self._parse_categories(doc)
        doc.unlink()
        return cats
      
    def get_level2_categories(self, level1_category_id):
        url = "%s?api_key=%s&requestmethod=rest&responsemethod=xml" \
            "&action=buyat.affiliate.category.listlevel2&level1_category_id=%s" \
            % (self.api_endpoint, self.api_key, level1_category_id)
        doc = self._download_url_as_xml(url)
        self._handle_errors(doc)
        cats = self._parse_categories(doc)
        doc.unlink()
        return cats
      
    def _parse_category_tree(self, els, parent_category_id, parent_category_name):
        categories = []
        for el in els:
            if el.nodeType == el.ELEMENT_NODE:
                cat = Category(self)
                cat.category_id = int(self._get_child_node_text(el, "category_id"))
                cat.category_name = self._get_child_node_text(el, "category_name")
                cat.level = int(self._get_child_node_text(el, "level"))
                cat.parent_category_id = parent_category_id
                cat.parent_category_name = parent_category_name
                subcat_el = self._get_child_element(el, "subcategories")
                
                if (subcat_el):
                    cat.subcategories = self._parse_category_tree(subcat_el.childNodes,
                                                                  cat.category_id,
                                                                  cat.category_name)
                     
                categories.append(cat)
            
        return categories
    
    def get_category_tree(self):
        url = "%s?api_key=%s&requestmethod=rest&responsemethod=xml" \
            "&action=buyat.affiliate.category.tree" \
            % (self.api_endpoint, self.api_key)
        doc = self._download_url_as_xml(url)
        self._handle_errors(doc)
        categories = self._get_child_element(doc, "categories").childNodes
        tree = self._parse_category_tree(categories, None, None)
        doc.unlink()
        return tree
    
    def _parse_programme(self,el):
        prog = Programme()
        prog.programme_id = int(self._get_child_node_text(el, "programme_id"))
        prog.programme_name = self._get_child_node_text(el, "programme_name")
        prog.programme_url = self._get_child_node_text(el, "programme_url")
        prog.has_feed = self._get_child_node_text(el, "has_feed") == 'Y'
        return prog
    
    def get_programmes(self):
        url = "%s?api_key=%s&requestmethod=rest&responsemethod=xml" \
            "&action=buyat.affiliate.programme.list" \
            % (self.api_endpoint, self.api_key)
        doc = self._download_url_as_xml(url)
        self._handle_errors(doc)
        
        programmes = []
        
        for el in doc.getElementsByTagName('programme'):
            programmes.append(self._parse_programme(el))
            
        doc.unlink()
        return programmes
    
    def get_programme(self, programme_id):
        url = "%s?api_key=%s&requestmethod=rest&responsemethod=xml" \
            "&action=buyat.affiliate.programme.info&programme_id=%s" \
            % (self.api_endpoint, self.api_key, programme_id)
        doc = self._download_url_as_xml(url)
        self._handle_errors(doc)
        
        el = self._get_child_element(doc, "programme")
        programme = self._parse_programme(el)
        doc.unlink()
        return programme
        
    
    def _parse_feed(self, el):
        feed = Feed()
        feed.feed_id = int(self._get_child_node_text(el, "feed_id"))
        feed.feed_name = self._get_child_node_text(el, "feed_name")
        feed.programme_id = int(self._get_child_node_text(el, "programme_id"))
        feed.programme_name = self._get_child_node_text(el, "programme_name")
        feed.programme_url = self._get_child_node_text(el, "programme_url")
        feed.number_of_products = int(self._get_child_node_text(el, "number_of_products"))
        last_updated_string = self._get_child_node_text(el, "last_updated")
        if last_updated_string:
            feed.last_updated = datetime.strptime(self._get_child_node_text(el, "last_updated"),
                                                  '%Y-%m-%d %H:%M:%S')
        else:
            feed.last_updated = None
        return feed
    
    def get_feeds(self):
        url = "%s?api_key=%s&requestmethod=rest&responsemethod=xml" \
            "&action=buyat.affiliate.feed.list" \
            % (self.api_endpoint, self.api_key)
        doc = self._download_url_as_xml(url)
        self._handle_errors(doc)
        
        feeds = []
        
        for el in doc.getElementsByTagName('feed'):
            feeds.append(self._parse_feed(el))
            
        doc.unlink()
        return feeds
    
        
    def get_feed(self, feed_id):
        api_url = "%s?api_key=%s&requestmethod=rest&responsemethod=xml" \
                  "&action=buyat.affiliate.feed.info&feed_id=%s" \
                  % (self.api_endpoint, self.api_key, feed_id)
        
        doc = self._download_url_as_xml(api_url)
        self._handle_errors(doc)
        
        feed = self._parse_feed(self._get_child_element(doc, 'feed'))
        doc.unlink()
        return feed

    
    def get_feed_url(self, feed_id, format, start=0, perpage=100,
                     level1_category_id=None, level2_category_id=None,
                     programme_category_id=None, levels=None,
                     lid=None, use_https=False, reverse_map_xml=False,
                     bestseller=False):
        url = "%s?api_key=%s&requestmethod=rest&responsemethod=xml" \
            "&action=buyat.affiliate.feed.geturl&feed_id=%s&format=%s" \
            "&start=%s&perpage=%s"\
            % (self.api_endpoint, self.api_key, feed_id, format, start, perpage)
            
        if level1_category_id:
            url += "&level1_category_id=%s" %level1_category_id
            
        if level2_category_id:
            url += "&level2_category_id=%s" %level2_category_id
            
        if programme_category_id:
            url += "&programme_category_id=%s" %programme_category_id
            
        if levels:
            for i, level_name in enumerate(levels):
                if level_name:
                    url += "&level%s=%s" %(i+1,level_name)
        
        if lid:
            url += "&lid=%s" %lid
        
        if use_https:
            url += "&use_https=y"
        
        if reverse_map_xml:
            url += "&reverse_map_xml=y"
        
        if bestseller:
            url += "&bestseller=y"
        
        doc = self._download_url_as_xml(url)
        self._handle_errors(doc)
        
        return self._get_child_node_text(doc, "url")
    
    
    def get_deeplink(self, url):
        api_url = "%s?api_key=%s&requestmethod=rest&responsemethod=xml" \
                  "&action=buyat.affiliate.linkengine.create&url=%s" \
                  % (self.api_endpoint, self.api_key, url)
        
        doc = self._download_url_as_xml(api_url)
        self._handle_errors(doc)
        
        return self._get_child_node_text(doc, "deep_link")
        
        
        
