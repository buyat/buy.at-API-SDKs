###############################################################################
#
# Copyright (c) 2009, Perfiliate Technologies Ltd
# All rights reserved.
# 
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#   * Redistributions of source code must retain the above copyright notice, this
#     list of conditions and the following disclaimer.
#   * Redistributions in binary form must reproduce the above copyright notice,
#     this list of conditions and the following disclaimer in the documentation
#     and/or other materials provided with the distribution.
#   * Neither the name of Perfiliate Technologies Ltd nor the names of its
#     contributors may be used to endorse or promote products derived from this
#     software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
# ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
# WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
# FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
# DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
# SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
# CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
# OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
# 
##############################################################################

package provide buyat 1.0

package require json 1.0

namespace eval buyat {
  variable api_key "API KEY"
  variable debug 0
}

proc ::buyat::perform_post { host path payload } {
  if { $buyat::debug } {
    puts stdout "Posting payload:"
    puts stdout $payload
  }
  set payload_length [ string length $payload ]
  set sock [ socket $host 80 ]
  fconfigure $sock -buffering line
  puts $sock "POST $path HTTP/1.0"
  puts $sock "HOST: $host"
  puts $sock "Content-Type: application/x-www-form-urlencoded; charset=iso-8859-1"
  puts $sock "Content-Length: $payload_length"
  puts $sock "User-Agent: ProductX/Tcl"
  puts $sock "\n$payload"
  set page [ read $sock ]
  close $sock
  return $page
}

proc ::buyat::construct_api_request { action api_key arguments } {
  set request "<request><action>$action</action><parameters><api_key>$api_key</api_key>"
  dict for { key value } $arguments {
    append request "<$key>$value</$key>"
  }
  append request "</parameters></request>"
  return $request
}

proc ::buyat::api_call { action api_key arguments } {
  if { $buyat::debug } {
    puts stdout "Calling $action"
  }
  set response ""
  set request [ ::buyat::construct_api_request $action $api_key $arguments ]
  set raw_response [ ::buyat::perform_post "api.perfb.com" "/api/api.php?requestmethod=xml&responsemethod=json" $request ]
  regexp "\n\{(.*)\}$" $raw_response response
  set response
  string trim $response
  return [ ::json::json2dict $response ]
}

proc ::buyat::test_echo { message } {
  dict set arguments "message" $message
  set response [ ::buyat::api_call "buyat.test.echo" $buyat::api_key $arguments ]
  return [ dict get $response "message" ]
}

proc buyat::test_add { x y } {
  dict set arguments "x" $x
  dict set arguments "y" $y
  set response [ ::buyat::api_call "buyat.test.add" $buyat::api_key $arguments ]
  return [ dict get $response "sum" ]
}

proc ::buyat::set_debug { debug } {
  set ::buyat::debug $debug
}

proc ::buyat::set_api_key { api_key } {
  set ::buyat::api_key $api_key
}
