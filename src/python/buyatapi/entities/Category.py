'''
Created on 3 Dec 2009

@author: aclarke
'''

class Category():
    '''
    classdocs
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
