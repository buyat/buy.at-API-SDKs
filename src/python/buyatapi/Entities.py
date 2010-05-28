'''
@LICENCE@
'''

class Category():
    '''
    Represents a simple category
    '''

    def __init__(self, client):
        '''
        Constructor
        '''
        self._client = client
        self._subcategories = None
        
    def get_subcategories(self):
        if self._subcategories == None and self.level == 1:
            self._subcategories = self._client.get_level2_categories(self.category_id)
        return self._subcategories
    
    def set_subcategories(self):
        pass

    subcategories = property(get_subcategories, set_subcategories)


class Feed():
    '''
    Representation of a feed
    '''

    def __init__(self):
        '''
        Constructor
        '''

class Product():
    '''
    Representation of a product, with methods to get its programme and feed entities
    '''

    def __init__(self, client):
        '''
        Constructor
        '''
        self._client = client
        self._programme = None
        self._feed = None
    
    def get_programme(self):
        if self._programme == None and self.programme_id:
            self._programme = self._client.get_programme(self.programme_id)
        return self._programme
    
    def set_programme(self):
        pass
    
    def get_feed(self):
        if self._feed == None and self.feed_id:
            self._feed = self._client.get_feed(self.feed_id)
        return self._feed
    
    def set_feed(self):
        pass

    programme = property(get_programme, set_programme)
    feed = property(get_feed, set_feed)

    
class ProductResultSet(object):
    '''
    Represents the results of a product search
    '''

    def __init__(self):
        '''
        Constructor
        '''
        self.products = []
        

class Programme():
    '''
    Represents a programme
    '''


    def __init__(self):
        '''
        Constructor
        '''
