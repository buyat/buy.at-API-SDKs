#!/usr/bin/perl

use BuyatApi;

my $buyatApi = new BuyatApi( api_key => '15a6f6e6f976c6db0608516c9d79d78b' );

my $response = $buyatApi->get_api_response('buyat.test.echo', { message => 'hi' } );

print "obtained response:\n-----------\n$response\n-----------\n";

exit 0;
