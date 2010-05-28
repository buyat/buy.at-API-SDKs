<?php

require_once('BuyatAPI.php');

$api = new BuyatAPI('15a6f6e6f976c6db0608516c9d79d78b');

echo "Testing echo\n\n";
echo $api->test_echo('test harness');

