#include <Foundation/Foundation.h> 

@interface Programme : NSObject {
  int programmeID;
  NSString* programmeName;
  NSString* programmeURL;
  BOOL hasFeed;
}

- (int) getProgrammeID;
- (NSString*) getProgrammeName;
- (NSString*) getProgrammeURL;
- (BOOL) hasFeed;

- (void) setProgrammeID: (int) input;
- (void) setProgrammeName: (NSString*) input;
- (void) setProgrammeURL: (NSString*) input;
- (void) setHasFeed: (BOOL) input;

@end
