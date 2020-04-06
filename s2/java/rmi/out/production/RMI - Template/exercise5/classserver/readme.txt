Running the ClassFileServer
	
ClassFileServer is an implementation of a mini-webserver that can be used by
an RMI application to load classes over the network.  Typically, RMI uses an
HTTP server to load files from a URL. The ClassFileServer provides the same
basic functionality.

In order to run the class server, compile the java files and run the server,
specifing the port on which you want the server to run and the classpath where
the server locates class files, e.g.:

(Solaris)

   % java examples.classServer.ClassFileServer 2001 /home/ann/classes

(Windows)

   C:> start java examples.classServer.ClassFileServer 2001 /home/ann/classes


When you start up your RMI server, the codebase URL you supply simply needs to
be a URL containing the host and port that the class server runs on (since the
classpath root is specified when you start up the ClassFileServer), e.g.:

(Solaris)

   % java -Djava.rmi.server.codebase=http://zaphod:2001/  MyRMIServer

(Windows)

   C:> start java -Djava.rmi.server.codebase=http://zaphod:2001/ MyRMIServer


You can embed your own class server inside your RMI server application instead
of running one separately. In your server, simply create a ClassFileServer
and supply the appropriate port number and classpath:

    import examples.classServer.ClassFileServer;

    //.....

    new ClassFileServer(port, classpath);

Read the documentation for the ClassFileServer "Main" method for more info on
how to run or embed a ClassFileServer in your application.
