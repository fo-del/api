<?php

include_once "HttpResponse.php";
include_once "FodelAPIException.php";
/**
 * User: Alex Meng
 */
class HttpRequestUtil
{

    private $handle = null;

    private $baseUrl = "http://api.test.fo-del.com";

    public function __construct()
    {
        if (!extension_loaded('curl')) {
            throw new FodelAPIException('CURL extension is not loaded');
        }

        $this->handle = curl_init();
        $this->initOptions();
    }

    public function __destruct()
    {
        curl_close($this->handle);
    }

    public function __clone()
    {
        $request = new self;
        $request->handle = curl_copy_handle($this->handle);

        return $request;
    }

    private function initOptions()
    {
        $this->setOptions(array(
            CURLOPT_RETURNTRANSFER  => true,
            CURLOPT_AUTOREFERER     => true,
            CURLOPT_FOLLOWLOCATION  => true,
            CURLOPT_MAXREDIRS       => 20,
            CURLOPT_HEADER          => true,
            CURLOPT_PROTOCOLS       => CURLPROTO_HTTP | CURLPROTO_HTTPS,
            CURLOPT_REDIR_PROTOCOLS => CURLPROTO_HTTP | CURLPROTO_HTTPS,
            CURLOPT_CONNECTTIMEOUT  => 30,
            CURLOPT_TIMEOUT         => 30
        ));
    }

    public function setBaseUrl($baseUrl){
        $this->baseUrl = $baseUrl;
    }

    public function setOption($option, $value)
    {
        return curl_setopt($this->handle, $option, $value);
    }

    public function setOptions($options)
    {
        return curl_setopt_array($this->handle, $options);
    }

    public function setTimeout($timeout)
    {
        $this->setOption(CURLOPT_TIMEOUT, $timeout);
    }

    public function setConnectTimeout($timeout)
    {
        $this->setOption(CURLOPT_CONNECTTIMEOUT, $timeout);
    }

    private function send($customHeader = array(), $fullResponse = false)
    {
        if (!empty($customHeader)) {
            $this->setOption(CURLOPT_HTTPHEADER, $customHeader);
        }

        $content = curl_exec($this->handle);

        if ($errno = curl_errno($this->handle)) {
            throw new FodelAPIException(curl_error($this->handle), $errno);
        }

        $headerSize = curl_getinfo($this->handle, CURLINFO_HEADER_SIZE);

        $response = new HttpResponse();

        if ($fullResponse) {
            $response->body = $content;
        } else {
            $response->body = substr($content, $headerSize);
        }

        return $response;
    }

    /**
     * Prepare data for a cURL post.
     *
     * @param mixed   $params      Data to send.
     * @param boolean $useEncoding Whether to url-encode params. Defaults to true.
     *
     * @return void
     */
    private function initPostFields($params, $useEncoding = true)
    {
        if (is_array($params)) {
            foreach ($params as $param) {
                if (is_string($param) && preg_match('/^@/', $param)) {
                    $useEncoding = false;
                    break;
                }
            }

            if ($useEncoding) {
                $params = http_build_query($params);
            }
        }

        if (!empty($params)) {
            $this->setOption(CURLOPT_POSTFIELDS, $params);
        }
    }

    public function setProxy($host, $port = 8080, $user = null, $pass = null)
    {
        $this->setOptions(array(
            CURLOPT_PROXY     => $host,
            CURLOPT_PROXYPORT => $port
        ));

        if (!empty($user) && is_string($user)) {
            $pair = $user;
            if (!empty($pass) && is_string($pass)) {
                $pair .= ':' . $pass;
            }
            $this->setOption(CURLOPT_PROXYUSERPWD, $pair);
        }
    }

    public function get($uri, $params = array(), $customHeader = array(), $fullResponse = false)
    {
        $uri = $this->baseUrl.$uri;

        if (!empty($params)) {
            $uri = $uri."?".http_build_query($params);
            //echo $uri;
        }

        $this->setOptions(array(
            CURLOPT_URL           => $uri,
            CURLOPT_HTTPGET       => true,
            CURLOPT_CUSTOMREQUEST => 'GET'
        ));

        return $this->send($customHeader, $fullResponse);
    }

    public function post($uri, $params = array(), $useEncoding = true, $customHeader = array(), $fullResponse = false)
    {
        $uri = $this->baseUrl.$uri;
        $this->setOptions(array(
            CURLOPT_URL           => $uri,
            CURLOPT_POST          => true,
            CURLOPT_CUSTOMREQUEST => 'POST'
        ));

        $this->initPostFields($params, $useEncoding);

        return $this->send($customHeader, $fullResponse);
    }

    public function postRaw($uri, $params = array(), $useEncoding = true, $customHeader = array(), $fullResponse = false)
    {
        $uri = $this->baseUrl.$uri;
        $this->setOptions(array(
            CURLOPT_URL           => $uri,
            CURLOPT_POST          => true,
            CURLOPT_RETURNTRANSFER          => 1,
            CURLOPT_CUSTOMREQUEST => 'POST'
        ));

        $this->initPostFields($params, $useEncoding);

        return $this->send($customHeader, $fullResponse);
    }



}