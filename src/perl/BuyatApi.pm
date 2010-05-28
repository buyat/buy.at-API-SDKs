#!/usr/bin/perl


package BuyatApi;

use strict;
use IO::Socket::INET;
use Socket qw( MSG_NOSIGNAL MSG_WAITALL);

my $default_api_host = 'storefront.perfiliate.com';
my $default_api_path = '/api/api.php?requestmethod=xml&responsemethod=php';
my $default_api_port = 80;
my $default_agent = 'buyatApiPerlSDK_Testing';
my $BUYATAPI_SOCKET_ERROR = -1;
my $BUYATAPI_KEY_ERROR = -2;

BEGIN {
	use Exporter	();
	use vars	qw($VERSION @ISA @EXPORT @EXPORT_OK %EXPORT_TAGS);
	$VERSION	= 1.0;
	@ISA        	= qw(Exporter);
}


sub new {
	my $type = shift;
	my %params = @_;
	my $self = {};

	$self->{'api_host'} = $params{'api_host'} || $default_api_host;
	$self->{'api_path'} = $params{'api_path'} || $default_api_path;
	$self->{'api_key'} = $params{'api_key'};
	$self->{'api_port'} = $params{'api_port'} || $default_api_port;
	$self->{'agent'} = $params{'agent'} || $default_agent;
	$self->{'api_url'} = 'http://' . $self->{'api_host'} . $self->{'api_path'};
	$self->{'sock_flags'} = MSG_NOSIGNAL;
	$self->{'sock'} = undef;
	$self->{'buflen'} = 4096;

	bless $self, $type;
}


sub create_api_request($$) {
	my $self = shift;
	my ($action, $args) = @_;
	my $request = "<request><action>$action</action><parameters><api_key>$self->{'api_key'}</api_key>";
	foreach(keys(%$args)) {
		my ($key, $val) = ($_, $args->{$_});
		$request .= "<$key>$val</$key>";
	}

	$request .= "</parameters></request>\r\n";
	return $request;
}

sub create_api_post($) {
	my $self = shift;
	my ($payload) = @_;
	my $request = "POST $self->{'api_path'} HTTP/1.0\r\nHost: $self->{'api_host'}\r\n";
	$request .= "Content-Type: application/x-www-form-urlencoded; charset=iso-8859-1\r\n";
	$request .= 'Content-Length: ' . length($payload) . "\r\n";
	$request .= "User-Agent: $self->{'agent'}\r\n\r\n";
	$request .= $payload;
	return $request;
}

sub dump() {
	my $self = shift;
	for(qw(api_host api_port api_key api_path)) {
		print "$_ => $self->{$_}\n";
	}
}

sub strip_http_headers($) {
	my $self = shift;
	my ($str) = @_;
	$str =~ s/^[^\0]*?\r\n\r\n//;
	return ($str);
}
		

sub get_api_response($$) {
	my $self = shift;
	my ($action, $argsref) = @_;

	unless($self->{'sock'}) {
		print "creating sock\n";
		$self->dump();
		my $sock = IO::Socket::INET->new(
				PeerAddr => "$self->{'api_host'}:$self->{'api_port'}",
				Proto    => 'tcp' 
		);
		$self->{'sock'} = $sock;
	}

	my $sock = $self->{'sock'};
	unless ($sock) {
		return $BUYATAPI_SOCKET_ERROR;
	}

	my $request = $self->create_api_request($action, $argsref);
	print "request: $request\n";
	my $post_request = $self->create_api_post($request);
	print "post_request: $post_request\n";
	my $post_status = send($sock, $post_request, $self->{'sock_flags'});
	print "post_status: $post_status\n";

	my $response = undef;
	my $response_status = undef;
	# $response_status = $sock->recv($response, $self->{'buflen'});
	$response_status = $sock->recv($response, 65536, MSG_WAITALL);
#	print "response_status: $response_status, response: $response\n";
	return ($self->strip_http_headers($response));
}

1;
