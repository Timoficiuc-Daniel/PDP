using System.Net;
using System.Net.Sockets;


namespace Lab4 {
    class AsyncAwaitSol {
        
        private static List<string> HOSTS;
        private static Task[] TASKS;

        public AsyncAwaitSol(List<string> hosts)
        {
            HOSTS = hosts;
            TASKS = new Task[hosts.Count];
        }

        public void Run()
        {
            HOSTS.ForEach(host =>
                {
                    Task t = Task.Run(async () =>
                    {
                        await runObject(host);
                    });
                    TASKS[HOSTS.IndexOf(host)] = t;
                }
                );
            Task.WaitAll(TASKS);
        }

        private async Task runObject( object? host)
        {
            string hostAsString = (string)host;
            int id = HOSTS.IndexOf(hostAsString);
            IPAddress IP = Dns.GetHostEntry(hostAsString.Split('/')[0]).AddressList[0];
            IPEndPoint endPoint = new(IP, 80);
            Socket client = new(IP.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
            byte[] buffer = new byte[State.BUFFER_SIZE];

            await this.connect(client, endPoint);
            Console.WriteLine("{0}) Socket connected to {1} ({2})",id, hostAsString, client.RemoteEndPoint);
            await this.send(client, endPoint, hostAsString);
            await this.receive(client, buffer);
            
           HttpUtils.interpretResponse(buffer);
            client.Shutdown(SocketShutdown.Both);
            client.Close();
        }

        private Task connect(Socket clientSocket, IPEndPoint endPoint)
        {
            TaskCompletionSource promise = new();
            clientSocket.BeginConnect(endPoint, (IAsyncResult ar) =>
            {
                clientSocket.EndConnect(ar);
                promise.SetResult();
            }, null);
        return promise.Task;
        }

        private int endConnectWrapper(Socket clientSocket, IAsyncResult ar) {
            clientSocket.EndConnect(ar);
            return 1;
        }

        private Task send(Socket clientSocket, IPEndPoint endPoint, string host) {
            TaskCompletionSource<int> promise = new();
            byte[] getRequest = HttpUtils.getRequestContent(host);
            clientSocket.BeginSend(getRequest, 0, getRequest.Length, 0, (IAsyncResult ar) => promise.SetResult(clientSocket.EndSend(ar)), null);
            return promise.Task;
        }

        private Task<int> receive(Socket clientSocket, byte[] buffer) {
            TaskCompletionSource<int> promise = new();
            clientSocket.BeginReceive(buffer, 0, State.BUFFER_SIZE, 0, (IAsyncResult ar) => promise.SetResult(clientSocket.EndReceive(ar)), null);
            return promise.Task;
        }
    }
}