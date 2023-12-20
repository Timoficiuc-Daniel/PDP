﻿using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
namespace Lab4
{
    internal static class CallbackSol
    {
        private static List<string> HOSTS;

        public static int counter;
        public static void Run(List<string> hostnames)
        {
            HOSTS = hostnames;
            counter = HOSTS.Count;
            for (var i = 0; i < HOSTS.Count; i++)
            {
                DoStart(i);
            }
        }

        private static void DoStart(object idObject)
        {
            var id = (int)idObject;
            StartClient(HOSTS[id], id);
        }

        private static void StartClient(string host, int id)
        {
            var hostInfo = Dns.GetHostEntry(host.Split('/')[0]);
            var ipAddress = hostInfo.AddressList[0];
            var remoteEndpoint = new IPEndPoint(ipAddress, HttpUtils.HTTP_PORT);
            var client = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

            var state = new State
            {
                socket = client,
                hostname = host.Split('/')[0],
                endpointPath = host.Contains("/") ? host.Substring(host.IndexOf("/")) : "/",
                remoteEndpoint = remoteEndpoint,
                clientID = id
            };
            state.socket.BeginConnect(state.remoteEndpoint, OnConnect, state);
        }

        private static void OnConnect(IAsyncResult ar)
        {
            var state = (State)ar.AsyncState;
            var clientSocket = state.socket;
            var clientId = state.clientID;
            var hostname = state.hostname;

            clientSocket.EndConnect(ar);
            Console.WriteLine("{0}) Socket connected to {1} ({2})", clientId, hostname, clientSocket.RemoteEndPoint);
            var byteData = Encoding.ASCII.GetBytes(HttpUtils.getRequestString(state.hostname, state.endpointPath));
            state.socket.BeginSend(byteData, 0, byteData.Length, 0, OnSend, state);
        }

        private static void OnSend(IAsyncResult ar)
        {
            var state = (State)ar.AsyncState;
            var clientSocket = state.socket;
            var clientId = state.clientID;
            var bytesSent = clientSocket.EndSend(ar);
            Console.WriteLine("{0}) Sent {1} bytes to server.", clientId, bytesSent);
            state.socket.BeginReceive(state.receiveBuffer, 0, State.BUFFER_SIZE, 0, OnReceive, state);
        }

        private static void OnReceive(IAsyncResult ar)
        {
            var state = (State)ar.AsyncState;
            var clientSocket = state.socket;
            var clientId = state.clientID;

            try
            {
                var bytesRead = clientSocket.EndReceive(ar);
                state.responseContent.Append(Encoding.ASCII.GetString(state.receiveBuffer, 0, bytesRead));
                if (!HttpUtils.responseHeaderFullyObtained(state.responseContent.ToString()))
                {
                    clientSocket.BeginReceive(state.receiveBuffer, 0, State.BUFFER_SIZE, 0, OnReceive, state);
                }
                else
                {
                    var responseBody = HttpUtils.getResponseBody(state.responseContent.ToString());
                    var contentLengthHeaderValue = HttpUtils.getContentLength(state.responseContent.ToString());
                    if (responseBody.Length < contentLengthHeaderValue)
                    {
                        clientSocket.BeginReceive(state.receiveBuffer, 0, State.BUFFER_SIZE, 0, OnReceive, state);
                    }
                    else
                    {
                        Console.WriteLine(
                            "{0}) Response received : expected {1} chars in body, got {2} chars (headers + body)",
                            clientId, contentLengthHeaderValue, state.responseContent.Length);
                        Monitor.Enter(Program.mutex);
                        counter--;
                        Monitor.Pulse(Program.mutex);
                        Monitor.Exit(Program.mutex);
                        clientSocket.Shutdown(SocketShutdown.Both);
                        clientSocket.Close();
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }
    }
}