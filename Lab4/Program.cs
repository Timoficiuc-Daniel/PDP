

namespace Lab4
{
    class Program
    {
        private static readonly List<string> Urls = new()
        {
            "www.serbanology.com/",
            "www.httpbin.org/",
            "www.columbia.edu/~fdc/sample.html",
            "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/index.html",
            "www.broadridge.com/"
        };

        public static Object mutex = new object(); 
        static void Main()
        {
            Console.WriteLine("1. Callback ");
            Console.WriteLine("2. Task ");
            Console.WriteLine("3. Async Await");
            string choice = Console.ReadLine();
            switch (choice)
            {
                case "1":
                    CallbackSol.Run(Urls);
                    Monitor.Enter(mutex);
                    while (CallbackSol.counter > 0)
                    {   
                        Monitor.Wait(mutex);
                    }
                    Monitor.Exit(mutex);
                    break;
                case "2":
                    TaskSol.Run(Urls);
                    break;
                case "3":
                    AsyncAwaitSol asyncAwaitSol = new AsyncAwaitSol(Urls);
                    asyncAwaitSol.Run();
                    break;
                default:
                    Console.WriteLine("Invalid choice");
                    break;
            }
        }
    }
}