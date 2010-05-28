'''
Created on 11 Dec 2009

@author: aclarke
'''

class Product():
    '''
    classdocs
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