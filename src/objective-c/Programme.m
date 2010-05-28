#import "Programme.h"

@implementation Programme

- (int) getProgrammeID {
  return programmeID;
}


- (NSString*) getProgrammeName {
  return programmeName;
}

- (NSString*) getProgrammeURL {
  return programmeURL;
}

- (BOOL) hasFeed {
  return hasFeed;
}

- (void) setProgrammeID: (int) input {
  programmeID = input;
}

- (void) setProgrammeName: (NSString*) input {
  [programmeName autorelease];
  programmeName = [input retain];
}

- (void) setProgrammeURL: (NSString*) input {
  [programmeURL autorelease];
  programmeURL = [input retain];
}

- (void) setHasFeed: (BOOL) input {
  hasFeed = input;
}

- (id) init {
  self = [super init];
  if(self) {
    [self setProgrammeID:0];
    [self setProgrammeName:@"Undefined"];
    [self setProgrammeURL:@"Undefined"];
    [self setHasFeed:NO];
  }
  return self;
}

- (void) dealloc {
  [programmeURL release];
  [programmeName release];
  [super dealloc];
}

@end
