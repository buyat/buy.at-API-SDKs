' Copyright (c) 2009, Perfiliate Technologies Ltd
' All rights reserved.
' 
' Redistribution and use in source and binary forms, with or without
' modification, are permitted provided that the following conditions are met:
'   * Redistributions of source code must retain the above copyright notice, this
'     list of conditions and the following disclaimer.
'   * Redistributions in binary form must reproduce the above copyright notice,
'     this list of conditions and the following disclaimer in the documentation
'     and/or other materials provided with the distribution.
'   * Neither the name of Perfiliate Technologies Ltd nor the names of its
'     contributors may be used to endorse or promote products derived from this
'     software without specific prior written permission.
'
' THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
' ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
' WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
' DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
' FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
' DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
' SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
' CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
' OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
' OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

Option Explicit

Dim Endpoint, ApiKey
Endpoint = "http://shop.api.perfb.com/api/api.php?responsemethod=xml"
ApiKey = "15a6f6e6f976c6db0608516c9d79d78b"

Dim Response
Response = TestEcho("hello!!")
WScript.Echo Response
Response = TestAdd(5, 6)
WSCript.Echo Response

Function TestEcho(Message)
  Dim URL, Response, Doc, Node
  Dim Params(1,2)
  Params(0,0) = "message"
  Params(0,1) = Message
  URL = ConstructURL("buyat.test.echo", Params)
  Response = GetURL(URL)
  set Doc = CreateObject("Microsoft.XMLDOM")
  Doc.async = False
  Doc.loadXML(Response)
  For Each Node In Doc.documentElement.childNodes
    If Node.nodename = "message" Then
      TestEcho = Node.text
    End If
  Next
End Function

Function TestAdd(X, Y)
  Dim URL, Response, Doc, Node
  Dim Params(2,2)
  Params(0,0) = "x"
  Params(0,1) = X
  Params(1,0) = "y"
  Params(1,1) = Y
  URL = ConstructURL("buyat.test.add", Params)
  Response = GetURL(URL)
  set Doc = CreateObject("Microsoft.XMLDOM")
  Doc.async = False
  Doc.loadXML(Response)
  For Each Node In Doc.documentElement.childNodes
    If Node.nodename = "sum" Then
      TestAdd = Node.text
    End If
  Next
End Function

Function ConstructURL(Action, Params)
  Dim URL, i
  URL = Endpoint & "&api_key=" & ApiKey & "&action=" & Action
  For i = 0 To UBound(Params) - 1
    URL = URL & "&" & Params(i, 0) & "=" & Params(i, 1)
  Next
  ConstructURL = URL
End Function

Function GetURL(URL)
  Dim HTTP

  Set HTTP = CreateObject("Microsoft.XmlHttp")
  HTTP.open "GET", URL, False
  HTTP.send

  If HTTP.status <> 200 Then
    WScript.Echo "Error calling API: " & HTTP.statusText
    Exit Function
  End If

  GetURL = HTTP.responseText
End Function
