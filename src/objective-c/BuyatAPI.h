#include <Foundation/Foundation.h> 

@interface BuyatAPI : NSObject {
  NSString* m_apiKey;
  NSString* m_endPoint;
}

- (NSString*) getApiKey;

- (void) setApiKey: (NSString*) input;

- (NSString*) testEcho: (NSString*) input;

- (NSString*) constructAPIRequest: (NSString*) action
                           apiKey: (NSString*) apiKey
                             args: (NSDictionary*) args;

- (NSString*) apiCall: (NSString*) action
               apiKey: (NSString*) apiKey
                 args: (NSDictionary*) args;

- (id) init;

- (id) initWithApiKey: (NSString*) apiKey;
 
@end
