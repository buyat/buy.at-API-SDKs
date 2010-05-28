#import "BuyatAPI.h"

@implementation BuyatAPI

- (NSString*) getApiKey {
  return m_apiKey;
}

- (void) setApiKey: (NSString*) input {
  [m_apiKey autorelease];
  m_apiKey = [input retain];
}

- (NSString*) testEcho: (NSString*) input {
  NSDictionary *args = [NSDictionary dictionaryWithObjectsAndKeys:input,@"message",nil];
  return [self apiCall:@"buyat.test.echo" apiKey:m_apiKey args:args];
}

- (NSString*) constructAPIRequest: (NSString*) action
                           apiKey: (NSString*) apiKey
                             args: (NSDictionary*) args {

  NSEnumerator *enumerator;
  id key;

  NSString *requestURL = [m_endPoint stringByAppendingString:@"&api_key="];
  requestURL = [requestURL stringByAppendingString:apiKey];
  requestURL = [requestURL stringByAppendingString:@"&action="];
  requestURL = [requestURL stringByAppendingString:action];

  enumerator = [args keyEnumerator];
  while((key = [enumerator nextObject])) {
    requestURL = [requestURL stringByAppendingString:@"&"];
    requestURL = [requestURL stringByAppendingString:key];
    requestURL = [requestURL stringByAppendingString:@"="];
    requestURL = [requestURL stringByAppendingString:[[args objectForKey:key] stringByAddingPercentEscapesUsingEncoding:NSASCIIStringEncoding]];
  }

  return requestURL;
}

- (NSString*) apiCall: (NSString*) action
               apiKey: (NSString*) apiKey
                 args: (NSDictionary*) args {
  NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
  NSString *urlString = [self constructAPIRequest:action apiKey:apiKey args:args];  
  NSURL *url = [NSURL URLWithString:urlString];
  NSString *response = [NSString stringWithContentsOfURL:url];
  [url release];
  [urlString release];
  [pool drain];
  return response;
}

- (id) initWithApiKey: (NSString*) apiKey {
  self = [super init];
  if(self) {
    [self setApiKey:apiKey];
    m_endPoint = @"http://localhost/api/api.php?responsemethod=xml";
  }
  return self;
}

- (id) init {
  return [self initWithApiKey:nil];
}

- (void) dealloc {
  [m_apiKey release];
  [m_endPoint release];
  [super dealloc];
}

@end
