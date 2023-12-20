using System.Text;

namespace Lab4
{
    class HttpUtils
    {
        public static readonly int HTTP_PORT = 80;

        public static string getResponseBody(string responseContent)
        {
            var responseParts = responseContent.Split(new[] { "\r\n\r\n" }, StringSplitOptions.RemoveEmptyEntries);

            return responseParts.Length > 1 ? responseParts[1] : "";
        }

        public static bool responseHeaderFullyObtained(string responseContent)
        {
            return responseContent.Contains("\r\n\r\n");
        }

        public static int getContentLength(string responseContent)
        {
            var responseLines = responseContent.Split('\r', '\n');

            foreach (var responseLine in responseLines)
            {
                var headerDetails = responseLine.Split(':');

                if (headerDetails[0].CompareTo("Content-Length") == 0)
                {
                    return int.Parse(headerDetails[1]);
                }
            }

            return 0;
        }

        public static string getRequestString(string hostname, string endpoint)
        {
            return "GET " + endpoint + " HTTP/1.1\r\n" +
                   "Host: " + hostname + "\r\n" +
                   "Content-Length: 0\r\n\r\n" +
                   "Content-Type: text/html";
        }

        public static void PrintResponse(State state)
        {
            foreach (var i in state.responseContent.ToString().Split('\r', '\n'))
                Console.WriteLine(i);
        }
        
        public static void interpretResponse(byte[] response) {
            string content = Encoding.ASCII.GetString(response, 0, State.BUFFER_SIZE);
            string[] splitContent = content.Split('\r', '\n');
            string header = "";
            foreach(string line in splitContent) {
                if (line.Length == 0) {
                    continue;
                }

                if (line.StartsWith("Content-Length:")) {
                    header += "Length: " + line.Split(":")[1] + " bytes\n";
                }
                else {
                    header += line + "\n";
                    if (line.StartsWith("Content-Type:")) {
                        break;
                    }
                }
            }
            Console.WriteLine(header + "\n\n\n");
        }
        
        public static byte[] getRequestContent(string host) {
            string requestEndpoint = host.Contains("/") ? host[host.IndexOf("/")..] : "/";
            string getRequestAsString = "GET " + requestEndpoint + " HTTP/1.1\r\nHost: " + host.Split('/')[0] + "\r\nContent-Length: 0\r\n\r\n";
            return Encoding.ASCII.GetBytes(getRequestAsString);
        }
    }
}