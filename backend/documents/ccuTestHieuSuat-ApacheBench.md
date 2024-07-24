# Apache bench
## Tổng quan
- Là công cụ command-line đánh giá hiệu năng của server trong việc xử lý đồng thời bằng cách
gửi nhiều http request đồng thời đến server và đo các thời gian phản hồi
## Usage:
  Syntax: ab [options] [http[s]://]hostname[:port]/path
## Options:
 * -A auth-username:password: Supply BASIC Authentication credentials.
 * -b windowsize: Specify the TCP send/receive buffer size.
 * -c concurrency: Set the number of simultaneous requests.
 * -C cookie-name=value: Add a Cookie header to the request.
 * -e csv-file: Generate a CSV file with response time data.
 * -E client-certificate-file: Use a client certificate for SSL authentication.
 * -f protocol: Specify the SSL/TLS protocol version.
 * -g gnuplot-file: Create a ‘gnuplot’ or TSV file for analysis.
 * -H custom-header: Append extra headers to the request.
 * -n requests: Specify the total number of requests.
 * -p POST-file: Send POST data from a file.
 * -t timelimit: Set a time limit for the test.
 * -v verbosity: Control the level of output detail. 
## Use Cases:
  Stress Testing: Determine how many simultaneous requests your server can handle before performance degrades.
  Baseline Metrics: Establish typical response times for your server. 
## Example: To test a website, use:
  ab -n 100 -c 10 http://example.com/
  This sends 100 requests with a concurrency of 10.

## Tham khảo
[https://httpd.apache.org/docs/2.4/programs/ab.html](https://httpd.apache.org/docs/2.4/programs/ab.html)
[https://www.datadoghq.com/blog/apachebench/](https://www.datadoghq.com/blog/apachebench/)