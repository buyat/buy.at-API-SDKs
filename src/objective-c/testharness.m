#include <Foundation/Foundation.h>
#include "Programme.h"
#include "BuyatAPI.h"

int main(void) {

  NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];

  id buyat = [[BuyatAPI alloc] initWithApiKey:@"15a6f6e6f976c6db0608516c9d79d78b"];
  NSString *response = [buyat testEcho:@"hello from objective-c"];
  NSLog(response);

  [pool drain];

  return 0;
}
