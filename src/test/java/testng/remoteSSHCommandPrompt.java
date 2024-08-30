package testng;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class remoteSSHCommandPrompt {
    public static void main(String[] args) throws JSchException {
    String host = "10.250.1.100";
    String user = "Administrator";
    String password = "WS7AuGaD*!";


    String commandExe = "C:\\Softlinx\\ReplixServer\\bin\\rpxcleanupd";


        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        System.out.println("hello world there is connection ");
        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(commandExe);
        System.out.println();
        channel.connect();

        channel.disconnect();
        session.disconnect();

}
}